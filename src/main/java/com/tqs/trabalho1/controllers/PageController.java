package com.tqs.trabalho1.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.tqs.trabalho1.model.AirStatus;
import com.tqs.trabalho1.services.AirService;

@Controller
@RequestMapping("/index")
public class PageController {
	
	@Autowired
	private AirService air_service;
	
	 @GetMapping("/")
	 public String index(Model model) {
		 
	     model.addAttribute("status", new AirStatus());

	     return "index";
	 }
	 
	 @GetMapping("/getAirStatus")
	 public ModelAndView getStatus(@ModelAttribute AirStatus status) throws IOException, UnirestException {
		 
		 
		 status = air_service.getAirStatus(status.getCountry(), status.getState(), status.getCity());
		 ModelAndView modelAndView = new ModelAndView();
		 
		 if (status != null){	 
			 modelAndView.setViewName("result");
			 modelAndView.addObject("status", status);
			 modelAndView.addObject("date", status.getTs().toString().replace("T", " "));
		 }
		 else {
			 modelAndView.setViewName("error");
			 modelAndView.addObject("message", "Location specified not found.");
		 }
		 
		 return modelAndView;    
	 }

}
