package com.capg.demo.batch;

import java.util.HashMap;
import java.util.Map;

import javax.batch.api.chunk.ItemProcessor;

import org.springframework.stereotype.Component;

import com.capg.demo.model.User;
@Component
public class Processor implements org.springframework.batch.item.ItemProcessor<User, User>{
	
	private static final Map<String, String> DEPT_NAMES=
			new HashMap<>();
	 public Processor() {
	DEPT_NAMES.put("001", "Technology");
	DEPT_NAMES.put("002", "Accounts");
	DEPT_NAMES.put("003", "Opeartions");
	
	 }

	
	
	@Override
	public User process(User item) throws Exception {
		String deptCode=item.getDept();
		
		String dept=DEPT_NAMES.get(deptCode);
		item.setDept(dept);
		System.out.println(String.format("Converted from [%s] to [%s]", deptCode,dept));
		return item;
	}

}
