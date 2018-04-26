/* 
 *  
 * 2018.03.26 JB.Jeon ITT(katherine) project start.  
 * 
 *  
 */

package window;

public class rotation90 {

	
	public static String resultbuffer;

	
	public rotation90() {
	
	char[] read = new char[JBeditormain.Finalarray.length()];
	char[] rotationbuffer = new char[JBeditormain.Finalarray.length()];

	String header = "";
	String tail = "";
	String body = "";
	String totalbuffer = "";

	int flag = 0;
	int k = 0;
	int enter = 0;
	int firsttouch = 0;
	int cnt = 0;
	int xlength = 0, ylength = 0;

	resultbuffer = "";

		// ���Ϸ� �о�帰, map�� char ��ü map�� read�� map �κ��� rotationbuffer �迭�� �и� .

		for (int j = 0; j < JBeditormain.Finalarray.length(); j++) {

			read[j] = JBeditormain.Finalarray.charAt(j);

			if (read[j] == '|') {

				firsttouch++;
				flag = 1;

			} else if (read[j] == '+') {

				flag = 1;

			} else if (read[j] == '-') {

				flag = 0;

			}

			if (!(read[j] == '+' || read[j] == '|')) {

				if (flag == 1 || (flag == 1 && read[j] == '\n')) {

					rotationbuffer[k] = read[j];

					k++;

				}

			}

			if (read[j] == '\n') {

				flag = 0;

			}

			if (firsttouch == 1 && flag == 0) {

				cnt++;

			}

		}

		// �о�� char�� string totalbuffer�� ��ȯ.
		for (int i = 0; i < read.length; i++) {
			totalbuffer += Character.toString(read[i]);
		}

		// totalbuffer���� �ʿ��� head �κи� header�� tail�� string ����.
		int p1 = totalbuffer.indexOf("**********  WAFER MAP");
		String sub1 = totalbuffer.substring(0, p1 - 1);
		header = sub1;

		int p2 = totalbuffer.indexOf("**********  BIN SUMMARY");
		String sub2 = totalbuffer.substring(p2);
		tail = sub2;

		// rotationbuffer�� x,y���̸� ����.

		for (int i = 0; i < rotationbuffer.length; i++) {

			if (rotationbuffer[i] == '\n') {

				ylength++;

			}

		}

		// System.out.println("ylength : " + ylength);

		for (int i = 0; i < rotationbuffer.length; i++) {

			xlength++;

			if (rotationbuffer[i] == '\n') {

				break;

			}

		}

		// System.out.println("xlength : " + xlength);

		int sxlength = (xlength / cnt) + 1;
		int sylength = ylength;
		int count = cnt;
		int ylength90 = sxlength - 1;
		int xlength90 = sylength + 1;

		String[][] rotationarray = new String[sylength][sxlength];
		String[][] rotationarray90 = new String[ylength90][xlength90];

		String imsibuffer = "";

		// body string�� �� ������ �迭 �ΰ��� �ʱ�ȭ.

		for (int i = 0; i < sylength; i++) {

			for (int j = 0; j < sxlength; j++) {

				rotationarray[i][j] = "";

			}

		}

		for (int i = 0; i < ylength90; i++) {

			for (int j = 0; j < xlength90; j++) {

				rotationarray90[i][j] = "";

			}

		}

		// char rotationbuffer�� string rotationarray 3�ڸ� ������ �迭�� ����.

		for (int x = 0, h = 0; x < sylength; x++) {

			for (int z = 0; z < sxlength; z++, h = h + count) {

				for (int m = 0; m < cnt; m++) {

					if (z == sxlength - 1) {

						rotationarray[x][z] = "\n";

						m = cnt;

					} else {

						imsibuffer = Character.toString(rotationbuffer[h + m]);
						rotationarray[x][z] += imsibuffer;

					}

				}

				if (rotationarray[x][z] == "\n") {
					count = 1;
				} else {
					count = cnt;
				}

			}

		}

		// map�� �ð�������� ����, rotation90 string 2���� �迭�� ����.

		for (int i = 0, z = 0; i < ylength90; i++, z++) {

			for (int j = 0, x = sylength - 1; j < xlength90; j++, x--) {

				if (j == xlength90 - 1) {

					rotationarray90[i][j] = "\n";

				} else if (j < xlength90 - 1 && x > -1) {

					rotationarray90[i][j] = rotationarray[x][z];

				}

			}

		}

		// 90�� ���ư� map�� body string�� ����
		for (int i = 0; i < ylength90; i++) {

			for (int j = 0; j < xlength90; j++) {

				body += rotationarray90[i][j];

			}

		}

		// resultbuffer string�� header string ����.

		resultbuffer = header;
		resultbuffer += "\n**********  WAFER MAP  **********\n\n";

		// numbering x��

		for (int i = 0; i < cnt; i++) {

			resultbuffer += " ";
		}

		if (cnt == 4) {

			for (int i = 1; i < xlength90; i++) {

				if (i < xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i / 100);

				} else if (i == xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i / 100) + "\n";

				}

			}

			for (int i = 0; i < cnt; i++) {

				resultbuffer += " ";
			}

