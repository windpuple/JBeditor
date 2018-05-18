package window;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MultiServer  implements Runnable{
	private ArrayList<MultiServerThread> list;
	private Socket socket;
	
	public void run() {
		list = new ArrayList<MultiServerThread>();
		
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(5000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MultiServerThread mst = null;
		
		boolean isStop = false;
		
		while (!isStop) {
			
			JOptionPane.showMessageDialog(null, "Server ready. waiting Socket accept", "Notice", JOptionPane.INFORMATION_MESSAGE );
			System.out.println("Server ready...");
			System.out.println("socket acceept wait");
			
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Socket accept Failed. Not Connected", "Notice", JOptionPane.WARNING_MESSAGE );
				e.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(null, "Socket accepted. Connect Established", "Notice", JOptionPane.INFORMATION_MESSAGE );
			System.out.println("socket acceept finish");
			mst = new MultiServerThread(this);
			list.add(mst);
			
			Thread t = new Thread(mst);
			t.start();
		}
	}
		

	public ArrayList<MultiServerThread> getList(){
		return list;
	}

	public Socket getSocket() {
		return socket;
	}



	
}