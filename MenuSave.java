/* 
 *  
 * 2018.03.26 JB.Jeon ITT(katherine) project start.  
 * 
 *  
 */

package window;

import java.awt.FileDialog;
import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class MenuSave extends JDialog{

	public  MenuSave() {
	FileDialog dialog = new FileDialog(this, "Save", FileDialog.SAVE);
	dialog.setDirectory(".");
	dialog.setVisible(true);
	if (dialog.getFile() == null)
		return;
	String dfName = dialog.getDirectory() + dialog.getFile();

	try {
		BufferedWriter writer = new BufferedWriter(new FileWriter(dfName));
		writer.write(JBeditormain.txtJBeditormain.getText());
		writer.close();

		setTitle(dialog.getFile() + " - text Save");
	} catch (Exception e2) {
		JOptionPane.showMessageDialog(this, "Save error");
	}
	
	
}

}
