package control_panel;

import services.RegisterIF;
import services.ThermostatModifIF;
import tcf.framework.TCFComponent;
import tcf.framework.TCFContext;
import tcf.framework.TCFException;
import tcf.framework.TCFMain;

public class TCFComponentHeader implements TCFComponent, TCFMain
{
	private ControlPanel controlPanel;
	private RegisterIF guiRegisterService;
	
	@Override
	public void start(TCFContext ctx) throws TCFException
	{
		ThermostatModifIF thermostatService = (ThermostatModifIF) ctx.getService("ThermostatModifIF");
		guiRegisterService = (RegisterIF) ctx.getService("RegisterIF");
		
		if (null == thermostatService || null == guiRegisterService)
			throw new TCFException("Unable to load required services: ThermostatModifIF,RegisterIF!");
		
		controlPanel = new ControlPanel(thermostatService);
		
		ctx.registerService("ControlPanelNotifIF", controlPanel);
		ctx.registerService("TCFMain", this);
	}

	@Override
	public void run()
	{
		guiRegisterService.registerGUI(controlPanel);
		System.out.println("ThermoControl started.");
	}
}
