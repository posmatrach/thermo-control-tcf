package thermostat;

import accontrolsys.ACMode;
import services.ACControlIF;
import services.ControlPanelNotifIF;
import services.RegisterIF;
import services.ThermoSensorIF;
import services.ThermostatModifIF;

public class Thermostat implements Runnable, ThermostatModifIF, RegisterIF {
	ACControlIF aircon;
	ThermoSensorIF sensor;
	volatile ControlPanelNotifIF guicp;

	volatile int desiredTemp;
	ACMode currentACM;

	volatile boolean doUpdate = false;

	Thermostat(ACControlIF ac, ThermoSensorIF ts) {
		this.aircon = ac;
		this.sensor = ts;
		this.desiredTemp = 20;
		this.currentACM = ACMode.OFF;
		new Thread(this).start();
	}

	@Override
	public void registerGUI(ControlPanelNotifIF cp) {
		this.guicp = cp;
		this.doUpdate = true;
	}

	@Override
	public void run() {
		int currentTemp = 0;
		while (true) {
			final int newTemp = this.sensor.getTemp();
			if (newTemp != currentTemp) {
				currentTemp = newTemp;
				this.doUpdate = true;
			}
			if ((this.desiredTemp < currentTemp) && (this.currentACM != ACMode.COOLING)) {
				this.aircon.switchTo(ACMode.COOLING);
				this.currentACM = ACMode.COOLING;
				this.doUpdate = true;
			} else if ((this.desiredTemp > currentTemp) && (this.currentACM != ACMode.HEATING)) {
				this.aircon.switchTo(ACMode.HEATING);
				this.currentACM = ACMode.HEATING;
				this.doUpdate = true;
			} else if ((this.desiredTemp == currentTemp) && (this.currentACM != ACMode.OFF)) {
				this.aircon.switchTo(ACMode.OFF);
				this.currentACM = ACMode.OFF;
				this.doUpdate = true;
			}
			if (this.doUpdate && (this.guicp != null)) {
				this.guicp.notify(currentTemp, this.desiredTemp, this.currentACM);
			}
			this.doUpdate = false;
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
			}
		}
	}

	@Override
	public void modifyDesiredTemp(int t) {
		this.desiredTemp += t;
		this.doUpdate = true;
	}

}
