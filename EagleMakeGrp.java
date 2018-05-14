package window;

import javax.swing.JDialog;

public class EagleMakeGrp extends JDialog {

	String backline[] = JBeditormain.Finalarray.split("\n");

	public static StringBuffer EagleGrpbuffer = new StringBuffer();


	public EagleMakeGrp() {

		EagleGrpbuffer.setLength(0);


		int k = 0;
		int x = 0;
		int cnt = 0;

		for (int i = 0; i < backline.length; i++) {

			if (backline[i].contains("define")) {

				// System.out.println("backline[i]" + i + ":" + backline[i]);

				cnt++;

			} else {

				// do nothing

			}

		}

		String[][] bodyline = new String[cnt][];

		for (int i = 0; i < backline.length; i++) {

			if (backline[i].contains("define")) {

				bodyline[k] = backline[i].split("\\s");

				k++;

			} else {

				// do nothing

			}

		}

		// OEN pin 제거한 data 확인.
		//for (int i = 0; i < bodyline.length; i++) {

		//	for (int j = 0; j < bodyline[i].length; j++) {

		//		System.out.println("bodyline[i][j]" + i + "|" + j + ":" + bodyline[i][j]);

		//	}

		//}

		String[][] newbodyline = new String[cnt][3];

		for (int i = 0; i < bodyline.length; i++) {

			for (int j = 0; j < bodyline[i].length; j++) {

				if (!bodyline[i][j].equals("")) {

					newbodyline[i][x] = bodyline[i][j];

					//System.out.println("newbodyline[i][x]" + i + "|" + x + ":" + newbodyline[i][x]);

					x++;
				}

			}

			x = 0;
		}

		for (int i = 1; i < newbodyline.length; i++) {

			int lastindex = 0;
			lastindex = newbodyline[i][1].length();

			EagleGrpbuffer.append("const int " + newbodyline[i][1].substring(0, lastindex - 1) + "[]={");
			for (int j = 0; j < Integer.parseInt(newbodyline[0][2]); j++) {

				EagleGrpbuffer.append(newbodyline[i][1].substring(0, lastindex - 1) + (j + 1) + ",");

				if (j == Integer.parseInt(newbodyline[0][2]) - 1) {

					EagleGrpbuffer.append(newbodyline[i][1].substring(0, lastindex - 1) + (j + 1) + "};\n");
				}

			}

		}

		EagleGrpbuffer.append(
				"\nMLOCAL VOID nameset_groupset(VOID)\n{\n    char resource_name[32];\n\n	for(site=0;site<NUM_SITES;site++)\n	{\n");

		String pinscale;
		pinscale = "";

		for (int i = 1; i < newbodyline.length; i++) {

			int lastindex = 0;
			lastindex = newbodyline[i][1].length();

			if (newbodyline[i][2].contains("APU")) {

				pinscale = "APU12";

			} else if (newbodyline[i][2].contains("SPU")) {

				pinscale = "SP100";

			} else if (newbodyline[i][2].contains("CBIT")) {

				pinscale = "CBIT";

			}

			EagleGrpbuffer.append("     sprintf_s(resource_name, \"" + newbodyline[i][1].substring(0, lastindex - 1)
					+ "%d\", site+1);          nameset((NAME_" + pinscale + ", "
					+ newbodyline[i][1].substring(0, lastindex - 1) + "[site], resource_name);\n");

		}

		EagleGrpbuffer.append(" }\n\n");
		EagleGrpbuffer.append("#if NUM_SITES == " + newbodyline[0][2] + "\n");

		for (int i = 1; i < newbodyline.length; i++) {

			int lastindex = 0;
			lastindex = newbodyline[i][1].length();

			if (newbodyline[i][2].contains("APU")) {

				pinscale = "APU12";

			} else if (newbodyline[i][2].contains("SPU")) {

				pinscale = "SP100";

			} else if (newbodyline[i][2].contains("CBIT")) {

				pinscale = "CBIT";

			}

			EagleGrpbuffer.append("    groupset(" + newbodyline[i][1].substring(0, lastindex - 3) + ", \""
					+ newbodyline[i][1].substring(0, lastindex - 3) + "\", \"");

			for (int j = 0; j < Integer.parseInt(newbodyline[0][2]); j++) {

				EagleGrpbuffer.append(pinscale + "_%d,");

				if (j == Integer.parseInt(newbodyline[0][2]) - 1) {

					EagleGrpbuffer.append(pinscale + "_%d\", ");
				}

			}

			for (int j = 0; j < Integer.parseInt(newbodyline[0][2]); j++) {

				EagleGrpbuffer.append(newbodyline[i][1].substring(0, lastindex - 1) + (j + 1) + ",");

				if (j == Integer.parseInt(newbodyline[0][2]) - 1) {

					EagleGrpbuffer.append(newbodyline[i][1].substring(0, lastindex - 1) + (j + 1) + ");\n");
				}

			}

		}

		EagleGrpbuffer.append("#endif\n\n");

		EagleGrpbuffer.append("	for(site=0;site<NUM_SITES;site++)\n {\n");

		int apucnt = 0;
		int spucnt = 0;
		int cbitcnt = 0;
		int apucount = 0;
		int spucount = 0;
		int cbitcount = 0;

		for (int i = 1; i < newbodyline.length; i++) {

			if (newbodyline[i][2].contains("APU")) {

				apucnt++;
			}

			if (newbodyline[i][2].contains("SPU")) {

				spucnt++;
			}

			if (newbodyline[i][2].contains("CBIT")) {

				cbitcnt++;
			}
		}
		if (apucnt > 0) {

			EagleGrpbuffer.append("     msGroupSite(site, \"");

			for (int i = 1; i < newbodyline.length; i++) {

				int lastindex = 0;
				lastindex = newbodyline[i][1].length();

				if (newbodyline[i][2].contains("APU")) {

					pinscale = "APU12";

					EagleGrpbuffer.append(pinscale + "_%d,");

					if (apucount == apucnt - 1) {

						EagleGrpbuffer.append(pinscale + "_%d\", ");
					}

					apucount++;
				}

			}

			apucount = 0;

			for (int i = 1; i < newbodyline.length; i++) {

				int lastindex = 0;
				lastindex = newbodyline[i][1].length();

				if (newbodyline[i][2].contains("APU")) {

					pinscale = "APU12";

					EagleGrpbuffer.append(newbodyline[i][1].substring(0, lastindex - 1) + "[site],");

					if (apucount == apucnt - 1) {

						EagleGrpbuffer.append(newbodyline[i][1].substring(0, lastindex - 1) + "[site]);\n");
					}

					apucount++;
				}

			}
		}

		if (spucnt > 0) {
			if (apucnt < 1) {

				EagleGrpbuffer.append("     msGroupSite(site, \"");

			} else {

				EagleGrpbuffer.append("     msGroupSiteModify(site, TRUE, \"");

			}

			for (int i = 1; i < newbodyline.length; i++) {

				int lastindex = 0;
				lastindex = newbodyline[i][1].length();

				if (newbodyline[i][2].contains("SPU")) {

					pinscale = "SP100";

					EagleGrpbuffer.append(pinscale + "_%d,");

					if (spucount == spucnt - 1) {

						EagleGrpbuffer.append(pinscale + "_%d\", ");
					}

					spucount++;
				}

			}

			spucount = 0;

			for (int i = 1; i < newbodyline.length; i++) {

				int lastindex = 0;
				lastindex = newbodyline[i][1].length();

				if (newbodyline[i][2].contains("SPU")) {

					pinscale = "SP100";

					EagleGrpbuffer.append(newbodyline[i][1].substring(0, lastindex - 1) + "[site],");

					if (spucount == spucnt - 1) {

						EagleGrpbuffer.append(newbodyline[i][1].substring(0, lastindex - 1) + "[site]);\n");
					}

					spucount++;
				}

			}

		}

		if (cbitcnt > 0) {
			EagleGrpbuffer.append("     msGroupSiteModify(site, TRUE, \"");

			for (int i = 1; i < newbodyline.length; i++) {

				int lastindex = 0;
				lastindex = newbodyline[i][1].length();

				if (newbodyline[i][2].contains("CBIT")) {

					pinscale = "CBIT";

					EagleGrpbuffer.append(pinscale + "_%d,");

					if (cbitcount == cbitcnt - 1) {

						EagleGrpbuffer.append(pinscale + "_%d\", ");
					}

					cbitcount++;
				}

			}

			cbitcount = 0;

			for (int i = 1; i < newbodyline.length; i++) {

				int lastindex = 0;
				lastindex = newbodyline[i][1].length();

				if (newbodyline[i][2].contains("CBIT")) {

					pinscale = "CBIT";

					EagleGrpbuffer.append(newbodyline[i][1].substring(0, lastindex - 1) + "[site],");

					if (cbitcount == cbitcnt - 1) {

						EagleGrpbuffer.append(newbodyline[i][1].substring(0, lastindex - 1) + "[site]);\n");
					}

					cbitcount++;
				}

			}

		}

		EagleGrpbuffer.append(" }\n\n}\n");
		
	}

}