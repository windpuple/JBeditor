/* 
 *  
 * 2018.03.26 JB.Jeon ITT(katherine) project start. 
 * 
 *  
 */

package window;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class lineardraw extends JDialog {

	DrawPanel drawpanel = new DrawPanel();

	int linearscalebuffer = linearwindow.Linearslider.getValue();
	double linearscale = (double) linearscalebuffer / 100.0;

	public lineardraw(linearwindow frame) {
		super(frame);

		JFrame frame1 = new JFrame("Linear Wave");
		frame1.setIconImage(Toolkit.getDefaultToolkit().getImage(lineardraw.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));

		drawpanel.setPreferredSize(
				new Dimension((int) (Integer.parseInt(linearwindow.samplecounttext) * linearscale) + 25, (int) ((Integer.parseInt(linearwindow.samplecounttext)) * linearscale)));

		JScrollPane scrollPane = new JScrollPane(drawpanel);

		frame1.getContentPane().add(scrollPane);

		frame1.setResizable(true);
		frame1.setPreferredSize(new Dimension(1000, 800));
		frame1.pack();
		frame1.setLocationByPlatform(true);
		frame1.setVisible(true);

	}

	class DrawPanel extends JPanel {

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			g.translate(0, this.getHeight()/2);
					
			int sample = Integer.parseInt(linearwindow.samplecounttext);
	
			g.setColor(Color.RED);
			g.drawString("Full Linear", 20, 0);

			for (int i = 0; i < Integer.parseInt(linearwindow.samplecounttext); i++) {
				g.drawLine((int) (i * linearscale), -(int)(((((double)this.getHeight()*0.48)/(sample-1))*i)),
						(int) ((i + 1) * linearscale), -(int)(((((double)this.getHeight()*0.48)/(sample-1))*(i+1))));
				
			}

			g.setColor(Color.GREEN);
			g.drawLine(0, 0,
					(int) (Integer.parseInt(linearwindow.samplecounttext) * linearscale), 0);
			
		
			
			g.setColor(Color.BLUE);
			g.drawString("Sampled Linear", 20, (int)(((((double)this.getHeight()*0.48)/(sample-1))*sample)));

			int j = sample;
			
			for (int i = 0; i < Integer.parseInt(linearwindow.samplecounttext); i++) {
				g.fillOval((int) (i * linearscale), (int)(((((double)this.getHeight()*0.48)/(sample-1))*j)), 2, 2);
				
				j--;
			}

			
			g.setColor(Color.GREEN);
			g.drawLine(0, (int)(((((double)this.getHeight()*0.48)/(sample-1))*sample)),
					(int) (Integer.parseInt(linearwindow.samplecounttext) * linearscale),
					(int)(((((double)this.getHeight()*0.48)/(sample-1))*sample)));
			

			
		}

	}

}