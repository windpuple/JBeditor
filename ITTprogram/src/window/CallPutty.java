package window;

import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class CallPutty extends JDialog {

	public CallPutty() throws IOException, RowsExceededException, WriteException {

		File file = new File("./SSH/putty.exe");
	
		Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getAbsolutePath());

		try {

			p.waitFor();

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

}
