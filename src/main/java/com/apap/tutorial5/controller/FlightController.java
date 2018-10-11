package com.apap.tutorial5.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;

@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilot(pilot);
		
		model.addAttribute("flight", flight);
		model.addAttribute("title", "Add Flight");
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"save"})
	private String addFligthSubmit(@ModelAttribute PilotModel pilot, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber());
		for(FlightModel flight : pilot.getPilotFlight()) {
			flight.setPilot(archive);
			flightService.addFlight(flight);
		}
		model.addAttribute("title", "APAP");
		return "add";
	}
	
	@RequestMapping("/flight/view/{id}")
	public String viewPath(@PathVariable String id, Model model) {
		FlightModel flight = flightService.getFlightDetailById(Long.parseLong(id));
		model.addAttribute("flight", flight);
		model.addAttribute("title","View Flight");
		return "view-flight";
	}
	
	@RequestMapping(value="/flight/delete", method = RequestMethod.POST)
	private String deleteFlight(@ModelAttribute PilotModel pilot, Model model) {
		for(FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlightById(flight.getId());
		}
		model.addAttribute("title","APAP");
		return "delete";
	}
	
	@RequestMapping(value="/flight/delete/{id}")
	private String deleteFlightDetail(@PathVariable String id, Model model) {
		FlightModel flight = flightService.getFlightDetailById(Long.parseLong(id));
		flightService.deleteFlight(flight);
		model.addAttribute("title","APAP");
		return "delete";
	}
	
	@RequestMapping(value="/flight/update/{licenseNumber}/{id}")
	private String update(@PathVariable("id") String id, @PathVariable("licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = flightService.getFlightDetailById(Long.parseLong(id));
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("flight",flight);
		model.addAttribute("pilot",pilot);
		model.addAttribute("title","Update Flight");
		return "update-flight";
	}
	
	@RequestMapping(value="/flight/update",method=RequestMethod.POST)
	private String updateFlight(@ModelAttribute FlightModel flight, Model model) {
		flightService.addFlight(flight);
		model.addAttribute("title","APAP");
		return "add";
	}
	
	@RequestMapping(value="/flight/add/{licenseNumber}", params={"addRow"}, method = RequestMethod.POST)
	public String addRow(PilotModel pilot, BindingResult bindingResult, Model model) {
		if (pilot.getPilotFlight() ==  null) {
			pilot.setPilotFlight(new ArrayList<FlightModel>());
		}
		pilot.getPilotFlight().add(new FlightModel());
	    model.addAttribute("pilot", pilot);
	    return "addFlight";
	}
	
	@RequestMapping(value="/flight/add/{licenseNumber}", params={"removeRow"}, method = RequestMethod.POST)
	public String removeRow(PilotModel pilot, BindingResult bindingResult, HttpServletRequest req, Model model) {
	   Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
	   pilot.getPilotFlight().remove(rowId.intValue());
	   model.addAttribute("pilot", pilot);
	   return "addFlight";
	}
	
}