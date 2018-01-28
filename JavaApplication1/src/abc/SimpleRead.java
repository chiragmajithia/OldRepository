package abc;

import gnu.io.*;
import java.io.*;
import java.util.*;


public class SimpleRead implements Runnable, SerialPortEventListener {
    static CommPortIdentifier portId;
    static Enumeration	      portList;
    InputStream		      inputStream;
    SerialPort		      serialPort;
    Thread		      readThread;
    int cnt;
    char start_char;
    boolean start_frame;
    char[] input = new char[8];

    public static void main(String[] args) {
    boolean		      portFound = false;
    String		      defaultPort = "COM8";

 	if (args.length > 0) {
	    defaultPort = args[0];
	}

	portList = CommPortIdentifier.getPortIdentifiers();

	while (portList.hasMoreElements()) {
	    portId = (CommPortIdentifier) portList.nextElement();
	    if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
		if (portId.getName().equals(defaultPort)) {
		    System.out.println("Found port: "+defaultPort);
		    portFound = true;
		    SimpleRead reader = new SimpleRead();
		}
	    }
	}
	if (!portFound) {
	    System.out.println("port " + defaultPort + " not found.");
	}

    }

    public SimpleRead() {
        cnt=0;
        start_char='A';
        start_frame=false;
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
	    serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
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
	    Thread.sleep(20000);
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
                    System.out.println("in=" +in+"cnt="+cnt);
                    int z=(char)in.charAt(0);
                    z=(readBuffer[0] & 0xff);
                    if(z== (int)start_char)
                    {
                        start_frame=true;
                        System.out.println("in here");
                    }
                    if(start_frame)
                    {
                    input[cnt]=(char)z;
                    System.out.println("z="+input[cnt]+"cnt="+cnt);
                    cnt++;
                    if(cnt==8)
                    {
                        cnt=0;
                        start_frame=false;
                        input[3]='1';
                        input[7]='1';
                        System.out.println("o/p="+new String(input));
                        hextodouble(input);
                    }
                    }
                    }

		
	    } catch (IOException e) {}

	    break;
	}
    }

    void hextodouble(char a[])
    {
        double hex_int=0;
                        for(int i=0;i<8;i++)
                        {
                            hex_int=hex_int+(Character.digit(a[i],16)*Math.pow(16, (i+1)*-1));
                           // System.out.println("input hex"+Character.digit(a[i],16)+"sum="+hex_int);
                        }
                        System.out.println("hex_int"+hex_int);
    }

}
