/* 
 *  
 * 2018.03.26 JB.Jeon ITT(katherine) project start.  
 * 
 *  
 */

package window;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.ScriptStyle;
import jxl.format.UnderlineStyle;
import jxl.write.WritableWorkbook; 
import jxl.Workbook;
import jxl.write.Formula;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet ;
import jxl.write.WritableCellFormat;
import jxl.write.Label;
import jxl.write.Blank;
import jxl.write.WriteException;

public class VGpgmBin2ExelFomat extends JDialog {

	String backline[] = JBeditormain.Finalarray.split("\n");

	public static StringBuffer VGprogramArrary = new StringBuffer();
	public static StringBuffer VGprogramArraryBuffer = new StringBuffer();

	public VGpgmBin2ExelFomat() throws IOException, RowsExceededException, WriteException {

		VGprogramArrary.setLength(0);
		VGprogramArraryBuffer.setLength(0);

		int k = 0;
		int cnt = 0;
		int sortingcnt = 0;
		int Hbinsortingcnt = 0;
		int BinIndex = 0;

		for (int i = 0; i < backline.length; i++) {

			if (backline[i].contains("stop_bin")) {

				cnt++;
			}

		}

		String[][] bodyline = new String[cnt][];

		for (int i = 0; i < backline.length; i++) {

			if (backline[i].contains("stop_bin")) {

				backline[i] = backline[i].replace(" ", "");
				bodyline[k] = backline[i].split("[,\\s]");

				k++;

			}

		}

		for (int i = 0; i < bodyline.length; i++) {

			for (int j = 0; j < bodyline[i].length; j++) {

				// System.out.println("backline" + i + "," + j + ":" + bodyline[i][j]);

			}

		}

		String[] VGsoftbin = new String[bodyline.length];
		String[] VGhardbin = new String[bodyline.length];
		String[] VGbinName = new String[bodyline.length];
		String[] VGGoodBadFlag = new String[bodyline.length];
		String[] LotYld = new String[bodyline.length];
		String[] WFYld = new String[bodyline.length];
		String[] YldChgDesc = new String[bodyline.length];
		String[] DownFlag = new String[bodyline.length];

		for (int i = 0; i < bodyline.length; i++) {

			bodyline[i][0] = bodyline[i][0].replace("\"", "");

			VGsoftbin[i] = bodyline[i][0].replace("stop_bin", "");
			VGhardbin[i] = bodyline[i][6];
			VGbinName[i] = bodyline[i][1].replace("\"", "");
			VGGoodBadFlag[i] = bodyline[i][3];
			
			if (VGGoodBadFlag[i].contains("good")) {
				LotYld[i] = "90";
			} else {
				LotYld[i] = "0";
			}
			if (VGGoodBadFlag[i].contains("good")) {
				WFYld[i] = "80";
			} else {
				WFYld[i] = "0";
			}
			YldChgDesc[i] = "initial";
			DownFlag[i] = " ";

		}

		for (int j = 0; j < bodyline.length; j++) {

			for (int i = 0; i < bodyline.length-1; i++) {

				if (Integer.parseInt(VGsoftbin[i]) > Integer.parseInt(VGsoftbin[i + 1])) {

					String sub1 = VGsoftbin[i];

					VGsoftbin[i] = VGsoftbin[i + 1];
					VGsoftbin[i + 1] = sub1;

					String sub2 = VGhardbin[i];

					VGhardbin[i] = VGhardbin[i + 1];
					VGhardbin[i + 1] = sub2;

					String sub3 = VGbinName[i];

					VGbinName[i] = VGbinName[i + 1];
					VGbinName[i + 1] = sub3;

					String sub4 = VGGoodBadFlag[i];

					VGGoodBadFlag[i] = VGGoodBadFlag[i + 1];
					VGGoodBadFlag[i + 1] = sub4;

					String sub5 = LotYld[i];

					LotYld[i] = LotYld[i + 1];
					LotYld[i + 1] = sub5;

					String sub6 = WFYld[i];

					WFYld[i] = WFYld[i + 1];
					WFYld[i + 1] = sub6;

					String sub7 = YldChgDesc[i];

					YldChgDesc[i] = YldChgDesc[i + 1];
					YldChgDesc[i + 1] = sub7;

					String sub8 = DownFlag[i];

					DownFlag[i] = DownFlag[i + 1];
					DownFlag[i + 1] = sub8;

				}

			}

		}

		for (int j = 0; j < bodyline.length; j++) {
				
			for (int i = j+1; i < bodyline.length-1; i++) {

				//System.out.println("VGsoftbin[j]"+j+":"+VGsoftbin[j]+" VGsoftbin[i]"+i+":"+VGsoftbin[i]);
				
				if (VGsoftbin[j].equals(VGsoftbin[i])) {

					VGsoftbin[i] = "erase";

					VGhardbin[i] = "erase";

					VGbinName[i] = "erase";

					VGGoodBadFlag[i] = "erase";
	
					LotYld[i] = "erase";

					WFYld[i] = "erase";
	
					YldChgDesc[i] = "erase";
	
					DownFlag[i] = "erase";
	

				}

			}

		}
		
		for (int i = 0; i < bodyline.length; i++) {
			
			
			
			if (VGsoftbin[i].equals("erase")) {

				
			} else {
				
				sortingcnt++;
				
			}
			


	}
		
		//System.out.println("sortingcnt:"+sortingcnt);
		
		
		String[] RVGsoftbin = new String[sortingcnt+1];
		String[] RVGhardbin = new String[sortingcnt+1];
		String[] RVGbinName = new String[sortingcnt+1];
		String[] RVGGoodBadFlag = new String[sortingcnt+1];
		String[] RLotYld = new String[sortingcnt+1];
		String[] RWFYld = new String[sortingcnt+1];
		String[] RYldChgDesc = new String[sortingcnt+1];
		String[] RDownFlag = new String[sortingcnt+1];
		
		int x = 0;
		
		for (int i = 0; i < bodyline.length; i++) {
			
		
				
				if (VGsoftbin[i].equals("erase")) {

					
				} else {
					
					RVGsoftbin[x] = VGsoftbin[i];

					RVGhardbin[x] = VGhardbin[i];

					RVGbinName[x] = VGbinName[i];

					RVGGoodBadFlag[x] = VGGoodBadFlag[i];
	
					RLotYld[x] = LotYld[i];

					RWFYld[x] = WFYld[i];
	
					RYldChgDesc[x] = YldChgDesc[i];
	
					RDownFlag[x] = DownFlag[i];
					
					//System.out.println("RVGsoftbin[x]"+x+":"+RVGsoftbin[x]);
					
					x++;
					
				}
				
	

		}
		
		
		RVGsoftbin[RVGsoftbin.length-1] = "99";
		RVGhardbin[RVGsoftbin.length-1] = "6";
		RVGbinName[RVGsoftbin.length-1] = "UNTESTED";
		RVGGoodBadFlag[RVGsoftbin.length-1] = "bad";
		RLotYld[RVGsoftbin.length-1] = "0";
		RWFYld[RVGsoftbin.length-1] = "0";
		RYldChgDesc[RVGsoftbin.length-1] = "initial";
		RDownFlag[RVGsoftbin.length-1] = " ";

		for (int i = 0; i < RVGsoftbin.length; i++) {

			if (RVGGoodBadFlag[i].equals("good")) {

				RVGGoodBadFlag[i] = "Y";

			} else if (RVGGoodBadFlag[i].equals("bad")) {

				RVGGoodBadFlag[i] = " ";

			}

		}
		
		
		String[] HBinNo = new String[RVGhardbin.length];
		String[] HBinName = new String[RVGhardbin.length];
		String[] GoodFlag = new String[RVGhardbin.length];
		String[] LotYieldSum = new String[RVGhardbin.length];
		String[] WFYieldSum = new String[RVGhardbin.length];
		String[] YldChgDescSum = new String[RVGhardbin.length];
		String[] DownFlagSum = new String[RVGhardbin.length];
		String[] NoRetestAll = new String[RVGhardbin.length];
		String[] NoRetestNth = new String[RVGhardbin.length];

		for (int i = 0; i < RVGhardbin.length; i++) {
			
			HBinNo[i] = RVGhardbin[i];
			HBinName[i] = RVGhardbin[i];			
			GoodFlag[i] = RVGGoodBadFlag[i];
		    LotYieldSum[i] = RLotYld[i];		    	
		    WFYieldSum[i] = RWFYld[i];
		    YldChgDescSum[i] = RYldChgDesc[i];
		    DownFlagSum[i] = RDownFlag[i];
		    NoRetestAll[i] = " ";
		    NoRetestNth[i] = " ";
			
		}
		

		for (int j = 0; j < HBinNo.length; j++) {
			
			for (int i = j+1; i < HBinNo.length-1; i++) {

				//System.out.println("HBinNo[j]"+j+":"+HBinNo[j]+" HBinNo[i]"+i+":"+HBinNo[i]);
				
				if (HBinNo[j].equals(HBinNo[i])) {
					
					HBinNo[i] = "erase";
					HBinName[i] = "erase";			
					GoodFlag[i] = "erase";
				    LotYieldSum[i] = "erase";		    	
				    WFYieldSum[i] = "erase";
				    YldChgDescSum[i] = "erase";
				    DownFlagSum[i] = "erase";
				    NoRetestAll[i] = "erase";
				    NoRetestNth[i] = "erase";
					
				}

			}

		}
		
		
		for (int i = 0; i < HBinNo.length; i++) {
			
			
			
			if (HBinNo[i].equals("erase")) {

				
			} else {
				
				Hbinsortingcnt++;
				
			}
			


	}
		String[] RHBinNo = new String[Hbinsortingcnt];
		String[] RHBinName = new String[Hbinsortingcnt];
		String[] RGoodFlag = new String[Hbinsortingcnt];
		String[] RLotYieldSum = new String[Hbinsortingcnt];
		String[] RWFYieldSum = new String[Hbinsortingcnt];
		String[] RYldChgDescSum = new String[Hbinsortingcnt];
		String[] RDownFlagSum = new String[Hbinsortingcnt];
		String[] RNoRetestAll = new String[Hbinsortingcnt];
		String[] RNoRetestNth = new String[Hbinsortingcnt];
		
		int y = 0;
		
		for (int i = 0; i < HBinNo.length; i++) {
			
		
				
				if (HBinNo[i].equals("erase")) {

					
				} else {
					
					RHBinNo[y] = HBinNo[i];

					RHBinName[y] = HBinName[i];

					RGoodFlag[y] = GoodFlag[i];

					RLotYieldSum[y] = LotYieldSum[i];
	
					RWFYieldSum[y] = WFYieldSum[i];

					RYldChgDescSum[y] = YldChgDescSum[i];
	
					RDownFlagSum[y] = DownFlagSum[i];
	
					RNoRetestAll[y] = NoRetestAll[i];
					
					RNoRetestNth[y] = NoRetestNth[i];
					
					y++;
					
				}
				
	

		}
		
		
		// each cell value check
		/*
		for (int i = 0; i < RVGhardbin.length; i++) {

			System.out.println("RVGsoftbin" + i + ":" + RVGsoftbin[i]);
			System.out.println("RVGhardbin" + i + ":" + RVGhardbin[i]);
			System.out.println("RVGbinName" + i + ":" + RVGbinName[i]);
			System.out.println("RVGGoodBadFlag" + i + ":" + RVGGoodBadFlag[i]);
			System.out.println("RLotYld" + i + ":" + RLotYld[i]);
			System.out.println("RWFYld" + i + ":" + RWFYld[i]);
			System.out.println("RYldChgDesc" + i + ":" + RYldChgDesc[i]);
			System.out.println("RDownFlag" + i + ":" + RDownFlag[i]);

		}
       
		
		for (int i = 0; i < RHBinNo.length; i++) {

			System.out.println("HBinNo" + i + ":" + RHBinNo[i]);
			System.out.println("HBinName" + i + ":" + RHBinName[i]);
			System.out.println("GoodFlag" + i + ":" + RGoodFlag[i]);
			System.out.println("LotYieldSum" + i + ":" + RLotYieldSum[i]);
			System.out.println("WFYieldSum" + i + ":" + RWFYieldSum[i]);
			System.out.println("YldChgDescSum" + i + ":" + RYldChgDescSum[i]);
			System.out.println("DownFlagSum" + i + ":" + RDownFlagSum[i]);
			System.out.println("NoRetestAll" + i + ":" + RNoRetestAll[i]);
			System.out.println("NoRetestNth" + i + ":" + RNoRetestNth[i]);

		}
		
		*/
		
		
		File file = new File("./EXEL/ITT_Exel_SetupBin.xls");
		File path = new File("./EXEL/");

		// 파일이 없을 시 파일 생성
		if (!file.exists()) {

			path.mkdirs();

			file.createNewFile();

		}

		// 엑셀 객체 생성
		WritableWorkbook workbook = Workbook.createWorkbook(file);

		// 쉬트 생성 ( 쉬트명, 인덱스)
		WritableSheet sheet = workbook.createSheet("ITT_Exel_SetupBin", 0);

		WritableCellFormat cellformat1 = new WritableCellFormat();
		cellformat1.setBorder(Border.ALL, BorderLineStyle.THIN);  
		// 보더와 보더라인스타일 설정
		
		WritableCellFormat cellformat2 = new WritableCellFormat();
		cellformat2.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellformat2.setBackground(Colour.ICE_BLUE);                    
		// 배경색 설정
		
		// 셀(Label) 생성
		Label label;
		
		label = new Label(0, 0, "Bin No",cellformat2);
		sheet.addCell(label);
		label = new Label(1, 0, "HNin No",cellformat2);
		sheet.addCell(label);
		label = new Label(2, 0, "Bin Name",cellformat2);
		sheet.addCell(label);
		label = new Label(3, 0, "Good Flag",cellformat2);
		sheet.addCell(label);
		label = new Label(4, 0, "Lot Yield",cellformat2);
		sheet.addCell(label);
		label = new Label(5, 0, "WF Yield",cellformat2);
		sheet.addCell(label);
		label = new Label(6, 0, "Yld Chg Desc",cellformat2);
		sheet.addCell(label);
		label = new Label(7, 0, "Down Flag",cellformat2);
		sheet.addCell(label);
		label = new Label(10, 0, "HbinNo",cellformat2);
		sheet.addCell(label);
		label = new Label(11, 0, "HBin Name",cellformat2);
		sheet.addCell(label);
		label = new Label(12, 0, "Good Flag",cellformat2);
		sheet.addCell(label);
		label = new Label(13, 0, "Lot Yield",cellformat2);
		sheet.addCell(label);
		label = new Label(14, 0, "WF Yield",cellformat2);
		sheet.addCell(label);
		label = new Label(15, 0, "Yld Chg Desc",cellformat2);
		sheet.addCell(label);
		label = new Label(16, 0, "Down Flag",cellformat2);
		sheet.addCell(label);
		label = new Label(17, 0, "No Retest(All)",cellformat2);
		sheet.addCell(label);
		label = new Label(18, 0, "No Retest Nth",cellformat2);
		sheet.addCell(label);
		
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < RVGhardbin.length; i++) {
				// Label 방식으로 생성하여 Add 하여야 한다.
				label = null;

				if (j == 0) {

					label = new Label(j, i+1, RVGsoftbin[i],cellformat1);
					sheet.addCell(label);

				} else if (j == 1) {

					label = new Label(j, i+1, RVGhardbin[i],cellformat1);
					sheet.addCell(label);

				} else if (j == 2) {

					label = new Label(j, i+1, RVGbinName[i],cellformat1);
					sheet.addCell(label);

				} else if (j == 3) {

					label = new Label(j, i+1, RVGGoodBadFlag[i],cellformat1);
					sheet.addCell(label);

				} else if (j == 4) {

					label = new Label(j, i+1, RLotYld[i],cellformat1);
					sheet.addCell(label);

				} else if (j == 5) {

					label = new Label(j, i+1, RWFYld[i],cellformat1);
					sheet.addCell(label);

				} else if (j == 6) {

					label = new Label(j, i+1, RYldChgDesc[i],cellformat1);
					sheet.addCell(label);

				} else if (j == 7) {

					label = new Label(j, i+1, RDownFlag[i],cellformat1);
					sheet.addCell(label);

				}
			}
		}

		
		for (int j = 10; j < 19; j++) {
			for (int i = 0; i < RHBinNo.length; i++) {
				// Label 방식으로 생성하여 Add 하여야 한다.
				
				if (j == 10) {

					label = new Label(j, i+1, RHBinNo[i],cellformat1);
					sheet.addCell(label);

				} else if (j == 11) {

					label = new Label(j, i+1, RHBinName[i],cellformat1);
					sheet.addCell(label);

				} else if (j == 12) {

					label = new Label(j, i+1, RGoodFlag[i],cellformat1);
					sheet.addCell(label);

				} else if (j == 13) {

					label = new Label(j, i+1, RLotYieldSum[i],cellformat1);
					sheet.addCell(label);

				} else if (j == 14) {

					label = new Label(j, i+1, RWFYieldSum[i],cellformat1);
					sheet.addCell(label);

				} else if (j == 15) {

					label = new Label(j, i+1, RYldChgDescSum[i],cellformat1);
					sheet.addCell(label);

				} else if (j == 16) {

					label = new Label(j, i+1, RDownFlagSum[i],cellformat1);
					sheet.addCell(label);

				} else if (j == 17) {

					label = new Label(j, i+1, RNoRetestAll[i],cellformat1);
					sheet.addCell(label);

				} else if (j == 18) {

					label = new Label(j, i+1, RNoRetestNth[i],cellformat1);
					sheet.addCell(label);

				}
			}
		}
		
		// 저장 및 닫기
		workbook.write();
		workbook.close();

		Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getAbsolutePath());

		try {

			p.waitFor();

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

}
