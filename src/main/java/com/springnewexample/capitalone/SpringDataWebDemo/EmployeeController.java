package com.springnewexample.capitalone.SpringDataWebDemo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springnewexample.capitalone.SpringDataWebDemo.Employee;
import com.springnewexample.capitalone.SpringDataWebDemo.EmployeeRepo;
import com.springnewexample.capitalone.SpringDataWebDemo.exception.ResourceNotFoundException;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeRepo empRepo;
	
	@Autowired
	EmployeeService empService;
	
	@GetMapping("/employees")
	List<Employee> getAllEmployees(){
		return empService.findAll();
	}
	
	@PostMapping("/employees")
	Employee createEmployee(@RequestBody Employee e)
	{
		return empRepo.save(e);
	}
	
	@Cacheable("employees")
	@GetMapping("/employeebyid/{id}")
	ResponseEntity<Employee> getEmployeeByID(@PathVariable int id) 
			throws InterruptedException{
		Thread.sleep(100);
		return ResponseEntity.ok().body(empRepo.findById(id).get());
	}
	
	@PutMapping("/updateemployee/{id}")
	Employee updateEmployeeByID(@PathVariable int id, 
			@RequestBody Employee input){
		Employee e = empRepo.findById(id).get();
		e.setName(input.getName());
		e.setAge(input.getAge());
		e.setSalary(input.getSalary());
		return empRepo.save(e);
	}
	
	@PutMapping("/updateemployee2/{id}")
	ResponseEntity<Employee> updateEmployeeByIDImproved(@PathVariable int id, 
			@RequestBody Employee input) throws ResourceNotFoundException{
		Employee e = empRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
		if(input.getName() != null & input.getName() != "")
			e.setName(input.getName());
		if(input.getAge() != 0)
			e.setAge(input.getAge());
		if(input.getSalary() != 0.0)
			e.setSalary(input.getSalary());
		return ResponseEntity.ok().body(empRepo.save(e));
	}
	
	@DeleteMapping("/deleteemployee/{id}")
	String deleteEmployeeByID(@PathVariable int id){
		empRepo.deleteById(id);
		return "Employee Deleted";
	}
	
	// Very inefficient Implementation
	// Get all employees & filter by name manually
	@GetMapping("/employeebyname/{name}")
	ResponseEntity<List<Employee>> getEmployeeByName(@PathVariable String name)throws ResourceNotFoundException{
		List<Employee> lfilteredEmployee = empRepo.findAll().stream().
				filter(e -> e.getName().equals(name))
				.collect(Collectors.toList());
		if(lfilteredEmployee== null || lfilteredEmployee.size()==0) {
			throw new ResourceNotFoundException("Employee not found for this name :: "+name);
		}
		return ResponseEntity.ok().body(lfilteredEmployee);
	}

	//Efficient implementation with custom query
	@GetMapping("/employeebyname2/{name}")
	List<Employee> getEmployeeByNameCustomQuery(@PathVariable String name){
		List<Employee> lfilteredEmployee = empRepo.findByName(name);
		return lfilteredEmployee;
	}
	

	//Efficient implementation with custom query
	@GetMapping("/agegreaterthan/{age}")
	List<Employee> getEmployeeByAgeCustomQuery(@PathVariable int age){
		List<Employee> lfilteredEmployee = empRepo.findByAgeGreaterThan(age);
		return lfilteredEmployee;
	}

	
	//Efficient implementation with custom query
	@GetMapping("/salarylesstan/{salary}")
	List<Employee> getEmployeeBySalaryCustomQuery(@PathVariable float salary){
		List<Employee> lfilteredEmployee = empRepo.findBySalaryLessThan(salary);
		return lfilteredEmployee;
	}
	
	@GetMapping("/getEmployeeNameAndAge/{inputname}/{age}")
	List<Employee> findByEmployeeNameAndAge(
			@PathVariable String inputname, @PathVariable int age)
	{
		return empRepo.findByNameAndAge(inputname, age);
	}
	
	@GetMapping("/getEmployeeNameOrAge/{inputname}/{age}")
	List<Employee> findEmployeeByNameOrAge(
			@PathVariable String inputname, @PathVariable int age){
		
		return empRepo.findByNameOrAge(inputname, age);
	}
	
	@GetMapping("/getEmployeeByAge/{age}")
	ResponseEntity<List<Employee>> findEmployeeByAge1(@PathVariable int age)throws ResourceNotFoundException{
		List<Employee> lfilteredEmployee = empRepo.findEmployeeByAge1(age);
		if(lfilteredEmployee== null || lfilteredEmployee.size()==0) {
			throw new ResourceNotFoundException("Employee not found for this age :: "+age);
		}
		return ResponseEntity.ok().body(lfilteredEmployee);
	}

}
