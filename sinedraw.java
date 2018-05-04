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

public class sinedraw extends JDialog {

	JPanel drawpanel = new DrawPanel();

	int sinescalebuffer = sinewindow.SineScaleSlider.getValue();
	double sinescale = (double) sinescalebuffer / 100.0;

	public sinedraw(sinewindow frame) {
		super(frame);

		JFrame frame1 = new JFrame("Sine Wave");
		frame1.setIconImage(Toolkit.getDefaultToolkit().getImage(sinedraw.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));

		drawpanel.setPreferredSize(
				new Dimension((int) (Integer.parseInt(sinewindow.samplecounttext) * sinescale) + 25, 1024));

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

			g.translate(0, this.getHeight() / 2);

			g.setColor(Color.RED);
			g.drawString("Full Sine", 20, 450);

			for (int i = 0; i < Integer.parseInt(sinewindow.samplecounttext); i++) {
				g.drawLine((int) (i * sinescale),
						(int) ((double) ((Math.sin(sinewindow.Mvalue * 2 * Math.PI * i
								/ (Integer.parseInt(sinewindow.samplecounttext) - 1)) + 1) * 200) + 0.5),
						(int) ((i + 1) * sinescale), (int) ((double) ((Math.sin(sinewindow.Mvalue * 2 * Math.PI
								* (i + 1) / (Integer.parseInt(sinewindow.samplecounttext) - 1)) + 1) * 200) + 0.5));
			}

			g.setColor(Color.BLUE);
			g.drawString("Sampled Sine", 20, -450);

			for (int i = 0; i < Integer.parseInt(sinewindow.samplecounttext); i++) {
				g.fillOval((int) (i * sinescale), (-450) + (int) ((double) ((Math.sin(
						sinewindow.Mvalue * 2 * Math.PI * i / (Integer.parseInt(sinewindow.samplecounttext) - 1)) + 1)
						* 200) + 0.5), 2, 2);

			}

			g.setColor(Color.GREEN);
			g.drawLine(0, -450 / 2 - 24, (int) (Integer.parseInt(sinewindow.samplecounttext) * sinescale),
					-450 / 2 - 24);

			g.setColor(Color.GREEN);
			g.drawLine(0, 310 / 2 + 46, (int) (Integer.parseInt(sinewindow.samplecounttext) * sinescale), 310 / 2 + 46);

		}

	}

}