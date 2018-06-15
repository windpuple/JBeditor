package window;

public class getRawDataItemName {

	
	public static String get(int Colum) {
		
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
					// System.out.println("Null 실행확인");

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

		
		return ChartComponent[0][Colum];
		
		
	}
	
	
}
