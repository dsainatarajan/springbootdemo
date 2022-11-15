package com.springnewexample.capitalone.SpringDataWebDemo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee {

	@Id
	int empid;
	@Column(length=40)
	String name;
	int age;
	float salary;
	Employee() {		
	}
	public Employee(int empid, String name, int age, float salary) {
		super();
		this.empid = empid;
		this.name = name;
		this.age = age;
		this.salary = salary;
	}
	public int getEmpid() {
		return empid;
	}
	public void setEmpid(int empid) {
		this.empid = empid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	
}
