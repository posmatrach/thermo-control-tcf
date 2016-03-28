package services;

import tcf.framework.TCFService;

public interface ThermostatModifIF extends TCFService {
	void modifyDesiredTemp(int d);
}