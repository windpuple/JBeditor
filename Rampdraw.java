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

public class Rampdraw extends JDialog {

	DrawPanel drawpanel = new DrawPanel();

	int Rampscalebuffer = RampWindow.Rampslider.getValue();
	double Rampscale = (double) Rampscalebuffer / 100.0;

	public Rampdraw(RampWindow frame) {
		super(frame);

		JFrame frame1 = new JFrame("Ramp Wave");
		frame1.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Rampdraw.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));

		drawpanel.setPreferredSize(
				new Dimension((int) (Integer.parseInt(RampWindow.RampSamplecounttext) * Rampscale) + 25,
						(int) ((Integer.parseInt(RampWindow.RampSamplecounttext)) * Rampscale)));

		JScrollPane scrollPane = new JScrollPane(drawpanel);

		frame1.getContentPane().add(scrollPane);

		frame1.setResizable(true);
		frame1.setPreferredSize(new Dimension(1000, 800));
		frame1.pack();
		frame1.setLocationByPlatform(true);
		frame1.setVisible(true);

		setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
		
	}

	class DrawPanel extends JPanel {

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			g.translate(0, this.getHeight() / 2);

			int sample = Integer.parseInt(RampWindow.RampSamplecounttext) / 2;

			g.setColor(Color.RED);
			g.drawString("Full Ramp", 20, 0);

			for (int i = 0; i < Integer.parseInt(RampWindow.RampSamplecounttext) / 2; i++) {
				g.drawLine((int) (i * Rampscale), -(int) (((((double) this.getHeight() * 0.48) / (sample - 1)) * i)),
						(int) ((i + 1) * Rampscale),
						-(int) (((((double) this.getHeight() * 0.48) / (sample - 1)) * (i + 1))));

			}


			for (int i = Integer.parseInt(RampWindow.RampSamplecounttext)
					/ 2, f = Integer.parseInt(RampWindow.RampSamplecounttext) / 2 - 2; i < Integer
							.parseInt(RampWindow.RampSamplecounttext); i++, f--) {

				if (f == 0) {

					f = 0;
				}

				g.drawLine((int) (i * Rampscale), -(int) (((((double) this.getHeight() * 0.48) / (sample - 1)) * f)),
						(int) ((i + 1) * Rampscale),
						-(int) (((((double) this.getHeight() * 0.48) / (sample - 1)) * (f - 1))));

			}

			g.setColor(Color.GREEN);
			g.drawLine(0, 0, (int) (Integer.parseInt(RampWindow.RampSamplecounttext) * Rampscale), 0);

			g.setColor(Color.BLUE);
			g.drawString("Sampled Ramp", 20, (int) (((((double) this.getHeight() * 0.48) / (sample - 1)) * sample)));

			int j = sample;

			for (int i = 0; i < Integer.parseInt(RampWindow.RampSamplecounttext) / 2; i++) {
				g.fillOval((int) (i * Rampscale), (int) (((((double) this.getHeight() * 0.48) / (sample - 1)) * j)), 2,
						2);

				j--;
			}

			j = 2;
			
			for (int i = Integer.parseInt(RampWindow.RampSamplecounttext) / 2; i < Integer
					.parseInt(RampWindow.RampSamplecounttext); i++) {
				g.fillOval((int) (i * Rampscale), (int) (((((double) this.getHeight() * 0.48) / (sample - 1)) * j)), 2,
						2);

				j++;

			}

			g.setColor(Color.GREEN);
			g.drawLine(0, (int) (((((double) this.getHeight() * 0.48) / (sample - 1)) * sample)),
					(int) (Integer.parseInt(RampWindow.RampSamplecounttext) * Rampscale),
					(int) (((((double) this.getHeight() * 0.48) / (sample - 1)) * sample)));

		}

	}

}