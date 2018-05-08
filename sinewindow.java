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
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import java.awt.SystemColor;
import java.awt.Toolkit;

public class sinewindow extends JDialog implements ActionListener {

	JButton btn = new JButton("\uC0DD\uC131");
	JButton btncancel = new JButton("\uCDE8\uC18C");
	JButton nbtn = new JButton("M\uACC4\uC0B0");

	JLabel notice = new JLabel("");
	public static JSlider SineScaleSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);

	public static String sinefinalbuffer;
	public static String samplecounttext;
	public static int Mvalue;

	private JTextField pintext;
	private JTextField sampletext;
	private JTextField Fttext;
	private JTextField Fstext;
	private JTextField Mcycletext;

	public sinewindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(sinewindow.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		

		sinefinalbuffer = "";
		samplecounttext = "";

		setTitle("sine vector generator");
		setModal(true);

		btn.setBounds(79, 121, 98, 21);
		getContentPane().setLayout(null);
		getContentPane().add(btn);

		nbtn.setBounds(190, 120, 97, 23);
		getContentPane().add(nbtn);

		btncancel.setBounds(299, 120, 97, 23);
		getContentPane().add(btncancel);

		btn.addActionListener(this);
		nbtn.addActionListener(this);
		btncancel.addActionListener(this);

		JLabel pincount = new JLabel("Pin count");
		pincount.setBounds(23, 35, 78, 15);
		getContentPane().add(pincount);

		JLabel samplecount = new JLabel("Sample count");
		samplecount.setBounds(23, 73, 120, 15);
		getContentPane().add(samplecount);

		pintext = new JTextField();
		pintext.setBounds(118, 32, 85, 21);
		getContentPane().add(pintext);
		pintext.setColumns(10);

		sampletext = new JTextField();
		sampletext.setBounds(118, 70, 85, 21);
		getContentPane().add(sampletext);
		sampletext.setColumns(10);

		JLabel lblFt = new JLabel("Ft");
		lblFt.setBounds(215, 35, 57, 15);
		getContentPane().add(lblFt);

		JLabel lblFs = new JLabel("Fs");
		lblFs.setBounds(315, 35, 57, 15);
		getContentPane().add(lblFs);

		Fttext = new JTextField();
		Fttext.setBounds(236, 32, 67, 21);
		getContentPane().add(Fttext);
		Fttext.setColumns(10);

		Fstext = new JTextField();
		Fstext.setBounds(341, 32, 67, 21);
		getContentPane().add(Fstext);
		Fstext.setColumns(10);

		JLabel lblMcycle = new JLabel("M(Cycle)");
		lblMcycle.setBounds(215, 73, 57, 15);
		getContentPane().add(lblMcycle);

		Mcycletext = new JTextField();
		Mcycletext.setBounds(284, 70, 78, 21);
		getContentPane().add(Mcycletext);
		Mcycletext.setColumns(10);

		pintext.setText("");
		Fttext.setText("");
		Fstext.setText("");
		sampletext.setText("");
		Mcycletext.setText("1");

		JLabel lblIfUWant = new JLabel(
				"M\uAC12\uB9CC\uC744 \uC774\uC6A9\uD574\uC11C Sine vector\uB97C \uC0DD\uC131\uD558\uACE0\uC790 \uD55C\uB2E4\uBA74,");
		lblIfUWant.setFont(new Font("굴림", Font.PLAIN, 12));
		lblIfUWant.setBounds(79, 152, 293, 29);
		getContentPane().add(lblIfUWant);

		JLabel lblNewLabel = new JLabel(
				"Ft\uC640 Fs\uB294 \uBE48\uC2AC\uB86F\uC73C\uB85C \uB0A8\uACA8 \uB450\uC2ED\uC2DC\uC624.");
		lblNewLabel.setBounds(79, 182, 283, 15);
		getContentPane().add(lblNewLabel);
		notice.setForeground(Color.RED);

		notice.setBounds(79, 98, 316, 15);
		getContentPane().add(notice);

		SineScaleSlider.setPaintLabels(true);
		SineScaleSlider.setPaintTicks(true);
		SineScaleSlider.setPaintTrack(true);
		SineScaleSlider.setMajorTickSpacing(50);
		SineScaleSlider.setMinorTickSpacing(10);

		SineScaleSlider.setBounds(196, 207, 200, 44);
		getContentPane().add(SineScaleSlider);

		JLabel SlideLabel = new JLabel("Scale Adjust");
		SlideLabel.setFont(new Font("굴림", Font.BOLD, 12));
		SlideLabel.setForeground(SystemColor.textHighlight);
		SlideLabel.setBounds(105, 225, 98, 15);
		getContentPane().add(SlideLabel);

		setBackground(Color.LIGHT_GRAY);
		setBounds(350, 250, 468, 300);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btn) {

			if (sampletext.getText().isEmpty() || pintext.getText().isEmpty() || Mcycletext.getText().isEmpty()) {
				notice.setText("");
				notice.setText("pin,sample,M 값이 없습니다.");

			} else {

				String pincounttext;
				String sinebridgebuffer;

				pincounttext = pintext.getText();
				samplecounttext = sampletext.getText();

				String[] sinebuffer = new String[Integer.parseInt(samplecounttext)];

				sinebridgebuffer = "";

				Mvalue = Integer.parseInt(Mcycletext.getText());

				for (int i = 0; i < sinebuffer.length; i++) {

					sinebuffer[i] = "";
				}

				
				for (int i = 0; i < sinebuffer.length; i++) {

					for (int j = 0; j < Integer.numberOfLeadingZeros(
							(int) ((double) ((Math.sin(Mvalue * 2 * Math.PI * i / (sinebuffer.length)) + 1)
									* ((sinebuffer.length-1) / 2)))); j++) {

						sinebuffer[i] += "0"; // 0을 빈자리 개수만큼 만들어 전진 배치

					}

					if ((int) ((double) ((Math.sin(Mvalue * 2 * Math.PI * i / (sinebuffer.length)) + 1)
							* ((sinebuffer.length-1) / 2))) != 0) {

						sinebuffer[i] = sinebuffer[i] + Integer.toBinaryString(
								(int) ((double) ((Math.sin(Mvalue * 2 * Math.PI * i / (sinebuffer.length)) + 1)
										* ((sinebuffer.length-1) / 2))))
								+ "\n";

					} else {

						sinebuffer[i] = sinebuffer[i] + "\n";

					}

					String sub = sinebuffer[i].substring(32 - Integer.parseInt(pincounttext));
					sinebuffer[i] = sub;

				}

				for (int i = 0; i < sinebuffer.length; i++) {

					sinebridgebuffer += sinebuffer[i];

				}

				sinefinalbuffer = sinebridgebuffer;

				new sinedraw(this);

				dispose();

			}

		} else if (e.getSource() == nbtn) {

			double Ft = 0;
			double Fs = 0;
			double Nvalue = 0;
			double Mvaluebuf = 0.0;

			if (Fttext.getText().isEmpty() || Fstext.getText().isEmpty() || sampletext.getText().isEmpty()) {
				notice.setText("");
				notice.setText("Ft,Fs,sample 값이 없습니다.");

			} else {

				notice.setText("");

				Ft = Double.parseDouble(Fttext.getText());
				Fs = Double.parseDouble(Fstext.getText());
				Nvalue = Double.parseDouble(sampletext.getText());

				Mvaluebuf = (Ft / Fs) * Nvalue;

				Mvalue = (int) Mvaluebuf;

				Mcycletext.setText("");
				Mcycletext.setText(Integer.toString(Mvalue));

			}

		} else if (e.getSource() == btncancel) {
			dispose();

		}

	}
}