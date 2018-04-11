package window;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;

public class LinearErrorMessage extends JDialog implements ActionListener {

 public LinearErrorMessage(JDialog linearwindow) {
  super(linearwindow);
  setTitle("linear error");
  setModal(true);    
  
  JLabel lbl = new JLabel("   Pin의 수가 Sample의 경우의 수 보다 적습니다. 생성할 수 없습니다.");
  lbl.setFont(new Font("굴림", Font.BOLD, 12));
  JButton btn = new JButton("확인");
  btn.addActionListener(this);
  getContentPane().add("Center", lbl);    // BordLayout
  getContentPane().add("South", btn);
  
  setBackground(Color.LIGHT_GRAY);
  setBounds(350, 250, 458, 150);
  setVisible(true);
 }
 
 @Override
 public void actionPerformed(ActionEvent e) {
  dispose();  // dialog 닫기
 }
}
