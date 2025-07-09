CREATE TABLE `users` (
  `id` int(5) PRIMARY KEY AUTO_INCREMENT COMMENT '유저 내부식별자',
  `email` varchar(100) UNIQUE NOT NULL COMMENT '계정 email(ID)',
  `pwd` varchar(255) NOT NULL COMMENT '계정 비밀번호',
  `name` varchar(100) NOT NULL COMMENT '유저명',
  `mobile_phone_num` varchar(11) NOT NULL COMMENT '휴대전화번호',
  `status` varchar(20) NOT NULL COMMENT '계정 상태',
  `profile_image_url` varchar(255) COMMENT '프로필 이미지 URL',
  `reg_ip` varchar(45) NOT NULL COMMENT '등록자IP',
  `reg_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '등록일시',
  `mod_ip` varchar(45) NOT NULL COMMENT '수정자IP',
  `mod_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '수정일시'
);

CREATE TABLE `user_change_history` (
  `id` int(5) PRIMARY KEY AUTO_INCREMENT COMMENT '유저변경이력 내부식별자',
  `user_id` int(5) NOT NULL COMMENT '유저ID',
  `filed_name` varchar(255) NOT NULL COMMENT '변경 필드명',
  `before_value` varchar(255) NOT NULL COMMENT '변경 전 데이터',
  `after_value` varchar(255) NOT NULL COMMENT '변경 후 데이터',
  `reg_id` int(5) NOT NULL COMMENT '등록자ID',
  `reg_ip` varchar(45) NOT NULL COMMENT '등록자IP',
  `reg_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '등록일시'
);

CREATE TABLE `user_roles` (
  `user_id` int(5) COMMENT '유저ID',
  `role_id` varchar(20) COMMENT '권한ID',
  PRIMARY KEY (`user_id`, `role_id`)
);

CREATE TABLE `code` (
  `cd_seq` int(5) PRIMARY KEY AUTO_INCREMENT COMMENT '공통코드 내부식별자',
  `parent_seq` int(5) COMMENT '공통코드 부모식별자',
  `ord` int(5) COMMENT '정렬',
  `left_val` int(5) COMMENT '왼쪽 키값',
  `right_val` int(5) COMMENT '오른쪽 키값',
  `depth` int(5) COMMENT '뎁스',
  `cd_id` varchar(20) COMMENT '그룹코드ID',
  `cd` varchar(20) COMMENT '코드ID',
  `cd_nm` varchar(255) COMMENT '코드명',
  `en_cd_nm` varchar(255) COMMENT '영문코드명',
  `desc` varchar(4000) COMMENT '코드설명',
  `reg_id` int(5) NOT NULL COMMENT '등록자ID',
  `reg_ip` varchar(45) NOT NULL COMMENT '등록자IP',
  `reg_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '등록일시',
  `mod_id` int(5) NOT NULL COMMENT '수정자ID',
  `mod_ip` varchar(45) NOT NULL COMMENT '수정자IP',
  `mod_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '수정일시'
);

CREATE TABLE `course` (
  `id` int(5) PRIMARY KEY AUTO_INCREMENT COMMENT '강의 내부식별자',
  `title` varchar(255) NOT NULL COMMENT '강의명',
  `desc` varchar(4000) COMMENT '강의설명',
  `category_id` varchar(20) COMMENT '카테고리코드',
  `difficulty` varchar(20) COMMENT '난이도코드',
  `teacher_id` int(5) NOT NULL COMMENT '강사ID',
  `thumbnail_url` text COMMENT '썸네일 URL',
  `status` varchar(20) NOT NULL COMMENT '강의 상태',
  `reg_id` int(5) NOT NULL COMMENT '등록자ID',
  `reg_ip` varchar(45) NOT NULL COMMENT '등록자IP',
  `reg_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '등록일시',
  `mod_id` int(5) NOT NULL COMMENT '수정자ID',
  `mod_ip` varchar(45) NOT NULL COMMENT '수정자IP',
  `mod_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '수정일시'
);

CREATE TABLE `enrollment` (
  `id` int(5) PRIMARY KEY AUTO_INCREMENT COMMENT '수강신청 내부식별자',
  `user_id` int(5) NOT NULL COMMENT '유저ID',
  `course_id` int(5) NOT NULL COMMENT '강의ID',
  `status` varchar(20) NOT NULL COMMENT '수강신청 상태코드',
  `reg_id` int(5) NOT NULL COMMENT '등록자ID',
  `reg_ip` varchar(45) NOT NULL COMMENT '등록자IP',
  `reg_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '등록일시',
  `mod_id` int(5) NOT NULL COMMENT '수정자ID',
  `mod_ip` varchar(45) NOT NULL COMMENT '수정자IP',
  `mod_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '수정일시'
);

CREATE TABLE `lesson` (
  `id` int(5) PRIMARY KEY AUTO_INCREMENT COMMENT '강의 회차 내부식별자',
  `course_id` int(5) NOT NULL COMMENT '강의ID',
  `title` varchar(255) NOT NULL COMMENT '강의 회차명',
  `content` text COMMENT '강의 회차 콘텐츠',
  `video_url` text COMMENT '강의 동영상 URL',
  `lesson_order` int(5) COMMENT '강의 회차 순번',
  `lesson_time` int(11) COMMENT '해당회차 학습 소요시간',
  `visible` boolean DEFAULT true COMMENT '공개여부',
  `reg_id` int(5) NOT NULL COMMENT '등록자ID',
  `reg_ip` varchar(45) NOT NULL COMMENT '등록자IP',
  `reg_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '등록일시',
  `mod_id` int(5) NOT NULL COMMENT '수정자ID',
  `mod_ip` varchar(45) NOT NULL COMMENT '수정자IP',
  `mod_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '수정일시'
);

CREATE TABLE `progress` (
  `id` int(5) PRIMARY KEY AUTO_INCREMENT COMMENT '수강진도 내부식별자',
  `user_id` int(5) NOT NULL COMMENT '유저ID',
  `course_id` int(5) NOT NULL COMMENT '강의ID',
  `lesson_id` int(5) NOT NULL COMMENT '강의회차ID',
  `progress_time` int(11) COMMENT '수강진행시간',
  `reg_id` int(5) NOT NULL COMMENT '등록자ID',
  `reg_ip` varchar(45) NOT NULL COMMENT '등록자IP',
  `reg_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '등록일시',
  `mod_id` int(5) NOT NULL COMMENT '수정자ID',
  `mod_ip` varchar(45) NOT NULL COMMENT '수정자IP',
  `mod_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '수정일시'
);

CREATE TABLE `assignment` (
  `id` int(5) PRIMARY KEY AUTO_INCREMENT COMMENT '과제정보 내부식별자',
  `course_id` int(5) NOT NULL COMMENT '강의ID',
  `lesson_id` int(5) NOT NULL COMMENT '강의회차ID',
  `title` varchar(255) NOT NULL COMMENT '과제명',
  `desc` varchar(4000) COMMENT '과제설명',
  `due_date` timestamp COMMENT '과제기한',
  `reg_id` int(5) NOT NULL COMMENT '등록자ID',
  `reg_ip` varchar(45) NOT NULL COMMENT '등록자IP',
  `reg_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '등록일시',
  `mod_id` int(5) NOT NULL COMMENT '수정자ID',
  `mod_ip` varchar(45) NOT NULL COMMENT '수정자IP',
  `mod_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '수정일시'
);

CREATE TABLE `submission` (
  `id` int(5) PRIMARY KEY AUTO_INCREMENT COMMENT '과제제출 내부식별자',
  `user_id` int(5) NOT NULL COMMENT '유저ID',
  `assignment_id` int(5) NOT NULL COMMENT '과제ID',
  `answer` text NOT NULL COMMENT '과제 제출 답변내용',
  `state` varchar(20) NOT NULL COMMENT '과제제출상태코드',
  `reg_id` int(5) NOT NULL COMMENT '등록자ID',
  `reg_ip` varchar(45) NOT NULL COMMENT '등록자IP',
  `reg_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '등록일시',
  `mod_id` int(5) NOT NULL COMMENT '수정자ID',
  `mod_ip` varchar(45) NOT NULL COMMENT '수정자IP',
  `mod_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '수정일시'
);

CREATE TABLE `evaluation` (
  `id` int(5) PRIMARY KEY AUTO_INCREMENT COMMENT '과제평가 내부식별자',
  `submission_id` int(5) NOT NULL COMMENT '과제제출ID',
  `evaluator_id` int(5) NOT NULL COMMENT '평가자ID',
  `score` varchar(20) NOT NULL COMMENT '평가점수',
  `feedback` varchar(4000) COMMENT '피드백내용',
  `reg_id` int(5) NOT NULL COMMENT '등록자ID',
  `reg_ip` varchar(45) NOT NULL COMMENT '등록자IP',
  `reg_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '등록일시',
  `mod_id` int(5) NOT NULL COMMENT '수정자ID',
  `mod_ip` varchar(45) NOT NULL COMMENT '수정자IP',
  `mod_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '수정일시'
);

CREATE TABLE `qna` (
  `id` int(5) PRIMARY KEY AUTO_INCREMENT COMMENT 'Q&A 내부식별자',
  `q_user_id` int(5) NOT NULL COMMENT '질문자ID',
  `a_user_id` int(5) DEFAULT null COMMENT '답변자ID',
  `title` varchar(255) NOT NULL COMMENT '질문 타이틀',
  `question` text NOT NULL COMMENT '질문내용',
  `answer` text NOT NULL COMMENT '답변내용',
  `state` varchar(20) NOT NULL COMMENT 'Q&A 상태코드',
  `reg_id` int(5) NOT NULL COMMENT '등록자ID',
  `reg_ip` varchar(45) NOT NULL COMMENT '등록자IP',
  `reg_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '등록일시',
  `mod_id` int(5) NOT NULL COMMENT '수정자ID',
  `mod_ip` varchar(45) NOT NULL COMMENT '수정자IP',
  `mod_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '수정일시'
);

CREATE TABLE `notice` (
  `id` int(5) PRIMARY KEY AUTO_INCREMENT COMMENT '공지사항 내부식별자',
  `title` varchar(255) NOT NULL COMMENT '제목',
  `content` text NOT NULL COMMENT '내용',
  `reg_id` int(5) NOT NULL COMMENT '등록자ID',
  `reg_ip` varchar(45) NOT NULL COMMENT '등록자IP',
  `reg_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '등록일시',
  `mod_id` int(5) NOT NULL COMMENT '수정자ID',
  `mod_ip` varchar(45) NOT NULL COMMENT '수정자IP',
  `mod_dtm` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '수정일시'
);

