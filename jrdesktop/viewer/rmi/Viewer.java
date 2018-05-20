package jrdesktop.viewer.rmi;

import jrdesktop.server.rmi.ServerInterface;
import jrdesktop.viewer.Recorder;
import jrdesktop.utilities.InetAdrUtility;
import jrdesktop.utilities.ZipUtility;
import jrdesktop.viewer.Config;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 * Viewer.java
 * @author benbac
 */

public class Viewer extends Thread {
    
    private int index = -1;
    private Recorder recorder;
    
    private Registry registry; 
    private ServerInterface rmiServer;
       
    private String server = "127.0.0.1";
    private int port = 6666;
    
    private boolean connected = false;
    
    private ArrayList<Object> Objects;        
        
    public Viewer () {
        Config.loadConfiguration();
        server = Config.server_address;
        port = Config.server_port;              
    }   
    
    public boolean isConnected() {
        return connected;
    }
    
    public void Start() { 
        connect();
        if (connected) {
            recorder = new Recorder(this);        
            recorder.viewerGUI.Start();
        }        
        else Stop();
    }
    
    public void Stop() {
        if (recorder != null) {
            recorder.Stop();
            recorder.interrupt();
        }
        disconnect();    
        interrupt();
    }
    
    public int connect() {  
        connected = false;
        
        try {       
                registry = LocateRegistry.getRegistry(server, port);        
          
            rmiServer = (ServerInterface) registry.lookup("ServerImpl");                          
         
            index = rmiServer.startViewer(InetAdrUtility.getLocalAdr());                        
            
            displayStatus();
            Objects = new ArrayList<Object>();
            connected = true;
            return index;
       } catch (Exception e) {    
           JOptionPane.showMessageDialog(null, e.getMessage(), "Error !!",
                    JOptionPane.ERROR_MESSAGE);
           return -1;
       }     
    }
    
    public void disconnect() {
        connected = false;
        try {
            if (rmiServer != null) {
                    rmiServer.stopViewer(index);
                    UnicastRemoteObject.unexportObject(rmiServer, true);
            }
        }
        catch (Exception e) {
            e.getStackTrace(); 
        } 
      rmiServer = null;
      registry = null;
    }
    
    public void updateData(Object object) {
        byte[] data;
        try {
             data = ZipUtility.objecttoByteArray(object);

            updateData(data);  
        }
        catch (IOException e) {
            e.getStackTrace();
        }
    }
    
    public void updateData(byte[] data) {
        try {rmiServer.updateData(data, index);} 
        catch (Exception re) {
            re.getStackTrace();
        }        
    }
    
    public void AddObject(Object object) {
        Objects.add(object);
    }   
   
   /* public void updateOptions () {     
        try {rmiServer.updateOptions(
                recorder.getViewerData(), index);
        } 
        catch (Exception re) {
            re.getStackTrace();
        }          
    }*/
    
    public void sendData() {
        ArrayList SendObjects;       
        synchronized(Objects){                       
            
            SendObjects = Objects; 
            Objects = new ArrayList<Object>();
        }
        updateData(SendObjects);
    }     
    
    public void recieveData() {
        Object object = null;
        try {
            byte[] data = rmiServer.updateData(index);
            object = ZipUtility.byteArraytoObject(data);              
                         
            recorder.updateData((ArrayList) object);    
        }
        catch (Exception e) {
            e.getStackTrace();
        }           
    }       
    
    public void displayStatus() {          
        System.out.println("Viewer connected to " + rmiServer);        
    }
}
