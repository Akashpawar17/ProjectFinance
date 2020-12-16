package com.capg.demo.batch;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capg.demo.model.User;
import com.capg.demo.repo.UserRepository;

@Component
public class DBWriter implements ItemWriter<User> {

	
	@Autowired
	private UserRepository userRepositor;
	
	@Override
	public void write(List<? extends User> users) throws Exception {
		
		
		System.out.println("Data Saved for Users:"+users);
		userRepositor.saveAll(users);
	}

	

}
