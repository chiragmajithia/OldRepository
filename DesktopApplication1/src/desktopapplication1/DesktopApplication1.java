/*
 * DesktopApplication1.java
 */

package desktopapplication1;

import gnu.io.*;
import java.io.*;
import java.util.*;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class DesktopApplication1 extends SingleFrameApplication implements Runnable, SerialPortEventListener {
    @Override protected void startup() {
        show(new DesktopApplication1View(this));
    }
    @Override protected void configureWindow(java.awt.Window root) {
    }
    public static DesktopApplication1 getApplication() {
        return Application.getInstance(DesktopApplication1.class);
    }

    static CommPortIdentifier portId;
    static Enumeration portList;
    InputStream	 inputStream;
    SerialPort serialPort;
    Thread readThread;
    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(DesktopApplication1.class, args);
        boolean portFound = false;


//        String defaultPort=jCommPortSelect;

    }

    public void run(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void serialEvent(SerialPortEvent spe) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
