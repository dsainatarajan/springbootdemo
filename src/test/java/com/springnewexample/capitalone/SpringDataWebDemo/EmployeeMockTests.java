package com.springnewexample.capitalone.SpringDataWebDemo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeMockTests {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	EmployeeRepo emprepo;
	
	@Autowired
    EmployeeService empservice;
	

	@Test
	void contextLoads() {
		Assertions.assertThat(emprepo).isNotNull();
	}
	
	@Test
	public void testGetAllEmployees() throws Exception {
		Employee e1 = new Employee(1, "D Sai Natarajan", 30, 10000.5f);
		Employee e2 = new Employee(2, "Shiva", 300, 150000.5f);
		Employee e3 = new Employee(3, "Natarajan", 400, 160000.5f);
		List<Employee> employees = Arrays.asList(e1,e2,e3);

		Mockito.when(emprepo.findAll()).thenReturn(employees);

		mockMvc.perform(get("/employees"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(3)))
				.andExpect(jsonPath("$[0].name", Matchers.is("D Sai Natarajan")));
	}
	
	@Test
	public void testCreateEmployee() throws Exception {
		
		Employee e1 = new Employee(1, "D Sai Natarajan", 30, 10000.5f);
		Mockito.when(emprepo.save(Mockito.any(Employee.class))).thenReturn(e1);
		mockMvc.perform(post("/employees").content(asJsonString(e1)).contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
				  .andExpect(status().isOk())
				  .andExpect(jsonPath("$.empid", Matchers.equalTo(1)))
				  .andExpect(jsonPath("$.name", Matchers.equalTo("D Sai Natarajan")))
				  .andExpect(jsonPath("$.age", Matchers.equalTo(30)))
				  .andExpect(jsonPath("$.salary", Matchers.equalTo(10000.5)));
	
	}
	
	@Test
	public void testUpdateEmployee() throws Exception {
		
		Employee e1 = new Employee(1, "D Sai Natarajan", 30, 10000.5f);		
		Optional<Employee> oe = Optional.ofNullable(e1);
		Mockito.when(emprepo.findById(1)).thenReturn(oe);
		Employee e2 = new Employee(1, "D Sai Natarajan", 33, 50000.5f);		
		Mockito.when(emprepo.save(Mockito.any(Employee.class))).thenReturn(e2);
		
		mockMvc.perform(put("/updateemployee2/1").content(asJsonString(e2)).contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
				  .andExpect(status().isOk())
				  .andExpect(jsonPath("$.empid", Matchers.equalTo(1)))
				  .andExpect(jsonPath("$.name", Matchers.equalTo("D Sai Natarajan")))
				  .andExpect(jsonPath("$.age", Matchers.equalTo(33)))
				  .andExpect(jsonPath("$.salary", Matchers.equalTo(50000.5)));
	
	}
	
	
	@Test
	public void testGetEmployeeByName() throws Exception {
		//Employee e1 = new Employee(1, "D Sai Natarajan", 30, 10000.5f);
		Employee e2 = new Employee(2, "Shiva", 300, 150000.5f);
		Employee e3 = new Employee(3, "Shiva", 400, 160000.5f);
		List<Employee> employees = Arrays.asList(e2,e3);

		Mockito.when(emprepo.findByName("Shiva")).thenReturn(employees);

		mockMvc.perform(get("/employeebyname2/Shiva"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(2)))
				.andExpect(jsonPath("$[0].name", Matchers.is("Shiva")))
				.andExpect(jsonPath("$[1].name", Matchers.is("Shiva")));
	}
	public static String asJsonString(final Object obj) {
	    try {
	      return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	  }
}
