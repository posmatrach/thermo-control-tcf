package services;

import tcf.framework.TCFService;

public interface ThermoSensorIF extends TCFService {
	int getTemp();
}