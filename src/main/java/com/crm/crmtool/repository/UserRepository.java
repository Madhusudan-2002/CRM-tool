package com.crm.crmtool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crm.crmtool.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}




