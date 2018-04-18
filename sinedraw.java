package window;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class sinedraw extends JDialog {

	JPanel drawpanel = new DrawPanel();

	public sinedraw(sinewindow frame) {
		super(frame);

		JFrame frame1 = new JFrame("Sine Wave");

		// drawpanel.setPreferredSize(new
		// Dimension(Toolkit.getDefaultToolkit().getScreenSize().width*2,
		// Toolkit.getDefaultToolkit().getScreenSize().height*2)); //!! added
		drawpanel.setPreferredSize(new Dimension(Integer.parseInt(sinewindow.samplecounttext) + 25, 1024));

		JScrollPane scrollPane = new JScrollPane(drawpanel);

		frame1.add(scrollPane);

		frame1.setResizable(true);
		frame1.setPreferredSize(new Dimension(1000, 800)); // !! added
		frame1.pack(); // !! added
		frame1.setLocationByPlatform(true);
		frame1.setVisible(true);

		// JScrollPane scrollPane = new
		// JScrollPane(drawpanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

	}

	class DrawPanel extends JPanel {

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			g.translate(0, this.getHeight() / 2);

			g.setColor(Color.RED);
			g.drawString("Full Sine", 20, 450);

			for (int i = 0; i < Integer.parseInt(sinewindow.samplecounttext); i++) {
				g.drawLine(i,
						(int) ((double) ((Math.sin(sinewindow.Mvalue * 2 * Math.PI * i
								/ (Integer.parseInt(sinewindow.samplecounttext) - 1)) + 1) * 200) + 0.5),
						i + 1, (int) ((double) ((Math.sin(sinewindow.Mvalue * 2 * Math.PI * (i + 1)
								/ (Integer.parseInt(sinewindow.samplecounttext) - 1)) + 1) * 200) + 0.5));
			}

			g.setColor(Color.BLUE);
			g.drawString("Sampled Sine", 20, -450);

			for (int i = 0; i < Integer.parseInt(sinewindow.samplecounttext); i++) {
				g.fillOval(i, (-450) + (int) ((double) ((Math.sin(
						sinewindow.Mvalue * 2 * Math.PI * i / (Integer.parseInt(sinewindow.samplecounttext) - 1)) + 1)
						* 200) + 0.5), 2, 2);

			}

			g.setColor(Color.GREEN);
			g.drawLine(0, -450 / 2 - 24, Integer.parseInt(sinewindow.samplecounttext), -450 / 2 - 24);

			g.setColor(Color.GREEN);
			g.drawLine(0, 310 / 2 + 46, Integer.parseInt(sinewindow.samplecounttext), 310 / 2 + 46);

			/*
			 * 10 times less M scaled sine
			 * 
			 * g.setColor(Color.GREEN);
			 * 
			 * for (int i = 0; i <= Integer.parseInt(sinewindow.samplecounttext)-1; i++) {
			 * g.drawLine(i, (int)( - (Math.sin(((int) sinewindow.Mvalue/10)*2 * Math.PI * i
			 * / Integer.parseInt(sinewindow.samplecounttext)) + 1)* ((
			 * Integer.parseInt(sinewindow.samplecounttext)-1) / 5)) , i+1, (int)( -
			 * (Math.sin(((int) sinewindow.Mvalue/10)*2 * Math.PI * (i+1) /
			 * Integer.parseInt(sinewindow.samplecounttext)) + 1)* ((
			 * Integer.parseInt(sinewindow.samplecounttext)-1) / 5))); }
			 * 
			 */

		}

	}

}