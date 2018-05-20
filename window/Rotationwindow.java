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
import java.awt.Toolkit;

public class Rotationwindow extends JDialog implements ActionListener {

	JButton btn = new JButton("확인");
	JButton btncancel = new JButton("\uCDE8\uC18C");

	JLabel notice = new JLabel("");

	String cwbuffer;
	String ccwbuffer;

	public static String rotationfinalbuffer;

	public Rotationwindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Rotationwindow.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
	
		cwbuffer = "";
		ccwbuffer = "";
		rotationfinalbuffer = "";

		setTitle("Map Rotaion Editor");
		setModal(true);

		btn.setBounds(23, 153, 98, 21);
		btn.addActionListener(this);
		getContentPane().setLayout(null);
		getContentPane().add(btn);

		JLabel clockwise = new JLabel("clock wise");
		clockwise.setBounds(23, 35, 78, 15);
		getContentPane().add(clockwise);

		JLabel counterclockwise = new JLabel("counter clock wise");
		counterclockwise.setBounds(23, 73, 120, 15);
		getContentPane().add(counterclockwise);

		btncancel.setBounds(133, 152, 97, 23);
		getContentPane().add(btncancel);
		btncancel.addActionListener(this);

		JComboBox cwcombo = new JComboBox();
		cwcombo.setModel(new DefaultComboBoxModel(new String[] { "", "90", "180", "270" }));
		cwcombo.setBounds(155, 32, 75, 18);
		getContentPane().add(cwcombo);

		cwcombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				cwbuffer = cwcombo.getSelectedItem().toString();

			}

		});

		JComboBox ccwcombo = new JComboBox();
		ccwcombo.setModel(new DefaultComboBoxModel(new String[] { "", "90", "180", "270" }));
		ccwcombo.setBounds(155, 70, 75, 18);
		getContentPane().add(ccwcombo);

		notice.setForeground(Color.RED);
		notice.setBounds(26, 112, 204, 15);
		getContentPane().add(notice);

		ccwcombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ccwbuffer = ccwcombo.getSelectedItem().toString();

			}

		});

		setBackground(Color.LIGHT_GRAY);
		setBounds(350, 250, 275, 223);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btn) {

			if (cwbuffer == "90") {

				new rotation90();
				JBeditormain.Finalarray = "";
				JBeditormain.Finalarray = rotation90.resultbuffer;

				rotationfinalbuffer = rotation90.resultbuffer;

				dispose();

			} else if (cwbuffer == "180") {

				new rotation90();
				JBeditormain.Finalarray = "";
				JBeditormain.Finalarray = rotation90.resultbuffer;
				new rotation90();

				rotationfinalbuffer = rotation90.resultbuffer;

				dispose();

			} else if (cwbuffer == "270") {

				new rotation90();
				JBeditormain.Finalarray = "";
				JBeditormain.Finalarray = rotation90.resultbuffer;
				new rotation90();
				JBeditormain.Finalarray = "";
				JBeditormain.Finalarray = rotation90.resultbuffer;
				new rotation90();

				rotationfinalbuffer = rotation90.resultbuffer;

				dispose();

			} else if (ccwbuffer == "90") {

				// System.out.println("실행되니");

				new rotationInverse90();
				JBeditormain.Finalarray = "";
				JBeditormain.Finalarray = rotationInverse90.resultbuffer;

				rotationfinalbuffer = rotationInverse90.resultbuffer;

				dispose();

			} else if (ccwbuffer == "180") {

				new rotationInverse90();
				JBeditormain.Finalarray = "";
				JBeditormain.Finalarray = rotationInverse90.resultbuffer;
				new rotationInverse90();

				rotationfinalbuffer = rotationInverse90.resultbuffer;

				dispose();

			} else if (ccwbuffer == "270") {

				new rotationInverse90();
				JBeditormain.Finalarray = "";
				JBeditormain.Finalarray = rotationInverse90.resultbuffer;
				new rotationInverse90();
				JBeditormain.Finalarray = "";
				JBeditormain.Finalarray = rotationInverse90.resultbuffer;
				new rotationInverse90();

				rotationfinalbuffer = rotationInverse90.resultbuffer;

				dispose();

			} else {

				notice.setText("");
				notice.setText("degree 값이 없습니다.");

			}

		} else if (e.getSource() == btncancel) {
			dispose();

		}

	}
}