CREATE TABLE `learning_log` (
  `id` int(5) PRIMARY KEY AUTO_INCREMENT COMMENT '학습로그 내부식별자',
  `user_id` int(5) NOT NULL COMMENT '유저ID',
  `course_id` int(5) NOT NULL COMMENT '강의ID',
  `lesson_id` int(5) NOT NULL COMMENT '강의회차ID',
  `action_type` varchar(20) NOT NULL COMMENT '행동유형(start,complete,pause,resume,etc)',
  `action_time` timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP) COMMENT '해당 액션의 지속 시간'
);

CREATE TABLE `user_preference` (
  `user_id` int(5) PRIMARY KEY AUTO_INCREMENT COMMENT '유저ID',
  `prefer_style` varchar(20) NOT NULL COMMENT '유저 선호 학습스타일',
  `daily_study_time` int(5) COMMENT '하루 학습시간',
  `target_score` int(3) COMMENT '목표점수',
  `interest_tag` varchar(255) COMMENT '관심태그'
);

CREATE UNIQUE INDEX `code_index_0` ON `code` (`cd_id`, `cd`);

CREATE UNIQUE INDEX `enrollment_index_1` ON `enrollment` (`user_id`, `course_id`);

ALTER TABLE `users` COMMENT = '유저 테이블';

