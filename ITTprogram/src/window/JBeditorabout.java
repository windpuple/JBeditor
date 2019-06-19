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
import javax.swing.SwingConstants;

public class JBeditorabout extends JDialog implements ActionListener {

 public JBeditorabout(JFrame frame) {
  super(frame);
  setIconImage(Toolkit.getDefaultToolkit().getImage(JBeditorabout.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
  setTitle("ITT infomation");
  setModal(true);
  JButton btn = new JButton("확인");
  btn.setBounds(0, 102, 205, 23);
  btn.addActionListener(this);
  getContentPane().setLayout(null);
  getContentPane().add(btn);
  
  JLabel lblNewLabel = new JLabel("ITT Ver 18.0");
  lblNewLabel.setBounds(12, 10, 130, 23);
  getContentPane().add(lblNewLabel);
  
  JLabel lblLeadDeveloperJb = new JLabel("Lead Dev: JB. Jeon");
  lblLeadDeveloperJb.setBounds(12, 35, 181, 23);
  getContentPane().add(lblLeadDeveloperJb);
  
  JLabel lblDevelopersJhJo = new JLabel("Sub Devs: JH. JO, Yang Qin, JH Kang");
  lblDevelopersJhJo.setBounds(12, 62, 181, 23);
  getContentPane().add(lblDevelopersJhJo);
  
  setBackground(Color.LIGHT_GRAY);
  setBounds(350, 250, 221, 164);
  setVisible(true);
 }
 
 @Override
 public void actionPerformed(ActionEvent e) {
  dispose();  // dialog  닫기
 }
}
