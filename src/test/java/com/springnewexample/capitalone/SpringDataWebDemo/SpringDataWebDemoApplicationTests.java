package com.springnewexample.capitalone.SpringDataWebDemo;


import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest
class SpringDataWebDemoApplicationTests {

	@Autowired
	EmployeeController empController;
	
	@Autowired
	EmployeeRepo emprepo;
	
	@Autowired
	EmployeeService empService;

	@Test
	void contextLoads() {
		Assertions.assertThat(empController).isNotNull();
		Assertions.assertThat(emprepo).isNotNull();
	}
	
/*	@Test
	public void testEmployeeCreate() {
		String url = "http://localhost:8080/employees";
		URI target_uri = UriComponentsBuilder.
				fromUriString(url).build().encode().toUri();
		Employee e = new Employee(1, "D Sai Natarajan", 30, 10000.5f);
		ResponseEntity<Employee> result = new RestTemplate().
				postForEntity(target_uri, e, Employee.class);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		Employee reultEmp = result.getBody();
		assertEquals(1, reultEmp.getEmpid());
		assertEquals("D Sai Natarajan", reultEmp.getName());
		assertEquals(30, reultEmp.getAge());
		assertEquals(10000.5f, reultEmp.getSalary());
		
		assertNotNull( emprepo.findById(1));
	}
*/
	@Test
	public void testCreateEmployee() {
		Employee e = new Employee(1, "D Sai Natarajan", 30, 10000.5f);
		Employee reultEmp = empController.createEmployee(e);
		assertNotNull(reultEmp);
		assertEquals(1, reultEmp.getEmpid());
		assertEquals("D Sai Natarajan", reultEmp.getName());
		assertEquals(30, reultEmp.getAge());
		assertEquals(10000.5f, reultEmp.getSalary());
		
		assertNotNull( emprepo.findById(1));
	}
	
	@Test
	public void testUpdateEmployee() {
		emprepo.deleteAll();
		assertEquals(0, emprepo.findAll().size());
		
		Employee e = new Employee(1, "D Sai Natarajan", 30, 10000.5f);
		Employee reultEmp = empController.createEmployee(e);
		assertNotNull(reultEmp);
		assertEquals(1, reultEmp.getEmpid());
		assertEquals("D Sai Natarajan", reultEmp.getName());
		assertEquals(30, reultEmp.getAge());
		assertEquals(10000.5f, reultEmp.getSalary());
		
		Employee e1 = new Employee(1, "Shiva", 300, 1000000.5f);
		Employee updatedEmployee = empController.updateEmployeeByID(1, e1);
		//Validate the return value
		assertNotNull(updatedEmployee);
		assertEquals(1, updatedEmployee.getEmpid());
		assertEquals("Shiva", updatedEmployee.getName());
		assertEquals(300, updatedEmployee.getAge());
		assertEquals(1000000.5f, updatedEmployee.getSalary());
		
		//Validate the Database value
		assertNotNull( emprepo.findById(1));
		Employee currentEmployee = emprepo.findById(1).get();
		assertEquals(1, currentEmployee.getEmpid());
		assertEquals("Shiva", currentEmployee.getName());
		assertEquals(300, currentEmployee.getAge());
		assertEquals(1000000.5f, currentEmployee.getSalary());
	}
	
	@Test
	public void testGetEmployeeByID() throws InterruptedException {
		emprepo.deleteAll();
		assertEquals(0, emprepo.findAll().size());
		
		Employee e = new Employee(1, "D Sai Natarajan", 30, 10000.5f);
		empController.createEmployee(e);

		ResponseEntity<Employee> updatedEmployeeResponse = empController.getEmployeeByID(1);
		assertEquals(HttpStatus.OK, updatedEmployeeResponse.getStatusCode());
		//Validate the return value
		Employee updatedEmployee= updatedEmployeeResponse.getBody();
		assertNotNull(updatedEmployee);
		assertEquals(1, updatedEmployee.getEmpid());
		assertEquals("D Sai Natarajan", updatedEmployee.getName());
		assertEquals(30, updatedEmployee.getAge());
		assertEquals(10000.5f, updatedEmployee.getSalary());			
	}
	
	@Test
	public void testDeleteEmployee() {
		emprepo.deleteAll();
		assertEquals(0, emprepo.findAll().size());
		
		Employee e = new Employee(1, "D Sai Natarajan", 30, 10000.5f);
		empController.createEmployee(e);
		String result = empController.deleteEmployeeByID(1);
		assertEquals("Employee Deleted", result);
		
		assertEquals(Optional.empty(), emprepo.findById(1));
	}
	
}
