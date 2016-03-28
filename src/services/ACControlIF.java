package services;

import accontrolsys.ACMode;
import tcf.framework.TCFService;

public interface ACControlIF extends TCFService {
	void switchTo(ACMode acm);
}