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
		int Rbinchars = 0;
		int firsttouch = 0;
		int Mapchar = 0;
		int xlength = 0, ylength = 0;
		int maxbinchar = 0;
		int Ycountlenght = 0;

		resultbuffer = "";

		for (int i = 0; i < read.length; i++) {
			rotationbuffer[i] = ' ';
		}

		// 파일로 읽어드린, map을 char 전체 map인 read와 map 부분인 rotationbuffer 배열로 분리 .

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

					if (rotationbuffer[0] == '\n') {

						// first cell is enter character, do not nothing

					} else {

						k++;

					}

				}

			}

			if (read[j] == '\n') {

				flag = 0;

			}

		}

		for (int i = 0; i < rotationbuffer.length; i++) {

			if (rotationbuffer[i] == '\n' && rotationbuffer[i + 1] == '\n') {

				rotationbuffer[i + 1] = '\0';
				rotationbuffer[i] = '\n';

			}

		}

		// 읽어온 char을 string totalbuffer로 변환.
		for (int i = 0; i < read.length; i++) {
			totalbuffer += Character.toString(read[i]);
		}

		// bin char 추가
		int tailindex = JBeditormain.Finalarray.indexOf("SoftBIN");
		String taillinebuf = JBeditormain.Finalarray.substring(tailindex);
		String[] tailline = taillinebuf.split("\n");

		String[][] tailcomponent = new String[tailline.length][];

		for (int i = 0; i < tailline.length; i++) {

			tailcomponent[i] = tailline[i].split("\\s");

		}
		


		for (int i = 0; i < tailcomponent.length - 1; i++) {

			
			
			for (int j = 0; j < tailcomponent[i][2].length(); j++) {

			
				
				maxbinchar++;

			}

			if (maxbinchar > Rbinchars) {

				Rbinchars = maxbinchar;
			}

			maxbinchar = 0;

		}
		
		

		// totalbuffer에서 필요한 head 부분만 header와 tail로 string 저장.
		int p1 = totalbuffer.indexOf("**********  WAFER MAP");
		String sub1 = totalbuffer.substring(0, p1 - 1);
		header = sub1;

		int p2 = totalbuffer.indexOf("**********  BIN SUMMARY");
		String sub2 = totalbuffer.substring(p2);
		tail = sub2;

		// rotationbuffer의 x,y길이를 측정.

		for (int i = 0; i < rotationbuffer.length; i++) {

			if (rotationbuffer[i] == '\n') {

				ylength++;

			}

		}

		for (int i = 0; i < rotationbuffer.length; i++) {

			xlength++;

			if (rotationbuffer[i] == '\n') {

				break;

			}

		}

		for (int i = 0; i < rotationbuffer.length; i++) {

			if ((rotationbuffer[i] < 47 || rotationbuffer[i] > 58) && rotationbuffer[i] != ' '
					&& rotationbuffer[i] != '\n') {

				Mapchar = 1;

			}

		}

		// Map 마다 char를 찍는 갯수의 상이함을 보상.
		if (Rbinchars == 2) {

			Rbinchars = Rbinchars - 1;

		} else if (Mapchar == 1) {

			Rbinchars = 1;

		}

		int sxlength = 0;

		if (Rbinchars == 1) {

			sxlength = (xlength / Rbinchars);

		} else {

			sxlength = (xlength / Rbinchars) + 1;

		}

		int sylength = ylength;
		int count = Rbinchars;
		int ylength90 = sxlength - 1;
		int xlength90 = sylength + 1;

		String[][] rotationarray = new String[sylength][sxlength];
		String[][] rotationarray90 = new String[ylength90][xlength90];

		// body string에 들어갈 이차원 배열 두개를 초기화.

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

		// char rotationbuffer를 string rotationarray 3자리 이차원 배열에 정렬.

		for (int x = 0, h = 0; x < sylength; x++) {

			for (int z = 0; z < sxlength; z++, h = h + count) {

				for (int m = 0; m < Rbinchars; m++) {

					rotationarray[x][z] += Character.toString(rotationbuffer[h + m]);

					if (rotationbuffer[h + m] == '\n') {

						count = 1;
						m = Rbinchars;

					} else {

						count = Rbinchars;

					}

				}

			}

		}

		// map을 시계방향으로 돌려, rotation90 string 2차원 배열에 저장.



			for (int i = 0, z = 0; i < ylength90; i++, z++) {

				for (int j = 0, x = sylength - 1; j < xlength90; j++, x--) {

					if (j == xlength90 - 1) {

						rotationarray90[i][j] = "\n";

					} else if (j < xlength90 - 1 && x > -1) {

						rotationarray90[i][j] = rotationarray[x][z];

					}

				}

			}



			// 90도 돌아간 map을 body string에 저장
		for (int i = 0; i < ylength90; i++) {

			for (int j = 0; j < xlength90; j++) {

				body += rotationarray90[i][j];

			}

		}

		if (ylength90 > 99 && ylength90 < 1000) {

			Ycountlenght = 4;

		}

		if (ylength90 > 9 && ylength90 < 100) {

			Ycountlenght = 3;

		}

		if (ylength90 > -1 && ylength90 < 10) {

			Ycountlenght = 2;

		}

		// resultbuffer string에 header string 삽입.

		resultbuffer = header;
		resultbuffer += "\n**********  WAFER MAP  **********\n\n";

		// numbering x축

		for (int i = 0; i < Ycountlenght; i++) {

			resultbuffer += " ";
		}

		if (Rbinchars == 4) {

			if (xlength90 > 99) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i / 100);

					} else if (i == xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i / 100) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i / 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i / 10) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i % 10) + "\n";

					}

				}

			} else if (xlength90 < 99 && xlength90 > 9) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i / 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i / 10) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i % 10) + "\n";

					}

				}

			} else if (xlength90 < 10) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i % 10) + "\n";

					}

				}
			}

		} else if (Rbinchars == 3) {

			if (xlength90 > 99) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i / 100);

					} else if (i == xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i / 100) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i / 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i / 10) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i % 10) + "\n";

					}

				}

			} else if (xlength90 < 99 && xlength90 > 9) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i / 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i / 10) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i % 10) + "\n";

					}

				}

			} else if (xlength90 < 10) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i % 10) + "\n";

					}

				}
			}

		} else if (Rbinchars == 2) {

			if (xlength90 > 99) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i / 100);

					} else if (i == xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i / 100) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i / 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i / 10) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i % 10) + "\n";

					}

				}

			} else if (xlength90 < 99 && xlength90 > 9) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i / 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i / 10) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i % 10) + "\n";

					}

				}

			} else if (xlength90 < 10) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i % 10) + "\n";

					}

				}
			}

		} else if (Rbinchars == 1) {

			if (xlength90 > 99) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += String.valueOf(i / 100);

					} else if (i == xlength90 - 1) {

						resultbuffer += String.valueOf(i / 100) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += String.valueOf(i / 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += String.valueOf(i / 10) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += String.valueOf(i % 10) + "\n";

					}

				}

			} else if (xlength90 < 99 && xlength90 > 9) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += String.valueOf(i / 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += String.valueOf(i / 10) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += String.valueOf(i % 10) + "\n";

					}

				}

			} else if (xlength90 < 10) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += String.valueOf(i % 10) + "\n";

					}

				}
			}

		}

		for (int i = 0; i < Ycountlenght; i++) {

			resultbuffer += " ";
		}

		for (int i = Rbinchars; i < xlength90 * Rbinchars + 1; i++) {

			if ((i - (Rbinchars - 1)) % (Rbinchars * 5) == 0) {

				resultbuffer += "+";

			} else if (i == xlength90 * Rbinchars) {

				resultbuffer += "\n";

			} else {

				resultbuffer += "-";

			}

		}

		// body string에서 bodybuffer로 옮기어 y 좌표 삽입

		StringBuffer bodybuffer = new StringBuffer(body);

		for (int i = 0, j = 1; i < bodybuffer.length(); i = i + (xlength90 * Rbinchars) + Ycountlenght
				- (Rbinchars - 1), j++) {

			if (ylength90 > 99 && ylength90 < 1000) {

				if (j < 10) {

					if (j % 5 == 0) {

						bodybuffer.insert(i, "  " + j + "+");

					} else {

						bodybuffer.insert(i, "  " + j + "|");

					}

				} else if (j > 9 && j < 100) {

					if (j % 5 == 0) {

						bodybuffer.insert(i, " " + j + "+");

					} else {

						bodybuffer.insert(i, " " + j + "|");

					}

				} else if (j > 99 && j < 1000) {

					if (j % 5 == 0) {

						bodybuffer.insert(i, j + "+");

					} else {

						bodybuffer.insert(i, j + "|");

					}

				}
			}

			if (ylength90 > 9 && ylength90 < 100) {

				if (j < 10) {

					if (j % 5 == 0) {

						bodybuffer.insert(i, " " + j + "+");

					} else {

						bodybuffer.insert(i, " " + j + "|");

					}

				} else if (j > 9 && j < 100) {

					if (j % 5 == 0) {

						bodybuffer.insert(i, j + "+");

					} else {

						bodybuffer.insert(i, j + "|");

					}

				}
			}

			if (ylength90 > -1 && ylength90 < 10) {

				if (j < 10) {

					if (j % 5 == 0) {

						bodybuffer.insert(i, j + "+");

					} else {

						bodybuffer.insert(i, j + "|");

					}

				}
			}

		}

		// bodybuffer 를 body string으로

		body = bodybuffer.toString();

		resultbuffer += body;

		// numbering x축

		for (int i = 0; i < Ycountlenght; i++) {

			resultbuffer += " ";
		}

		for (int i = Rbinchars; i < xlength90 * Rbinchars + 1; i++) {

			if ((i - (Rbinchars - 1)) % (Rbinchars * 5) == 0) {

				resultbuffer += "+";

			} else if (i == xlength90 * Rbinchars) {

				resultbuffer += "\n";

			} else {

				resultbuffer += "-";

			}

		}

		for (int i = 0; i < Ycountlenght; i++) {

			resultbuffer += " ";
		}

		if (Rbinchars == 4) {

			if (xlength90 > 99) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i / 100);

					} else if (i == xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i / 100) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i / 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i / 10) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i % 10) + "\n";

					}

				}

			} else if (xlength90 < 99 && xlength90 > 9) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i / 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i / 10) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i % 10) + "\n";

					}

				}

			} else if (xlength90 < 10) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "   " + String.valueOf(i % 10) + "\n";

					}

				}
			}

		} else if (Rbinchars == 3) {

			if (xlength90 > 99) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i / 100);

					} else if (i == xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i / 100) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i / 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i / 10) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i % 10) + "\n";

					}

				}

			} else if (xlength90 < 99 && xlength90 > 9) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i / 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i / 10) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i % 10) + "\n";

					}

				}

			} else if (xlength90 < 10) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += "  " + String.valueOf(i % 10) + "\n";

					}

				}
			}

		} else if (Rbinchars == 2) {

			if (xlength90 > 99) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i / 100);

					} else if (i == xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i / 100) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i / 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i / 10) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i % 10) + "\n";

					}

				}

			} else if (xlength90 < 99 && xlength90 > 9) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i / 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i / 10) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i % 10) + "\n";

					}

				}

			} else if (xlength90 < 10) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += " " + String.valueOf(i % 10) + "\n";

					}

				}
			}

		} else if (Rbinchars == 1) {

			if (xlength90 > 99) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += String.valueOf(i / 100);

					} else if (i == xlength90 - 1) {

						resultbuffer += String.valueOf(i / 100) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += String.valueOf(i / 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += String.valueOf(i / 10) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += String.valueOf(i % 10) + "\n";

					}

				}

			} else if (xlength90 < 99 && xlength90 > 9) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += String.valueOf(i / 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += String.valueOf(i / 10) + "\n";

					}

				}

				for (int i = 0; i < Ycountlenght; i++) {

					resultbuffer += " ";
				}

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += String.valueOf(i % 10) + "\n";

					}

				}

			} else if (xlength90 < 10) {

				for (int i = 1; i < xlength90; i++) {

					if (i < xlength90 - 1) {

						resultbuffer += String.valueOf(i % 10);

					} else if (i == xlength90 - 1) {

						resultbuffer += String.valueOf(i % 10) + "\n";

					}

				}
			}

		}

		// 결과 출력

		resultbuffer += "\n\n";
		resultbuffer += tail;

	}

}
