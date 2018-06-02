package window;

import java.io.IOException;
import java.io.ObjectInputStream;

public class MultiClientThread extends Thread{
    private MultiClient mc;
	private ObjectInputStream ois;
    
    public MultiClientThread(MultiClient mc){
        this.mc = mc;
    }
    
    
    public void run(){
        String message = null;
        String[] receivedMsg = null;
        
        boolean isStop = false;
        
        try {
		
        	ois = new ObjectInputStream(MultiClient.clientsocket.getInputStream());
		
        } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        while(!isStop){
            try{
                message = (String)ois.readObject();
                receivedMsg = message.split("#");

            }catch(Exception e){
                e.printStackTrace();
                isStop = true;
            }
            
            //client side message debug
            //System.out.println("rmsg0:"+receivedMsg[0]);
            //System.out.println("rmsg1:"+receivedMsg[1]);
            //System.out.println(receivedMsg[0]+","+receivedMsg[1]);
            
            if(receivedMsg[1].equals("exit")){
                if(receivedMsg[0].equals(mc.getId())){
                    try {
						mc.exit();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }else{
                    mc.getJta().append(
                    receivedMsg[0] +"님이 종료 하셨습니다."+
                    System.getProperty("line.separator"));
                    mc.getJta().setCaretPosition(
                    mc.getJta().getDocument().getLength());
                }
            }else if(receivedMsg[1].equals("change")){               
                mc.changepower=true;
                mc.getJta().append("바꿀 아이디를 입력하세요"+ System.getProperty("line.separator"));
                String name = receivedMsg[1];
                mc.SetName(name);
            }else if(receivedMsg[1].equals("clear")){               
            	mc.Clear();
            }else if(receivedMsg[0].equals(mc.getId())){
            	mc.getJta().append(
            			receivedMsg[0] +" : "+receivedMsg[1]+
                        System.getProperty("line.separator"));
                        mc.getJta().setCaretPosition(
                            mc.getJta().getDocument().getLength());  
            	
            }else if(receivedMsg[0].equals("list")){            	
            	int len =receivedMsg.length-1;
            	String numStr2 = String.valueOf(len);
            	mc.getJta().append("현재접속인원 :"+numStr2+System.getProperty("line.separator"));
            	for(int i=0;i<receivedMsg.length;i++){
            		mc.getJta().append(receivedMsg[i]+System.getProperty("line.separator"));
            	}       
            }else{               
                mc.getJta().append(
                receivedMsg[0] +" : "+receivedMsg[1]+
                System.getProperty("line.separator"));
                mc.getJta().setCaretPosition(
                    mc.getJta().getDocument().getLength());     
            }
        }
    }
}