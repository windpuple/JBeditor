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

public class MAPwindow extends JFrame {

	public static String MAParrary = "";
	public static String MAPsumarrary = "";
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
	

	public MAPwindow() {
		
		String backline[] = JBeditormain.Finalarray.split("\r\n");
		
				
			String str1[] = backline[0].split("\\s");
			String str2[] = backline[1].split("\\s");
			String str3[] = backline[2].split("\\s");
			String str4[] = backline[3].split("\\s");		
			String str5[] = backline[4].split("\\s");
			String str6[] = backline[5].split("\\s");
			String str7[] = backline[6].split("\\s");

			for(int i = 0; i < 7; i++) {
				
			System.out.println(backline[i]);
			
			}

			/*
		
		MAPsumarrary = MAPsumarrary + MAParrary;
	

		FileDialog dialog = new FileDialog(this, "Save", FileDialog.SAVE);
		dialog.setDirectory("."); // .�� ��������
		dialog.setVisible(true); // �ڽ��� �׳� Ʋ�̰�
		if (dialog.getFile() == null)
			return; // �̰ɻ��� ��Ҹ� �ص� �����̵�
		String dfName = dialog.getDirectory() + dialog.getFile(); // ��θ� ���ϸ�
		// System.out.println(dfName);
		// ���� ������ ���⿡��
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(dfName));
			writer.write(MAPsumarrary);
			writer.close();

			setTitle(dialog.getFile() + " - DBE save..");
			MAParrary ="";
			MAPsumarrary ="";
			
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(this, "Save error");
		}
*/
			
	}

}