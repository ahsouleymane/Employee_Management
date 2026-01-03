package com.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.employee.model.Employee;
import com.employee.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute Employee employee) {
		Employee save = employeeService.saveEmployee(employee);
		
		if(!ObjectUtils.isEmpty(save)) {
			System.out.println("Employee enregistré avec succès !");
		} else {
			System.out.println("Echec enregistrement !");
		}
		
		return "redirect:/listEmployee";
	}
	
	@GetMapping("/listEmployee")
	public String listEmployee(Model m) {
		List<Employee> employee = employeeService.findAllEmployee();
		m.addAttribute("employee", employee);
		return "list_employee.html";
	}
	
	@PostMapping("/updateEmployee")
	public String updateEmployee(@ModelAttribute Employee employee, int id) {
		Employee updateEmployee = employeeService.getEmployeeById(employee.getId());
		
		if (!ObjectUtils.isEmpty(employee)) {
			updateEmployee.setNom(employee.getNom());
			updateEmployee.setPrenom(employee.getPrenom());
			updateEmployee.setAge(employee.getAge());
			updateEmployee.setPoste(employee.getPoste());
		}
		
		employeeService.saveEmployee(updateEmployee);
		
		return "redirect:/findEmployee/" + employee.getId();
	}
	
	
	  @GetMapping("/") 
	  public String index() { 
		  return "index.html"; 
	  }
	 
	
	@GetMapping("/addEmployee")
	public String addEmployee() {
		return "add_employee.html";
	}
	
	@GetMapping("/findEmployee/{id}")
	public String findEmployee(@PathVariable int id,  Model m) {
		Employee employee = employeeService.getEmployeeById(id);
		m.addAttribute("employee", employee);
		return "/update_employee";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable int id, HttpSession session) {
		
		Boolean deleteEmploye = employeeService.deleteEmployee(id);
		
		if (deleteEmploye) {
			session.setAttribute("successMsg", "Employee supprimé avec succès !");
		} else {
			session.setAttribute("errorMsg", "Erreur sur le serveur");
		}
		
		return "redirect:/listEmployee";
	}

}
