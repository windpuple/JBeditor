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
import java.awt.Toolkit;

public class JBeditorabout extends JDialog implements ActionListener {

 public JBeditorabout(JFrame frame) {
  super(frame);
  setIconImage(Toolkit.getDefaultToolkit().getImage(JBeditorabout.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
  setTitle("ITT infomation");
  setModal(true);    // true : Modal, false : Modeless
  
  JLabel lbl = new JLabel("   ITT ver 0.6 made by JB.Jeon");
  JButton btn = new JButton("»Æ¿Œ");
  btn.addActionListener(this);
  getContentPane().add("Center", lbl);    // BordLayout
  getContentPane().add("South", btn);
  
  setBackground(Color.LIGHT_GRAY);
  setBounds(350, 250, 200, 150);
  setVisible(true);
 }
 
 @Override
 public void actionPerformed(ActionEvent e) {
  dispose();  // dialog ¥›±‚
 }
}
