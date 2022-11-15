package com.springnewexample.capitalone.SpringDataWebDemo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepo empRepo;
	
	public List<Employee> findAll() {
		List<Employee> result = (List<Employee>) empRepo.findAll();

		if (result.size() > 0) {
			return result;
		} else {
			return new ArrayList<Employee>();
		}
	}
}
