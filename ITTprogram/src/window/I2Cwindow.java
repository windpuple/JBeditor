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

public class I2Cwindow extends JDialog implements ActionListener {

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
	private JTextField WriteData_Text_Field;

	public I2Cwindow() {

		dTXTfName = "";
		dTXTfdirectory = "";
		dTXTffile = "";
		swavheader = "";
		dTXTSavefName = "";
		TXTloadbuffer.setLength(0);

		setTitle("I2C Write Vector Generator");
		setModal(true);

		btn.setBounds(33, 222, 98, 21);
		btn.addActionListener(this);
		getContentPane().setLayout(null);
		getContentPane().add(btn);

		JLabel ChipID = new JLabel("ChipID");
		ChipID.setBounds(33, 50, 120, 15);
		getContentPane().add(ChipID);

		btncancel.setBounds(143, 221, 97, 23);
		getContentPane().add(btncancel);
		btncancel.addActionListener(this);

		noticeTXT.setForeground(Color.BLUE);
		noticeTXT.setBounds(43, 191, 197, 21);
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

		WriteData_Text_Field = new JTextField();
		WriteData_Text_Field.setBounds(115, 155, 116, 21);
		getContentPane().add(WriteData_Text_Field);
		WriteData_Text_Field.setColumns(10);

		JLabel Address = new JLabel("Address");
		Address.setBounds(33, 100, 150, 21);
		getContentPane().add(Address);

		JLabel WriteData = new JLabel("WriteData");
		WriteData.setBounds(33, 159, 150, 21);
		getContentPane().add(WriteData);

		setBackground(Color.LIGHT_GRAY);
		setBounds(350, 250, 283, 307);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String Chip_ID_input = "";
		String Adress_input = "";
		String Write_data_input = "";

		String[] Chip_ID_hexa = new String[4];
		String[] Adress_hexa = new String[4];
		String[] Write_data_hexa = new String[4];
		
		int Chip_ID_input_length = 0;
		int Adress_input_length = 0;
		int Write_data_input_length = 0;
		
		Chip_ID_input = ChipID_Text_Field.getText();
		Adress_input = Address_Text_Field.getText();
		Write_data_input = WriteData_Text_Field.getText();

		Chip_ID_input = Chip_ID_input.replaceAll("0x", "");
		Adress_input = Adress_input.replaceAll("0x", "");
		Write_data_input = Write_data_input.replaceAll("0x", "");

		Chip_ID_input_length = Chip_ID_input.length() * 4;
		Adress_input_length = Adress_input.length() * 4;
		Write_data_input_length = Write_data_input.length() * 4;
		
		if (e.getSource() == btn) {

			if (ChipID_Text_Field.getText().isEmpty() || Address_Text_Field.getText().isEmpty()
					|| WriteData_Text_Field.getText().isEmpty()) {
				noticeTXT.setText("");
				noticeTXT.setText("text filed에 값이 없습니다.");

			} else {

				String[] hexa = { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001",
						"1010", "1011", "1100", "1101", "1110", "1111" };

				String bin = "";

				// System.out.println("16진수 문자열을 입력하시오 : ");

				for (int x = 0; x < 3; x++) {

					if (x == 0)
						bin = Chip_ID_input;
					if (x == 1)
						bin = Adress_input;
					if (x == 2)
						bin = Write_data_input;

					System.out.println("bin+에 대한 이진수는 ");

					for (int i = 0; i < bin.length(); i++) {
						switch (bin.charAt(i)) {
						case '0':
							System.out.println(hexa[0]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[0];
							if (x == 1)
								Adress_hexa[i] = hexa[0];
							if (x == 2)
								Write_data_hexa[i] = hexa[0];

							break;

						case '1':
							System.out.println(hexa[1]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[1];
							if (x == 1)
								Adress_hexa[i] = hexa[1];
							if (x == 2)
								Write_data_hexa[i] = hexa[1];

							break;

						case '2':
							System.out.println(hexa[2]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[2];
							if (x == 1)
								Adress_hexa[i] = hexa[2];
							if (x == 2)
								Write_data_hexa[i] = hexa[2];

							break;

						case '3':
							System.out.println(hexa[3]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[3];
							if (x == 1)
								Adress_hexa[i] = hexa[3];
							if (x == 2)
								Write_data_hexa[i] = hexa[3];

							break;

						case '4':
							System.out.println(hexa[4]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[4];
							if (x == 1)
								Adress_hexa[i] = hexa[4];
							if (x == 2)
								Write_data_hexa[i] = hexa[4];

							break;
						case '5':
							System.out.println(hexa[5]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[5];
							if (x == 1)
								Adress_hexa[i] = hexa[5];
							if (x == 2)
								Write_data_hexa[i] = hexa[5];

							break;

						case '6':
							System.out.println(hexa[6]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[6];
							if (x == 1)
								Adress_hexa[i] = hexa[6];
							if (x == 2)
								Write_data_hexa[i] = hexa[6];

							break;

						case '7':
							System.out.println(hexa[7]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[7];
							if (x == 1)
								Adress_hexa[i] = hexa[7];
							if (x == 2)
								Write_data_hexa[i] = hexa[7];

							break;

						case '8':
							System.out.println(hexa[8]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[8];
							if (x == 1)
								Adress_hexa[i] = hexa[8];
							if (x == 2)
								Write_data_hexa[i] = hexa[8];

							break;

						case '9':
							System.out.println(hexa[9]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[9];
							if (x == 1)
								Adress_hexa[i] = hexa[9];
							if (x == 2)
								Write_data_hexa[i] = hexa[9];

							break;

						case 'A':
							System.out.println(hexa[10]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[10];
							if (x == 1)
								Adress_hexa[i] = hexa[10];
							if (x == 2)
								Write_data_hexa[i] = hexa[10];

							break;
						
						case 'a':
							System.out.println(hexa[10]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[10];
							if (x == 1)
								Adress_hexa[i] = hexa[10];
							if (x == 2)
								Write_data_hexa[i] = hexa[10];

							break;

							
						case 'B':
							System.out.println(hexa[11]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[11];
							if (x == 1)
								Adress_hexa[i] = hexa[11];
							if (x == 2)
								Write_data_hexa[i] = hexa[11];

							break;

						case 'b':
							System.out.println(hexa[11]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[11];
							if (x == 1)
								Adress_hexa[i] = hexa[11];
							if (x == 2)
								Write_data_hexa[i] = hexa[11];

							break;

						case 'C':
							System.out.println(hexa[12]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[12];
							if (x == 1)
								Adress_hexa[i] = hexa[12];
							if (x == 2)
								Write_data_hexa[i] = hexa[12];

							break;

						case 'c':
							System.out.println(hexa[12]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[12];
							if (x == 1)
								Adress_hexa[i] = hexa[12];
							if (x == 2)
								Write_data_hexa[i] = hexa[12];

							break;
							
						case 'D':
							System.out.println(hexa[13]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[13];
							if (x == 1)
								Adress_hexa[i] = hexa[13];
							if (x == 2)
								Write_data_hexa[i] = hexa[13];

							break;

						case 'd':
							System.out.println(hexa[13]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[13];
							if (x == 1)
								Adress_hexa[i] = hexa[13];
							if (x == 2)
								Write_data_hexa[i] = hexa[13];

							break;
							
						case 'E':
							System.out.println(hexa[14]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[14];
							if (x == 1)
								Adress_hexa[i] = hexa[14];
							if (x == 2)
								Write_data_hexa[i] = hexa[14];

							break;

						case 'e':
							System.out.println(hexa[14]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[14];
							if (x == 1)
								Adress_hexa[i] = hexa[14];
							if (x == 2)
								Write_data_hexa[i] = hexa[14];

							break;

							
						case 'F':
							System.out.println(hexa[15]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[15];
							if (x == 1)
								Adress_hexa[i] = hexa[15];
							if (x == 2)
								Write_data_hexa[i] = hexa[15];

							break;

						case 'f':
							System.out.println(hexa[15]);

							if (x == 0)
								Chip_ID_hexa[i] = hexa[15];
							if (x == 1)
								Adress_hexa[i] = hexa[15];
							if (x == 2)
								Write_data_hexa[i] = hexa[15];

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
				TXTloadbuffer.append("vector  ($tset, I2C_SDA, I2C_SCK )\n");
				TXTloadbuffer.append("{\n");
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
				
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[0].charAt(1) + "     0    ; //Slave_Add  0x"
				//		+ Chip_ID_hexa + "\n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[0].charAt(1) + "     1    ; //6\n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[0].charAt(1) + "     0    ;\n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[0].charAt(2) + "     0    ;\n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[0].charAt(2) + "     1    ; //5\n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[0].charAt(2) + "     0    ; \n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[0].charAt(3) + "     0    ; \n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[0].charAt(3) + "     1    ; //4\n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[0].charAt(3) + "     0    ;\n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[1].charAt(0) + "     0    ;\n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[1].charAt(0) + "     1    ; //3\n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[1].charAt(0) + "     0    ;\n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[1].charAt(1) + "     0    ;\n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[1].charAt(1) + "     1    ; //2\n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[1].charAt(1) + "     0    ;\n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[1].charAt(2) + "     0    ;\n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[1].charAt(2) + "     1    ; //1\n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[1].charAt(2) + "     0    ;\n");	
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[1].charAt(3) + "     0    ;\n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[1].charAt(3) + "     1    ; //0\n");
				//TXTloadbuffer.append("   > TS1    " + Chip_ID_hexa[1].charAt(3) + "     0    ; \n");
				
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

				
				//################### ADDRESS WRITE PART ###################
			    //TXTloadbuffer.append("   > TS1    0     0    ; /ADD  0x00" & Add_Data);
			    //TXTloadbuffer.append("   > TS1    0     1    ; //15");
			    //TXTloadbuffer.append("   > TS1    0     0    ;");
			    //TXTloadbuffer.append("   > TS1    0     0    ; ");
			    //TXTloadbuffer.append("   > TS1    0     1    ; //14");
			    //TXTloadbuffer.append("   > TS1    0     0    ;");
			    //TXTloadbuffer.append("   > TS1    0     0    ; ");
			    //TXTloadbuffer.append("   > TS1    0     1    ; //13");
			    //TXTloadbuffer.append("   > TS1    0     0    ;");
			    //TXTloadbuffer.append("   > TS1    0     0    ; ");
			    //TXTloadbuffer.append("   > TS1    0     1    ; //12");
			    //TXTloadbuffer.append("   > TS1    0     0    ;");
			    //TXTloadbuffer.append("   > TS1    0     0    ; ");
			    //TXTloadbuffer.append("   > TS1    0     1    ; //11");
			    //TXTloadbuffer.append("   > TS1    0     0    ;");
			    //TXTloadbuffer.append("   > TS1    0     0    ; ");
			    //TXTloadbuffer.append("   > TS1    0     1    ; //10");
			    //TXTloadbuffer.append("   > TS1    0     0    ;");
			    //TXTloadbuffer.append("   > TS1    0     0    ;");
			    //TXTloadbuffer.append("   > TS1    0     1    ; //9 ");
			    //TXTloadbuffer.append("   > TS1    0     0    ;");
			    //TXTloadbuffer.append("   > TS1    0     0    ;");
			    //TXTloadbuffer.append("   > TS1    0     1    ; //8");
			    //TXTloadbuffer.append("   > TS1    0     0    ; ");

			    
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
			      
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(7) & "     0    ; ");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(7) & "     1    ; //7");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(7) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(6) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(6) & "     1    ; //6");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(6) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(5) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(5) & "     1    ; //5");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(5) & "     0    ; ");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(4) & "     0    ; ");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(4) & "     1    ; //4");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(4) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(3) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(3) & "     1    ; //3");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(3) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(2) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(2) & "     1    ; //2");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(2) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(1) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(1) & "     1    ; //1");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(1) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(0) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(0) & "     1    ; //0");
			    //TXTloadbuffer.append("   > TS1    " & Add_bit(0) & "     0    ; ");

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
				
			    //################### REG_DATA WRITE PART ###################
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(15) & "     0    ; //REG_DATA  0x" & Reg_Data);
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(15) & "     1    ; //15");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(15) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(14) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(14) & "     1    ; //14");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(14) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(13) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(13) & "     1    ; //13");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(13) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(12) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(12) & "     1    ; //12");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(12) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(11) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(11) & "     1    ; //11");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(11) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(10) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(10) & "     1    ; //10");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(10) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(9) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(9) & "     1    ; //09");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(9) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(8) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(8) & "     1    ; //08");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(8) & "     0    ;");
			    
				for(int j = 0; j < 2; j++) {
					
					if(Write_data_hexa[j] != null) {
					
						for(int k = 0; k < 4; k++) {
							

								
								if(j ==0 && k == 0) {
									
									TXTloadbuffer.append("   > TS1    " + Write_data_hexa[j].charAt(k) + "     0    ; //WRITE_DATA 0x"
											+ Write_data_input + "\n");
									
								} else {
								
									TXTloadbuffer.append("   > TS1    " + Write_data_hexa[j].charAt(k) + "     0    ;\n");
									
								}
								
								
								TXTloadbuffer.append("   > TS1    " + Write_data_hexa[j].charAt(k) + "     1    ; //"+((Write_data_input_length-1)-count)+"\n");
								TXTloadbuffer.append("   > TS1    " + Write_data_hexa[j].charAt(k) + "     0    ; \n");
								
				
								count = count + 1;
						}
						
					}
				}
			    
			    
			    TXTloadbuffer.append("   > TS1    X     0    ;\n");
			    TXTloadbuffer.append("   > TS1    L     1    ; //ack\n");
			    TXTloadbuffer.append("   > TS1    X     0    ;\n");
			    
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(7) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(7) & "     1    ; //07");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(7) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(6) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(6) & "     1    ; //06");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(6) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(5) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(5) & "     1    ; //05");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(5) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(4) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(4) & "     1    ; //04");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(4) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(3) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(3) & "     1    ; //03");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(3) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(2) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(2) & "     1    ; //02");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(2) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(1) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(1) & "     1    ; //01");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(1) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(0) & "     0    ;");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(0) & "     1    ; //00");
			    //TXTloadbuffer.append("   > TS1    " & Data_bit(0) & "     0    ;");

				for(int j = 2; j < 4; j++) {
					
					if(Write_data_hexa[j] != null) {
					
						for(int k = 0; k < 4; k++) {
							


								TXTloadbuffer.append("   > TS1    " + Write_data_hexa[j].charAt(k) + "     0    ;\n");
								TXTloadbuffer.append("   > TS1    " + Write_data_hexa[j].charAt(k) + "     1    ; //"+((Write_data_input_length-1)-count)+"\n");
								TXTloadbuffer.append("   > TS1    " + Write_data_hexa[j].charAt(k) + "     0    ; \n");
								
								count = count + 1;
							
						}
						
					}
				}
			    
				count = 0;
			    
			    TXTloadbuffer.append("   > TS1    X     0    ;\n");
			    TXTloadbuffer.append("   > TS1    L     1    ; //ack\n");
			    TXTloadbuffer.append("   > TS1    X     0    ;\n");
			    TXTloadbuffer.append("   > TS1    0     0    ;\n");
			    TXTloadbuffer.append("   repeat 100\n");
			    TXTloadbuffer.append("   > TS1    0     1    ;\n");
			    TXTloadbuffer.append("   > TS1    1     1    ; //STOP\n");
			    TXTloadbuffer.append("   halt\n");
			    TXTloadbuffer.append("   > TS1    1     1    ;\n");
			    TXTloadbuffer.append("}\n");

			    
				dispose();
				
			}

		} else if (e.getSource() == btncancel) {
			dispose();

		}

	}

}
