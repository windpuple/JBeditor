package window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExelExport {
	public ExelExport() throws IOException, RowsExceededException, WriteException {
		// 엑셀 파일 지정
		File file = new File("./EXEL/ITT_Exel.xls");
		File path = new File("./EXEL/");

		// 파일이 없을 시 파일 생성
		if (!file.exists()) {

			path.mkdirs();

			file.createNewFile();

		}

		String[] ExelLine = JBeditormain.Finalarray.split("\n");
		String[][] ExelRowCol = new String[ExelLine.length][];
		
		for(int i = 0; i < ExelLine.length; i++) {
			
			ExelRowCol[i] = ExelLine[i].split("[,\\s]");
		
		}
		
		
		// 엑셀 객체 생성
		WritableWorkbook workbook = Workbook.createWorkbook(file);

		// 쉬트 생성 ( 쉬트명, 인덱스)
		WritableSheet sheet = workbook.createSheet("ITT_Exel_Export", 0);

		// 셀(Label) 생성
		Label label;
		for (int i = 0; i < ExelRowCol.length; i++) {
			for (int j = 0; j < ExelRowCol[i].length; j++) {
				// Label 방식으로 생성하여 Add 하여야 한다.
				label = null;
				label = new Label(j, i, ExelRowCol[i][j]);
				sheet.addCell(label);
			}
		}

		// 저장 및 닫기
		workbook.write();
		workbook.close();

		// folder 권한 설정.
		// String cmd = "chmod 750 -R " + path;
		// 750, 770등 권한을 부여하고 -R옵션은 하위 디렉토리 생성시 같은 권한 부여 옵션으로
		// 사용하면 하위 디렉토리 생성시 같은 권한으로 계속 생성
		// Runtime rt = Runtime.getRuntime();
		// Process prc = rt.exec(cmd);

		// MS Windows Only
		Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getAbsolutePath());

		// or
		// Process p= Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "
		// +
		// file.getAbsolutePath());

		try {

			p.waitFor();

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}
}
