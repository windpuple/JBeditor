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

	int flag = 0;
	int bodycnt = 0;
	int tailcnt = 0;

	public MAPwindow() {


		StringBuilder Bodybuffer1 = new StringBuilder();
		StringBuilder Mapbuffer = new StringBuilder();

		String backline[] = JBeditormain.Finalarray.split("\n");

		String[][] headelement = new String[7][];
		String[][] neckelement = new String[4][];
		String[][] bodyelement = new String[backline.length][];
		String[][] tailelement = new String[backline.length][];

		for (int i = 0; i < 7; i++) {

			headelement[i] = backline[i].split("\\s");

		}

		for (int i = 7, m = 0; i < 11; i++) {

			neckelement[m] = backline[i].split("\\s");

			m++;

		}

		for (int i = 11, m = 0, k = 0; i < backline.length; i++) {

			if (backline[i].contains("Stop") || flag == 1) {

				flag = 1;

				tailelement[k] = backline[i].split(",");

				tailcnt++;
				k++;

			} else {

				flag = 0;

				bodyelement[m] = backline[i].split(",");

				bodycnt++;
				m++;

			}

		}

		
		
		String[] xposition = new String[bodycnt];
		String[] yposition = new String[bodycnt];
		String[] Softbin = new String[bodycnt];
		String[] Hardbin = new String[bodycnt];

		for (int i = 0; i < bodycnt; i++) {

			xposition[i] = bodyelement[i][1];
			yposition[i] = bodyelement[i][2];
			Softbin[i] = bodyelement[i][3];
			Hardbin[i] = bodyelement[i][4];

			//System.out.println("xposition "+i+":"+xposition[i]);
			//System.out.println("yposition "+i+":"+yposition[i]);
			//System.out.println("Softbin "+i+":"+Softbin[i]);
			//System.out.println("Hardbin "+i+":"+Hardbin[i]);
		}

		
		//for (int i = 4800; i < bodycnt; i++) {

		//	System.out.println("xposition "+i+":"+xposition[i]);
		//	System.out.println("yposition "+i+":"+yposition[i]);
		//	System.out.println("Softbin "+i+":"+Softbin[i]);
		//	System.out.println("Hardbin "+i+":"+Hardbin[i]);
		//}
		
		
		
		int Xmax = 0;
		int Ymax = 0;
		int Softmax = 0;
		int binchars = 0;
		int Ycountlength = 0;

		for (int i = 0; i < xposition.length; i++) {

			// System.out.println("xposition "+i+":"+xposition[i]);

			if (Integer.parseInt(xposition[i]) > Xmax) {

				Xmax = Integer.parseInt(xposition[i]);
			}

		}

		for (int i = 0; i < yposition.length; i++) {

			// System.out.println("yposition "+i+":"+Integer.parseInt(yposition[i]));

			if (Integer.parseInt(yposition[i]) > Ymax) {

				Ymax = Integer.parseInt(yposition[i]);
			}

		}
		
		for (int i = 0; i < Softbin.length; i++) {

			// System.out.println("yposition "+i+":"+Integer.parseInt(yposition[i]));

			if (Integer.parseInt(Softbin[i]) > Softmax) {

				Softmax = Integer.parseInt(Softbin[i]);
			}

		}

		if(Softmax > 0 && Softmax < 10) {
			
			binchars = 1;
			
		} else if(Softmax > 9 && Softmax < 100) {
			
			binchars = 2;
			
		} else if(Softmax > 99 && Softmax < 1000) {
			
			binchars = 3;
		}
		
		if(Ymax > 0 && Ymax < 10) {
			
			Ycountlength = 2;
			
		} else if(Ymax > 9 && Ymax < 100) {
			
			Ycountlength = 3;
			
		} else if(Ymax > 99 && Ymax < 1000) {
			
			Ycountlength = 4;
		}
		
		System.out.println("Xmax :" + Xmax);
		System.out.println("Ymax :" + Ymax);
		System.out.println("Softmax :" + Softmax);
		System.out.println("binchars :" + binchars);
		
		String[][] ChipPosition = new String[Ymax][Xmax];

		for (int i = 0; i < Ymax; i++) {

			for (int j = 0; j < Xmax; j++) {

				if(binchars == 3) {
					
					ChipPosition[i][j] = "    ";
					
				} else if(binchars == 2) {
					
					ChipPosition[i][j] = "   ";
					
				} else if(binchars == 1) {
					
					ChipPosition[i][j] = "  ";
					
				}
				
				
				
				for (int x = 0; x < bodycnt; x++) {
					
					if (i == (Integer.parseInt(yposition[x])-1) && j == (Integer.parseInt(xposition[x])-1)) {

						if(Integer.parseInt(Softbin[x]) > 0 && Integer.parseInt(Softbin[x]) < 10) {
							
							if(binchars == 3) {
							
								ChipPosition[i][j] = "   "+Softbin[x];
							
							} else if(binchars == 2) {
								
								ChipPosition[i][j] = "  "+Softbin[x];
								
							} else if(binchars == 1) {
								
								ChipPosition[i][j] = " "+Softbin[x];
								
							}
							
						} else if(Integer.parseInt(Softbin[x]) > 9 && Integer.parseInt(Softbin[x]) < 100) {
							
							if(binchars == 3) {
								
								ChipPosition[i][j] = "  "+Softbin[x];
							
							} else if(binchars == 2) {
								
								ChipPosition[i][j] = " "+Softbin[x];
								
							} 
							
							
						} else if(Integer.parseInt(Softbin[x]) > 99 && Integer.parseInt(Softbin[x]) < 1000) {
							
							if(binchars == 3) {
								
								ChipPosition[i][j] = " "+Softbin[x];
							
							} 
							
						}
						
			
						
						
						
						//System.out.println("Softbin "+i+":"+j+":"+x+" :"+Softbin[x]);
						//System.out.println("chip component :"+ChipPosition[i][j]);

					}

				}

			}

		}


		for (int i = 0; i < Ymax; i++) {

			for (int j = 0; j < Xmax; j++) {
				
			
		
				//System.out.print(ChipPosition[i][j]);
				
				//System.out.println("map chip component : "+i+":"+j+":"+ChipPosition[i][j]);
		
				Bodybuffer1.append(ChipPosition[i][j]);
				
				
			}

			Bodybuffer1.append("\n");
			
		}
		
		for(int i = 0; i <  headelement.length; i++) {
			
			for(int j =0; j < headelement[i].length; j++) {
				
				System.out.println("head elem "+i+","+j+":"+headelement[i][j]);
				
			}
		}
		
	
		
		for(int i = 0; i < tailcnt; i++) {
			
			for(int j =0; j < tailelement[i].length; j++) {
				
				System.out.println("tail elem "+i+","+j+":"+tailelement[i][j]);
				
			}
		}
		
		
		String CodeName = headelement[3][3];
		String PgmName = headelement[1][3];
		String TestSystem = headelement[0][4];
		String LotNumber = headelement[2][5];
		String[] WaferIdbuf = tailelement[16][0].split("\\s");
		String WaferId = WaferIdbuf[1];
		
		String[] TestStartTimebuf1 = headelement[5][5].split("\\.");
		String[] TestStartTimebuf2 = headelement[6][5].split(":");
		
		for(int i = 0; i < TestStartTimebuf1.length; i++) {
			
			System.out.println("TESTTIME1 "+TestStartTimebuf1[i]);
		}
		for(int i = 0; i < TestStartTimebuf2.length; i++) {
			
			System.out.println("TESTTIME2 "+TestStartTimebuf2[i]);
		}
		
		String TestStartTime = TestStartTimebuf1[0]+TestStartTimebuf1[1]+TestStartTimebuf1[2]+" "+TestStartTimebuf2[0]+TestStartTimebuf2[1]+TestStartTimebuf2[2];
		
		String TestStopTimebuf00 = tailelement[0][0].substring(tailelement[0][0].indexOf(":")+2);
		String TestStopTimebuf01 = tailelement[1][0].substring(tailelement[1][0].indexOf(":")+2);
		
		String[] TestStopTimebuf1 = TestStopTimebuf00.split("\\.");
		String[] TestStopTimebuf2 = TestStopTimebuf01.split(":");
		
		String TestStopTime = TestStopTimebuf1[0]+TestStopTimebuf1[1]+TestStopTimebuf1[2]+" "+TestStopTimebuf2[0]+TestStopTimebuf2[1]+TestStopTimebuf2[2];
		
		String[] FlatZonebuf = tailelement[15][0].split("\\s");
		String FlatZone = FlatZonebuf[1];
		
		String DiePerWaferbuf00= tailelement[2][0].substring(tailelement[2][0].indexOf(":")+2);
		String DiePerWafer = DiePerWaferbuf00;
		
		String MaxXSize = Integer.toString(Xmax);
		String MaxYSize = Integer.toString(Ymax);
		
		Mapbuffer.append("CODE NAME        : " + CodeName + "\n");
		Mapbuffer.append("PGM NAME         : " + PgmName + "\n");
		Mapbuffer.append("TEST SYSTEM      : " + TestSystem + "\n");
		Mapbuffer.append("LOT Number       : " + LotNumber + "\n");
		Mapbuffer.append("WAFER ID         : " + WaferId + "\n");
		Mapbuffer.append("Test Start Time  : " + TestStartTime + "\n");
		Mapbuffer.append("Test Stop  Time  : " + TestStopTime + "\n");
		Mapbuffer.append("FLAT ZONE        : " + FlatZone + "\n");
		Mapbuffer.append("Die Per Wafer    : " + DiePerWafer + "\n");
		Mapbuffer.append("MAX X SIZE       : " + MaxXSize + "\n");
		Mapbuffer.append("MAX Y SIZE       : " + MaxYSize + "\n\n");
		Mapbuffer.append("**********  WAFER MAP  **********\n\n");
		
		int Rbinchars = 0;
		
		if(binchars != 1) {
		
			Rbinchars = binchars + 1;
		
		}
		

		// numbering x축

		for (int i = 0; i < Ycountlength; i++) {

			Mapbuffer.append(" ");
		}

		if (Rbinchars == 4) {

			if ((Xmax+1) > 99) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i / 100));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i / 100) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i / 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i / 10) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i % 10) + "\n");

					}

				}

			} else if ((Xmax+1) < 99 && (Xmax+1) > 9) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i / 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i / 10) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i % 10) + "\n");

					}

				}

			} else if ((Xmax+1) < 10) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i % 10) + "\n");

					}

				}
			}

		} else if (Rbinchars == 3) {

			if ((Xmax+1) > 99) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i / 100));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i / 100) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i / 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i / 10) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i % 10) + "\n");

					}

				}

			} else if ((Xmax+1) < 99 && (Xmax+1) > 9) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i / 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i / 10) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i % 10) + "\n");

					}

				}

			} else if ((Xmax+1) < 10) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i % 10) + "\n");

					}

				}
			}

		} else if (Rbinchars == 2) {

			if ((Xmax+1) > 99) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i / 100));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i / 100) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i / 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i / 10) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i % 10) + "\n");

					}

				}

			} else if ((Xmax+1) < 99 && (Xmax+1) > 9) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i / 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i / 10) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i % 10) + "\n");

					}

				}

			} else if ((Xmax+1) < 10) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i % 10) + "\n");

					}

				}
			}

		} else if (Rbinchars == 1) {

			if ((Xmax+1) > 99) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i / 100));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i / 100) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i / 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i / 10) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i % 10) + "\n");

					}

				}

			} else if ((Xmax+1) < 99 && (Xmax+1) > 9) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i / 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i / 10) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i % 10) + "\n");

					}

				}

			} else if ((Xmax+1) < 10) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i % 10) + "\n");

					}

				}
			}

		}

		for (int i = 0; i < Ycountlength; i++) {

			Mapbuffer.append(" ");
		}

		for (int i = Rbinchars; i < (Xmax+1) * Rbinchars + 1; i++) {

			if ((i - (Rbinchars - 1)) % (Rbinchars * 5) == 0) {

				Mapbuffer.append("+");

			} else if (i == (Xmax+1) * Rbinchars) {

				Mapbuffer.append("\n");

			} else {

				Mapbuffer.append("-");

			}

		}


		StringBuffer bodybuffer2 = new StringBuffer(Bodybuffer1.toString());

		for (int i = 0, j = 1; i < bodybuffer2.length(); i = i + ((Xmax+1) * Rbinchars) + Ycountlength- (Rbinchars - 1), j++) {

			if (Ymax > 99 && Ymax < 1000) {

				if (j < 10) {

					if (j % 5 == 0) {

						bodybuffer2.insert(i, "  " + j + "+");

					} else {

						bodybuffer2.insert(i, "  " + j + "|");

					}

				} else if (j > 9 && j < 100) {

					if (j % 5 == 0) {

						bodybuffer2.insert(i, " " + j + "+");

					} else {

						bodybuffer2.insert(i, " " + j + "|");

					}

				} else if (j > 99 && j < 1000) {

					if (j % 5 == 0) {

						bodybuffer2.insert(i, j + "+");

					} else {

						bodybuffer2.insert(i, j + "|");

					}

				}
			}

			if (Ymax > 9 && Ymax < 100) {

				if (j < 10) {

					if (j % 5 == 0) {

						bodybuffer2.insert(i, " " + j + "+");

					} else {

						bodybuffer2.insert(i, " " + j + "|");

					}

				} else if (j > 9 && j < 100) {

					if (j % 5 == 0) {

						bodybuffer2.insert(i, j + "+");

					} else {

						bodybuffer2.insert(i, j + "|");

					}

				}
			}

			if (Ymax > -1 && Ymax < 10) {

				if (j < 10) {

					if (j % 5 == 0) {

						bodybuffer2.insert(i, j + "+");

					} else {

						bodybuffer2.insert(i, j + "|");

					}

				}
			}

		}

		Mapbuffer.append(bodybuffer2.toString());

		
		// numbering x축
		
		for (int i = 0; i < Ycountlength; i++) {

			Mapbuffer.append(" ");
		}

		for (int i = Rbinchars; i < (Xmax+1) * Rbinchars + 1; i++) {

			if ((i - (Rbinchars - 1)) % (Rbinchars * 5) == 0) {

				Mapbuffer.append("+");

			} else if (i == (Xmax+1) * Rbinchars) {

				Mapbuffer.append("\n");

			} else {

				Mapbuffer.append("-");

			}

		}
	


		for (int i = 0; i < Ycountlength; i++) {

			Mapbuffer.append(" ");
		}

		if (Rbinchars == 4) {

			if ((Xmax+1) > 99) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i / 100));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i / 100) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i / 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i / 10) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i % 10) + "\n");

					}

				}

			} else if ((Xmax+1) < 99 && (Xmax+1) > 9) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i / 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i / 10) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i % 10) + "\n");

					}

				}

			} else if ((Xmax+1) < 10) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("   " + String.valueOf(i % 10) + "\n");

					}

				}
			}

		} else if (Rbinchars == 3) {

			if ((Xmax+1) > 99) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i / 100));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i / 100) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i / 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i / 10) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i % 10) + "\n");

					}

				}

			} else if ((Xmax+1) < 99 && (Xmax+1) > 9) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i / 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i / 10) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i % 10) + "\n");

					}

				}

			} else if ((Xmax+1) < 10) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append("  " + String.valueOf(i % 10) + "\n");

					}

				}
			}

		} else if (Rbinchars == 2) {

			if ((Xmax+1) > 99) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i / 100));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i / 100) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i / 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i / 10) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i % 10) + "\n");

					}

				}

			} else if ((Xmax+1) < 99 && (Xmax+1) > 9) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i / 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i / 10) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i % 10) + "\n");

					}

				}

			} else if ((Xmax+1) < 10) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(" " + String.valueOf(i % 10) + "\n");

					}

				}
			}

		} else if (Rbinchars == 1) {

			if ((Xmax+1) > 99) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i / 100));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i / 100) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i / 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i / 10) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i % 10) + "\n");

					}

				}

			} else if ((Xmax+1) < 99 && (Xmax+1) > 9) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i / 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i / 10) + "\n");

					}

				}

				for (int i = 0; i < Ycountlength; i++) {

					Mapbuffer.append(" ");
				}

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i % 10) + "\n");

					}

				}

			} else if ((Xmax+1) < 10) {

				for (int i = 1; i < (Xmax+1); i++) {

					if (i < (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i % 10));

					} else if (i == (Xmax+1) - 1) {

						Mapbuffer.append(String.valueOf(i % 10) + "\n");

					}

				}
			}

		}
		
		String[] TotalTested = tailelement[2][0].split("\\s");
		String[] PassUnits = tailelement[13][0].split("\\s");
		String[] PassYld = tailelement[6][0].split("\\s");
		
		double PassYldcount1 =((double)(Integer.parseInt(PassYld[4]))/(double)(Integer.parseInt(TotalTested[3])))*100.00;
		
		String PassYldcount2 = String.format("%.2f", PassYldcount1);

		String[] softbinbuffer1 = tailelement[4][0].split("\\s");
        String[] softbinbuffer2 = tailelement[4][tailelement[4].length-1].split("\\s");
		
		tailelement[4][tailelement[4].length-1] = softbinbuffer2[0];
		tailelement[4][0] = softbinbuffer1[3];
		
		for(int i = 0; i < tailelement[4].length; i++) {
			
			tailelement[4][i] = String.format("%3s", tailelement[4][i]);
			
		}
		
		String[] hardbinbuffer1 = tailelement[3][0].split("\\s");
        String[] hardbinbuffer2 = tailelement[3][tailelement[3].length-1].split("\\s");
		
		tailelement[3][tailelement[3].length-1] = hardbinbuffer2[0];
		
		tailelement[3][0] = hardbinbuffer1[3];
		
		for(int i = 0; i < tailelement[3].length; i++) {
			
			tailelement[3][i] = String.format("%2s", tailelement[3][i]);
			
		}
			
		String[] softbinnamebuffer1 = tailelement[5][0].split("\\s");
        String[] softbinnamebuffer2 = tailelement[5][tailelement[5].length-1].split("\\s");
		
		tailelement[5][tailelement[5].length-1] = softbinnamebuffer2[0];
		
		tailelement[5][0] = softbinnamebuffer1[3];
		
		for(int i = 0; i < tailelement[5].length; i++) {
			
			tailelement[5][i] = String.format("%27s", tailelement[5][i]);
			
		}
		
		String[] softbincountbuffer1 = tailelement[13][0].split("\\s");
		String[] softbincountbuffer2 = tailelement[13][tailelement[13].length-1].split("\\s");
		
		tailelement[13][0] = softbincountbuffer1[4];
		tailelement[13][tailelement[13].length-1] = softbincountbuffer2[0];

		for(int i = 0; i < tailelement[13].length; i++) {
			
			System.out.println("tail sfot count :"+tailelement[13][i]+"|"+i);
			
		}
		
		
		double[] SoftbinYld = new double[tailelement[13].length];
		String[] StrSoftbinYld = new String[tailelement[13].length];
	
		for(int i = 0; i < tailelement[13].length; i++) {
			
			SoftbinYld[i] = ((double)(Integer.parseInt(tailelement[13][i]))/(double)(Integer.parseInt(TotalTested[3])))*100.0;
			
			StrSoftbinYld[i] = String.format("%.2f", SoftbinYld[i]);
			
			System.out.println(StrSoftbinYld[i]);
			
			StrSoftbinYld[i] = String.format("%9s",StrSoftbinYld[i]);
		}
		
		
		for(int i = 0; i < tailelement[13].length; i++) {
			
			tailelement[13][i] = String.format("%7s", tailelement[13][i]);
			
		}
		

		
		Mapbuffer.append("\n\n**********  BIN SUMMARY  **********\n\n");
		Mapbuffer.append("Total Tested                      "+TotalTested[3]+"\n");
		Mapbuffer.append(" PASS UNITS                       "+PassUnits[4]+" [ "+PassYldcount2+"% ]\n\n");
		Mapbuffer.append("SoftBIN(HBin)       BIN DESCRIPTION  COUNT   YIELD\n");
		
		for(int i = 0; i < tailelement[4].length; i++) {
		
			Mapbuffer.append(" "+tailelement[4][i]+"("+tailelement[3][i]+")"+tailelement[5][i]+tailelement[13][i]+StrSoftbinYld[i]+"%\n");
			
		}
		
		
		
		System.out.print(Mapbuffer.toString());
		
		/*
		 * 
		 * MAPsumarrary = MAPsumarrary + MAParrary;
		 * 
		 * 
		 * FileDialog dialog = new FileDialog(this, "Save", FileDialog.SAVE);
		 * dialog.setDirectory("."); // .은 지금폴더 dialog.setVisible(true); // 박스는 그냥 틀이고
		 * if (dialog.getFile() == null) return; // 이걸빼면 취소를 해도 저장이됨 String dfName =
		 * dialog.getDirectory() + dialog.getFile(); // 경로명 파일명 //
		 * System.out.println(dfName); // 실제 저장은 여기에서 try { BufferedWriter writer = new
		 * BufferedWriter(new FileWriter(dfName)); writer.write(MAPsumarrary);
		 * writer.close();
		 * 
		 * setTitle(dialog.getFile() + " - DBE save.."); MAParrary =""; MAPsumarrary
		 * ="";
		 * 
		 * } catch (Exception e2) { JOptionPane.showMessageDialog(this, "Save error"); }
		 */

	}

}