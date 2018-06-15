package window;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Toolkit;

public class ChartWindow extends JDialog implements ActionListener {

	JButton btn = new JButton("Confirm");
	JButton btncancel = new JButton("Cancel");
	JLabel notice = new JLabel("");

	String Chartbuffer;

	public ChartWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Rotationwindow.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
	
		Chartbuffer = "";

		setTitle("Back to Histogram Chart");
		setModal(true);

		btn.setBounds(56, 76, 98, 21);
		btn.addActionListener(this);
		getContentPane().setLayout(null);
		getContentPane().add(btn);

		JLabel clockwise = new JLabel("Select Chart");
		clockwise.setBounds(23, 35, 78, 15);
		getContentPane().add(clockwise);

		btncancel.setBounds(185, 75, 97, 23);
		getContentPane().add(btncancel);
		btncancel.addActionListener(this);

		JComboBox ChartCombo = new JComboBox();
		ChartCombo.setModel(new DefaultComboBoxModel(new String[] {"", "PolyLineBarChart", "CirCleChart"}));
		ChartCombo.setBounds(155, 32, 152, 18);
		getContentPane().add(ChartCombo);

		ChartCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Chartbuffer = ChartCombo.getSelectedItem().toString();

			}

		});

		notice.setForeground(Color.RED);
		notice.setBounds(23, 60, 204, 15);
		getContentPane().add(notice);
		
		setBackground(Color.LIGHT_GRAY);
		setBounds(350, 250, 354, 146);
		setVisible(true);
	}


	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btn) {

				
			
			if (Chartbuffer == "PolyLineBarChart") {

				CallPolylineBarChart.CallPolylineBarChartmain();

				dispose();

			} else if (Chartbuffer == "CirCleChart") {

				new TDLderiveQuartet();

				dispose();

			} else {

				notice.setText("");
				notice.setText("Chart를 선택하지 않았습니다.");

			}
			

		} else if (e.getSource() == btncancel) {
			dispose();

		}

	}
}
