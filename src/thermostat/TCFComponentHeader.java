package thermostat;

import services.ACControlIF;
import services.ThermoSensorIF;
import tcf.framework.TCFComponent;
import tcf.framework.TCFContext;
import tcf.framework.TCFException;

public class TCFComponentHeader implements TCFComponent
{
	private Thermostat thermostat;
	
	@Override
	public void start(TCFContext ctx) throws TCFException
	{
		ThermoSensorIF thermoSensorService =  (ThermoSensorIF) ctx.getService("ThermoSensorIF");
		ACControlIF acControlService = (ACControlIF) ctx.getService("ACControlIF");
		
		if (null == thermoSensorService || null == acControlService)
			throw new TCFException("Unable to get required services!");
		
		thermostat = new Thermostat(acControlService, thermoSensorService);
		
		ctx.registerService("RegisterIF", thermostat);
		ctx.registerService("ThermostatModifIF", thermostat);
	}

}
