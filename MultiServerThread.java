package window;

import java.net.*;
import java.io.*;

public class MultiServerThread implements Runnable {
	private Socket socket;
	private MultiServer ms;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public static int ServerIndex = 0;
	
	public MultiServerThread(MultiServer ms) {
		this.ms = ms;
	}

	public  void run() {
		boolean isStop = false;
		try {
			
			System.out.println("Count server thread :"+ServerIndex);
			
			ServerIndex++;
			
			socket = ms.getSocket();
			
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			
			String message = null;
			
			while (!isStop) {
				
				message = (String) ois.readObject();
				
				String[] str = message.split("#");
								
				System.out.println("str0:"+str[0]);
				System.out.println("str1:"+str[1]);
				
				String name = "list"+"#";
				
				for(int i=0; i<ms.getList().size(); i++){
					name += ms.getList().get(i)+"#";
				}
				
				if (str[1].equals("exit")) {
					broadCasting(message);
					isStop = true;
				} else if(str[1].equals("list")){
					broadCasting(name);
				} else {
					broadCasting(message);
				}
			}		
			
			
			broadCasting(socket.getInetAddress() + "정상적으로 종료하셨습니다.");
			broadCasting("Current Connected Users : " + ms.getList().size());
			
			System.out.println(socket.getInetAddress() + "정상적으로 종료하셨습니다.");
			System.out.println("Current Connected Users : " + ms.getList().size());
		
			ms.getList().remove(this);
			
		}catch (Exception e) {
			
			
			try {
				broadCasting(socket.getInetAddress() + "비정상적으로 종료하셨습니다.");
				broadCasting("Current Connected Users : " + ms.getList().size());
			
				System.out.println(socket.getInetAddress() + "비정상적으로 종료하셨습니다.");
				System.out.println("list size : " + ms.getList().size());
				
				ms.getList().remove(this);
			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		
		
		}
	}

	public void broadCasting(String message) throws IOException {
		for (MultiServerThread ct : ms.getList()) {
			ct.send(message);
		}
	}

	public void send(String message) throws IOException {
		oos.writeObject(message);
	}
}