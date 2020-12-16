package com.capg.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capg.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
