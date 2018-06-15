/* 
 *  
 * 208.04.10 JB.Jeon ITT(katherine) project start. 
 * 
 *  
 */

package window;

import java.awt.FileDialog;
import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.*;

public class DBEwindow extends JFrame {

	public static String DBEarrary = "";
	public static String DBEsumarrary = "";
	String str1 = "Wafer_ID : ";
	String str2 = "Flat_Notch : ";
	String enter = "\n";
	String hype = "-";

	char[] cutmap = new char[JBeditormain.Finalarray.length()];
	char[] defbuffer = new char[JBeditormain.Finalarray.length()];

	public DBEwindow() {

		String header[] = JBeditormain.Finalarray.split("\n");

		String LotId = "";
		String WaferId = "";
		String FlatZone = "";

		int flag = 0;
		int j = 0;
		int cnt = 0;
		int binsize = 0;
		int maxbinchar = 0;
		int Mapchar = 0;
		int firstflag = 0;

		for (int i = 0; i < header.length; i++) {

			if (header[i].contains("LOT Number")) {

				int p1 = header[i].indexOf("LOT Number");
				LotId = header[i].substring(p1 + 19);

			}

			if (header[i].contains("WAFER ID")) {

				int p1 = header[i].indexOf("WAFER ID");
				WaferId = header[i].substring(p1 + 19);

			}

			if (header[i].contains("FLAT ZONE")) {

				int p1 = header[i].indexOf("FLAT ZONE");
				FlatZone = header[i].substring(p1 + 19);

			}

		}

		// bin char 추가
		int tailindex = JBeditormain.Finalarray.indexOf("SoftBIN");
		String taillinebuf = JBeditormain.Finalarray.substring(tailindex);
		String[] tailline = taillinebuf.split("\n");

		String[][] tailcomponent = new String[tailline.length][];

		for (int i = 0; i < tailline.length; i++) {

			tailcomponent[i] = tailline[i].split("\\s");

		}

		for (int i = 0; i < tailcomponent.length - 1; i++) {

			for (int j1 = 0; j1 < tailcomponent[i][2].length(); j1++) {

				maxbinchar++;

			}

			if (maxbinchar > binsize) {

				binsize = maxbinchar;
			}

			maxbinchar = 0;

		}

		for (int i = 0; i < JBeditormain.Finalarray.length(); i++) {

			cutmap[i] = JBeditormain.Finalarray.charAt(i);

			
			
			
			if (cutmap[i] == '|' || flag == 1) {

				flag = 1;
				firstflag = 1;
				

				if (cutmap[i] == '\n') {

					flag = 0;

					defbuffer[j] = cutmap[i];

					j++;
					cnt++;

				} else if (cutmap[i] == ' ' || cutmap[i] == '1' || cutmap[i] == '|'|| cutmap[i] == '+') {

						defbuffer[j] = cutmap[i];

						j++;
						cnt++;

					} else if (cutmap[i] < 47 || cutmap[i] > 58 && cutmap[i] != '|' && cutmap[i] != '+') {

						cutmap[i] = 'X';

						defbuffer[j] = cutmap[i];

						j++;
						cnt++;
						Mapchar = 1;

					} else {

						cutmap[i] = 'X';

						defbuffer[j] = cutmap[i];

						j++;
						cnt++;

					}

			

			} else if(cutmap[i] == '+' && firstflag == 1) {
				
				flag = 1;
				
				defbuffer[j] = cutmap[i];

				j++;
				cnt++;
				
			}
			
			else if (cutmap[i] == '-') {
				flag = 0;
				firstflag =0;
			}

		}

		// Map 마다 char를 찍는 갯수의 상이함을 보상.
		if (binsize == 2) {

			binsize = binsize - 1;

		} else if (Mapchar == 1) {

			binsize = 1;

		}

		DBEsumarrary = str1 + LotId + hype + WaferId + enter;

		DBEsumarrary = DBEsumarrary + str2 + FlatZone + enter + enter;

		for (int i = 0; i < cnt; i++) {
			DBEarrary += Character.toString(defbuffer[i]);
		}

		
		if (binsize == 4) {
			DBEarrary = DBEarrary.replace("null", "");
			DBEarrary = DBEarrary.replace("|", "");
			DBEarrary = DBEarrary.replace("+", "");
			DBEarrary = DBEarrary.replace(" 11", "  X");
			DBEarrary = DBEarrary.replace("111", "  X");
			DBEarrary = DBEarrary.replace("11X", "  X");
			DBEarrary = DBEarrary.replace("1XX", "  X");
			DBEarrary = DBEarrary.replace("1X1", "  X");
			DBEarrary = DBEarrary.replace("XX1", "  X");
			DBEarrary = DBEarrary.replace("X11", "  X");
			DBEarrary = DBEarrary.replace("XXX", "  X");
			DBEarrary = DBEarrary.replace("   1", "1");
			DBEarrary = DBEarrary.replace("   X", "X");
			DBEarrary = DBEarrary.replace("    ", " ");
			DBEarrary = DBEarrary.replace(" ", ".");
		} else if (binsize == 3) {
			DBEarrary = DBEarrary.replace("null", "");
			DBEarrary = DBEarrary.replace("|", "");
			DBEarrary = DBEarrary.replace("+", "");
			DBEarrary = DBEarrary.replace("11", " X");
			DBEarrary = DBEarrary.replace("1X", " X");
			DBEarrary = DBEarrary.replace("X1", " X");
			DBEarrary = DBEarrary.replace("XX", " X");
			DBEarrary = DBEarrary.replace("  1", "1");
			DBEarrary = DBEarrary.replace("  X", "X");
			DBEarrary = DBEarrary.replace("   ", " ");
			DBEarrary = DBEarrary.replace(" ", ".");

		} else if (binsize == 1) {
			DBEarrary = DBEarrary.replace("null", "");
			DBEarrary = DBEarrary.replace("|", "");
			DBEarrary = DBEarrary.replace("+", "");
			DBEarrary = DBEarrary.replace(" ", ".");

		}

		DBEsumarrary = DBEsumarrary + DBEarrary;

		FileDialog dialog = new FileDialog(this, "Save", FileDialog.SAVE);
		dialog.setDirectory(".");
		dialog.setVisible(true);
		if (dialog.getFile() == null)
			return;
		String dfName = dialog.getDirectory() + dialog.getFile();

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(dfName));
			writer.write(DBEsumarrary);
			writer.close();

			setTitle(dialog.getFile() + " - DBE save..");
			DBEarrary = "";
			DBEsumarrary = "";

		} catch (Exception e2) {
			JOptionPane.showMessageDialog(this, "Save error");
		}

	}

}
