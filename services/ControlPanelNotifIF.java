package services;

import accontrolsys.ACMode;
import tcf.framework.TCFService;

public interface ControlPanelNotifIF extends TCFService {
	void notify(int mt, int dt, ACMode acm);
}
