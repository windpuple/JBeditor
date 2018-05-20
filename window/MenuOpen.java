/* 
 *  
 * 2018.03.26 JB.Jeon ITT(katherine) project start.  
 * 
 *  
 */

package window;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class MenuOpen extends JDialog{

	public  MenuOpen() {
		
		
		FileDialog dialog = new FileDialog(this, "open", FileDialog.LOAD);
		dialog.setDirectory(".");
		dialog.setVisible(true);
		if (dialog.getFile() == null)
			return;
		String dfName = dialog.getDirectory() + dialog.getFile();

		File f = new File(dfName);

		//int fileSize = (int) f.length();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(dfName));
			// FileReader reader = new FileReader(dfName);

			JBeditormain.txtJBeditormain.setText("");

			String line;

			while ((line = reader.readLine()) != null) {

				JBeditormain.txtJBeditormainbuffer.append(line);
				JBeditormain.txtJBeditormainbuffer.append('\n');
			}

			JBeditormain.txtJBeditormain.setText(JBeditormain.txtJBeditormainbuffer.toString());

			JBeditormain.Finalarray = "";
			JBeditormain.Finalarray = JBeditormain.txtJBeditormainbuffer.toString();

			JBeditormain.txtJBeditormainbuffer.setLength(0);

			reader.close();

			setTitle(dialog.getFile() + " - ITT");
		
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(this, "Open Error");
		}

		
		
	}
	
	
}
