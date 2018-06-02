package jrdesktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Config.java
 * @author benbac
 */

public class Config {
    
    public static boolean GUI_disabled = false;
    public static boolean Systray_disabled = false;
    
    public static void loadConfiguration() {
    
            try {
                Properties properties = new Properties();
                properties.load(new FileInputStream(Rdesktopmain.CONFIG_FILE));
                //GUI_disabled = Boolean.valueOf(properties.getProperty("GUI-disabled"));
                GUI_disabled = true;
                //Systray_disabled = Boolean.valueOf(properties.getProperty("Systray-disabled"));
                Systray_disabled = true; 
            }
            catch (Exception e) {
                e.getStackTrace();
            }
     
            storeConfiguration();  
    }
    
    public static void storeConfiguration () {
        try {
            new File(Rdesktopmain.CONFIG_FILE).createNewFile();        
            Properties properties = new Properties();
            properties.put("GUI-disabled", String.valueOf(GUI_disabled));
            properties.put("Systray-disabled", String.valueOf(Systray_disabled));        
            properties.store(new FileOutputStream(Rdesktopmain.CONFIG_FILE), 
                "jrdesktop configuration file"); 
        } catch (Exception e) {
            e.getStackTrace();
        }            
    }            
}
