package ac_sensor_bundle;

import tcf.framework.TCFComponent;
import tcf.framework.TCFContext;
import tcf.framework.TCFException;

public class TCFComponentHeader implements TCFComponent
{
	private AirConditioner airConditioner;
	private ThermoSensor thermoSensor;
	
	@Override
	public void start(TCFContext ctx) throws TCFException
	{
		airConditioner = new AirConditioner();
		thermoSensor = new ThermoSensor();
		
		airConditioner.setSensor(thermoSensor);
		airConditioner.start();
		
		ctx.registerService("ACControlIF", airConditioner);
		ctx.registerService("ThermoSensorIF", thermoSensor);
	}
}
