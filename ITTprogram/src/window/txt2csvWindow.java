package window;

import java.awt.Color;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Arrays;
import java.util.Date;
import java.text.ParseException;

public class txt2csvWindow extends JDialog implements ActionListener {

	JButton btn = new JButton("Convert");
	JButton btncancel = new JButton("Close");
	JButton TXTload = new JButton("txt Load");

	JLabel noticeTXT = new JLabel("");
	JLabel fileEmpty = new JLabel("");

	public static StringBuffer TXTloadbuffer = new StringBuffer(); // MAX cap is 12383875 line

	static int p = 0;

	static String[] dTXTfName = new String[256];
	static String[] dTXTfdirectory = new String[256];
	static String[] dTXTffile = new String[256];
	static String[] dTXTSavefName = new String[256];
	static String[][] dTXTSavefName_split = new String[256][];
	static String swavheader;

	File[] select_multi_files;

	public txt2csvWindow() {

		int jb;

		for (jb = 0; jb < 256; jb++) {

			dTXTfName[jb] = "";
			dTXTfdirectory[jb] = "";
			dTXTffile[jb] = "";
			dTXTSavefName[jb] = "";

		}

		swavheader = "";

		TXTloadbuffer.setLength(0);

		setTitle("txt to csv converter");
		setModal(true);

		btn.setBounds(23, 94, 98, 21);
		btn.addActionListener(this);
		getContentPane().setLayout(null);
		getContentPane().add(btn);

		JLabel TXTfile = new JLabel("TXTfile");
		TXTfile.setBounds(23, 28, 120, 15);
		getContentPane().add(TXTfile);

		btncancel.setBounds(133, 93, 97, 23);
		getContentPane().add(btncancel);
		btncancel.addActionListener(this);

		noticeTXT.setForeground(Color.BLUE);
		noticeTXT.setBounds(26, 53, 204, 15);
		getContentPane().add(noticeTXT);

		TXTload.setBounds(133, 24, 97, 23);
		getContentPane().add(TXTload);
		TXTload.addActionListener(this);

		fileEmpty.setForeground(Color.RED);
		fileEmpty.setBounds(23, 75, 207, 15);
		getContentPane().add(fileEmpty);

		setBackground(Color.LIGHT_GRAY);
		setBounds(350, 250, 275, 169);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == TXTload) {

			int jb;

			for (jb = 0; jb < 256; jb++) {

				dTXTfName[jb] = "";
				dTXTfdirectory[jb] = "";
				dTXTffile[jb] = "";
				dTXTSavefName[jb] = "";

			}

			try {

				// System.out.println("multi select is this work?");
				FileDialog dialog = new FileDialog(this, "open", FileDialog.LOAD);

				dialog.setDirectory(".");
				dialog.setMultipleMode(true);
				dialog.setVisible(true);

				// if (dialog.getFile() == null)
				// return;

				String directory = dialog.getDirectory();
				// println("multifile is",dialog.isMultipleMode());
				// System.out.println("multifile is "+dialog.isMultipleMode());
				select_multi_files = dialog.getFiles();

				if (select_multi_files != null && select_multi_files.length > 0) {

					int i;

					for (i = 0; i < select_multi_files.length; i++) {

						// System.out.println(select_multi_files[i]);

						dTXTfdirectory[i] = directory;
						dTXTffile[i] = select_multi_files[i].getName();
						dTXTfName[i] = dTXTfdirectory[i] + dTXTffile[i];


						if (dTXTfName[i].isEmpty()) {

							noticeTXT.setText("");
							noticeTXT.setText("        not loaded TXT File");

						} else {

							noticeTXT.setText("");
							noticeTXT.setText("            loaded TXT File");

						}

					}
				}



			} catch (Exception e2) {

				JOptionPane.showMessageDialog(this, "Open Error");

			}

			// File f = new File(dfName);

			// long fileSize = (long) f.length();
			// if too big file size is need long integer

			// Giga byte file load on PC memory is impossible, change method to data
			// streaming.

		} else if (e.getSource() == btn) {

			int j;

			for (j = 0; j < select_multi_files.length; j++) {

				dTXTSavefName[j] = "";
				swavheader = "";
				TXTloadbuffer.setLength(0);

				int cnt = 0;

				if (dTXTfName[j].isEmpty()) {

					fileEmpty.setText("");
					fileEmpty.setText("   TXT file is empty.");

				} else {

					fileEmpty.setText("");
					fileEmpty.setText("   processing..............");

//					FileDialog dialog = new FileDialog(this, "Save", FileDialog.SAVE);
//					dialog.setDirectory(".");
//					dialog.setVisible(true);
//					if (dialog.getFile() == null)
//						return;

					//System.out.println(dTXTfdirectory[j] + dTXTffile[j]);
					dTXTSavefName[j] = dTXTfdirectory[j] + dTXTffile[j];

					setTitle(dTXTffile[j] + " - TXT save..");

					try {
						FileInputStream ftstream = new FileInputStream(dTXTfName[j]);
						BufferedReader reader = new BufferedReader(new InputStreamReader(ftstream));

						// BufferedReader reader = new BufferedReader(new FileReader(dfName));
						// FileReader reader = new FileReader(dTXTfName);

						String line;
						String line_sub_replace;

						int rows = 0;

						int head_flag = 0;
						int loopcount = 1;
						int linesplit_count = 0;
						int head_end_count = 0;
						int body_end_count = 0;
						int ex_body_end_count = 0;
						int body_loop_count = 0;
						int mainbody_end_count = 0;
						int engage_main_body = 0;
						int bin_engage = 0;
						int ignore_item = 0;
						int bining_end_count = 0;
						int line_done = 0;
						int block_end = 0;
						int data_align = 0;
						int first_data_en = 0;

						String hadler_id;
						String ATE_id;
						String date_data;
						String Lot_id;

						Lot_id = "";
						date_data = "";
						hadler_id = "";
						ATE_id = "";

						int isite = 0;
						int[] site_counter = new int[32];
						String[] SITE32_array = new String[32];
						int[] first_head_information = new int[32];

						for(int i = 0; i < SITE32_array.length; i++) {
							
							SITE32_array[i] = String.valueOf(i);
							
						}
						
						for (int i = 0; i < site_counter.length; i++) {

							site_counter[i] = 999;
							first_head_information[i] = 0;
						}

						int bin_isite = 0;
						String[] site_Hbin_counter = new String[64];

						for (int i = 0; i < site_Hbin_counter.length; i++) {

							site_Hbin_counter[i] = null;

						}

						String[] site_Sbin_counter = new String[64];

						for (int i = 0; i < site_Sbin_counter.length; i++) {

							site_Sbin_counter[i] = null;

						}

						String[] site_PF_counter = new String[64];

						for (int i = 0; i < site_PF_counter.length; i++) {

							site_PF_counter[i] = null;

						}

						long diff = 0;
						long sec = 0;

						SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
						Date d1 = null;
						Date d2 = null;

						Date[] time = null;
						time = new Date[172032];

						String[][] head_data = null;
						head_data = new String[100][1024];

						String[][] body_data = null;
						body_data = new String[172032][1024];

						String[][] main_body_data = null;
						main_body_data = new String[172032][1024];

						int time_count = 0;

						BufferedWriter writer = new BufferedWriter(new FileWriter(dTXTSavefName[j] + ".csv"));

						while ((line = reader.readLine()) != null) {

							// System.out.println("rows:"+rows);

							line_sub_replace = line;

							if (line_sub_replace.contains("Device#")) {
								head_flag = 1;

								if (head_end_count == 0) {
									head_end_count = rows;
								}

							}


							if (line_sub_replace.contains("Date:") && line_sub_replace.contains("Time:")) {

								// System.out.println("line_done_activate");
								line_done = 1;
								engage_main_body = engage_main_body + 1;
								data_align = 1;
								ex_body_end_count = body_end_count;
								body_loop_count = rows - ex_body_end_count;
								body_end_count = rows;

								// System.out.println("body_end_count,rows:" + body_end_count + ":" + rows);
							}


							for (int i = 0; i < line.length(); i++) {

								if (line_sub_replace.contains("  ")) {

									line_sub_replace = line_sub_replace.replace("  ", " ");
									// System.out.println("line_sub_replace_process:"+i+":"+line_sub_replace);

								} else {

									// System.out.println("line_sub_replace_final:"+i+":"+line_sub_replace);

									break;

								}

							}


							for (int i = 0; i < line_sub_replace.length(); i++) {
								if (line_sub_replace.charAt(i) == ' ' || line_sub_replace.charAt(i) == ',')

									linesplit_count = linesplit_count + 1;

							}

							String[] linesplit = new String[linesplit_count];

							linesplit = line_sub_replace.split("\\s|,");

							// System.out.println("body_end_count,rows:"+body_end_count+":"+rows);
							// System.out.println("engage_main_body:"+engage_main_body);
							// System.out.println("line_done:"+line_done);

							// if(engage_main_body == 2) {

							// System.out.println("engage_main_body?"+engage_main_body+" row:"+rows);

							// }

							// if(engage_main_body == 3) {

							// System.out.println("engage_main_body?"+engage_main_body+" row:"+rows);

							// }

							for (int y = 0; y < linesplit.length; y++) {

								// System.out.println("line_spilit?");
								// System.out.println("linsplit:" + y + ":" + linesplit[y]);

								if (head_flag == 0) {

									head_data[rows][y] = linesplit[y];
									// System.out.println("head_data[rows][y]:" + rows + ":" + y + ":" +
									// head_data[rows][y]);

								} else {

									if (engage_main_body >= 1) {

										if (first_data_en == 0) {

											// System.out.println("final enagage?");
											body_data[rows - head_end_count][y] = linesplit[y];
											// System.out.println("rows - head_end_count:" + (rows - (head_end_count)));
											// System.out
											// .println("body_data[rows-head_end_count][y]:" + (rows - head_end_count)
											// + ":" + y + ":" + body_data[rows - head_end_count][y]);

										} else {

											// System.out.println("main_enagage?");
											// System.out.println("data_align?"+data_align);

											// if(data_align == 1) {

											// System.out.println("data_align?"+data_align);
											// System.out.println("engage_main_body?"+engage_main_body);
											// System.out.println("line_done?"+line_done);
											// System.out.println("rows?"+rows);
											// System.out.println("body_end_count?"+body_end_count);
											// System.out.println("ex_body_end_count?"+ex_body_end_count);
											// }

											if (data_align == 1) {

												// System.out
												// .println("rows - ex_body_end_count:" + (rows - ex_body_end_count));
												// System.out.println("body_data[rows - ex_body_end_count][y]:"
												// + (rows - ex_body_end_count) + ":" + y + ":"
												// + body_data[rows - (ex_body_end_count)][y]);

												body_data[rows - ex_body_end_count][y] = linesplit[y];

												// System.out.println("body_data[rows - ex_body_end_count][y]:"
												// + (rows - ex_body_end_count) + ":" + y + ":"
												// + body_data[rows - ex_body_end_count][y]);

											} else {

												// System.out.println("rows - body_end_count:" + (rows -
												// (body_end_count)));
												// System.out.println(
												// "body_data[rows - body_end_count][y]:" + (rows - (body_end_count))
												// + ":" + y + ":" + body_data[rows - (body_end_count)][y]);

												body_data[rows - body_end_count][y] = linesplit[y];

												// System.out.println(
												// "body_data[rows - body_end_count][y]:" + (rows - (body_end_count))
												// + ":" + y + ":" + body_data[rows - (body_end_count)][y]);

											}

										}

									} else {

										// System.out.println("engage?");

										body_data[rows - head_end_count][y] = linesplit[y];

										// System.out.println("rows - head_end_count:" + (rows - (head_end_count)));
										// System.out.println("body_data[rows-head_end_count][y]:" + (rows -
										// head_end_count)
										// + ":" + y + ":" + body_data[rows - head_end_count][y]);

									}

								}

							}

							// TXTloadbuffer.append(line_sub_replace);
							// TXTloadbuffer.append('\n');

							// System.out.println("IS THIS RUN START? row:"+rows);

							// if(engage_main_body == 3) {

							// System.out.println("engage_main_body?"+engage_main_body+" row:"+rows);

							// }

							if (rows != 0 && rows == head_end_count) {

								writer.append("PGM Name" + "," + head_data[2][3] + "\n");

							} else if (rows != 0 && rows == body_loop_count && engage_main_body == 1
									&& line_done == 1) {

								//System.out.println("first loop");

								writer.append("SerialNumber" + "," + "Test Pass/Fail Status" + "," + "HBIN" + ","
										+ "SBIN" + "," + "Site" + "," + "TesterID" + "," + "HanderID" + ","
										+ "StartTime" + "," + "EndTime" + "," + "TestTime" + "," + "LotNumber");

								for (int i = 0; i < body_loop_count + 1; i++) {
									// for(int i = 0; i < 100; i++) {

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][0].equals("BARCODE") || body_data[i][3].equals("Test")
											|| body_data[i][3].equals("alarm") || body_data[i][3].equals("GPIB_Echo")
											|| body_data[i][3].equals("Barcode") || body_data[i][3].equals("[Pin")) {
										// System.out.println("body_data[i][3]"+":"+i+":"+body_data[i][3]);
										// do nothing

									} else if (body_data[i][3].equals("tests/Executed")) {
										// System.out.println("body_data[i][3]"+":"+i+":"+body_data[i][3]);
										writer.append("\n");
										break;

									} else {

										if (i == 0) {

											writer.append("," + body_data[i][3]);

										} else {

											// System.out.println("int i:"+i);
											// System.out.println("body_data[i][3]:"+body_data[i][3]);
											// System.out.println("body_data[i-1][3]:"+body_data[i-1][3]);
											if (body_data[i - 1][3] == null && !body_data[i][3].isEmpty()) {
												// System.out.println("IS THIS RUN C?");
												writer.append("," + body_data[i][3] + " " + body_data[i][4]);

											} else if (body_data[i][3].equals(body_data[i - 1][3])
													&& body_data[i][4].equals(body_data[i - 1][4])) {
												// System.out.println("IS THIS RUN A?");
												// System.out.println("body_data[i][4].equals(body_data[i -
												// 1][4]:"+body_data[i - 1][4]+", "+body_data[i][4]);
												// Do nothing

											} else {

												for (int x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													writer.append("," + body_data[i][3] + " " + body_data[i][4]);

												}

											}

										}
									}

								}


								writer.append("Upper Limit ----->" + "," + "," + "," + "," + "," + "," + "," + "," + ","
										+ ",");

								for (int i = 0; i < body_loop_count + 1; i++) {
									// for(int i = 0; i < 100; i++) {

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][0].equals("BARCODE") || body_data[i][3].equals("Test")
											|| body_data[i][3].equals("alarm") || body_data[i][3].equals("GPIB_Echo")
											|| body_data[i][3].equals("Barcode") || body_data[i][3].equals("[Pin")) {
										// System.out.println("body_data[i][3]"+":"+i+":"+body_data[i][3]);
										// do nothing

									} else if (body_data[i][3].equals("tests/Executed")) {
										// System.out.println("body_data[i][3]"+":"+i+":"+body_data[i][3]);
										writer.append("\n");
										break;

									} else {

										if (i == 0) {

											if (body_data[i][10].equals("(F)")) {

												writer.append("," + body_data[i][11]);

											} else {


												writer.append("," + body_data[i][10]);
											}

										} else {

											// System.out.println("int i:"+i);
											// System.out.println("body_data[i][3]:"+body_data[i][3]);
											// System.out.println("body_data[i-1][3]:"+body_data[i-1][3]);
											// System.out.println("body_data[i][10]:"+body_data[i][10]);
											// System.out.println("body_data[i-1][10]:"+body_data[i-1][10]);
											// System.out.println("body_data[i][4]:"+body_data[i][4]);

											if (body_data[i - 1][3] == null && !body_data[i][3].isEmpty()) {


												if (body_data[i][3].equals("D1") || body_data[i][3].equals("D2")) { // added
																													// "
																													// "
																													// exeception
																													// for
																													// cygnus
																													// diode
																													// item

													// System.out.println("ignore_item:"+ignore_item);

													for (int x = i - 2; x < i; x++) {
														// System.out.println("body_data[i][3]:"+body_data[i][3]);
														// System.out.println("body_data[i][4]:"+body_data[i][4]);
														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {

														if (body_data[i][11].equals("(F)")) {

															writer.append("," + body_data[i][12]);

														} else {


															// System.out.println("body_data[i][11]:"+body_data[i][11]);
															// System.out.println("body_data[i-1][11]:"+body_data[i-1][11]);
															// System.out.println("body_data[i+1][11]:"+body_data[i+1][11]);
															writer.append("," + body_data[i][11]);

														}

													}

												} else if (body_data[i][4].equals("-1")) {

													//System.out.println("body_data[12]: "+body_data[i][12]);
													
													if (body_data[i][12] == null) {

														if (body_data[i][5].equals("N/A")) {

															for (int x = i - 2; x < i; x++) {

																if (body_data[i][3].equals(body_data[x][3])
																		&& body_data[i][4].equals(body_data[x][4])) {

																	ignore_item = 1;

																	break;
																}

															}

															if (ignore_item == 1) {

																// do nothing
																ignore_item = 0;

															} else {

																if (body_data[i][7].equals("(F)")) {

																	writer.append("," + body_data[i][8]);

																} else {


																	if((body_data[i][7].contains("u")||body_data[i][7].contains("m")||body_data[i][7].contains("A")||body_data[i][7].contains("V"))&&!body_data[i][7].contains("N/A")) {

																		writer.append("," + body_data[i][8]);		
																		
																	} else {
																	

																		writer.append("," + body_data[i][7]);

																		
																	}
																	
																	
																}

															}

														} else {

															for (int x = i - 2; x < i; x++) {

																if (body_data[i][3].equals(body_data[x][3])
																		&& body_data[i][4].equals(body_data[x][4])) {

																	ignore_item = 1;

																	break;
																}

															}

															if (ignore_item == 1) {

																// do nothing
																ignore_item = 0;

															} else {

																if (body_data[i][7].equals("(F)")) {

																	writer.append("," + body_data[i][8]);

																} else {


																	writer.append("," + body_data[i][7]);

																}

															}

														}

													} else {
														
														if (body_data[i][5].equals("N/A")) {

															for (int x = i - 2; x < i; x++) {

																if (body_data[i][3].equals(body_data[x][3])
																		&& body_data[i][4].equals(body_data[x][4])) {

																	ignore_item = 1;

																	break;
																}

															}

															if (ignore_item == 1) {

																// do nothing
																ignore_item = 0;

															} else {

																if (body_data[i][8].equals("(F)")) {

																	writer.append("," + body_data[i][9]);

																} else {

																	writer.append("," + body_data[i][8]);

																}

															}

														} else {

															for (int x = i - 2; x < i; x++) {

																if (body_data[i][3].equals(body_data[x][3])
																		&& body_data[i][4].equals(body_data[x][4])) {

																	ignore_item = 1;

																	break;
																}

															}

															if (ignore_item == 1) {

																// do nothing
																ignore_item = 0;

															} else {

																if (body_data[i][7].equals("(F)")) {

																	writer.append("," + body_data[i][10]);

																} else {


																	writer.append("," + body_data[i][9]);

																}

															}

														}

														
													}

												} else if (body_data[i][6].equals("N/A")) {

													for (int x = i - 2; x < i; x++) {

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {

														if (body_data[i][9].equals("(F)")) {

															writer.append("," + body_data[i][10]);

														} else {

															if(body_data[i][8].equals("N/A")) {
																
																writer.append("," + body_data[i][8]);	
																
															} else {
																
																writer.append("," + body_data[i][9]);																
																
															}
															

														}

													}

												} else {

													for (int x = i - 2; x < i; x++) {

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {

														if (body_data[i][10].equals("(F)")) {

															writer.append("," + body_data[i][11]);

														} else {

															writer.append("," + body_data[i][10]);

														}

													}

												}

											} else if (body_data[i][3].equals(body_data[i - 1][3])
													&& body_data[i][4].equals(body_data[i - 1][4])) {

												// Do nothing

											} else if (body_data[i][4].equals("-1")) {
												
												if (body_data[i][12] == null) {

													if (body_data[i][5].equals("N/A")) {

														for (int x = i - 2; x < i; x++) {

															if (body_data[i][3].equals(body_data[x][3])
																	&& body_data[i][4].equals(body_data[x][4])) {

																ignore_item = 1;

																break;
															}

														}

														if (ignore_item == 1) {

															// do nothing
															ignore_item = 0;

														} else {

															if (body_data[i][7].equals("(F)")) {

																writer.append("," + body_data[i][8]);

															} else {


																if((body_data[i][7].contains("u")||body_data[i][7].contains("m")||body_data[i][7].contains("A")||body_data[i][7].contains("V"))&&!body_data[i][7].contains("N/A")) {

																	writer.append("," + body_data[i][8]);		
																	
																} else {
																

																	writer.append("," + body_data[i][7]);

																	
																}
																
																
															}

														}

													} else {

														for (int x = i - 2; x < i; x++) {

															if (body_data[i][3].equals(body_data[x][3])
																	&& body_data[i][4].equals(body_data[x][4])) {

																ignore_item = 1;

																break;
															}

														}

														if (ignore_item == 1) {

															// do nothing
															ignore_item = 0;

														} else {

															if (body_data[i][7].equals("(F)")) {

																writer.append("," + body_data[i][8]);

															} else {


																writer.append("," + body_data[i][7]);

															}

														}

													}

												} else {
													
													if (body_data[i][5].equals("N/A")) {

														for (int x = i - 2; x < i; x++) {

															if (body_data[i][3].equals(body_data[x][3])
																	&& body_data[i][4].equals(body_data[x][4])) {

																ignore_item = 1;

																break;
															}

														}

														if (ignore_item == 1) {

															// do nothing
															ignore_item = 0;

														} else {

															if (body_data[i][8].equals("(F)")) {

																writer.append("," + body_data[i][9]);

															} else {

																writer.append("," + body_data[i][8]);

															}

														}

													} else {

														for (int x = i - 2; x < i; x++) {

															if (body_data[i][3].equals(body_data[x][3])
																	&& body_data[i][4].equals(body_data[x][4])) {

																ignore_item = 1;

																break;
															}

														}

														if (ignore_item == 1) {

															// do nothing
															ignore_item = 0;

														} else {

															if (body_data[i][7].equals("(F)")) {

																writer.append("," + body_data[i][10]);

															} else {

																writer.append("," + body_data[i][9]);

															}

														}

													}

													
												}

											} else if (body_data[i][6].equals("N/A")) {

												for (int x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													if (body_data[i][9].equals("(F)")) {

														writer.append("," + body_data[i][10]);

													} else {

														if(body_data[i][8].equals("N/A")) {
															
															writer.append("," + body_data[i][8]);	
															
														} else {
															
															writer.append("," + body_data[i][9]);																
															
														}

													}

												}

											} else if (body_data[i][3].equals("D1") || body_data[i][3].equals("D2")) { // added
																														// "
																														// "
																														// exeception
																														// for
																														// cygnus
																														// diode
																														// item

												// System.out.println("ignore_item:"+ignore_item);

												for (int x = i - 2; x < i; x++) {
													// System.out.println("body_data[i][3]:"+body_data[i][3]);
													// System.out.println("body_data[i][4]:"+body_data[i][4]);
													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													if (body_data[i][11].equals("(F)")) {

														writer.append("," + body_data[i][12]);

													} else {

														// System.out.println("body_data[i][11]:"+body_data[i][11]);
														// System.out.println("body_data[i-1][11]:"+body_data[i-1][11]);
														// System.out.println("body_data[i+1][11]:"+body_data[i+1][11]);
														writer.append("," + body_data[i][11]);

													}

												}

											}

											else {

												for (int x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													if (body_data[i][10].equals("(F)")) {

														writer.append("," + body_data[i][11]);

													} else {

														writer.append("," + body_data[i][10]);
													}

												}

											}

										}
									}

								}


								writer.append("Lower Limit ----->" + "," + "," + "," + "," + "," + "," + "," + "," + ","
										+ ",");

								for (int i = 0; i < body_loop_count + 1; i++) {
									// for(int i = 0; i < 100; i++) {

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][0].equals("BARCODE") || body_data[i][3].equals("Test")
											|| body_data[i][3].equals("alarm") || body_data[i][3].equals("GPIB_Echo")
											|| body_data[i][3].equals("Barcode") || body_data[i][3].equals("[Pin")) {
										// System.out.println("body_data[i][3]"+":"+i+":"+body_data[i][3]);
										// do nothing

									} else if (body_data[i][3].equals("tests/Executed")) {
										// System.out.println("body_data[i][3]"+":"+i+":"+body_data[i][3]);
										writer.append("\n");
										break;

									} else {

										if (i == 0) {

											writer.append("," + body_data[i][6]);

										} else {

											// System.out.println("int i:"+i);
											// System.out.println("body_data[i][3]:"+body_data[i][3]);
											// System.out.println("body_data[i-1][3]:"+body_data[i-1][3]);
											// System.out.println("body_data[i][6]:"+body_data[i][6]);
											// System.out.println("body_data[i-1][6]:"+body_data[i-1][6]);
											// System.out.println("body_data[i][4]:"+body_data[i][4]);

											if (body_data[i - 1][3] == null && !body_data[i][3].isEmpty()) {

												if (body_data[i][3].equals("D1") || body_data[i][3].equals("D2")) { // added
													// "
													// "
													// exeception
													// for
													// cygnus
													// diode
													// item

													// System.out.println("ignore_item:"+ignore_item);

													for (int x = i - 2; x < i; x++) {
														// System.out.println("body_data[i][3]:"+body_data[i][3]);
														// System.out.println("body_data[i][4]:"+body_data[i][4]);
														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {

														writer.append("," + body_data[i][8]);

													}

												} else if (body_data[i][4].equals("-1")) {

													for (int x = i - 2; x < i; x++) {

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {


														writer.append("," + body_data[i][5]);

													}

												} else if (body_data[i][6].equals("N/A")) {

													for (int x = i - 2; x < i; x++) {

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {


														writer.append("," + body_data[i][6]);

													}

												} else {

													for (int x = i - 2; x < i; x++) {

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {


														writer.append("," + body_data[i][6]);

													}

												}

											} else if (body_data[i][3].equals(body_data[i - 1][3])
													&& body_data[i][4].equals(body_data[i - 1][4])) {


												// Do nothing

											} else if (body_data[i][4].equals("-1")) {

												for (int x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {


													writer.append("," + body_data[i][5]);

												}

											} else if (body_data[i][6].equals("N/A")) {

												for (int x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {


													writer.append("," + body_data[i][6]);
													;

												}

											} else if (body_data[i][3].equals("D1") || body_data[i][3].equals("D2")) { // added
												// "
												// "
												// exeception
												// for
												// cygnus
												// diode
												// item

												// System.out.println("ignore_item:"+ignore_item);

												for (int x = i - 2; x < i; x++) {
													// System.out.println("body_data[i][3]:"+body_data[i][3]);
													// System.out.println("body_data[i][4]:"+body_data[i][4]);
													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													writer.append("," + body_data[i][8]);

												}

											} else {

												for (int x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													writer.append("," + body_data[i][6]);

												}

											}

										}
									}

								}


								writer.append("Measurement Limit ----->" + "," + "," + "," + "," + "," + "," + "," + ","
										+ "," + "sec" + ",");

								for (int i = 0; i < body_loop_count + 1; i++) {

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][0].equals("BARCODE") || body_data[i][3].equals("Test")
											|| body_data[i][3].equals("alarm") || body_data[i][3].equals("GPIB_Echo")
											|| body_data[i][3].equals("Barcode") || body_data[i][3].equals("[Pin")) {
										// System.out.println("body_data[i][3]"+":"+i+":"+body_data[i][3]);
										// do nothing

									} else if (body_data[i][3].equals("tests/Executed")) {
										// System.out.println("body_data[i][3]"+":"+i+":"+body_data[i][3]);
										writer.append("\n");
										break;

									} else {

										if (i == 0) {
											
											writer.append("," + body_data[i][7]);

										} else {

											// System.out.println("int i:"+i);
											// System.out.println("body_data[i][3]:"+body_data[i][3]);
											// System.out.println("body_data[i-1][3]:"+body_data[i-1][3]);
											// System.out.println("body_data[i][7]:"+body_data[i][7]);
											// System.out.println("body_data[i-1][7]:"+body_data[i-1][7]);
											// System.out.println("body_data[i][4]:"+body_data[i][4]);

											if (body_data[i - 1][3] == null && !body_data[i][3].isEmpty()) {

												if (body_data[i][4].equals("-1")) {

													for (int x = i - 2; x < i; x++) {

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {

														if(body_data[i][7].equals("N/A") || body_data[i][8].equals("N/A")) {
															
															writer.append(",");
															
														} else {
															
															if(body_data[i][6].equals("ohm")||body_data[i][6].equals("mohm")||body_data[i][6].equals("%")||body_data[i][6].equals("pA")||body_data[i][6].equals("nA")||body_data[i][6].equals("uA")||body_data[i][6].equals("mA")||body_data[i][6].equals("A")||body_data[i][6].equals("pV")||body_data[i][6].equals("nV")||body_data[i][6].equals("uV")||body_data[i][6].equals("mV")||body_data[i][6].equals("V")) {
																
																writer.append("," + body_data[i][6]);
																
															} else if(body_data[i][9].equals("ohm")||body_data[i][9].equals("mohm")||body_data[i][9].equals("%")||body_data[i][9].equals("pA")||body_data[i][9].equals("nA")||body_data[i][9].equals("uA")||body_data[i][9].equals("mA")||body_data[i][9].equals("A")||body_data[i][9].equals("pV")||body_data[i][9].equals("nV")||body_data[i][9].equals("uV")||body_data[i][9].equals("mV")||body_data[i][9].equals("V")) {
																
																writer.append("," + body_data[i][9]);
																
															} else {
																	
																	// System.out.println("IS THIS RUN -1?");
																	writer.append(",");
																	
															}
																
															
														}
														
														


													}

												} else if (body_data[i][6].equals("N/A")) {

													for (int x = i - 2; x < i; x++) {

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {
														
														if(body_data[i][8].equals("N/A") || body_data[i][9].equals("N/A")) {
															
															writer.append(",");
															
														} else {
															
															if(body_data[i][6].equals("ohm")||body_data[i][6].equals("mohm")||body_data[i][6].equals("%")||body_data[i][6].equals("pA")||body_data[i][6].equals("nA")||body_data[i][6].equals("uA")||body_data[i][6].equals("mA")||body_data[i][6].equals("A")||body_data[i][6].equals("pV")||body_data[i][6].equals("nV")||body_data[i][6].equals("uV")||body_data[i][6].equals("mV")||body_data[i][6].equals("V")) {
																
																writer.append("," + body_data[i][6]);
																
															} else if(body_data[i][10].equals("ohm")||body_data[i][10].equals("mohm")||body_data[i][10].equals("%")||body_data[i][10].equals("pA")||body_data[i][10].equals("nA")||body_data[i][10].equals("uA")||body_data[i][10].equals("mA")||body_data[i][10].equals("A")||body_data[i][10].equals("pV")||body_data[i][10].equals("nV")||body_data[i][10].equals("uV")||body_data[i][10].equals("mV")||body_data[i][10].equals("V")) {
																
																writer.append("," + body_data[i][10]);
																
															} else {
																	
																	writer.append(",");
																	
															}
																
															
														}

													}

												} else if (body_data[i][3].equals("D1") || body_data[i][3].equals("D2")) { // added
													// "
													// "
													// exeception
													// for
													// cygnus
													// diode
													// item

													// System.out.println("ignore_item:"+ignore_item);

													for (int x = i - 2; x < i; x++) {
														// System.out.println("body_data[i][3]:"+body_data[i][3]);
														// System.out.println("body_data[i][4]:"+body_data[i][4]);
														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {

														writer.append("," + body_data[i][12]);

													}

												} else {

													for (int x = i - 2; x < i; x++) {

														if (body_data[i][3].equals(body_data[x][3])
																&& body_data[i][4].equals(body_data[x][4])) {

															ignore_item = 1;

															break;
														}

													}

													if (ignore_item == 1) {

														// do nothing
														ignore_item = 0;

													} else {

														writer.append("," + body_data[i][7]);

													}

												}

											} else if (body_data[i][3].equals(body_data[i - 1][3])
													&& body_data[i][4].equals(body_data[i - 1][4])) {

												// Do nothing

											} else if (body_data[i][4].equals("-1")) {

												for (int x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													if(body_data[i][7].equals("N/A") || body_data[i][8].equals("N/A")) {
														
														writer.append(",");
														
													} else {
														
														if(body_data[i][6].equals("ohm")||body_data[i][6].equals("mohm")||body_data[i][6].equals("%")||body_data[i][6].equals("pA")||body_data[i][6].equals("nA")||body_data[i][6].equals("uA")||body_data[i][6].equals("mA")||body_data[i][6].equals("A")||body_data[i][6].equals("pV")||body_data[i][6].equals("nV")||body_data[i][6].equals("uV")||body_data[i][6].equals("mV")||body_data[i][6].equals("V")) {
															
															writer.append("," + body_data[i][6]);
															
														} else if(body_data[i][9].equals("ohm")||body_data[i][9].equals("mohm")||body_data[i][9].equals("%")||body_data[i][9].equals("pA")||body_data[i][9].equals("nA")||body_data[i][9].equals("uA")||body_data[i][9].equals("mA")||body_data[i][9].equals("A")||body_data[i][9].equals("pV")||body_data[i][9].equals("nV")||body_data[i][9].equals("uV")||body_data[i][9].equals("mV")||body_data[i][9].equals("V")) {
															
															writer.append("," + body_data[i][9]);
															
														} else {
																
																writer.append(",");
																
														}
															
														
													}

												}

											} else if (body_data[i][6].equals("N/A")) {

												for (int x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													if(body_data[i][8].equals("N/A") || body_data[i][9].equals("N/A")) {
														
														writer.append(",");
														
													} else {
														
														if(body_data[i][6].equals("ohm")||body_data[i][6].equals("mohm")||body_data[i][6].equals("%")||body_data[i][6].equals("pA")||body_data[i][6].equals("nA")||body_data[i][6].equals("uA")||body_data[i][6].equals("mA")||body_data[i][6].equals("A")||body_data[i][6].equals("pV")||body_data[i][6].equals("nV")||body_data[i][6].equals("uV")||body_data[i][6].equals("mV")||body_data[i][6].equals("V")) {
															
															writer.append("," + body_data[i][6]);
															
														} else if(body_data[i][10].equals("ohm")||body_data[i][10].equals("mohm")||body_data[i][10].equals("%")||body_data[i][10].equals("pA")||body_data[i][10].equals("nA")||body_data[i][10].equals("uA")||body_data[i][10].equals("mA")||body_data[i][10].equals("A")||body_data[i][10].equals("pV")||body_data[i][10].equals("nV")||body_data[i][10].equals("uV")||body_data[i][10].equals("mV")||body_data[i][10].equals("V")) {
															
															writer.append("," + body_data[i][10]);
															
														} else {
																
																writer.append(",");
																
														}
															
														
													}

												}

											} else if (body_data[i][3].equals("D1") || body_data[i][3].equals("D2")) { // added
												// "
												// "
												// exeception
												// for
												// cygnus
												// diode
												// item

												// System.out.println("ignore_item:"+ignore_item);

												for (int x = i - 2; x < i; x++) {
													// System.out.println("body_data[i][3]:"+body_data[i][3]);
													// System.out.println("body_data[i][4]:"+body_data[i][4]);
													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													writer.append("," + body_data[i][12]);

												}

											}  else {

												for (int x = i - 2; x < i; x++) {

													if (body_data[i][3].equals(body_data[x][3])
															&& body_data[i][4].equals(body_data[x][4])) {

														ignore_item = 1;

														break;
													}

												}

												if (ignore_item == 1) {

													// do nothing
													ignore_item = 0;

												} else {

													writer.append("," + body_data[i][7]);

												}


											}

										}
									}

								}


								System.out.println("NO HEAD A?");								
								
								for (int i = 0; i < head_end_count; i++) {

									
									System.out.println("NO HEAD A-1?");	
									// System.out.println("head_end_count:"+head_end_count+":i:"+i);
									// System.out.println("head_data[i].length:"+head_data[i].length+":i:"+i);
									// System.out.println("head_data[i][1]:"+head_data[i][1]);


									// System.out.println("body_data[i][0]:"+i+":"+body_data[i][0]);
									// System.out.println("body_data[i][3]:"+i+":"+body_data[i][3]);

									if (head_data[i][1] == null) {

										// do nothing

									} else if (head_data[i][0].equals("Datalog") && head_data[i][1].equals("report")) {
										
										d1 = f.parse(head_data[i + 1][1]);

									} else if (head_data[i][1].equals("Lot:")) {

										Lot_id = head_data[i][2];

									} else if (head_data[i][1].equals("Node") && head_data[i][2].equals("Name:")) {

										ATE_id = head_data[i][3];

									} else if (head_data[i][1].equals("HandID:")) {

										hadler_id = head_data[i][2];

									} else {

										// do nothing
									}

								}


								System.out.println("NO HEAD B?");	
								
								for (int i = 0; i < 100; i++) {

									System.out.println("NO HEAD B-1?");
									
									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][3].equals("Test") || body_data[i][3].equals("alarm")
											|| body_data[i][3].equals("GPIB_Echo") || body_data[i][3].equals("Barcode")
											|| body_data[i][3].equals("[Pin")) {

										// System.out.println("prebody_data[i][0]:"+i+":"+body_data[i][0]);
										// System.out.println("prebody_data[i][3]:"+i+":"+body_data[i][3]);

										// do nothing

									} else if (body_data[i][3].equals("tests/Executed")) {
										// System.out.println("body_data[i][3]"+":"+i+":"+body_data[i][3]);
										// writer.append("\n");
										break;

									} else {


										// System.out.println("body_data[i][0]:"+i+":"+body_data[i][0]);
										// System.out.println("body_data[i][3]:"+i+":"+body_data[i][3]);

										if (body_data[i][0].equals("BARCODE") && !body_data[i][3].equals("0")) {

											// System.out.println("body_data[i][0]:"+body_data[i][0]);
											// System.out.println("body_data[i][3]:"+body_data[i][3]);

											site_counter[isite] = Integer.parseInt(body_data[i][2]);
											// writer.append(body_data[i][4]);

											isite = isite + 1;

										} else { // added for non site information case
											
											for(int z = 0 ; z < site_counter.length; z++) {
											
												if (body_data[i][2].equals(SITE32_array[z]) && site_counter[z] == 999) {
													
													site_counter[z] = Integer.parseInt(body_data[i][2]);
													
												}
												
											}
											
											
											
										}
									}

								}

								System.out.println("NO HEAD C?");	
								
								for (int i = body_loop_count - 50 ; i < body_loop_count + 1; i++) {

									System.out.println("NO HEAD C-1?");
									
									if(site_counter[bin_isite] == 999){
										
										// do nothing
										bin_isite = bin_isite + 1;
										
									}
									
									// System.out.println("current i:"+i);
									// System.out.println("body_loop_count:"+body_loop_count);
									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][3].equals("Test") || body_data[i][3].equals("alarm")
											|| body_data[i][3].equals("GPIB_Echo") || body_data[i][3].equals("Barcode")
											|| body_data[i][3].equals("[Pin")) {

										// System.out.println("body_data[i][0]:"+i+":"+body_data[i][0]);
										// System.out.println("body_data[i][1]:"+i+":"+body_data[i][1]);
										// System.out.println("body_data[i][2]:"+i+":"+body_data[i][2]);
										// System.out.println("body_data[i][3]:"+i+":"+body_data[i][3]);

										// do nothing

									} else {


										// System.out.println("body_data[i][0]:"+i+":"+body_data[i][0]);
										// System.out.println("body_data[i][1]:"+i+":"+body_data[i][1]);
										// System.out.println("body_data[i][2]:"+i+":"+body_data[i][2]);
										// System.out.println("body_data[i][3]:"+i+":"+body_data[i][3]);

										if (body_data[i][2].equals("Sort") && body_data[i][3].equals("Bin")) {

											bin_engage = 1;

										}  else if (bin_engage == 1
												&& body_data[i][1].equals(String.valueOf(site_counter[bin_isite]))) {

											site_Hbin_counter[bin_isite] = body_data[i][2];
											site_Sbin_counter[bin_isite] = body_data[i][3];

											if (site_Hbin_counter[bin_isite].equals("1")) {

												site_PF_counter[bin_isite] = "PASS";

											} else {

												site_PF_counter[bin_isite] = "FAIL";

											}

											bin_isite = bin_isite + 1;

										} else if (body_data[i][0].equals("Date:") && body_data[i][2].equals("Time:")) {

											// System.out.println("body_data[i][0]"+body_data[i][0]);
											// System.out.println("body_data[i][1]"+body_data[i][1]);
											// System.out.println("body_data[i][2]"+body_data[i][2]);
											// System.out.println("body_data[i][3]"+body_data[i][3]);
											date_data = body_data[i][1];

											d2 = f.parse(body_data[i][3]);
										}
									}

									if (i == body_loop_count)
										bin_engage = 0;

								}



								// if(body_loop_count == 54834) {

								// System.out.println("DEBUG POINT");

								// }

								System.out.println("NO HEAD D?");	
								
								for (int i = 0; i < site_counter.length; i++) {

									System.out.println("NO HEAD D-1?");
									
									if (site_counter[i] == 999) {

										// writer.append("\n");
										//break;

									} else {

										for (int x = 0; x < body_loop_count + 1; x++) {

											if (body_data[x][3] == null || body_data[x][3].isEmpty()
													|| body_data[x][3].equals("Test") || body_data[x][3].equals("alarm")
													|| body_data[x][3].equals("GPIB_Echo")
													|| body_data[x][3].equals("Barcode")
													|| body_data[x][3].equals("[Pin")) {

												// System.out.println("body_data[x][3]"+":"+i+":"+body_data[x][3]);
												// do nothing

											} else if (body_data[x][3].equals("tests/Executed")) {
	
												// System.out.println("body_data[x][3]"+":"+i+":"+body_data[x][3]);
												//writer.append("\n");
												break;

											} else if (body_data[x][0].equals("BARCODE")
													&& body_data[x][2].equals(String.valueOf(site_counter[i]))) {

												// System.out.println("site_counter[i]"+":"+i+":"+site_counter[i]);
												// System.out.println("body_data[x][0]"+":"+x+":"+body_data[x][0]);
												// System.out.println("body_data[x][1]"+":"+x+":"+body_data[x][1]);
												// System.out.println("body_data[x][2]"+":"+x+":"+body_data[x][2]);
												// System.out.println("body_data[x][3]"+":"+x+":"+body_data[x][3]);
												// System.out.println("body_data[x][4]"+":"+x+":"+body_data[x][4]);
												// System.out.println("body_data[x][5]"+":"+x+":"+body_data[x][5]);
												// System.out.println("body_data[x][6]"+":"+x+":"+body_data[x][6]);
												// System.out.println("body_data[x][7]"+":"+x+":"+body_data[x][7]);
												// System.out.println("body_data[x][8]"+":"+x+":"+body_data[x][8]);
												// System.out.println("body_data[x][9]"+":"+x+":"+body_data[x][9]);

												// System.out.println("d1:"+f.format(d1));
												// System.out.println("d2:"+f.format(d2));
												// System.out.println("d1.getTime():"+d1.getTime());
												// System.out.println("d2.getTime():"+d2.getTime());

												diff = d1.getTime() - d2.getTime();
												sec = diff / 1000;

												writer.append(body_data[x][3] + "," + site_PF_counter[i] + ","
														+ site_Hbin_counter[i] + "," + site_Sbin_counter[i] + ","
														+ site_counter[i] + "," + ATE_id + "," + hadler_id + ","
														+ date_data + " " + f.format(d1) + "," + date_data + " "
														+ f.format(d2) + "," + Math.abs(sec) + "," + Lot_id);
												
	
											//	System.out.println("x, body_loop_count: "+x+" "+body_loop_count);
											//	if(x == body_loop_count ) {
											//		System.out.println("line divider "+x);
											//		writer.append("\n");
												
											//	}
												
												
											} else if (body_data[x][2].equals(String.valueOf(site_counter[i]))) {


												if(first_head_information[i] == 0) {
													
													writer.append("Unknown" + "," + site_PF_counter[i] + ","
															+ site_Hbin_counter[i] + "," + site_Sbin_counter[i] + ","
															+ site_counter[i] + "," + "Unknown" + "," + "Unknown" + ","
															+ "Unknown" + " " + "Unknown" + "," + "Unknown" + " "
															+ "Unknown" + "," + "Unknown" + "," + "Unknown");
													
													first_head_information[i] = 1;
												}
												
												// System.out.println("site_counter[i]"+":"+i+":"+site_counter[i]);
												// System.out.println("body_data[x][0]"+":"+x+":"+body_data[x][0]);
												// System.out.println("body_data[x][1]"+":"+x+":"+body_data[x][1]);
												// System.out.println("body_data[x][2]"+":"+x+":"+body_data[x][2]);
												// System.out.println("body_data[x][3]"+":"+x+":"+body_data[x][3]);
												// System.out.println("body_data[x][4]"+":"+x+":"+body_data[x][4]);
												// System.out.println("body_data[x][5]"+":"+x+":"+body_data[x][5]);
												// System.out.println("body_data[x][6]"+":"+x+":"+body_data[x][6]);
												// System.out.println("body_data[x][7]"+":"+x+":"+body_data[x][7]);
												// System.out.println("body_data[x][8]"+":"+x+":"+body_data[x][8]);
												// System.out.println("body_data[x][9]"+":"+x+":"+body_data[x][9]);

												if (body_data[x][4].equals("-1")) {

													if (body_data[x][5].equals("N/A")) {

														writer.append("," + body_data[x][6]);

													} else if (body_data[x][6].equals("mohm") || body_data[x][6].equals("ohm") || body_data[x][6].equals("%") || body_data[x][6].equals("m") || body_data[x][6].equals("mA")
															|| body_data[x][6].equals("mV")
															|| body_data[x][6].equals("V")
															|| body_data[x][6].equals("A")
															|| body_data[x][6].equals("uA")
															|| body_data[x][6].equals("uV")
															|| body_data[x][6].equals("nA")
															|| body_data[x][6].equals("nV")) {

														writer.append("," + body_data[x][7]);

													} else {


														writer.append("," + body_data[x][6]);

													}

												} else if (body_data[x][6].equals("N/A")) {

													// if (body_data[x][9].equals("(A)") ||
													// body_data[x][9].equals("(F)")) {


													// writer.append("," + body_data[x][7]);
													// do nothing

													// } else {


													writer.append("," + body_data[x][7]);

													// }

												} else if (body_data[x][3].equals("D1")||body_data[x][3].equals("D2")) {

		
													writer.append("," + body_data[x][9]);


												} else {


													writer.append("," + body_data[x][8]);

												}

											//	System.out.println("x, body_loop_count: "+x+" "+body_loop_count);
												
											//	if(x == body_loop_count ) {
											//		System.out.println("line divider "+x);						
											//		writer.append("\n");
												
											//	}
												
											}

										}

									//	if(site_counter[i] == 999) {
										
											// do nothing
											
									//	} else {
										
											writer.append("\n");
										
									//	}
								
									}

								}

								System.out.println("NO HEAD E?");

								// Arrays.fill(body_data, "");
								// body_data = new String[172032][1024];

								for (int l = 0; l < body_loop_count + 1; l++) {

									for (int h = 0; h < body_data[l].length; h++) {

										body_data[l][h] = null;
										// System.out.println("body_data[l][h]:" + l
										// + ":" + h + ":" +body_data[l][h]);
									}
								}

								for (int l = 0; l < site_counter.length; l++) {

									site_counter[l] = 999;
									first_head_information[l] = 0;

								}

								for (int i = 0; i < site_Hbin_counter.length; i++) {

									site_Hbin_counter[i] = null;

								}

								for (int i = 0; i < site_Sbin_counter.length; i++) {

									site_Sbin_counter[i] = null;

								}

								for (int i = 0; i < site_PF_counter.length; i++) {

									site_PF_counter[i] = null;

								}

								isite = 0;
								line_done = 0;
								first_data_en = 1;
								data_align = 0;
								bin_isite = 0;

								// System.out.println("first data_align?"+data_align);
								// System.out.println("IS THIS first body part over?");

							} else if (rows != 0 && engage_main_body > 1 && line_done == 1) {

								// System.out.println("Engage MAIN BODY!!!!!!!");
								// break;

								// for(int i = body_loop_count-100; i < body_loop_count+1; i++) {
								// for(int y = 0; y < body_data[i].length; y++) {
								// System.out.println("body_data[i][y]:"+i+":"+y+":"+body_data[i][y]);
								// }
								// }

								System.out.println("NO HEAD F?");
								
								for (int i = 0; i < head_end_count; i++) {

									// System.out.println("head_end_count:"+head_end_count+":i:"+i);
									// System.out.println("head_data[i].length:"+head_data[i].length+":i:"+i);
									// System.out.println("head_data[i][1]:"+head_data[i][1]);


									// System.out.println("body_data[i][0]:"+i+":"+body_data[i][0]);
									// System.out.println("body_data[i][3]:"+i+":"+body_data[i][3]);

									if (head_data[i][1] == null) {

										// do nothing

									} else if (head_data[i][0].equals("Datalog") && head_data[i][1].equals("report")) {

										d1 = f.parse(head_data[i + 1][1]);

									} else if (head_data[i][1].equals("Lot:")) {

										Lot_id = head_data[i][2];

									} else if (head_data[i][1].equals("Node") && head_data[i][2].equals("Name:")) {

										ATE_id = head_data[i][3];

									} else if (head_data[i][1].equals("HandID:")) {

										hadler_id = head_data[i][2];

									} else {

										// do nothing
									}

								}

								System.out.println("NO HEAD G?");

								// for(int i = 0; i < body_loop_count+1; i++) {
								for (int i = 0; i < 100; i++) {
									// System.out.println("body_data[i][0]:"+i+":"+body_data[i][0]);
									// System.out.println("body_data[i][3]:"+i+":"+body_data[i][3]);

									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][3].equals("Test") || body_data[i][3].equals("alarm")
											|| body_data[i][3].equals("GPIB_Echo") || body_data[i][3].equals("Barcode")
											|| body_data[i][3].equals("[Pin")) {

										// System.out.println("prebody_data[i][0]:"+i+":"+body_data[i][0]);
										// System.out.println("prebody_data[i][3]:"+i+":"+body_data[i][3]);

										// do nothing

									} else if (body_data[i][3].equals("tests/Executed")) {
										// System.out.println("body_data[i][3]"+":"+i+":"+body_data[i][3]);
										// writer.append("\n");
										break;

									} else {


										// System.out.println("body_data[i][0]:"+i+":"+body_data[i][0]);
										// System.out.println("body_data[i][3]:"+i+":"+body_data[i][3]);

										if (body_data[i][0].equals("BARCODE") && !body_data[i][3].equals("0")) {

											// System.out.println("body_data[i][0]:"+body_data[i][0]);
											// System.out.println("body_data[i][3]:"+body_data[i][3]);

											site_counter[isite] = Integer.parseInt(body_data[i][2]);
											// writer.append(body_data[i][4]);

											isite = isite + 1;

										} else { // added for non site information case
											
											for(int z = 0 ; z < site_counter.length; z++) {
												
												if (body_data[i][2].equals(SITE32_array[z]) && site_counter[z] == 999) {
													
													site_counter[z] = Integer.parseInt(body_data[i][2]);
													
												}
												
											}
											
											
										}
									}

								}

								System.out.println("NO HEAD H?");
								
								for (int i = body_loop_count - 50; i < body_loop_count + 1; i++) {
									
									System.out.println("site_counter[bin_isite]: "+bin_isite+" "+site_counter[bin_isite]);
									
									
									if(site_counter[bin_isite] == 999){
										
										// do nothing
										bin_isite = bin_isite + 1;
										
									}
									
									// System.out.println("current i:"+i);
									// System.out.println("body_loop_count:"+body_loop_count);
									if (body_data[i][3] == null || body_data[i][3].isEmpty()
											|| body_data[i][3].equals("Test") || body_data[i][3].equals("alarm")
											|| body_data[i][3].equals("GPIB_Echo") || body_data[i][3].equals("Barcode")
											|| body_data[i][3].equals("[Pin")) {

										// System.out.println("body_data[i][0]:"+i+":"+body_data[i][0]);
										// System.out.println("body_data[i][1]:"+i+":"+body_data[i][1]);
										// System.out.println("body_data[i][2]:"+i+":"+body_data[i][2]);
										// System.out.println("body_data[i][3]:"+i+":"+body_data[i][3]);

										// do nothing

									} else {


										// System.out.println("body_data[i][0]:"+i+":"+body_data[i][0]);
										// System.out.println("body_data[i][1]:"+i+":"+body_data[i][1]);
										// System.out.println("body_data[i][2]:"+i+":"+body_data[i][2]);
										// System.out.println("body_data[i][3]:"+i+":"+body_data[i][3]);

										if (body_data[i][2].equals("Sort") && body_data[i][3].equals("Bin")) {

											bin_engage = 1;

										}  else if (bin_engage == 1
												&& body_data[i][1].equals(String.valueOf(site_counter[bin_isite]))) {

											site_Hbin_counter[bin_isite] = body_data[i][2];
											site_Sbin_counter[bin_isite] = body_data[i][3];

											if (site_Hbin_counter[bin_isite].equals("1")) {

												site_PF_counter[bin_isite] = "PASS";

												// System.out.println("site_counter[bin_isite]:"+String.valueOf(site_counter[bin_isite]));
												// System.out.println("site_PF_counter[bin_isite]:"+site_PF_counter[bin_isite]);
											} else {

												site_PF_counter[bin_isite] = "FAIL";

												// System.out.println("site_counter[bin_isite]:"+String.valueOf(site_counter[bin_isite]));
												// System.out.println("site_PF_counter[bin_isite]:"+site_PF_counter[bin_isite]);
											}

											bin_isite = bin_isite + 1;

										} else if (body_data[i][0].equals("Date:") && body_data[i][2].equals("Time:")) {

											// System.out.println("body_data[i][0]"+body_data[i][0]);
											// System.out.println("body_data[i][1]"+body_data[i][1]);
											// System.out.println("body_data[i][2]"+body_data[i][2]);
											// System.out.println("body_data[i][3]"+body_data[i][3]);
											// System.out.println("ftime_count:"+time_count);

											date_data = body_data[i][1];

											time[time_count] = f.parse(body_data[i][3]);
											time_count = time_count + 1;

											// System.out.println("atime_count:"+time_count);
										}
									}

									// System.out.println("body_loop_count:"+body_loop_count+":i:"+i);
									if (i == body_loop_count)
										bin_engage = 0;

								}



								
								// if(body_loop_count == 54834) {

								// System.out.println("DEBUG S POINT");

								// }

								System.out.println("NO HEAD I?");
								//System.out.println("site_counter.length " + site_counter.length);
								
								for (int i = 0; i < site_counter.length; i++) {
										System.out.println("site_counter[i] "+i+" "+site_counter[i]);
										System.out.println("SITE32_array[i] "+i+" "+SITE32_array[i]);
									if (site_counter[i] == 999) {
										
										// writer.append("\n");
										//break;

									} else {
										System.out.println("NO HEAD I-2?");
										for (int x = 0; x < body_loop_count + 1; x++) {
											// System.out.println("IS THIS RUN SC1?");
											if (body_data[x][3] == null || body_data[x][3].isEmpty()
													|| body_data[x][3].equals("Test") || body_data[x][3].equals("alarm")
													|| body_data[x][3].equals("GPIB_Echo")
													|| body_data[x][3].equals("Barcode")
													|| body_data[x][3].equals("[Pin")) {

												// System.out.println("body_data[x][3]"+":"+i+":"+body_data[x][3]);
												// do nothing

											} else if (body_data[x][3].equals("tests/Executed")) {

												// System.out.println("body_data[x][3]"+":"+i+":"+body_data[x][3]);
												// writer.append("\n");
												break;

											} else if (body_data[x][0].equals("BARCODE")
													&& body_data[x][2].equals(String.valueOf(site_counter[i]))) {

												// System.out.println("site_counter[i]"+":"+i+":"+site_counter[i]);
												// System.out.println("body_data[x][0]"+":"+x+":"+body_data[x][0]);
												// System.out.println("body_data[x][1]"+":"+x+":"+body_data[x][1]);
												// System.out.println("body_data[x][2]"+":"+x+":"+body_data[x][2]);
												// System.out.println("body_data[x][3]"+":"+x+":"+body_data[x][3]);
												// System.out.println("body_data[x][4]"+":"+x+":"+body_data[x][4]);
												// System.out.println("body_data[x][5]"+":"+x+":"+body_data[x][5]);
												// System.out.println("body_data[x][6]"+":"+x+":"+body_data[x][6]);
												// System.out.println("body_data[x][7]"+":"+x+":"+body_data[x][7]);
												// System.out.println("body_data[x][8]"+":"+x+":"+body_data[x][8]);
												// System.out.println("body_data[x][9]"+":"+x+":"+body_data[x][9]);

												// System.out.println("d1:"+f.format(d1));
												// System.out.println("d2:"+f.format(d2));
												// System.out.println("time_count:"+(time_count-1));
												// System.out.println("time[time_count-1]:"+f.format(time[time_count-1]));
												// System.out.println("d1.getTime():"+d1.getTime());
												// System.out.println("d2.getTime():"+d2.getTime());
												// System.out.println("time[time_count-1].getTime():"+time[time_count-1].getTime());

												if (engage_main_body == 2) {

													diff = d2.getTime() - time[time_count - 1].getTime();
													sec = diff / 1000;

													writer.append(body_data[x][3] + "," + site_PF_counter[i] + ","
															+ site_Hbin_counter[i] + "," + site_Sbin_counter[i] + ","
															+ site_counter[i] + "," + ATE_id + "," + hadler_id + ","
															+ date_data + " " + f.format(d2) + "," + date_data + " "
															+ f.format(time[time_count - 1]) + "," + Math.abs(sec) + ","
															+ Lot_id);

												} else {

													diff = time[time_count - 2].getTime()
															- time[time_count - 1].getTime();
													sec = diff / 1000;

													writer.append(body_data[x][3] + "," + site_PF_counter[i] + ","
															+ site_Hbin_counter[i] + "," + site_Sbin_counter[i] + ","
															+ site_counter[i] + "," + ATE_id + "," + hadler_id + ","
															+ date_data + " " + f.format(time[time_count - 2]) + ","
															+ date_data + " " + f.format(time[time_count - 1]) + ","
															+ Math.abs(sec) + "," + Lot_id);

												}

												
												//if(x == body_loop_count ) {
													
												//	writer.append("\n");
												
												//}
												
											} else if (body_data[x][2].equals(String.valueOf(site_counter[i]))) {


												// System.out.println("site_counter[i]"+":"+i+":"+site_counter[i]);
												// System.out.println("body_data[x][0]"+":"+x+":"+body_data[x][0]);
												// System.out.println("body_data[x][1]"+":"+x+":"+body_data[x][1]);
												// System.out.println("body_data[x][2]"+":"+x+":"+body_data[x][2]);
												// System.out.println("body_data[x][3]"+":"+x+":"+body_data[x][3]);
												// System.out.println("body_data[x][4]"+":"+x+":"+body_data[x][4]);
												// System.out.println("body_data[x][5]"+":"+x+":"+body_data[x][5]);
												// System.out.println("body_data[x][6]"+":"+x+":"+body_data[x][6]);
												// System.out.println("body_data[x][7]"+":"+x+":"+body_data[x][7]);
												// System.out.println("body_data[x][8]"+":"+x+":"+body_data[x][8]);
												// System.out.println("body_data[x][9]"+":"+x+":"+body_data[x][9]);

												if(first_head_information[i] == 0) {
													
													writer.append("Unknown" + "," + site_PF_counter[i] + ","
															+ site_Hbin_counter[i] + "," + site_Sbin_counter[i] + ","
															+ site_counter[i] + "," + "Unknown" + "," + "Unknown" + ","
															+ "Unknown" + " " + "Unknown" + "," + "Unknown" + " "
															+ "Unknown" + "," + "Unknown" + "," + "Unknown");
													
													first_head_information[i] = 1;
												}
												
												
												if (body_data[x][4].equals("-1")) {

													if (body_data[x][5].equals("N/A")) {

														writer.append("," + body_data[x][6]);

													} else if (body_data[x][6].equals("mohm") || body_data[x][6].equals("ohm") || body_data[x][6].equals("%") || body_data[x][6].equals("m") || body_data[x][6].equals("mA")
															|| body_data[x][6].equals("mV")
															|| body_data[x][6].equals("V")
															|| body_data[x][6].equals("A")
															|| body_data[x][6].equals("uA")
															|| body_data[x][6].equals("uV")
															|| body_data[x][6].equals("nA")
															|| body_data[x][6].equals("nV")) {

														writer.append("," + body_data[x][7]);

													} else {


														writer.append("," + body_data[x][6]);

													}

												} else if (body_data[x][6].equals("N/A")) {

													// if (body_data[x][9].equals("(A)") ||
													// body_data[x][9].equals("(F)")) {


													// writer.append("," + body_data[x][7]);
													// do nothing

													// } else {


													writer.append("," + body_data[x][7]);

													// }

												} else if (body_data[x][3].equals("D1")||body_data[x][3].equals("D2")) {

		
													writer.append("," + body_data[x][9]);


												} else {

	
													writer.append("," + body_data[x][8]);

												}

												//if(x == body_loop_count ) {
													
												//	writer.append("\n");
												
												//}
												
											}

										}

			
								//		if(site_counter[i] == 999) {
											
											// do nothing
											
									//	} else {
										
											writer.append("\n");
										
									//	}
									}

								}

								System.out.println("NO HEAD J?");

								// Arrays.fill(body_data, "");
								// body_data = new String[172032][1024];

								for (int l = 0; l < body_loop_count + 1; l++) {

									for (int h = 0; h < body_data[l].length; h++) {

										body_data[l][h] = null;
										// System.out.println("body_data[l][h]:" + l
										// + ":" + h + ":" +body_data[l][h]);
									}
								}

								for (int l = 0; l < site_counter.length; l++) {

									site_counter[l] = 999;
									first_head_information[l] = 0;

								}

								for (int i = 0; i < site_Hbin_counter.length; i++) {

									site_Hbin_counter[i] = null;

								}

								for (int i = 0; i < site_Sbin_counter.length; i++) {

									site_Sbin_counter[i] = null;

								}

								for (int i = 0; i < site_PF_counter.length; i++) {

									site_PF_counter[i] = null;

								}

								isite = 0;
								line_done = 0;
								first_data_en = 1;
								data_align = 0;
								bin_isite = 0;

								// System.out.println("second data_align?"+data_align);
								//System.out.println("IS THIS Second body part over?");

							}

							rows = rows + 1;

							
							//System.out.println("NO HEAD K?");
							// if(rows == 100) break;

							// writer.append(TXTloadbuffer.toString());

							// TXTloadbuffer.setLength(0);

						}

						// System.out.println("head_data.length:" + head_data.length);
						// System.out.println("head_data[x].length:" + head_data[1].length);

						/*
						 * for (int x = 0; x < head_data.length; x++) {
						 * 
						 * for (int y = 0; y < head_data[x].length; y++) {
						 * 
						 * System.out.println("IS THIS RUN?");
						 * 
						 * if (head_data[x][y] == "") {
						 * 
						 * break;
						 * 
						 * } else {
						 * 
						 * 
						 * System.out.println("head_data[x][y]:" + x + ":" + y + ":" + head_data[x][y]);
						 * 
						 * }
						 * 
						 * }
						 * 
						 * }
						 */
						// writer.append("this is massive file read and write test");

						System.out.println("NO HEAD L?");						
						
						fileEmpty.setText("");
						fileEmpty.setText("   processing..........done");

						reader.close();
						writer.close();

					} catch (Exception e2) {

						JOptionPane.showMessageDialog(this, "Open Error");

					}
				}

				// dispose(); // do not close for checking result.

			}

		} else if (e.getSource() == btncancel) {
			dispose();

		}

	}

	private String cstr(File file) {
		// TODO Auto-generated method stub
		return null;
	}
}
