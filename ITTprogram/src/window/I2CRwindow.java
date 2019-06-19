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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Arrays;
import java.util.Date;
import java.text.ParseException;

public class I2CRwindow extends JDialog implements ActionListener {

	JButton btn = new JButton("Generate");
	JButton btncancel = new JButton("Close");

	JLabel noticeTXT = new JLabel("");
	JLabel fileEmpty = new JLabel("");

	public static StringBuffer TXTloadbuffer = new StringBuffer(); // MAX cap is 12383875 line

	static int p = 0;

	static String dTXTfName;
	static String dTXTfdirectory;
	static String dTXTffile;
	static String dTXTSavefName;
	static String swavheader;
	private JTextField ChipID_Text_Field;
	private JTextField Address_Text_Field;

	public I2CRwindow() {

		dTXTfName = "";
		dTXTfdirectory = "";
		dTXTffile = "";
		swavheader = "";
		dTXTSavefName = "";
		TXTloadbuffer.setLength(0);

		setTitle("I2C Read Vector Generator");
		setModal(true);

		btn.setBounds(32, 159, 98, 21);
		btn.addActionListener(this);
		getContentPane().setLayout(null);
		getContentPane().add(btn);

		JLabel ChipID = new JLabel("ChipID");
		ChipID.setBounds(33, 50, 120, 15);
		getContentPane().add(ChipID);

		btncancel.setBounds(142, 158, 97, 23);
		getContentPane().add(btncancel);
		btncancel.addActionListener(this);

		noticeTXT.setForeground(Color.BLUE);
		noticeTXT.setBounds(35, 131, 197, 21);
		getContentPane().add(noticeTXT);

		fileEmpty.setForeground(Color.RED);
		fileEmpty.setBounds(23, 75, 207, 15);
		getContentPane().add(fileEmpty);

		ChipID_Text_Field = new JTextField();
		ChipID_Text_Field.setBounds(115, 46, 116, 21);
		getContentPane().add(ChipID_Text_Field);
		ChipID_Text_Field.setColumns(10);

		Address_Text_Field = new JTextField();
		Address_Text_Field.setBounds(115, 99, 116, 21);
		getContentPane().add(Address_Text_Field);
		Address_Text_Field.setColumns(10);

		JLabel Address = new JLabel("Address");
		Address.setBounds(33, 100, 150, 21);
		getContentPane().add(Address);

		setBackground(Color.LIGHT_GRAY);
		setBounds(350, 250, 283, 233);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String Chip_ID_input = "";
		String Adress_input = "";


		String[] Chip_ID_hexa = new String[4];
		String[] Adress_hexa = new String[4];

		int Chip_ID_input_length = 0;
		int Adress_input_length = 0;


		Chip_ID_input = ChipID_Text_Field.getText();
		Adress_input = Address_Text_Field.getText();


		Chip_ID_input = Chip_ID_input.replaceAll("0x", "");
		Adress_input = Adress_input.replaceAll("0x", "");

		Chip_ID_input_length = Chip_ID_input.length() * 4;
		Adress_input_length = Adress_input.length() * 4;


		if (e.getSource() == btn) {

			if (ChipID_Text_Field.getText().isEmpty() || Address_Text_Field.getText().isEmpty()) {
				noticeTXT.setText("");
				noticeTXT.setText("text filed에 값이 없습니다.");

			} else {

				String[] hexa = { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001",
						"1010", "1011", "1100", "1101", "1110", "1111" };

				String bin = "";

				// System.out.println("16진수 문자열을 입력하시오 : ");

				for (int x = 0; x < 2; x++) {

					if (x == 0)
						bin = Chip_ID_input;
					if (x == 1)
						bin = Adress_input;


					System.out.println("bin+에 대한 이진수는 ");

					for (int i = 0; i < bin.length(); i++) {
						switch (bin.charAt(i)) {
						case '0':
							System.out.println(hexa[0]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[0];
							if (x == 1)
								Adress_hexa[i] = hexa[0];


							break;

						case '1':
							System.out.println(hexa[1]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[1];
							if (x == 1)
								Adress_hexa[i] = hexa[1];


							break;

						case '2':
							System.out.println(hexa[2]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[2];
							if (x == 1)
								Adress_hexa[i] = hexa[2];


							break;

						case '3':
							System.out.println(hexa[3]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[3];
							if (x == 1)
								Adress_hexa[i] = hexa[3];


							break;

						case '4':
							System.out.println(hexa[4]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[4];
							if (x == 1)
								Adress_hexa[i] = hexa[4];


							break;
						case '5':
							System.out.println(hexa[5]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[5];
							if (x == 1)
								Adress_hexa[i] = hexa[5];


							break;

						case '6':
							System.out.println(hexa[6]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[6];
							if (x == 1)
								Adress_hexa[i] = hexa[6];


							break;

						case '7':
							System.out.println(hexa[7]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[7];
							if (x == 1)
								Adress_hexa[i] = hexa[7];


							break;

						case '8':
							System.out.println(hexa[8]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[8];
							if (x == 1)
								Adress_hexa[i] = hexa[8];


							break;

						case '9':
							System.out.println(hexa[9]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[9];
							if (x == 1)
								Adress_hexa[i] = hexa[9];


							break;

						case 'A':
							System.out.println(hexa[10]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[10];
							if (x == 1)
								Adress_hexa[i] = hexa[10];


							break;
						
						case 'a':
							System.out.println(hexa[10]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[10];
							if (x == 1)
								Adress_hexa[i] = hexa[10];


							break;

							
						case 'B':
							System.out.println(hexa[11]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[11];
							if (x == 1)
								Adress_hexa[i] = hexa[11];


							break;

						case 'b':
							System.out.println(hexa[11]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[11];
							if (x == 1)
								Adress_hexa[i] = hexa[11];


							break;

						case 'C':
							System.out.println(hexa[12]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[12];
							if (x == 1)
								Adress_hexa[i] = hexa[12];


							break;

						case 'c':
							System.out.println(hexa[12]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[12];
							if (x == 1)
								Adress_hexa[i] = hexa[12];


							break;
							
						case 'D':
							System.out.println(hexa[13]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[13];
							if (x == 1)
								Adress_hexa[i] = hexa[13];

							break;

						case 'd':
							System.out.println(hexa[13]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[13];
							if (x == 1)
								Adress_hexa[i] = hexa[13];


							break;
							
						case 'E':
							System.out.println(hexa[14]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[14];
							if (x == 1)
								Adress_hexa[i] = hexa[14];


							break;

						case 'e':
							System.out.println(hexa[14]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[14];
							if (x == 1)
								Adress_hexa[i] = hexa[14];


							break;

							
						case 'F':
							System.out.println(hexa[15]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[15];
							if (x == 1)
								Adress_hexa[i] = hexa[15];


							break;

						case 'f':
							System.out.println(hexa[15]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[15];
							if (x == 1)
								Adress_hexa[i] = hexa[15];


							break;

						default:
							noticeTXT.setText("");
							noticeTXT.setText("잘못된 값을 입력하였습니다.");
						}

						if (i == bin.length())
							System.out.println("\n");

					}

				}

				TXTloadbuffer.append("import tset TS1;\n");
				TXTloadbuffer.append("\n");
				TXTloadbuffer.append("instruments = {\n");
				TXTloadbuffer.append("        (I2C_SDA):DigCap:1;\n");
				TXTloadbuffer.append("}\n");
				TXTloadbuffer.append("\n");
				TXTloadbuffer.append("\n");
				TXTloadbuffer.append("vector ($tset, I2C_SDA, I2C_SCK )\n");
				TXTloadbuffer.append("{\n");
				TXTloadbuffer.append("start_label I2C_read_data:\n");
				TXTloadbuffer.append("((I2C_SDA):DigCap = Trig I2C_read_data) \n");
				TXTloadbuffer.append("   > TS1    1     1    ; // INIT  \n");
				TXTloadbuffer.append("   repeat 1000 \n");
				TXTloadbuffer.append("   > TS1    1     1    ;\n");
				TXTloadbuffer.append("   > TS1    1     1    ;\n");
				TXTloadbuffer.append("   > TS1    0     1    ;\n");
				TXTloadbuffer.append("   > TS1    0     1    ;\n");
				TXTloadbuffer.append("   > TS1    0     1    ;\n");
				TXTloadbuffer.append("   > TS1    0     0    ; // START\n");
				TXTloadbuffer.append("   > TS1    0     0    ;\n");
				TXTloadbuffer.append("   > TS1    0     0    ;\n");
				
				
				int count = 1;
				
				for(int j = 0; j < Chip_ID_hexa.length; j++) {
					
					if(Chip_ID_hexa[j] != null) {
					
						for(int k = 0; k < 4; k++) {
							
							if(j == 0 && k == 0) {
								
								//do nothing
								
							} else {
								
								if(j ==0 && k == 1) {
									
									TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[j].charAt(k) + "     0    ; //Slave_Add  0x"
											+ Chip_ID_input + "\n");
									
								} else {
								
									TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[j].charAt(k) + "     0    ;\n");
									
								}
								
								
								TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[j].charAt(k) + "     1    ; //"+((Chip_ID_input_length-1)-count)+"\n");
								TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[j].charAt(k) + "     0    ; \n");
								
								count = count + 1;
								
							}
							
						}
						
					}
				}
				
				count = 0;
				
				TXTloadbuffer.append("   > TS1    0     0    ;\n");
				TXTloadbuffer.append("   > TS1    0     1    ;// write bit\n");
				TXTloadbuffer.append("   > TS1    0     0    ;\n");
				TXTloadbuffer.append("   > TS1    X     0    ;\n");
				TXTloadbuffer.append("   > TS1    L     1    ; //ack\n");
				TXTloadbuffer.append("   > TS1    X     0    ;\n");

			    
				for(int j = 0; j < 2; j++) {
					
					if(Adress_hexa[j] != null) {
					
						for(int k = 0; k < 4; k++) {
							

								
								if(j ==0 && k == 0) {
									
									TXTloadbuffer.append("   > TS1    " + Adress_hexa[j].charAt(k) + "     0    ; //ADD 0x"
											+ Adress_input + "\n");
									
								} else {
								
									TXTloadbuffer.append("   > TS1    " + Adress_hexa[j].charAt(k) + "     0    ;\n");
									
								}
								
								
								TXTloadbuffer.append("   > TS1    " + Adress_hexa[j].charAt(k) + "     1    ; //"+((Adress_input_length-1)-count)+"\n");
								TXTloadbuffer.append("   > TS1    " + Adress_hexa[j].charAt(k) + "     0    ; \n");
								
				
								count = count + 1;
						}
						
					}
				}
			    
				
			    TXTloadbuffer.append("   > TS1    X     0    ;\n");
			    TXTloadbuffer.append("   > TS1    L     1    ; //ack\n");
			    TXTloadbuffer.append("   > TS1    X     0    ;\n");
			      
				for(int j = 2; j < 4; j++) {
					
					if(Adress_hexa[j] != null) {
					
						for(int k = 0; k < 4; k++) {
							


								TXTloadbuffer.append("   > TS1    " + Adress_hexa[j].charAt(k) + "     0    ;\n");
								TXTloadbuffer.append("   > TS1    " + Adress_hexa[j].charAt(k) + "     1    ; //"+((Adress_input_length-1)-count)+"\n");
								TXTloadbuffer.append("   > TS1    " + Adress_hexa[j].charAt(k) + "     0    ; \n");
								
								count = count + 1;
							
						}
						
					}
				}
			    
				count = 0;
	
				if(Adress_hexa[2] != null) {
				
					TXTloadbuffer.append("   > TS1    X     0    ;\n");
					TXTloadbuffer.append("   > TS1    L     1    ; //ack\n");
					TXTloadbuffer.append("   > TS1    X     0    ;\n");

				}
				
			    TXTloadbuffer.append("   > TS1    0     1    ;\n");
			    TXTloadbuffer.append("   > TS1    0     1    ;\n");
			    TXTloadbuffer.append("   > TS1    0     1    ;\n");
			    TXTloadbuffer.append("   repeat 10 \n");
			    TXTloadbuffer.append("   > TS1    1     1    ;\n");
			    TXTloadbuffer.append("   > TS1    1     1    ;\n");
			    TXTloadbuffer.append("   > TS1    0     1    ;\n");
			    TXTloadbuffer.append("   > TS1    0     1    ;\n");
			    TXTloadbuffer.append("   > TS1    0     0    ;\n");		    
			    

				count = 1;
				
				for(int j = 0; j < Chip_ID_hexa.length; j++) {
					
					if(Chip_ID_hexa[j] != null) {
					
						for(int k = 0; k < 4; k++) {
							
							if(j == 0 && k == 0) {
								
								//do nothing
								
							} else {
								
								if(j ==0 && k == 1) {
									
									TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[j].charAt(k) + "     0    ; //Slave_Add  0x"
											+ Chip_ID_input + "\n");
									
								} else {
								
									TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[j].charAt(k) + "     0    ;\n");
									
								}
								
								
								TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[j].charAt(k) + "     1    ; //"+(7-count)+"\n");
								TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[j].charAt(k) + "     0    ; \n");
								
								count = count + 1;
								
							}
							
						}
						
					}
				}
				
				count = 0;
				
				TXTloadbuffer.append("   > TS1    1     0    ;\n");
				TXTloadbuffer.append("   > TS1    1     1    ;// read bit\n");
				TXTloadbuffer.append("   > TS1    1     0    ;\n");
				TXTloadbuffer.append("   > TS1    X     0    ;\n");
				TXTloadbuffer.append("   > TS1    L     1    ; //ack\n");
				TXTloadbuffer.append("   > TS1    X     0    ;\n");
			    
			   
			    
				for(int j = 0; j < 2; j++) {
					
					
						for(int k = 0; k < 4; k++) {
							

								
								if(j ==0 && k == 0) {
									
									TXTloadbuffer.append("   > TS1    X     0    ; //readXX Start\n");
									
								} else {
								
									TXTloadbuffer.append("   > TS1    X     0    ; \n");
									
								}
								
								TXTloadbuffer.append("((I2C_SDA):DigCap = Store)\n");
								TXTloadbuffer.append("   > TS1    V     1    ; //"+(15-count)+"\n");
								TXTloadbuffer.append("   > TS1    X     0    ;\n");
								
				
								count = count + 1;
						}
						
					
				}
			    
			    
			    TXTloadbuffer.append("   > TS1    0     0    ; \n");
			    TXTloadbuffer.append("   > TS1    0     1    ; //ACK\n");
			    TXTloadbuffer.append("   > TS1    0     0    ; \n");
			    

				for(int j = 2; j < 4; j++) {
					
					for(int k = 0; k < 4; k++) {
						

						

						
						TXTloadbuffer.append("   > TS1    X     0    ; \n");
						TXTloadbuffer.append("((I2C_SDA):DigCap = Store)\n");
						TXTloadbuffer.append("   > TS1    V     1    ; //"+(15-count)+"\n");
						TXTloadbuffer.append("   > TS1    X     0    ;\n");
						
		
						count = count + 1;
					}
				}
			    
				count = 0;
			    
			    TXTloadbuffer.append("   > TS1    1     0    ; \n");
			    TXTloadbuffer.append("   > TS1    1     1    ; //ACK\n");
			    TXTloadbuffer.append("   > TS1    1     0    ;\n");
			    TXTloadbuffer.append("repeat 3 \n");
			    TXTloadbuffer.append("   > TS1    0     1    ; //repeat 3\n");
			    TXTloadbuffer.append("   > TS1    1     1    ; //STOP\n");
			    TXTloadbuffer.append("repeat 100\n");
			    TXTloadbuffer.append("   > TS1    1     1    ;\n");
			    TXTloadbuffer.append("halt \n");
			    TXTloadbuffer.append("   > TS1    1     1    ;\n");
			    TXTloadbuffer.append("}\n");
			    
				dispose();
				
			}

		} else if (e.getSource() == btncancel) {
			dispose();

		}

	}

}
