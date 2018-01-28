/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dataaquisitionsystem;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author Chirag
 */
public class Draw extends Canvas
{
   public JTable jTableOutput;
   int v_pix;
   int h_pix;
   int pX,pY,prev_pX,prev_pY;
   double seconds_frame,value_hpix,value_h_seg,num_h_seg,h_pix_seg,maxY;//pX pixel of X and not value,max value of Y
   double value_vpix,num_v_seg,v_pix_seg,value_v_seg;
   public Graphics backg;
   BufferedImage backbuffer;
   int follower=0;
   boolean maxYchanged=false,hold=false;
   int hpix_cnt=0;
   DecimalFormat df = new DecimalFormat("#.##");


   public void init()
   {
      setSize(h_pix,v_pix);
      int vpix_cnt=0,cnt=0;
      Runtime r=Runtime.getRuntime();
      r.gc();
      String print;
      backbuffer =  new BufferedImage(5000,v_pix,BufferedImage.TYPE_INT_RGB );
      backg = backbuffer.getGraphics();
      backg.fillRect(0, 0, this.getWidth(), this.getHeight());
     // System.out.println("g="+backg);
       value_hpix=seconds_frame/h_pix;
       h_pix_seg=h_pix/num_h_seg;
       value_h_seg=value_hpix*h_pix_seg;     // segment after 8 pixels or 4 seconds
       value_vpix=maxY/v_pix;
     //  System.out.println("value_vpix before="+value_vpix+"maxY%v_pix="+(maxY%v_pix));
      // System.out.println("value_vpix="+value_hpix+"maxY%v_pix="+(maxY%v_pix));
       v_pix_seg=v_pix/num_v_seg;
       value_v_seg=value_vpix*v_pix_seg;
       

       follower=0;
       hpix_cnt=0;
       cnt=0;
       while(vpix_cnt<v_pix)
      {
                backg.setColor(Color.black);
                //System.out.println("in:="+(v_pix-vpix_cnt)+"value_hseg"+value_h_seg+"cnt="+cnt);
                backg.drawLine(0, (v_pix-vpix_cnt),this.getWidth(), (v_pix-vpix_cnt));
                print = df.format(value_v_seg*cnt);
                backg.drawString(print,0,v_pix-vpix_cnt);
                vpix_cnt+=v_pix_seg;
                cnt++;
      }

   }

   public Draw(JTable jT)
   {
   v_pix=650;
   h_pix=1100;
   maxY=100;
   seconds_frame=600;
   num_h_seg=10;
   num_v_seg=10;
   jTableOutput = jT;
   setSize(h_pix,v_pix);
   this.setBackground(Color.white);
   this.init();
   System.out.println("width"+this.getWidth());
   
 //  System.out.println("in constructor");
   }

   public void update()
   {
       maxYchanged=true;
       follower=0;
        pX=0;
        pY=0;
        prev_pX=0;
        prev_pY=0;
        hpix_cnt=0;
        init();
       update(this.getGraphics());
   }
   public void updateInit(int v_pix,int h_pix,double maxY,int seconds_frame,int num_h_seg,int num_v_seg)
   {
   this.getGraphics().clearRect(0, 0, this.getWidth(), this.getHeight());
   this.v_pix=v_pix;
   this.h_pix=h_pix;
   this.maxY=maxY;
   this.seconds_frame=seconds_frame;
   this.num_h_seg=num_h_seg;
   this.num_v_seg=num_v_seg;

   this.init();
   this.update(getGraphics());
   }
   public void updateTable(JTable jT)
   {
       jTableOutput=jT;
       value_vpix=maxY/v_pix;
       v_pix_seg=v_pix/num_v_seg;
       value_v_seg=value_vpix*v_pix_seg;
       repaint();
   }

    @Override
   public void update(Graphics g)
   {
       if(!hold)
       {

       updateYAxis();
       updatePlot();
       updateXAxis();
       //System.out.println("Imae Update");
       g.drawImage( backbuffer, 0, 0, this );
       }
   }

   public void updateYAxis()
   {
       String print;
        int cnt=0;
        int vpix_cnt=0;
        boolean wasInside=false;
        //System.out.println("backg="+backg);
        //System.out.println("updateAxis cnt="+cnt);
        if(pX>this.getWidth() && this.getWidth()<9000)
        {
            this.setSize(this.getWidth()+1000, v_pix);
            backg.setColor(Color.white);
            backg.fillRect(0, 0, this.getWidth(), this.getHeight());
            wasInside=true;
       }

        if(maxYchanged || wasInside)
        {
            maxYchanged=false;
            backg.setColor(Color.white);
            backg.fillRect(0, 0, this.getWidth(), this.getHeight());
            while(vpix_cnt<v_pix)
            {
                backg.setColor(Color.black);
                backg.drawLine(0,(v_pix-vpix_cnt),this.getWidth(), (v_pix-vpix_cnt));
                print=df.format(value_v_seg*cnt);
                backg.drawString(print,0,v_pix-vpix_cnt);
                vpix_cnt+=v_pix_seg;
                cnt++;
            }
            wasInside=false;
        follower=0;
        pX=0;
        pY=0;
        prev_pX=0;
        prev_pY=0;
        hpix_cnt=0;
        }
        

   }

   public void updatePlot()
   {
       int cnt=jTableOutput.getRowCount()-follower-1;
       while(cnt>=0)
       {
        backg.setColor(Color.red);
        try
        {
        pX=(int)(Double.parseDouble(jTableOutput.getValueAt(cnt,0).toString())/value_hpix);
        pY=(int)(Double.parseDouble(jTableOutput.getValueAt(cnt,1).toString())/value_vpix);
        }
        catch(Exception e){};
        if(cnt != jTableOutput.getRowCount()-1)
        backg.drawLine(prev_pX, v_pix-prev_pY,pX,v_pix-pY);
        cnt--;
        follower++;
        prev_pX=pX;
        prev_pY=pY;
       }
   }

   public void updateXAxis()
   {
       String print;
       backg.setColor(Color.black);
        while(hpix_cnt<=pX)
        {
       // System.out.println("inside"+hpix_cnt);
        backg.drawLine(hpix_cnt, 0, hpix_cnt, v_pix);
        print = df.format(hpix_cnt*value_hpix);
        backg.drawString(print,hpix_cnt,(int)v_pix/2);
        hpix_cnt+=h_pix_seg;
        }

   }
@Override
    public void paint(Graphics g)
    {
    update(g);
    }

    
}
        