			for (int i = 1; i < xlength90; i++) {

				if (i < xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i / 10);

				} else if (i == xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i / 10) + "\n";

				}

			}

			for (int i = 0; i < cnt; i++) {

				resultbuffer += " ";
			}

			for (int i = 1; i < xlength90; i++) {

				if (i < xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i % 10);

				} else if (i == xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i % 10) + "\n";

				}

			}

		} else if (cnt == 3) {

			for (int i = 1; i < xlength90; i++) {

				if (i < xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i / 10);

				} else if (i == xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i / 10) + "\n";

				}

			}

			for (int i = 0; i < cnt; i++) {

				resultbuffer += " ";
			}

			for (int i = 1; i < xlength90; i++) {

				if (i < xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i % 10);

				} else if (i == xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i % 10) + "\n";

				}

			}

		} else if (cnt == 2) {

			for (int i = 1; i < xlength90; i++) {

				if (i < xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i);

				} else if (i == xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i) + "\n";

				}

			}

		}

		for (int i = 0; i < cnt; i++) {

			resultbuffer += " ";
		}

		for (int i = cnt; i < xlength90 * cnt + 1; i++) {

			if ((i - (cnt - 1)) % (cnt * 5) == 0) {

				resultbuffer += "+";

			} else if (i == xlength90 * cnt) {

				resultbuffer += "\n";

			} else {

				resultbuffer += "-";

			}

		}

		// body string���� bodybuffer�� �ű�� y ��ǥ ����

		StringBuffer bodybuffer = new StringBuffer(body);

		for (int i = 0, j = 1; i < bodybuffer.length(); i = i + (xlength90 * cnt) + 1, j++) {

			if (j < 10 && cnt == 4) {

				if (j % 5 == 0) {

					bodybuffer.insert(i, "  " + j + "+");

				} else {

					bodybuffer.insert(i, "  " + j + "|");

				}

			} else if (j < 10 && cnt == 3) {

				if (j % 5 == 0) {

					bodybuffer.insert(i, " " + j + "+");

				} else {

					bodybuffer.insert(i, " " + j + "|");

				}

			} else if (j < 10 && cnt == 2) {

				if (j % 5 == 0) {

					bodybuffer.insert(i, j + "+");

				} else {

					bodybuffer.insert(i, j + "|");

				}

			}

			if (9 < j && j < 100 && cnt == 4) {

				if (j % 5 == 0) {

					bodybuffer.insert(i, " " + j + "+");

				} else {

					bodybuffer.insert(i, " " + j + "|");

				}

			} else if (9 < j && j < 100 && cnt == 3) {

				if (j % 5 == 0) {

					bodybuffer.insert(i, j + "+");

				} else {

					bodybuffer.insert(i, j + "|");

				}

			}

			if (99 < j && j < 1000 && cnt == 4) {

				if (j % 5 == 0) {

					bodybuffer.insert(i, j + "+");

				} else {

					bodybuffer.insert(i, j + "|");

				}

			}

		}

		// bodybuffer �� body string����

		body = bodybuffer.toString();

		resultbuffer += body;

		// numbering x��

		for (int i = 0; i < cnt; i++) {

			resultbuffer += " ";
		}

		for (int i = cnt; i < xlength90 * cnt + 1; i++) {

			if ((i - (cnt - 1)) % (cnt * 5) == 0) {

				resultbuffer += "+";

			} else if (i == xlength90 * cnt) {

				resultbuffer += "\n";

			} else {

				resultbuffer += "-";

			}

		}

		for (int i = 0; i < cnt; i++) {

			resultbuffer += " ";
		}

		if (cnt == 4) {

			for (int i = 1; i < xlength90; i++) {

				if (i < xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i / 100);

				} else if (i == xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i / 100) + "\n";

				}

			}

			for (int i = 0; i < cnt; i++) {

				resultbuffer += " ";
			}

			for (int i = 1; i < xlength90; i++) {

				if (i < xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i / 10);

				} else if (i == xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i / 10) + "\n";

				}

			}

			for (int i = 0; i < cnt; i++) {

				resultbuffer += " ";
			}

			for (int i = 1; i < xlength90; i++) {

				if (i < xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i % 10);

				} else if (i == xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i % 10) + "\n";

				}

			}

		} else if (cnt == 3) {

			for (int i = 1; i < xlength90; i++) {

				if (i < xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i / 10);

				} else if (i == xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i / 10) + "\n";

				}

			}

			for (int i = 0; i < cnt; i++) {

				resultbuffer += " ";
			}

			for (int i = 1; i < xlength90; i++) {

				if (i < xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i % 10);

				} else if (i == xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i % 10) + "\n";

				}

			}

		} else if (cnt == 2) {

			for (int i = 1; i < xlength90; i++) {

				if (i < xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i);

				} else if (i == xlength90 - 1) {

					resultbuffer += "  " + String.valueOf(i) + "\n";

				}

			}

		}

		// ��� ���

		resultbuffer += "\n\n";
		resultbuffer += tail;

	
	    System.out.print(resultbuffer);

	}
	
	
}
