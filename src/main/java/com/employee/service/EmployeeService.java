package com.employee.service;

import java.util.List;

import com.employee.model.Employee;

public interface EmployeeService {
	
	public Employee saveEmployee(Employee employee);
	
	public Employee getEmployeeById(int id);
	
	public Boolean deleteEmployee(int id);
	
	public List<Employee> findAllEmployee();

}
