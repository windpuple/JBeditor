package window;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class lineardraw extends JDialog {

	DrawPanel drawpanel = new DrawPanel();

	public lineardraw(linearwindow frame) {
		super(frame);

		JFrame frame1 = new JFrame("Linear Wave");

		// drawpanel.setPreferredSize(new
		// Dimension(Toolkit.getDefaultToolkit().getScreenSize().width*2,
		// Toolkit.getDefaultToolkit().getScreenSize().height*2)); //!! added
		drawpanel.setPreferredSize(new Dimension(Integer.parseInt(linearwindow.samplecounttext) + 25, 1024));

		JScrollPane scrollPane = new JScrollPane(drawpanel);

		frame1.add(scrollPane);

		frame1.setResizable(true);
		frame1.setPreferredSize(new Dimension(1000, 800)); // !! added
		frame1.pack(); // !! added
		frame1.setLocationByPlatform(true);
		frame1.setVisible(true);

	}

	class DrawPanel extends JPanel {

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			g.translate(0, 0);
			
			g.setColor(Color.RED);
			g.drawString("Full Linear", 20, 720+(this.getHeight()-20)/5);

			for (int i = 0; i < Integer.parseInt(linearwindow.samplecounttext); i++) {
				g.drawLine(i, 700+((this.getHeight()-20)-i)/5
						,
						i + 1, 700+((this.getHeight()-20)-(i+1))/5);
			}

			g.setColor(Color.BLUE);
			g.drawString("Sampled Linear", 20, 220+this.getHeight()/5);

			for (int i = 0; i < Integer.parseInt(linearwindow.samplecounttext); i++) {
				g.fillOval(i, 200+(this.getHeight() - i)/5, 2, 2);

			}

			g.setColor(Color.GREEN);
			g.drawLine(0, 700+(this.getHeight()-20)/5, Integer.parseInt(linearwindow.samplecounttext), 700+(this.getHeight()-20)/5);

			g.setColor(Color.GREEN);
			g.drawLine(0, 200+this.getHeight()/5, Integer.parseInt(linearwindow.samplecounttext), 200+this.getHeight()/5);

			/*
			 * 10 times less M scaled linear
			 * 
			 * g.setColor(Color.GREEN);
			 * 
			 * for (int i = 0; i <= Integer.parseInt(linearwindow.samplecounttext)-1; i++) {
			 * g.drawLine(i, (int)( - (Math.sin(((int) linearwindow.Mvalue/10)*2 * Math.PI * i
			 * / Integer.parseInt(linearwindow.samplecounttext)) + 1)* ((
			 * Integer.parseInt(linearwindow.samplecounttext)-1) / 5)) , i+1, (int)( -
			 * (Math.sin(((int) linearwindow.Mvalue/10)*2 * Math.PI * (i+1) /
			 * Integer.parseInt(linearwindow.samplecounttext)) + 1)* ((
			 * Integer.parseInt(linearwindow.samplecounttext)-1) / 5))); }
			 * 
			 */

		}

	}

}