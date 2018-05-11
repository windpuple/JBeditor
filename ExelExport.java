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
		// ���� ���� ����
		File file = new File("./EXEL/ITT_Exel.xls");
		File path = new File("./EXEL/");

		// ������ ���� �� ���� ����
		if (!file.exists()) {

			path.mkdirs();

			file.createNewFile();

		}

		String[] ExelLine = JBeditormain.Finalarray.split("\n");
		String[][] ExelRowCol = new String[ExelLine.length][];
		
		for(int i = 0; i < ExelLine.length; i++) {
			
			ExelRowCol[i] = ExelLine[i].split("[,\\s]");
		
		}
		
		
		// ���� ��ü ����
		WritableWorkbook workbook = Workbook.createWorkbook(file);

		// ��Ʈ ���� ( ��Ʈ��, �ε���)
		WritableSheet sheet = workbook.createSheet("ITT_Exel_Export", 0);

		// ��(Label) ����
		Label label;
		for (int i = 0; i < ExelRowCol.length; i++) {
			for (int j = 0; j < ExelRowCol[i].length; j++) {
				// Label ������� �����Ͽ� Add �Ͽ��� �Ѵ�.
				label = null;
				label = new Label(j, i, ExelRowCol[i][j]);
				sheet.addCell(label);
			}
		}

		// ���� �� �ݱ�
		workbook.write();
		workbook.close();

		// folder ���� ����.
		// String cmd = "chmod 750 -R " + path;
		// 750, 770�� ������ �ο��ϰ� -R�ɼ��� ���� ���丮 ������ ���� ���� �ο� �ɼ�����
		// ����ϸ� ���� ���丮 ������ ���� �������� ��� ����
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
