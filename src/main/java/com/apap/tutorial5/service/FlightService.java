package com.apap.tutorial5.service;

import com.apap.tutorial5.model.FlightModel;

public interface FlightService {
	void addFlight (FlightModel flight);
	public FlightModel getFlightDetailById(long id);
	public void deleteFlight(FlightModel flight);
	public void deleteFlightDetail(FlightModel flight);
	public void deleteFlightById(long id);
}
