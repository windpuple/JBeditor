package window;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class TDLderiveQuartet extends JDialog {

		String backline[] = JBeditormain.Finalarray.split("\n");

		public static StringBuffer TDLderivebuffer = new StringBuffer();
		public static StringBuffer derivebufferarrary = new StringBuffer();

		public TDLderiveQuartet() {

			TDLderivebuffer.setLength(0);
			derivebufferarrary.setLength(0);
			
			
			int k = 0;
			int cnt = 0;

			for (int i = 4; i < backline.length; i++) {

				if (backline[i].contains("OEN") || backline[i].contains("oen")) {

					// do nothing

				} else {

					//System.out.println("backline[i]" + i + ":" + backline[i]);

					cnt++;
				}

			}

			String[][] bodyline = new String[cnt][];

			for (int i = 4; i < backline.length; i++) {

				if (backline[i].contains("OEN") || backline[i].contains("oen")) {

					// do nothing

				} else {

					bodyline[k] = backline[i].split(",");

					k++;

				}

			}

			// OEN pin 제거한 data 확인.
			//for (int i = 0; i < bodyline.length; i++) {

			//	for (int j = 0; j < bodyline[i].length; j++) {

			//		System.out.println("bodyline[i][j]" + i + "|" + j + ":" + bodyline[i][j]);

			//	}

			//}

			String[] derivebuffer = new String[bodyline.length - 1];

			for (int i = 0; i < bodyline.length - 1; i++) {

				String sub1 = bodyline[i][0].replace("\"", "'");
				String sub2 = bodyline[i][0].replace("\"", "'");
				sub2 = sub2.replaceAll("[^\\uAC00-\\uD7A3xfe0-9a-zA-Z\\\\s]", "");
				sub2 = sub2.replaceAll("[a-z]", "");

				derivebuffer[i] = sub1 + ":" + "'" + sub2 + "'" + ","+"\n";

				derivebufferarrary.append(derivebuffer[i]);
				
				//System.out.println("derivebuffer[i]" + i + ":" + derivebuffer[i]);
			}

			
			
			
			try {

				FileInputStream ftstream = new FileInputStream("./ATE/CredenceQuartet.txt");
				BufferedReader reader = new BufferedReader(new InputStreamReader(ftstream));

				String line;
				int flag = 0;
		

				while ((line = reader.readLine()) != null) {

					if(flag == 1) {
						
						TDLderivebuffer.append(derivebufferarrary);
						
						flag = 0;
					
					} else {
					
					TDLderivebuffer.append(line);
					TDLderivebuffer.append("\n");
					
					}
					
					
					if(line.contains("rename_signals")) {
						
						flag = 1;
						
					}
					
					
					
					
				}

				reader.close();

			} catch (Exception e2) {

				JOptionPane.showMessageDialog(this, "Open Error");

			}
		}

	

	
	
}
