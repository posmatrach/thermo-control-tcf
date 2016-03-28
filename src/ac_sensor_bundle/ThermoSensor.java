package ac_sensor_bundle;

import services.ThermoSensorIF;

public class ThermoSensor implements ThermoSensorIF {
	ThermoSensor() {
	}

	@Override
	public int getTemp() {
		return (int) this.measuredTemperature;
	}

	// For simulation
	volatile double measuredTemperature = 10.0;

	void modifyTemp(double deltaTemp) {
		this.measuredTemperature += deltaTemp;
		System.out.println("measured temperature is " + this.measuredTemperature);
	}
}
