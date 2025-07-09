package com.newlearn.client.config;

import com.newlearn.common.util.EncryptionUtil;
import com.newlearn.service.annotation.CustomEncryption;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Aspect
@Component
public class AspectConfig {

    // 암호화 대상 필드 목록 캐시 : 리플렉션 성능 최적화를 위한 필수 캐시 구조
    private static final Map<Class<?>, List<Field>> encryptedFieldCache = new ConcurrentHashMap<>();

    /**
     * 컨트롤러 메서드의 파라미터 중 @CustomEncryption 어노테이션이 붙은 필드를 암호화하는 Aspect 메서드
     * 지정된 패키지 내 메서드 호출을 가로채고, 어노테이션이 붙은 필드를 암호화합니다.
     *
     * @param pjp 가로챈 메서드 호출을 나타내는 ProceedingJoinPoint
     * @return 가로챈 메서드 실행 결과
     * @throws Throwable 메서드 실행 중 오류가 발생한 경우
     */
    @Around("execution(* com.newlearn.client..*.*(..))")
    public Object encryptAnnotatedFields(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();

        for(Object arg : args) {
            if(arg == null) continue;

            List<Field> targetFields = getEncryptedFields(arg.getClass());

            for(Field field : targetFields) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(arg);

                    if(value instanceof String str && !str.isBlank()) {
                        String encrypted = EncryptionUtil.encrypt(str);
                        field.set(arg, encrypted);
                    }
                } catch (IllegalAccessException e) {
                    log.error("Failed to access field: {}", field.getName(), e);
                }
            }
        }

        return pjp.proceed();
    }


    /**
     * 암호화 대상 필드를 찾고, 캐시에 저장하거나 불러온다
     *
     * @param clazz 컨트롤러 파라미터 객체
     * @return @CustomEncryption이 붙은 필드 리스트
     */
    private List<Field> getEncryptedFields(Class<?> clazz) {
        return encryptedFieldCache.computeIfAbsent(clazz, clz -> {
            List<Field> list = new ArrayList<>();
            for(Field field : clz.getDeclaredFields()) {
                if(field.isAnnotationPresent(CustomEncryption.class)) {
                    field.setAccessible(true);
                    list.add(field);
                }
            }
            return list;
        });
    }
}
