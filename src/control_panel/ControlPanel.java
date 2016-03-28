package control_panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import accontrolsys.ACMode;
import services.ControlPanelNotifIF;
import services.ThermostatModifIF;

public class ControlPanel implements ControlPanelNotifIF {
	final ThermostatModifIF myThermostat;

	JFrame frame;
	JLabel measuredTempLbl;
	JLabel desiredTempLbl;
	JLabel controlLampLbl;
	JButton incTempBtn;
	JButton decTempBtn;
	GroupLayout layout;

	ControlPanel(ThermostatModifIF t) {
		this.myThermostat = t;

		this.frame = new JFrame("AC Control");
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.layout = new GroupLayout(this.frame.getContentPane());
		this.frame.setLayout(this.layout);
		this.layout.setAutoCreateGaps(true);
		this.layout.setAutoCreateContainerGaps(true);

		final GroupLayout.SequentialGroup vGroup = this.layout.createSequentialGroup();
		final GroupLayout.ParallelGroup hGroup = this.layout.createParallelGroup();

		this.measuredTempLbl = new JLabel();
		this.measuredTempLbl.setBorder(BorderFactory.createTitledBorder("Measured Temperature"));
		this.measuredTempLbl.setMinimumSize(new Dimension(200, 0));
		this.measuredTempLbl.setText("-- °C");
		hGroup.addComponent(this.measuredTempLbl);
		vGroup.addComponent(this.measuredTempLbl);

		this.desiredTempLbl = new JLabel();
		this.desiredTempLbl.setBorder(BorderFactory.createTitledBorder("Desired Temperature"));
		this.desiredTempLbl.setMinimumSize(new Dimension(200, 0));
		this.desiredTempLbl.setText("-- °C");
		hGroup.addComponent(this.desiredTempLbl);
		vGroup.addComponent(this.desiredTempLbl);

		this.controlLampLbl = new JLabel();
		this.controlLampLbl.setBorder(BorderFactory.createTitledBorder("Air Conditioner"));
		this.controlLampLbl.setMinimumSize(new Dimension(200, 0));
		this.controlLampLbl.setText("OFF");
		hGroup.addComponent(this.controlLampLbl);
		vGroup.addComponent(this.controlLampLbl);

		this.layout.setHorizontalGroup(hGroup);
		this.layout.setVerticalGroup(vGroup);

		final JPanel btnPanel = new JPanel();

		this.decTempBtn = new JButton("-");
		this.incTempBtn = new JButton("+");

		this.incTempBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlPanel.this.myThermostat.modifyDesiredTemp(1);
			}
		});

		this.decTempBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlPanel.this.myThermostat.modifyDesiredTemp(-1);
			}
		});

		btnPanel.add(this.incTempBtn);
		btnPanel.add(this.decTempBtn);

		hGroup.addComponent(btnPanel);
		vGroup.addComponent(btnPanel);

		this.frame.pack();
		this.frame.setLocation(500, 300);

		this.frame.setVisible(true);
	}

	@Override
	public void notify(final int newMeasured, final int newDesired, final ACMode acm) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ControlPanel.this.measuredTempLbl.setText(newMeasured + " �C");
				ControlPanel.this.desiredTempLbl.setText(newDesired + " �C");
				ControlPanel.this.controlLampLbl.setText(acm.toString());
			}
		});
	}
}
