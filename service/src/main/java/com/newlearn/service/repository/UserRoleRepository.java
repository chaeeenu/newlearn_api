package com.newlearn.service.repository;

import com.newlearn.service.entity.UserRoleEntity;
import com.newlearn.service.entity.ids.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleId> {


}
