/* 
 *  
 * 2018.03.26 JB.Jeon ITT(katherine) project start. 
 * 
 *  
 */

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
import javax.swing.JSlider;
import java.awt.Font;
import java.awt.Frame;
import java.awt.SystemColor;
import java.awt.Toolkit;

public class RampWindow extends JDialog implements ActionListener {

	JButton btn = new JButton("Confirm");
	JButton btncancel = new JButton("Cancel");
	JLabel notice = new JLabel("");

	public static String Rampfinalbuffer;
	public static String RampSamplecounttext;

	private JTextField pintext;
	private JTextField sampletext;
	public static JSlider Rampslider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
	private JLabel RampsliderLabel = new JLabel("Scale Adjust");

	public RampWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(RampWindow.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		

		Rampfinalbuffer = "";
		RampSamplecounttext = "";

		setTitle("Ramp vector generator");
		setModal(true);

		btn.setBounds(23, 116, 98, 21);
		btn.addActionListener(this);
		getContentPane().setLayout(null);
		getContentPane().add(btn);

		JLabel clockwise = new JLabel("Pin count");
		clockwise.setBounds(23, 35, 78, 15);
		getContentPane().add(clockwise);

		JLabel counterclockwise = new JLabel("Sample count");
		counterclockwise.setBounds(23, 73, 120, 15);
		getContentPane().add(counterclockwise);

		btncancel.setBounds(137, 115, 97, 23);
		getContentPane().add(btncancel);
		btncancel.addActionListener(this);

		pintext = new JTextField();
		pintext.setBounds(131, 32, 116, 21);
		getContentPane().add(pintext);
		pintext.setColumns(10);

		sampletext = new JTextField();
		sampletext.setBounds(133, 70, 116, 21);
		getContentPane().add(sampletext);
		sampletext.setColumns(10);

		notice.setForeground(Color.RED);
		notice.setBounds(23, 116, 224, 15);
		getContentPane().add(notice);

		Rampslider.setPaintLabels(true);
		Rampslider.setPaintTicks(true);
		Rampslider.setPaintTrack(true);
		Rampslider.setMajorTickSpacing(50);
		Rampslider.setMinorTickSpacing(10);

		Rampslider.setBounds(34, 177, 200, 38);
		getContentPane().add(Rampslider);

		RampsliderLabel.setForeground(SystemColor.textHighlight);
		RampsliderLabel.setFont(new Font("굴림", Font.BOLD, 12));
		RampsliderLabel.setBounds(85, 152, 97, 15);

		getContentPane().add(RampsliderLabel);

		setBackground(Color.LIGHT_GRAY);
		setBounds(350, 250, 275, 264);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btn) {

			if (sampletext.getText().isEmpty() || pintext.getText().isEmpty()) {
				notice.setText("");
				notice.setText("pin,sample 값이 없습니다.");

			} else {

				String pincounttext;
				String Rampbridgebuffer;

				pincounttext = pintext.getText();
				RampSamplecounttext = sampletext.getText();

				String[] RampbufferRising = new String[Integer.parseInt(RampSamplecounttext)/2];
				String[] RampbufferFalling = new String[Integer.parseInt(RampSamplecounttext)/2];

				Rampbridgebuffer = "";

				for (int i = 0; i < RampbufferRising.length; i++) {

					RampbufferRising[i] = "";
				}
				
				for (int i = 0; i < RampbufferFalling.length; i++) {

					RampbufferFalling[i] = "";
				}

				for (int i = 0; i < RampbufferRising.length; i++) {

					for (int j = 0; j < Integer.numberOfLeadingZeros(i); j++) {

						RampbufferRising[i] += "0"; // 0을 빈자리 개수만큼 만들어 전진 배치

					}

					if (i != 0) {

						RampbufferRising[i] = RampbufferRising[i] + Integer.toBinaryString(i) + "\n";

					} else {

						RampbufferRising[i] = RampbufferRising[i] + "\n";

					}

					String sub = RampbufferRising[i].substring(32 - Integer.parseInt(pincounttext));
					RampbufferRising[i] = sub;

				}

				//System.out.println("RampbufferFalling length:"+RampbufferFalling.length);
				
				for (int i =  RampbufferFalling.length-2, f = 0; f < RampbufferFalling.length; i--, f++) {

					if(i == -1) {
						
						i = 0;
					
					}

					for (int j = 0; j < Integer.numberOfLeadingZeros(i); j++) {

						//System.out.println("f value:"+f);
						
						RampbufferFalling[f] += "0"; // 0을 빈자리 개수만큼 만들어 전진 배치

						//System.out.println("i:"+i+"-RampbufferFalling"+f+":"+RampbufferFalling[f]);
						
					}

				
					if (i != 0) {

						RampbufferFalling[f] = RampbufferFalling[f] + Integer.toBinaryString(i) + "\n";

					} else {

						RampbufferFalling[f] = RampbufferFalling[f] + "\n";

					}

					//System.out.print("i:"+i+"-RampbufferFalling"+f+":"+RampbufferFalling[f]);
					
					String sub = RampbufferFalling[f].substring(32 - Integer.parseInt(pincounttext));
					RampbufferFalling[f] = sub;

				}
				
				
				
				for (int i = 0; i < RampbufferRising.length; i++) {

					Rampbridgebuffer += RampbufferRising[i];

				}
				
				for (int i = 0; i < RampbufferFalling.length; i++) {

					Rampbridgebuffer += RampbufferFalling[i];

				}

				Rampfinalbuffer = Rampbridgebuffer;

				new Rampdraw(this);

				dispose();

			}

		} else if (e.getSource() == btncancel) {
			dispose();

		}

	}
}