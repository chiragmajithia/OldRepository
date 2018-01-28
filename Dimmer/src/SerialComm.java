/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import gnu.io.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author Chirag
 */
public class SerialComm implements SerialPortEventListener {
    char[] timer;
    boolean ready, new_read;
    int cnt;
    protected InputStream inputStream;
    protected OutputStream outputStream;
    public char read;
    public static final int TIMEOUTSECONDS = 30;
    public static int BAUD;
    protected String response;
    protected CommPortIdentifier selectedPortIdentifier;
    protected SerialPort serialPort;
    protected JLabel readChars;
    protected JProgressBar jVL;

    public SerialComm(String name, CommPortIdentifier sPI, int baud, JLabel rc, JProgressBar jVoltLvl) {
        jVL=jVoltLvl;
        timer = new char[2];
        readChars = rc;
        ready = false;
        cnt = 10;
        selectedPortIdentifier = sPI;
        BAUD = baud;
        try {
            serialPort = (SerialPort) selectedPortIdentifier.open(name + "open", TIMEOUTSECONDS * 1000);
           // System.out.println("serialPort.getName() = " + serialPort.getName());
        } catch (PortInUseException ex) {
            Logger.getLogger(Dimmer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
        } catch (IOException e) {
        }

        try {
            serialPort.addEventListener(this);
        } catch (TooManyListenersException e) {
        }

        //serialPort.notifyOnDataAvailable(true);
        serialPort.notifyOnDataAvailable(true);

        try {
            serialPort.setSerialPortParams(BAUD, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            //serialPort.setFlowControlMode(serialPort.FLOWCONTROL_RTSCTS_OUT);
        } catch (UnsupportedCommOperationException e) {
        }

    }

    public void serialEvent(SerialPortEvent event) {
        
        SerialPort p = (SerialPort) event.getSource();
        //System.out.print("event.getSource().toString();=" + p.getName());
        
        switch (event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:
                new_read=true;
                byte[] readBuffer = new byte[1];
                try {
                    while (inputStream.available() > 0) {
                        int numBytes = inputStream.read(readBuffer);
                        String in = new String(readBuffer);
                        int z = (char) in.charAt(0);
                        z = (readBuffer[0] & 0xff);
                        read = (char) z;
                        if(cnt < 2)
                        {
                            timer[cnt]=(char)z;
                            cnt++;
                        } 
                        if(cnt == 2)
                        {
                            if(jVL.getString().length()>25)
                                jVL.setString("");
                            int delay=(timer[0]*255+timer[1]);
                            double v =220.0- (delay * 220/4800);
                            readChars.setText("TH0= "+ (int)timer[0] + "TL0= "+ (int)timer[1]+ "Val = "+delay);
                            jVL.setIndeterminate(false);
                            jVL.setValue(delay);
                            jVL.setString(Double.toString(v));
                            jVL.setStringPainted(true);
                            cnt++;
                        }
                        if((char)z == 'S' && cnt > 1)
                        {   
                          cnt = 0;
                        }
                          
                        readChars.setText(readChars.getText()+in);
                        //System.out.println("cnt="+cnt);
                    }
                } catch (IOException e) {
                    System.out.println("error");
                }
                break;
        }

    }

    public char readInput()
    {
        new_read=false;
        return read;
    }

    public void write(char out) {
        try {
            outputStream.flush();
            outputStream.write(out);
            ready=false;
            //cnt++;
            //System.out.println("sucess"+ out);
            
        } catch (IOException e) {
            System.out.println("error in writing" + e);
        }

    }
}
