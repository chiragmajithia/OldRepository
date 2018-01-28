package abc;


import gnu.io.*;
import java.util.*;
public class ListPorts {
  public static void main(String args[]) {
    Enumeration ports = CommPortIdentifier.getPortIdentifiers();
      System.out.println("Comm has: "+ports.toString());
    while (ports.hasMoreElements()) {
      CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
      String type;
      switch (port.getPortType()) {
      case CommPortIdentifier.PORT_PARALLEL:
        type = "Parallel";
        break;
      case CommPortIdentifier.PORT_SERIAL:
        type = "Serial";
        break;
      default: /// Shouldn't happen
        type = "Unknown";
        break;
      }
      System.out.println(port.getName() + ": " + type);
    }
  }
}

