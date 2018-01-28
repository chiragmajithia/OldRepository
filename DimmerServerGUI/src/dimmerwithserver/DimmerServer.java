/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dimmerwithserver;

/**
 *
 * @author XPS
 */
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DimmerServer extends Thread
{
   private static ServerSocket serverSocket;
   public static ArrayList<DimmerClientHandler> client_connections = new ArrayList<DimmerClientHandler>();
   double cnt;
   
   public DimmerServer(int port) throws IOException
   {
       cnt=0;
     // serverSocket = new ServerSocket(port,0,InetAddress.getLocalHost());
      serverSocket = new ServerSocket(port);
      DimmerServerGUI.jIPLabel.setText(java.net.InetAddress.getLocalHost().toString());
      //System.out.println(java.net.InetAddress.getLocalHost());
      //serverSocket.setSoTimeout(10000);
   }
   
   public static String getInetAddress()
   {
       String s;
       try {
          s= java.net.InetAddress.getLocalHost().toString();
       } catch (UnknownHostException ex) {
           Logger.getLogger(DimmerServer.class.getName()).log(Level.SEVERE, null, ex);
          s = "Server Error";
       }
       return s;
   }
   
   
   @Override
   public void run()
   {
      while(true)
      {
         try
         {
            DimmerServerGUI.jServerClientComm.append("Waiting for client on port " + serverSocket.getLocalPort() + "...\n");
            Socket server_client = serverSocket.accept();
            
            DimmerClientHandler client_handler = new DimmerClientHandler(server_client);
            client_handler.id= cnt++;
            DimmerServerGUI.jServerClientComm.append("Just connected to " + server_client.getRemoteSocketAddress()+"\n");
            DimmerServerGUI.jServerClientComm.append("ID = " +(cnt-1)+"\n");
            client_connections.add(client_handler);
            Thread c = new Thread(client_handler);
            c.start();
         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e)
         {
            e.printStackTrace();
            break;
         }
      }
   }
   
   public static void notifyClients(String msg)
   {
       for(int i =0;i<client_connections.size();i++)
       {
           DimmerClientHandler temp_soc= (DimmerClientHandler) client_connections.get(i);
           temp_soc.write(msg);
       }
   }
   
   public static void notifyClient(String msg, double id)
   {
       for(int i =0;i<client_connections.size();i++)
       {
           if(client_connections.get(i).id == id)
           {
               System.out.println("Notifying client"+msg);
               client_connections.get(i).write("M="+msg+"\n");
           }
       }
   }
}