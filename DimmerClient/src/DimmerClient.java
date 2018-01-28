/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author XPS
 */
import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DimmerClient extends Thread
{
   Socket client_socket; 
   Scanner in;
   PrintWriter out;
   String message;
   boolean run_thread;
   
   public DimmerClient(Socket client_socket)
   {
       this.client_socket= client_socket;
       run_thread=true;
   }
   
   public void checkConnection() throws IOException
    {
        if(!client_socket.isConnected())
        {
         DimmerClientGUI.jServerClientComm.append("Client Closed");
         run_thread=false;
         client_socket.close();
         //to implement when multiple client joins the server   
        }   
    }
   public static String getInetAddress()
   {
       String s;
       try {
          s= java.net.InetAddress.getLocalHost().toString();
       } catch (UnknownHostException ex) {
           Logger.getLogger(DimmerClient.class.getName()).log(Level.SEVERE, null, ex);
          s = "Server Error";
       }
       return s;
   }
   
   public  void write(char typed)
   {
       write(Character.toString(typed));
   }
   
   @Override
   public void run()
   {
       try
       {
            try
            {
                in = new Scanner(client_socket.getInputStream());
                out = new PrintWriter(client_socket.getOutputStream());
                read();
                System.out.println("Thread Stopped");
            }
            finally
            {
                DimmerClientGUI.jServerClientComm.append(client_socket.getInetAddress()+" disconnected from server \n");
                client_socket.close();
            }
       }
       catch(IOException e)
       {
       }
   }
   
   public void read()
   {
       while(run_thread)
       {
           try
           {
               checkConnection();
           }
           catch(Exception e)
           {
           }
           if(in.hasNext())
           {
                message = in.nextLine();
                if(!isStatusReport(message))
                DimmerClientGUI.jServerClientComm.append("Server -> Client : "+ message+"\n");
                if(message.contains("ID="))
                {            
                    DimmerClientGUI.jIPLabel.setText(DimmerClientGUI.jIPLabel.getText() +" Id:"+message.substring(message.indexOf("=")+1));
                    System.out.println("received Id");
                }
           }
       }
   }
   
   public void write(String msg)
   {
       out.println(msg);
       out.flush();
       //DimmerClientGUI.jServerClientComm.append("Client -> Server : "+ msg +"\n");
   }
   
   public boolean isStatusReport(String msg)
   {
       if(msg.contains("Status Report = ["))
       {
           String temp = msg.substring(17);
           write("Server -> Client: " +msg+"\n");
           DimmerClientGUI.jReceivedChars.setText(msg);
           DimmerClientGUI.updateGUI(temp);
           return true;
       }
       else
           return false;
   }
}
