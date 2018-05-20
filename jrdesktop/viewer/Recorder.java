package jrdesktop.viewer;

import java.awt.Rectangle;
import java.util.ArrayList;

import jrdesktop.viewer.rmi.Viewer;

/**
 * Recorder.java
 * @author benbac
 */

public class Recorder extends Thread {
    
    private boolean recording = false;          // control recording

    public Viewer viewer;    
    public ViewerGUI viewerGUI;
    public ScreenPlayer screenPlayer;
    
    public Recorder(Viewer viewer) {
        this.viewer = viewer;      
        start(); 
      
        screenPlayer = new ScreenPlayer(this);
        viewerGUI = new ViewerGUI(this);
    }
    
    @Override
    public void  run()
    {
        while (true) {
            Wait();

            while (recording) {                            
                viewer.sendData();            
                viewer.recieveData();
            } 
        }
    }
   
    public void Wait() {
        try {
            synchronized(this) {    
                wait();
            }
        }
        catch (Exception e) {
            e.getStackTrace();
        }         
    }
    
    public void Notify() {
        try {
            synchronized(this){            
                notify();
            }    
        }
        catch (Exception e) {
            e.getStackTrace();
        }   
    }
    
    public void Stop() {
        recording = false;   
        screenPlayer.removeAdapters();
        screenPlayer.clearScreen();                 
        viewer.disconnect();
    }
    
    public void Start() {
        if (!viewer.isConnected()) 
            if (viewer.connect() == -1) return; 
        screenPlayer.addAdapters();         
        recording = true;               
        Notify();     
    }
                
    public boolean isRecording () { 
        return recording;
    }        
    
    public void updateData(ArrayList objects){
        screenPlayer.UpdateScreen((byte[]) objects.get(0));
        screenPlayer.setScteenRect((Rectangle) objects.get(1));        
    }
}
