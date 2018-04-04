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

public class Rotationwindow extends JDialog implements ActionListener {

	JButton btn = new JButton("»Æ¿Œ");
	JButton btncancel = new JButton("\uCDE8\uC18C");

	public static String txtSbinbuffer;

	public Rotationwindow(JFrame frame) {
		super(frame);

		setTitle("Map Rotaion Editor");
		setModal(true);

		btn.setBounds(23, 121, 98, 21);
		btn.addActionListener(this);
		getContentPane().setLayout(null);
		getContentPane().add(btn);

		JLabel clockwise = new JLabel("clock wise");
		clockwise.setBounds(23, 35, 78, 15);
		getContentPane().add(clockwise);

		JLabel counterclockwise = new JLabel("counter clock wise");
		counterclockwise.setBounds(23, 73, 120, 15);
		getContentPane().add(counterclockwise);

		btncancel.setBounds(133, 120, 97, 23);
		getContentPane().add(btncancel);
		
		JComboBox cwcombo = new JComboBox();
		cwcombo.setModel(new DefaultComboBoxModel(new String[] {"90", "180"}));
		cwcombo.setBounds(155, 32, 75, 18);
		getContentPane().add(cwcombo);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"90", "180"}));
		comboBox.setBounds(155, 70, 75, 18);
		getContentPane().add(comboBox);

		setBackground(Color.LIGHT_GRAY);
		setBounds(350, 250, 275, 191);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// dispose(); // dialog ¥›±‚
		if (e.getSource() == btn) { 
			
			String rotationarray[] = JBeditormain.Sbinarray.split(" ");
			
			for(int i = 0; i < rotationarray.length; i++) {
			
				//System.out.println("ration arrary ["+i+"] : "+rotationarray[i]);
				System.out.print(rotationarray[i]);
				
			}
            
			

			dispose();

		} else if (e.getSource() == btncancel) {
			dispose();

		}

	}
}