package com.springnewexample.capitalone.SpringDataWebDemo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer>{

	//This feature is called custom query
	//Below function defintion will generate a query as given below
	// select * from employee where name=inputname;
	List<Employee> findByName(String inputname);

	List<Employee> findByAge(int age);
	List<Employee> findByAgeGreaterThan(int age);
	List<Employee> findBySalaryLessThan(float salary);
	List<Employee> findByNameAndAge(String inputname, int age);
	
	List<Employee> findByNameOrAge(String inputname, int age);
	
	@Query("SELECT u FROM Employee u WHERE u.age = ?1")
	List<Employee> findEmployeeByAge1(Integer age);

}
