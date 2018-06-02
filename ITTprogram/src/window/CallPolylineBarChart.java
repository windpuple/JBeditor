package window;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.jfree.ui.TextAnchor;

/**
 * 
 * A simple demonstration application showing how to create a bar chart overlaid
 * 
 * with a line chart.
 * 
 */

public class CallPolylineBarChart {



	public static void CallPolylineBarChartmain() {

		int Colum = getRawDataColum.get();
		
		System.out.println("컬럼값:"+Colum);
		
		for(int i = 0; i < Colum; i++) {
		
		CallPolylineBarChart demo = new CallPolylineBarChart();

		JFreeChart chart = demo.getChart(i);

		ChartFrame frame1 = new ChartFrame("Bar Chart", chart);

		frame1.setSize(800, 400);

		frame1.setVisible(true);
		
		}
		
		
	}

	int flag = 0;
	int bodycnt = 0;

	String backline[] = JBeditormain.Finalarray.split("\n");

	String[][] bodyelement = new String[backline.length][];
	
	public JFreeChart getChart(int y) {

		System.out.println("들어왔는지 확인 컬럼값:"+y);
		
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
		
		final String[][] ChartComponent = new String[bodycnt][bodyelement[0].length-5];

		//for (int i = 0; i < bodycnt; i++) {
		for (int i = 0; i < bodycnt; i++) {
			for (int j = 5; j < bodyelement[i].length; j++) {
				
				if(j ==  bodyelement[i].length-1 && i > 3) {

				   // 5 over row do nothing insert Charcomponet.
				
				
				} else if(bodyelement[i][j].equals("")) {
					
					ignore = 1;
					
				   // NO value do nothing insert Charcomponet.
				   // System.out.println("Null 실행 확인");
					
				} else {
					
					ChartComponent[x][k] = bodyelement[i][j];
					
					
					System.out.println("ChartComponent "+x+","+k+":"+ChartComponent[x][k]);
				    
					
					k++; 
				
				}
				
				
				}
			
			//System.out.println("ChartComponent[x][0] "+i+":"+ChartComponent[x][0]);
			
			if(ignore == 1) {
				
				ignore = 0;
				k = 0;
				
			} else {
				
				x++;	
				k = 0;
			}
			
		}

		int chartRowCount = x;
		System.out.println("chartRowCount :"+chartRowCount);

		
		
		double[] Maxvalue = new double[ChartComponent[0].length];
		double[] Minvalue = new double[ChartComponent[0].length];
		double[] deltavalue = new double[ChartComponent[0].length];
		double[] step = new double[ChartComponent[0].length];
		double[][] Xsidelabel = new double[100][ChartComponent[0].length];
		double[][] Ysidelabel = new double[100][ChartComponent[0].length];
			
			for(int i = 0; i < ChartComponent[0].length; i++) {
				
				for(int j = 4; j < chartRowCount-1; j++) {
					
				if(Double.parseDouble(ChartComponent[j][i]) > Double.parseDouble(ChartComponent[j+1][i])) {
					
					Maxvalue[i] = Double.parseDouble(ChartComponent[j][i]);
					
					
				}
				
			}
			
			
		}
		
			for(int i = 0; i < ChartComponent[0].length; i++) {
				
				for(int j = 4; j < chartRowCount-1; j++) {
					
				if(Double.parseDouble(ChartComponent[j][i]) < Double.parseDouble(ChartComponent[j+1][i])) {
					
					Minvalue[i] = Double.parseDouble(ChartComponent[j][i]);
					
				}
				
			}
			
			
		}
			for(int i = 0; i < ChartComponent[0].length; i++) {
		
				deltavalue[i] = Maxvalue[i] -Minvalue[i];
				step[i] = deltavalue[i] / 100;
			}
	
			for(int i = 0; i < ChartComponent[0].length; i++) {
				for(int j = 0; j < 100; j++) {
					
					Xsidelabel[j][i] = Minvalue[i];
					Xsidelabel[j][i] = Xsidelabel[j][i] + j*step[i];
					
				}
			}
			
			for(int i = 0; i < ChartComponent[0].length; i++) {
				
				for(int j = 4; j < chartRowCount; j++) {
					
					for(int divide = 0 ; divide < 100; divide++ ) {
						
						double dividevalue = Double.parseDouble(ChartComponent[j][i]) / Xsidelabel[divide][i];
						
						if(dividevalue > 0.5 && dividevalue < 1.5) {
							
							Ysidelabel[divide][i] = Ysidelabel[divide][i] + 1;
							
						}
								
					}
					
				}
			}
			
			
		// 데이터 생성

			DefaultCategoryDataset dataset1 = new DefaultCategoryDataset(); 
		
		// 데이터 입력 ( 값, 범례, 카테고리 )

		// 그래프 1
			
		for(int i = 0; i < 100; i++) {
		
				//System.out.println("Double.parseDouble(ChartComponent[i][y]):"+i+":"+Double.parseDouble(ChartComponent[i][y])+":"+ ChartComponent[0][y]+":"+ ChartComponent[0][y]);
			
				dataset1.addValue(Double.parseDouble(ChartComponent[i][y]), ChartComponent[0][y], ChartComponent[0][y]);
					
		
			
		}
	

		// 렌더링 생성 및 세팅

		// 렌더링 생성

		final BarRenderer renderer = new BarRenderer();

		//final LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();

		// 공통 옵션 정의

		final CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();

		final ItemLabelPosition p_center = new ItemLabelPosition(

				ItemLabelAnchor.CENTER, TextAnchor.CENTER

		);

		final ItemLabelPosition p_below = new ItemLabelPosition(

				ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_LEFT

		);

		Font f = new Font("Gulim", Font.BOLD, 14);

		Font axisF = new Font("Gulim", Font.PLAIN, 14);

		// 렌더링 세팅

		// 그래프 1

		renderer.setBaseItemLabelGenerator(generator);

		renderer.setBaseItemLabelsVisible(true);

		renderer.setBasePositiveItemLabelPosition(p_center);

		renderer.setBaseItemLabelFont(f);

		// renderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(

		// GradientPaintTransformType.VERTICAL));

		// renderer.setSeriesPaint(0, new GradientPaint(1.0f, 1.0f, Color.white, 0.0f,
		// 0.0f, Color.blue));

		renderer.setSeriesPaint(0, new Color(0, 162, 255));


		// 그래프 3 line chart

		//renderer2.setBaseItemLabelGenerator(generator);
		//renderer2.setBaseItemLabelsVisible(true);
		//renderer2.setBaseShapesVisible(true);
		//renderer2.setDrawOutlines(true);
		//renderer2.setUseFillPaint(true);
		//renderer2.setBaseFillPaint(Color.WHITE);
		//renderer2.setBaseItemLabelFont(f);
		//renderer2.setBasePositiveItemLabelPosition(p_below);
		//renderer2.setSeriesPaint(0, new Color(219, 121, 22));
		//renderer2.setSeriesStroke(0, new BasicStroke(
		//		2.0f,
		//		BasicStroke.CAP_ROUND,
		//		BasicStroke.JOIN_ROUND,
		//		3.0f)
		//);

		// plot 생성

		final CategoryPlot plot = new CategoryPlot();

		// plot 에 데이터 적재

		plot.setDataset(dataset1);

		plot.setRenderer(renderer);


		// plot 기본 설정

		plot.setOrientation(PlotOrientation.VERTICAL); // 그래프 표시 방향

		plot.setRangeGridlinesVisible(true); // X축 가이드 라인 표시여부

		plot.setDomainGridlinesVisible(true); // Y축 가이드 라인 표시여부

		// 렌더링 순서 정의 : dataset 등록 순서대로 렌더링 ( 즉, 먼저 등록한게 아래로 깔림 )

		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

		// X축 세팅

		plot.setDomainAxis(new CategoryAxis()); // X축 종류 설정

		plot.getDomainAxis().setTickLabelFont(axisF); // X축 눈금라벨 폰트 조정

		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD); // 카테고리 라벨 위치 조정

		// Y축 세팅

		plot.setRangeAxis(new NumberAxis()); // Y축 종류 설정

		plot.getRangeAxis().setTickLabelFont(axisF); // Y축 눈금라벨 폰트 조정

		// 세팅된 plot을 바탕으로 chart 생성

		final JFreeChart chart = new JFreeChart(plot);

		
		// chart.setTitle("Overlaid Bar Chart"); // 차트 타이틀

		// TextTitle copyright = new TextTitle("JFreeChart WaferMapPlot", new
		// Font("SansSerif", Font.PLAIN, 9));

		// copyright.setHorizontalAlignment(HorizontalAlignment.RIGHT);

		// chart.addSubtitle(copyright); // 차트 서브 타이틀
	

		return chart;
	}

}
