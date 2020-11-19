package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.UserInfo;

public interface UserRepo extends JpaRepository<UserInfo, Integer>
{
	
}
