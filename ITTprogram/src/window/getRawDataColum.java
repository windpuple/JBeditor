package window;

public class getRawDataColum {

	
	
	public static int get() {
		
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
		
		final String[][] ChartComponent = new String[bodycnt][bodyelement[0].length-5];

		//for (int i = 0; i < bodycnt; i++) {
		for (int i = 0; i < bodycnt; i++) {
			for (int j = 5; j < bodyelement[i].length; j++) {
				
				if(j ==  bodyelement[i].length-1 && i > 3) {

				   // 5 over row do nothing insert Charcomponet.
				
				
				} else if(bodyelement[i][j].equals("")) {
					
					ignore = 1;
					
				   // NO value do nothing insert Charcomponet.
				   // System.out.println("Null ���� Ȯ��");
					
				} else {
					
					ChartComponent[x][k] = bodyelement[i][j];
					
					
					//System.out.println("ChartComponent "+x+","+k+":"+ChartComponent[x][k]);
				    
					
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


		return ChartComponent[0].length;
		
		
	}
	
}
