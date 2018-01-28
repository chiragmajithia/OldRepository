package abc;

import gnu.io.*;
import java.io.*;
import java.util.*;


public class SimpleReadTime implements Runnable, SerialPortEventListener {
    static CommPortIdentifier portId;
    static Enumeration	      portList;
    InputStream		      inputStream;
    SerialPort		      serialPort;
    Thread		      readThread;
    public static void main(String[] args) 
    {
    boolean portFound = false;
    String  defaultPort = "COM5";

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
		    SimpleReadTime reader = new SimpleReadTime();
		}
	    }
	}
	if (!portFound) {
	    System.out.println("port " + defaultPort + " not found.");
	}

    }

    public SimpleReadTime() {
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
	    serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
	} catch (UnsupportedCommOperationException e) {}

	readThread = new Thread(this);
        readThread.start();
    }

    public void run() {
	try {
	    Thread.sleep(20000);
	} catch (InterruptedException e) {}
    }

    public void serialEvent(SerialPortEvent event) {
        int cnt=0;
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
	    byte[] readBuffer = new byte[2];

	    try {
		while (inputStream.available() > 0) {
                    int numBytes = inputStream.read(readBuffer);
                    String in=new String(readBuffer);
                    int z=(char)in.charAt(0);
                    z=(readBuffer[0] & 0xff);
                    System.out.println((cnt++) +" "+z+","+(z*2.5/255));
                }

		
	    } catch (IOException e) {}

	    break;
	}
    }

}
