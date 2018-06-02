package window;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import jrdesktop.SysTray;
import jrdesktop.server.rmi.Server;

/**
 * A demo of the {@link HistogramDataset} class.
 */
public class CallHistogramChart extends JFrame {

	/**
	 * Creates a new demo.
	 *
	 * @param title
	 *            the frame title.
	 */

	public static StringBuffer RawDatabuffer = new StringBuffer();

	public CallHistogramChart(String title, int colum) {
		super(title);
		JPanel chartPanel = createDemoPanel(colum);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);

		// aplicationframe is not work setDefaultCloseOperation
		// (JFrame.DISPOSE_ON_CLOSE);
		// so i replace application to Jframe. and jfreechart dont allow discloe only
		// kill whole JVM
		// this is intended by developer.
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * Creates a sample {@link HistogramDataset}.
	 *
	 * @return the dataset.
	 */
	private IntervalXYDataset createDataset(int Colum) {

		int flag = 0;
		int bodycnt = 0;

		String backline[] = JBeditormain.Finalarray.split("\n");

		String[][] bodyelement = new String[backline.length][];

		// RawData Gathering.

		for (int i = 7, m = 0; i < backline.length; i++) {

			if (backline[i].contains("Stop") || flag == 1) {

				flag = 1;

			} else {

				flag = 0;

				bodyelement[m] = backline[i].split(",");

				bodycnt++;
				m++;

			}

		}

		int k = 0;
		int x = 0;
		int ignore = 0;

		final String[][] ChartComponent = new String[bodycnt][bodyelement[0].length - 5];

		// for (int i = 0; i < bodycnt; i++) {
		for (int i = 0; i < bodycnt; i++) {
			for (int j = 5; j < bodyelement[i].length; j++) {

				if (j == bodyelement[i].length - 1 && i > 3) {

					// 5 over row do nothing insert Charcomponet.

				} else if (bodyelement[i][j].equals("")) {

					ignore = 1;

					// NO value do nothing insert Charcomponet.
					// System.out.println("Null ���� Ȯ��");

				} else {

					ChartComponent[x][k] = bodyelement[i][j];

					// System.out.println("ChartComponent "+x+","+k+":"+ChartComponent[x][k]);

					k++;

				}

			}

			// System.out.println("ChartComponent[x][0] "+i+":"+ChartComponent[x][0]);

			if (ignore == 1) {

				ignore = 0;
				k = 0;

			} else {

				x++;
				k = 0;
			}

		}

		int chartRowCount = x;
		//System.out.println("chartRowCount :" + chartRowCount);

		int datacount = 0;
		int datarecount = 0;
		int datay = 0;

		for (int i = 4; i < ChartComponent.length; i++) {

			if (ChartComponent[i][Colum] == "") {

			} else {

				datacount++;

			}

		}

		final double[] DATA = new double[datacount];

		for (int i = 4; i < ChartComponent.length; i++) {

			if (ChartComponent[i][Colum] == null) {

			} else {

				// System.out.println("ChartComponent.length : " + ChartComponent.length);
				// System.out.println("colum : " + Colum);
				// System.out.println("ChartComponent[i][Colum] "+i+": " +
				// ChartComponent[i][Colum]);

				DATA[datay] = Double.parseDouble(ChartComponent[i][Colum]);

				//System.out.println("DATA[datay] " + datay + ": " + DATA[datay]);

				//RawDatabuffer.append(Double.toString(DATA[datay]) + "\n");

				datay++;

			}

		}

		double[] DATArearange = new double[datay];
		
		for (int i = 0; i < DATA.length; i++) {
			
			if (DATA[i] == 0.0) {
				
				
			} else {
				
				DATArearange[datarecount] = DATA[i];
				
				datarecount++;
			}
			
		}
		
		
		
		double Maxvalue = DATArearange[0];
		double Minvalue = DATArearange[0];

		
		
		for (int i = 0; i < DATArearange.length; i++) {

			if (DATArearange[i] > Maxvalue) {

				Maxvalue = DATArearange[i];

				//System.out.println("max DATArearange[i] "+DATArearange[i] + "Maxvalue : "+ Maxvalue);
			}

		}

		for (int i = 0; i < DATArearange.length; i++) {

			if (DATArearange[i] < Minvalue) {

				Minvalue = DATArearange[i];
				
				//System.out.println("min DATArearange[i] "+i+":" + DATArearange[i]);
			}

		}
		//System.out.println("datacount " +datacount);
		//System.out.println("DATArearange[0] " + DATArearange[0] +" Maxvalue " + Maxvalue + ": " + "Minvalue " + Minvalue);

		HistogramDataset dataset = new HistogramDataset();

		dataset.addSeries(ChartComponent[0][Colum], DATArearange, 100, Minvalue, Maxvalue);

		return dataset;
	}

	/**
	 * Creates a chart.
	 *
	 * @param dataset
	 *            a dataset.
	 *
	 * @return The chart.
	 */
	private JFreeChart createChart(IntervalXYDataset dataset, int colum) {
		
		 String ItemName = getRawDataItemName.get(colum);
		 
		 ItemName = ItemName+" Histogram";
		 
		JFreeChart chart = ChartFactory.createHistogram(ItemName, null, null, dataset,
				PlotOrientation.VERTICAL, true, true, false);
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setDomainPannable(true);
		plot.setRangePannable(true);
		plot.setForegroundAlpha(0.85f);
		NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
		yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		// flat bars look best...
		renderer.setBarPainter(new StandardXYBarPainter());
		renderer.setShadowVisible(false);

		return chart;
	}

	/**
	 * Creates a panel for the demo (used by SuperDemo.java).
	 *
	 * @return A panel.
	 */
	public JPanel createDemoPanel(int colum) {
		JFreeChart chart = createChart(createDataset(colum), colum);
		ChartPanel panel = new ChartPanel(chart);
		panel.setMouseWheelEnabled(true);
		return panel;
	}

	/**
	 * The starting point for the demo.
	 *
	 * @param args
	 *            ignored.
	 *
	 * @throws IOException
	 *             if there is a problem saving the file.
	 */
	public static void CallHistogramChartmain() throws IOException {

		int Colum = getRawDataColum.get();

		System.out.println("colum : " + Colum);
		double position = 0.0;

		for (int i = 0; i < Colum; i++, position = position + 0.05) {

			CallHistogramChart demo = new CallHistogramChart("Raw data Histogram", i);
			demo.pack();
			// RefineryUtilities.centerFrameOnScreen(demo);
			RefineryUtilities.positionFrameOnScreen(demo, 0.2 + position, 0.2 + position);
			demo.setVisible(true);

		}

	}

}
