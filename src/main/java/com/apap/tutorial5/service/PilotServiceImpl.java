package com.apap.tutorial5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.repository.FlightDB;
import com.apap.tutorial5.repository.PilotDB;

@Service
@Transactional
public class PilotServiceImpl implements PilotService {
	@Autowired
	private PilotDB pilotDb;
	
	@Override
	public PilotModel getPilotDetailByLicenseNumber (String licenseNumber) {
		return pilotDb.findByLicenseNumber(licenseNumber);
	}
	
	@Override
	public void addPilot (PilotModel pilot) {
		pilotDb.save(pilot);
	}
	
	@Autowired
	private FlightDB flightDb;
	
	@Override
	public void deletePilot(PilotModel pilot) {
		// TODO Auto-generated method stub
		flightDb.deleteAll(pilot.getPilotFlight());
		pilotDb.delete(pilot);
	}
}