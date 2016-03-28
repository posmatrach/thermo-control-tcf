package ac_sensor_bundle;

import java.util.Random;

import accontrolsys.ACMode;
import services.ACControlIF;

public class AirConditioner implements ACControlIF {
	AirConditioner() {
	}

	@Override
	public void switchTo(ACMode acm) {
		this.state = acm;
	}

	// For simulation
	volatile ACMode state = ACMode.OFF;
	ThermoSensor sensor;

	void setSensor(ThermoSensor s) {
		this.sensor = s;
	}

	final static double OFFSET = 0.05;
	final static Random r = new Random();

	void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(100);
					} catch (final InterruptedException e) {
					}
					switch (AirConditioner.this.state) {
					case HEATING:
						AirConditioner.this.sensor.modifyTemp(OFFSET);
						break;
					case COOLING:
						AirConditioner.this.sensor.modifyTemp(-OFFSET);
						break;
					case OFF:
						AirConditioner.this.sensor.modifyTemp((2 - r.nextInt(5)) * OFFSET);
						break;
					}
				}
			}
		}).start();
	}
}