ALTER TABLE `user_change_history` COMMENT = '유저변경이력 테이블';

ALTER TABLE `user_roles` COMMENT = '유저 권한관리 테이블';

ALTER TABLE `code` COMMENT = '공통코드 테이블';

ALTER TABLE `course` COMMENT = '강의 테이블';

ALTER TABLE `enrollment` COMMENT = '수강신청 테이블';

ALTER TABLE `lesson` COMMENT = '강의 회차 테이블';

ALTER TABLE `progress` COMMENT = '수강진도 테이블';

ALTER TABLE `assignment` COMMENT = '과제정보 테이블';

ALTER TABLE `submission` COMMENT = '과제제출정보 테이블';

ALTER TABLE `evaluation` COMMENT = '과제평가 테이블';

ALTER TABLE `qna` COMMENT = 'Q&A 테이블';

ALTER TABLE `notice` COMMENT = '공지사항 테이블';

ALTER TABLE `learning_log` COMMENT = '학습로그 테이블';

ALTER TABLE `user_preference` COMMENT = '사용자 학습성향 수집테이블';

ALTER TABLE `user_change_history` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `user_roles` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `course` ADD FOREIGN KEY (`teacher_id`) REFERENCES `users` (`id`);

ALTER TABLE `enrollment` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `enrollment` ADD FOREIGN KEY (`course_id`) REFERENCES `course` (`id`);

ALTER TABLE `lesson` ADD FOREIGN KEY (`course_id`) REFERENCES `course` (`id`);

ALTER TABLE `progress` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `progress` ADD FOREIGN KEY (`course_id`) REFERENCES `course` (`id`);

ALTER TABLE `progress` ADD FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`id`);

ALTER TABLE `assignment` ADD FOREIGN KEY (`course_id`) REFERENCES `course` (`id`);

ALTER TABLE `assignment` ADD FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`id`);

ALTER TABLE `submission` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `submission` ADD FOREIGN KEY (`assignment_id`) REFERENCES `assignment` (`id`);

ALTER TABLE `evaluation` ADD FOREIGN KEY (`submission_id`) REFERENCES `submission` (`id`);

ALTER TABLE `evaluation` ADD FOREIGN KEY (`evaluator_id`) REFERENCES `users` (`id`);

ALTER TABLE `qna` ADD FOREIGN KEY (`q_user_id`) REFERENCES `users` (`id`);

ALTER TABLE `qna` ADD FOREIGN KEY (`a_user_id`) REFERENCES `users` (`id`);

ALTER TABLE `learning_log` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `learning_log` ADD FOREIGN KEY (`course_id`) REFERENCES `course` (`id`);

ALTER TABLE `learning_log` ADD FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`id`);

ALTER TABLE `user_preference` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
