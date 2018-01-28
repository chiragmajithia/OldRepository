/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dimmerwithserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Time;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author XPS
 */
public class DimmerClientHandler implements Runnable
{
    
    Socket s;
    Scanner in;
    PrintWriter out;
    String message;
    char command;
    double id;
    long last_activity;
    boolean run_thread;
 
    public DimmerClientHandler(Socket s)
    {
     this.s=s;   
     last_activity=0;
     run_thread=true;
    }
    
    public void checkConnection() throws IOException
    {
        if(!s.isConnected())
        {
         DimmerServerGUI.jServerClientComm.append("Client Closed");
         run_thread=false;
         s.close();
         //to implement when multiple client joins the server   
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
                    catch (IOException ex) 
                    {
                         Logger.getLogger(DimmerClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(last_activity != 0 && (System.currentTimeMillis()-last_activity)>1000)
                    {
                        System.out.println("last_activity"+id+"="+(System.currentTimeMillis()-last_activity));
                        System.out.println("Socker{"+id+"Timed out - will be closed");
                        System.out.println("Threads Running"+ Thread.getAllStackTraces().keySet().size());
                        run_thread=false;
                        System.out.println("Socker{"+id+"Timed out -closed");
                    }
                    if(in.hasNext())
                    {
                        System.out.println("last_activity"+id+"="+(System.currentTimeMillis()-last_activity));
                        last_activity = System.currentTimeMillis();
                        message=in.nextLine();
                        DimmerServerGUI.jServerClientComm.append(message+'\n');
                        DimmerServerGUI.Control(message);
                        if(message.contains("RqstSR"))
                        {    
                        write("ID="+Double.toString(id));    
                        DimmerServerGUI.getStatusReport(id);
                        }
                        if(message.contains("myID"))
                        {
                            write("ID="+Double.toString(id));
                            System.out.println("give id = "+id);
                        }
                        if(message.contains("notifyClient"))
                        {
                            System.out.println(message);
                        double client_id = 0;
                        String temp_msg,c_id;
                        int str_indx_msg = message.indexOf("(");
                        int end_indx_msg = message.indexOf(")");
                        temp_msg = message.substring(str_indx_msg+1,end_indx_msg);
                        str_indx_msg = message.indexOf("[");
                        end_indx_msg = message.indexOf("]");
                        c_id=message.substring(str_indx_msg+1,end_indx_msg);
                        client_id = Double.parseDouble(c_id);
                        System.out.println("msg="+temp_msg+"id="+client_id);
                        DimmerServer.notifyClient(temp_msg, client_id);
                        }
                      //System.out.println("Server Received message from client"+message);
                    //    DimmerServerGUI.client_update=true;
                    }
                       
                }
    }
    
    public void write(String msg)
    {
       out.println(msg);
       out.flush();
       System.out.println("last_activity"+id+"="+(System.currentTimeMillis()-last_activity));
        last_activity = System.currentTimeMillis();
    //   System.out.println("Server ->Client");
    }
    @Override
    public void run() 
    {
        try
        {
            try
            {
            in = new Scanner(s.getInputStream());
            out = new PrintWriter(s.getOutputStream());
            read();
            }
            finally
            {
                out.flush();
                out.close();
                in.close();
                s.close();
            }
        }
        catch(Exception e)
        {
            System.out.println("Something Went Wrong");
        }
    }
    
}
