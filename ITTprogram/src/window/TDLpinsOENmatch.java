/* 
 *  
 * 2018.03.26 JB.Jeon ITT(katherine) project start.  
 * 
 *  
 */

package window;

public class TDLpinsOENmatch {

	String backline[] = JBeditormain.Finalarray.split("\n");
	String[][] TDLpinsLine = new String[backline.length][];

	public static StringBuffer TDLpinsOENmatchbuffer = new StringBuffer();

	public TDLpinsOENmatch() {

		TDLpinsOENmatchbuffer.setLength(0);

		int k = 0;
		int cnt = 0;
		int name = 0;

		for (int i = 0; i < backline.length; i++) {

			TDLpinsLine[i] = backline[i].split(",");

		}

		for (int i = 0; i < TDLpinsLine.length; i++) {

			for (int j = 0; j < TDLpinsLine[i].length; j++) {

				if (TDLpinsLine[i][j].contains("OEN") || TDLpinsLine[i][j].contains("oen")
						|| TDLpinsLine[i][j].contains("DIR") || TDLpinsLine[i][j].contains("dir")) {

					cnt++;

				}

			}

		}

		String[] TDLoenPins = new String[cnt];

		for (int i = 0; i < TDLpinsLine.length; i++) {

			for (int j = 0; j < TDLpinsLine[i].length; j++) {

				if (TDLpinsLine[i][j].contains("OEN") || TDLpinsLine[i][j].contains("oen")
						|| TDLpinsLine[i][j].contains("DIR") || TDLpinsLine[i][j].contains("dir")) {

					TDLoenPins[k] = "\"in:" + TDLpinsLine[i][j].replace("\"", "") + "=1X,out:"
							+ TDLpinsLine[i][j].replace("\"", "") + "=0\"";

					// System.out.println("TDLoenPins "+k+":"+TDLoenPins[k]);

					k++;

				}

				// System.out.println("TDLpinsLine " + i + "|" + j + ":" + TDLpinsLine[i][j]);

			}

		}

		String[] changeOENnumber = new String[TDLoenPins.length];

		for (int j = 0; j < TDLoenPins.length; j++) {

			changeOENnumber[j] = TDLoenPins[j];

			for (int l = 0; l < 10; l++) {

				String changenumber = String.format("%02d", l);
				String targetnumber = String.format("%d", l);

				if (TDLoenPins[j].contains(changenumber)) {

					changeOENnumber[j] = changeOENnumber[j].replace(changenumber, targetnumber);

					// System.out.println("number change:"+TDLoenPins[j].replace(changenumber,
					// targetnumber));
				}

			}

			for (int l = 0; l < 100; l++) {

				String changenumber = String.format("%03d", l);
				String targetnumber = String.format("%d", l);

				if (TDLoenPins[j].contains(changenumber)) {

					changeOENnumber[j] = changeOENnumber[j].replace(changenumber, targetnumber);

					// System.out.println("number change:"+TDLoenPins[j].replace(changenumber,
					// targetnumber));
				}

			}

		}

		String[] TDLpinsLinebuffer = new String[TDLpinsLine.length];

		for (int i = 0; i < TDLpinsLine.length; i++) {

			TDLpinsLinebuffer[i] = TDLpinsLine[i][0].replace("\"", "");
			TDLpinsLinebuffer[i] = TDLpinsLinebuffer[i].replaceAll("[a-z]", "");

			if (i > -1 && i < 4) {

				TDLpinsLinebuffer[i] = "No match pattern";

			} else if (i == TDLpinsLine.length - 1) {

				TDLpinsLinebuffer[i] = "No match pattern";
			}

			// System.out.println(TDLpinsLinebuffer[i]);

		}

		for (int i = 0; i < TDLpinsLine.length; i++) {

			for (int j = 0; j < TDLoenPins.length; j++) {

				if (changeOENnumber[j].contains(TDLpinsLinebuffer[i]) && TDLpinsLinebuffer[i].contains("OEN") == false
						&& TDLpinsLinebuffer[i].contains("oen") == false
						&& TDLpinsLinebuffer[i].contains("DIR") == false
						&& TDLpinsLinebuffer[i].contains("dir") == false) {

					TDLpinsLine[i][1] = "inout";
					TDLpinsLine[i][2] = TDLoenPins[j];

					// System.out.println("+TDLoenPins[j] "+j+":"+TDLoenPins[j]);

				}

				if (TDLpinsLinebuffer[i].contains(".")) {

					String[] sub = TDLpinsLinebuffer[i].split("\\.");

					for (int m = 0; m < sub.length; m++) {

						if (changeOENnumber[j].contains(sub[m].replace("\"", ""))) {

							name++;

						}

						// System.out.println("sub[m]:"+sub[m]+":"+m+":"+j+":"+name);

					}

					if (name > 1 && TDLpinsLinebuffer[i].contains("OEN") == false
							&& TDLpinsLinebuffer[i].contains("oen") == false
							&& TDLpinsLinebuffer[i].contains("DIR") == false
							&& TDLpinsLinebuffer[i].contains("dir") == false) {

						TDLpinsLine[i][1] = "inout";
						TDLpinsLine[i][2] = TDLoenPins[j];

						// System.out.println(TDLpinsLine[i][0]+":"+TDLpinsLine[i][2]+":"+j+":"+i+":"+name);

					}

					// System.out.println("+TDLoenPins[j] "+j+":"+TDLoenPins[j]);

				}

				name = 0;
				// System.out.println("TDLpinsLine "+i+"|"+j+":"+TDLpinsLine[i][j]);

			}

		}

		for (int i = 0; i < TDLpinsLine.length; i++) {

			for (int j = 0; j < TDLpinsLine[i].length; j++) {

				if (i == TDLpinsLine.length - 1 && j == TDLpinsLine[i].length - 1) {

					TDLpinsOENmatchbuffer.append(TDLpinsLine[i][j]);

				} else if (j == TDLpinsLine[i].length - 1) {

					TDLpinsOENmatchbuffer.append(TDLpinsLine[i][j] + "\n");

				} else {

					TDLpinsOENmatchbuffer.append(TDLpinsLine[i][j] + ",");

				}

			}

		}

	}

}
