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

	int flag = 0;
	int j = 0;
	int cnt = 0;
	int binsize = 0;
	int BSflag = 0;
	int BEflag = 0;
	int firstflag = 1;
	

	public DBEwindow() {

		// DBEarrary = "DBE test 중입니다.";
		/*
		int p1 = JBeditormain.Finalarray.indexOf("WAFER ID");
		int p2 = JBeditormain.Finalarray.indexOf("FLAT ZONE");
		int p3 = JBeditormain.Finalarray.indexOf("LOT Number");
		
		waferid = JBeditormain.Finalarray.substring(p1+11,);
		flatzone = JBeditormain.Finalarray.substring(p2+10);
		lotnumber = JBeditormain.Finalarray.substring(p3+10);
		lotnumber = JBeditormain.Finalarray.subs
		System.out.println("wafer id : "+waferid);
		System.out.println("flat zone : "+flatzone);
		System.out.println("lot number : "+lotnumber);
		*/
		
		String header[] = JBeditormain.Finalarray.split(":");
		
		
		for(int i = 0; i < header.length; i++) {
			int p1 = header[i].indexOf(")");
			String sub = header[i].substring(p1-2);
			//System.out.println("hedear sub 1 "+i+" : "+sub);
			
			header[i] = header[i].replace(sub, "");
								
			//System.out.println("hedear "+i+" : "+header[i]);
		}
        
		
		
		for (int i = 0; i < JBeditormain.Finalarray.length(); i++) {

			cutmap[i] = JBeditormain.Finalarray.charAt(i);

			// System.out.println("debug 0 "+firstflag+flag+BSflag+BEflag+cutmap[i]);

			if (cutmap[i] == '\n' && flag == 1 && BEflag == 0) {

				BSflag = 1;
				// System.out.println("debug 1"+firstflag+flag+BSflag+BEflag+cutmap[i]);

			} else if (cutmap[i] == '|' && flag == 0 && firstflag == 0) {

				BEflag = 1;
				// System.out.println("debug 2 "+firstflag+flag+BSflag+BEflag+cutmap[i]);

			} else if (BSflag == 1 && BEflag == 0) {

				binsize++;
				// System.out.println("binsize 정보 : " + binsize);

			}

			if (cutmap[i] == '|' || flag == 1) {

				flag = 1;
				firstflag = 0;

				if (cutmap[i] == '\n') {

					flag = 0;

					defbuffer[j] = cutmap[i];

					j++;
					cnt++;

				} else {

					if (cutmap[i] < '0' || cutmap[i] > '9' || cutmap[i] == '1') {

						defbuffer[j] = cutmap[i];

						j++;
						cnt++;

					} else {

						cutmap[i] = 'X';

						defbuffer[j] = cutmap[i];

						j++;
						cnt++;

					}

				}

			} else if (cutmap[i] == '-') {
				flag = 0;
			}

		}

		// System.out.print("def buffer1 내용:");
		// System.out.print(defbuffer);
		
		
		
		DBEsumarrary = str1+header[2].substring(1)+hype+header[3].substring(1)+enter;
		//System.out.print(DBEsumarrary);
		
		DBEsumarrary = DBEsumarrary+str2+header[4].substring(1)+enter+enter;
		//System.out.print(DBEsumarrary);
		
		
		

		for (int i = 0; i < cnt; i++) {
			DBEarrary += Character.toString(defbuffer[i]);
		}

		if (binsize == 3) {
			DBEarrary = DBEarrary.replace("null", "");
			DBEarrary = DBEarrary.replace("|", "");
			DBEarrary = DBEarrary.replace("111", "  1");
			DBEarrary = DBEarrary.replace("11X", "  X");
			DBEarrary = DBEarrary.replace("XXX", "  X");
			DBEarrary = DBEarrary.replace("   1", "1");
			DBEarrary = DBEarrary.replace("   X", "X");
			DBEarrary = DBEarrary.replace("    ", " ");
			DBEarrary = DBEarrary.replace(" ", ".");
		} else if (binsize == 2) {
			DBEarrary = DBEarrary.replace("null", "");
			DBEarrary = DBEarrary.replace("|", "");
			DBEarrary = DBEarrary.replace("11", " 1");
			DBEarrary = DBEarrary.replace("1X", " X");
			DBEarrary = DBEarrary.replace("XX", " X");
			DBEarrary = DBEarrary.replace("  1", "1");
			DBEarrary = DBEarrary.replace("  X", "X");
			DBEarrary = DBEarrary.replace("   ", " ");
			DBEarrary = DBEarrary.replace(" ", ".");

		} else if (binsize == 1) {
			DBEarrary = DBEarrary.replace("null", "");
			DBEarrary = DBEarrary.replace("|", "");
			DBEarrary = DBEarrary.replace(" ", ".");

		}

		//System.out.print("debug 1 : " + DBEsumarrary + "\n");
		
		DBEsumarrary = DBEsumarrary + DBEarrary;
		
		//System.out.print("debug 2 : " + DBEsumarrary + "\n");
		// System.out.print(defbuffer);

		FileDialog dialog = new FileDialog(this, "Save", FileDialog.SAVE);
		dialog.setDirectory("."); // .은 지금폴더
		dialog.setVisible(true); // 박스는 그냥 틀이고
		if (dialog.getFile() == null)
			return; // 이걸빼면 취소를 해도 저장이됨
		String dfName = dialog.getDirectory() + dialog.getFile(); // 경로명 파일명
		// System.out.println(dfName);
		// 실제 저장은 여기에서
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(dfName));
			writer.write(DBEsumarrary);
			writer.close();

			setTitle(dialog.getFile() + " - DBE save..");
			DBEarrary ="";
			DBEsumarrary ="";
			
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(this, "Save error");
		}

	}

}
