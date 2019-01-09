package com.bankapp.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/")
	public String home()
	{
		return "home";
		
	}
	
	@RequestMapping("/addnewaccount")
	public String addnewaccount()
	{
		return "addnewaccount";
	}
	
	@RequestMapping("/addingaccount")
	public String addingnewaccount()
	{
		
		return "addnewaccount";
	}
}
