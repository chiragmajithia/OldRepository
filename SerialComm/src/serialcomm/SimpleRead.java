package serialcomm;

import gnu.io.*;
import java.io.*;
import java.util.*;


public class SimpleRead implements Runnable, SerialPortEventListener {
    
    InputStream		      inputStream;
    SerialPort		      serialPort;
    Thread		      readThread;
    int cnt;
    char start_char;
    boolean start_frame;
    boolean data_fetched;
    char[] input = new char[12];
    double voltage, current;

     public SimpleRead(CommPortIdentifier portId) {
        data_fetched=false;
        cnt=0;
        start_char='A';
        start_frame=true;
	try {
	    serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
	} catch (PortInUseException e) {}

	try {
	    inputStream = serialPort.getInputStream();
	} catch (IOException e) {}

	try {
	    serialPort.addEventListener(this);
	} catch (TooManyListenersException e) {}

	serialPort.notifyOnDataAvailable(true);

	try {
	    serialPort.setSerialPortParams(19200, SerialPort.DATABITS_8,
					   SerialPort.STOPBITS_1,
					   SerialPort.PARITY_NONE);
	} catch (UnsupportedCommOperationException e) {}

	readThread = new Thread(this);

	readThread.start();
    }

    /**
     * Method declaration
     *
     *
     * @see
     */
    public void run() {
	try {
	    Thread.sleep(10000);
	} catch (InterruptedException e) {}
    }

    /**
     * Method declaration
     *
     *
     * @param event
     *
     * @see
     */
    public void serialEvent(SerialPortEvent event) {

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
	    byte[] readBuffer = new byte[1];
            try {

                    while (inputStream.available() > 0)
                    {
                    int numBytes = inputStream.read(readBuffer);
                    String in=new String(readBuffer);
                    String hexequi;
                    System.out.println("in=" +in+"cnt="+cnt);
                    int z=(char)in.charAt(0);
                    z=(readBuffer[0] & 0xff);
                    hexequi=Integer.toHexString(z);
                    System.out.println("hex_equi"+hexequi);
                    if(z== (int)start_char)
                    {
                        start_frame=true;
                        System.out.println("in here");
                    }
                    if(start_frame)
                    {
                    input[cnt]=hexequi.charAt(0);
                    System.out.println("z="+input[cnt]+"cnt="+cnt);
                    cnt++;
                    input[cnt]=hexequi.charAt(1);
                    System.out.println("z="+input[cnt]+"cnt="+cnt);
                    cnt++;
                    if(cnt==12)
                    {
                        String V,I,i;
                        i=new String(input);
                        System.out.println("i="+i);
                        V=i.substring(0,6);
                        I=i.substring(6,12);
                        System.out.println("V= "+V+"I= "+I);
                        cnt=0;
                        start_frame=true;
                        System.out.println("o/p="+new String(input));
                        voltage=hextodouble(V.toCharArray());
                        current=hextodouble(I.toCharArray());
                        data_fetched=true;
                    }
                    }
                    }


	    } catch (IOException e) {}

	    break;
	}
    }

    double hextodouble(char a[])
    {
        double hex_int=0;
                        for(int i=a.length-1;i>=0;i--)
                        {

                            hex_int=hex_int+(Character.digit(a[i],16)*Math.pow(16,(a.length-1-i)));
                            System.out.println("converting "+ a[i]+ "to "+ Character.digit(a[i],10));
                            //hex_int=hex_int*250;
                           // System.out.println("input hex"+Character.digit(a[i],16)+"sum="+hex_int);
                        }
                        System.out.println("hex_int"+hex_int);
                        return hex_int;
    }

    double fetchVoltage()
    {
        return voltage;
    }
     double fetchCurrent()
    {
        return current;
    }

}
