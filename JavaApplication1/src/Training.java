
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chirag
 */
public class Training extends Applet {

    Point loc = new Point();
    double maxpos, maxneg, llmt, ulmt, Fx, x, nodx, nody, dx, min, Xp_d, Yp_d, Xp_dx, Yp_dy, Xsegwidth, Ysegwidth, NoXseg, NoYseg,maxCn,T;
    int index,hpix, vpix, centerx, centery, offsetX, offsetY,in,nf,cnt=0,exprl=0,expno=0,TD,l,f,ps,pf;
    int length[]=new int[10];
    double x_in[]=new double[2500];
    double y_in[]=new double[2500];
    double an[]=new double[1000];
    double bn[]=new double[1000];
    double cn[]=new double[1000];
    double n[]=new double[1000];


    public void paint(Graphics g) {
        double print;
        g.setColor(Color.black);
        axis(g);
        //   g.drawRect(0,0,hpix,vpix);
        g.setColor(Color.blue);
        plotFx(g);
        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                double print;
                Graphics g = getGraphics();
                g.setPaintMode();
                Point x = e.getPoint();
                System.out.println("Clicked");

                g.drawLine(hpix + offsetX, loc.y, loc.x, loc.y);
                g.drawLine(loc.x, loc.y, loc.x, vpix + offsetY);
                print = maxpos - ((loc.y - offsetY) / Yp_dy);
                g.drawString(Double.toString(print), hpix, (int) loc.y + offsetY);
                print = llmt + ((loc.x - offsetX) / Xp_dx * dx);
                g.drawString(Double.toString(print), (int) loc.x, vpix + offsetY);
                loc.x = x.x;
                loc.y = x.y;
                g.drawString("X", loc.x - 3, loc.y + 5);

            }

            public void mouseReleased(MouseEvent e) {
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseMoved(MouseEvent e) {
                Graphics g = getGraphics();
                Point x = e.getPoint();
                loc.x = x.x;
                loc.y = x.y;
            }
        });
        addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                InputStreamReader r=new InputStreamReader(System.in);
                BufferedReader inp=new BufferedReader(r);
                String s;

                Graphics g = getGraphics();
                 if(e.getKeyChar() =='1')
                 {
                    try
                      {
                         System.out.println("Cnt corresponding to period");
                         System.out.println("Starting of point of period");
                         s=inp.readLine();
                         ps=Integer.parseInt(s);
                         System.out.println("Final point of period");
                         s=inp.readLine();
                         pf=Integer.parseInt(s);
                         T=(x_in[pf]-x_in[ps]);
                         fourierCoeff();
                         e.setKeyChar('2');
                     }
                    catch(IOException exp)
                     {
                     }
                }
                if(e.getKeyChar()=='f')
                {
                    //plotFR(g);
                }
                else
                if(e.getKeyChar()=='a')
                {
                    plotAn(g);
                }
                else
                if(e.getKeyChar()=='A')
                {
                    plotAnSum(g);
                }
                else
                if(e.getKeyChar()=='b')
                {
                    plotBn(g);
                }
                else
                if(e.getKeyChar()=='B')
                {
                    plotBnSum(g);
                }
                else
                if(e.getKeyChar()=='s')
                {
                    plotSum(g);
                }
                else
                if(e.getKeyChar()=='n')
                {
                    try{
                    System.out.println("Change the Frequency response Values");
                    System.out.print("Initial Value of n ::");
                    s=inp.readLine();
                    f=Integer.parseInt(s);
                    System.out.print("Final Value of n ::");
                    s=inp.readLine();
                    l=Integer.parseInt(s);
                    calCoeff();
                    fourierCoeff();
                    }
                    catch(IOException ex){}
                    e.setKeyChar('e');
                }
                else
                 {
                    repaint();
                 }
            }
        }
        );
        g.setColor(Color.black);


    }


    void fourierCoeff()
    {
      int i,c=0;
      calCoeff();
      System.out.println("Sr no         n       An          Bn      Cn");
        for(i=f;i<=l;i++)
        {
           System.out.println(i+"     "+n[c]+"        "+an[c]+"        "+bn[c]+"          "+Math.sqrt((an[c]*an[c])+(bn[c]*bn[c])));
                c++;
        }
    }

    double trapezoid()
    {
	double ans=0,nx,f1,f2,h;
        int cnt=ps;
       // System.out.println("dX="+dx);
        while (cnt<pf-1)
        {
	cnt++;
	f1=y_in[cnt];
	f2=y_in[cnt+1];
        ans=ans+((f1+f2)/2*dx);
	}
        
        System.out.println("trap="+ans);
	return ans;
    }

    double trapezoidS(int n)
    {
	double ans=0,nx,f1,f2,h,T=(x_in[pf]-x_in[ps]);
        int cnt=ps;
       // System.out.println("dX="+dx);

        while (cnt<pf)
        {
	cnt++;
	f1=y_in[cnt]*Math.sin((2*Math.PI/T*n*x_in[cnt]));
	f2=y_in[cnt+1]*Math.sin((2*Math.PI/T*n*x_in[cnt+1]));
        ans=ans+((f1+f2)/2*dx);
	}
	return ans;
    }

    double trapezoidC(int n)
    {
	double ans=0,nx,f1,f2,h;
        int cnt=ps;
       // System.out.println("dX="+dx);

        while (cnt<pf)
        {
	cnt++;
	f1=y_in[cnt]*Math.cos((2*Math.PI/T*n*x_in[cnt]));
	f2=y_in[cnt+1]*Math.cos((2*Math.PI/T*n*x_in[cnt+1]));
        ans=ans+((f1+f2)/2*dx);
	}
	return ans;
    }
    void calCoeff()
    {
    maxCn=0;
    int i,c=0;
    
    for(i=f;i<=l;i++)
    {
        n[c]=i;
        if(i==0)
        {
            an[c]=1/T*trapezoid();
        }
        else
        an[c]=2/T*trapezoidC(i);
        bn[c]=2/T*trapezoidS(i);
        cn[c]=Math.sqrt((an[c]*an[c])+(bn[c]*bn[c]));
        if(maxCn<cn[c])
        {
            maxCn=Math.sqrt((an[c]*an[c])+(bn[c]*bn[c]));
        }
        c++;
    }
    }

    public void plotFx(Graphics g)
    {
        double pixel_dy;
        int cnt = 0, flag = 0, pX = 0, pY = 0, X = 0, Y = 0;
        dx = (ulmt - llmt) / nodx;
        x = llmt;
        //System.out.println("hpix_dx"+Xp_dx+"pixel_dx"+pixel_dx);
        cnt = 0;
        while (cnt<2500) {
            Fx = y_in[cnt];
            X = (int) ((cnt * Xp_dx));
            Y = (int) ((maxpos * Yp_dy) - (Fx * Yp_dy));
         //   System.out.println("Fx: "+Fx+" Y: "+(Fx*Yp_dy));
           //System.out.println("Centery+Y"+centery+" + "+(Fx*pixel_dy)+"="+(centery-Y));

            if (flag == 1) {
                g.drawLine(offsetX + pX, offsetY + pY, offsetX + X, offsetY + Y);
            }
            pX = X;
            pY = Y;
            cnt++;
            flag = 1;
        }

    }

    public void plotAn(Graphics g)
    {
        double pixel_dy;
        int cnt = 0, flag = 0, pX = 0, pY = 0, X = 0, Y = 0;
        dx = (ulmt - llmt) / nodx;
        x = llmt;
        //System.out.println("hpix_dx"+Xp_dx+"pixel_dx"+pixel_dx);
        cnt = ps;
        for(int i=0;i<(l-f+1);i++)
        {
            flag=0;
            cnt=ps;
            while (cnt<pf)
            {
            Fx = an[i]*Math.cos(Math.PI *i*x_in[cnt]/T);
            X = (int) ((cnt * Xp_dx));
            Y = (int) ((maxpos * Yp_dy) - (Fx * Yp_dy));
            //System.out.println("Fx: "+Fx+" Y: "+(Fx*Yp_dy));
           //System.out.println("Centery+Y"+centery+" + "+(Fx*pixel_dy)+"="+(centery-Y));

            if (flag == 1)
            {
                g.drawLine(offsetX + pX, offsetY + pY, offsetX + X, offsetY + Y);
            }
            pX = X;
            pY = Y;
            cnt++;
            flag = 1;
            }
       }
    }

    void plotSum(Graphics g)
    {
    getGraphics();
    g.setColor(Color.magenta);
    double pixel_dy,fx;
    int  flag = 0, pX = 0, pY = 0, X = 0, Y = 0,cnt=ps;
        while (cnt<pf)
        {
            fx=0;
            x = x_in[cnt];
             for(int i=0;i<(l-f+1);i++)
             {
                  fx+= an[i]*Math.cos((2*Math.PI*i*x/T))+bn[i]*Math.sin((2*Math.PI*i*x/T));
             }
            //System.out.println("Plotting at x= "+x+"with Xp_dx="+Xp_dx+" y= "+Fx+"with Yp_dy="+Yp_dy );
            X = (int) ((cnt * Xp_dx));
            Y = (int) ((maxpos * Yp_dy) - (fx * Yp_dy));
            //System.out.println("X= "+x+"Fx: "+fx+" An "+an[i]+"i="+i);
            //System.out.println("Centery+Y"+centery+" + "+(Fx*pixel_dy)+"="+(centery-Y));

            if (flag == 1)
            {
                g.drawLine(offsetX + pX, offsetY + pY, offsetX + X, offsetY + Y);
            }
            cnt++;
            pX = X;
            pY = Y;
            flag = 1;

        }
    }

    void plotBnSum(Graphics g)
    {
    getGraphics();
    g.setColor(Color.magenta);
    double pixel_dy,fx;
    int  flag = 0, pX = 0, pY = 0, X = 0, Y = 0,cnt=ps;
        while (cnt<pf)
        {
            fx=0;
            x = x_in[cnt];
             for(int i=0;i<(l-f+1);i++)
             {
                  fx+= bn[i]*Math.sin((Math.PI*i*x/T));
             }
            //System.out.println("Plotting at x= "+x+"with Xp_dx="+Xp_dx+" y= "+Fx+"with Yp_dy="+Yp_dy );
            X = (int) ((cnt * Xp_dx));
            Y = (int) ((maxpos * Yp_dy) - (fx * Yp_dy));
            //System.out.println("X= "+x+"Fx: "+fx+" An "+an[i]+"i="+i);
            //System.out.println("Centery+Y"+centery+" + "+(Fx*pixel_dy)+"="+(centery-Y));

            if (flag == 1)
            {
                g.drawLine(offsetX + pX, offsetY + pY, offsetX + X, offsetY + Y);
            }
            cnt++;
            pX = X;
            pY = Y;
            flag = 1;

        }
    }
    void plotAnSum(Graphics g)
    {
    getGraphics();
    g.setColor(Color.magenta);
    double pixel_dy,fx;
    int  flag = 0, pX = 0, pY = 0, X = 0, Y = 0,cnt=ps;
        while (cnt<pf)
        {
            fx=0;
            x = x_in[cnt];
             for(int i=0;i<(l-f+1);i++)
             {
                  fx+= an[i]*Math.cos((Math.PI*i*x/T));
             }
            //System.out.println("Plotting at x= "+x+"with Xp_dx="+Xp_dx+" y= "+Fx+"with Yp_dy="+Yp_dy );
            X = (int) ((cnt * Xp_dx));
            Y = (int) ((maxpos * Yp_dy) - (fx * Yp_dy));
            //System.out.println("X= "+x+"Fx: "+fx+" An "+an[i]+"i="+i);
            //System.out.println("Centery+Y"+centery+" + "+(Fx*pixel_dy)+"="+(centery-Y));

            if (flag == 1)
            {
                g.drawLine(offsetX + pX, offsetY + pY, offsetX + X, offsetY + Y);
            }
            cnt++;
            pX = X;
            pY = Y;
            flag = 1;

        }
    }

    public void plotBn(Graphics g)
    {
        double pixel_dy;
        int cnt = 0, flag = 0, pX = 0, pY = 0, X = 0, Y = 0;
        dx = (ulmt - llmt) / nodx;
        x = llmt;
        //System.out.println("hpix_dx"+Xp_dx+"pixel_dx"+pixel_dx);
        cnt = ps;
        for(int i=0;i<(l-f+1);i++)
        {
            flag=0;
            cnt=ps;
            while (cnt<pf)
            {
            Fx = bn[i]*Math.sin(Math.PI *i*x_in[cnt]/T);
            X = (int) ((cnt * Xp_dx));
            Y = (int) ((maxpos * Yp_dy) - (Fx * Yp_dy));
            //System.out.println("Fx: "+Fx+" Y: "+(Fx*Yp_dy));
           //System.out.println("Centery+Y"+centery+" + "+(Fx*pixel_dy)+"="+(centery-Y));

            if (flag == 1)
            {
                g.drawLine(offsetX + pX, offsetY + pY, offsetX + X, offsetY + Y);
            }
            pX = X;
            pY = Y;
            cnt++;
            flag = 1;
            }
       }
    }



    public void axis(Graphics g) {

        int cnt1 = 0;
        double cnt = 0;
        String print;
        g.setColor(Color.black);
        DecimalFormat df = new DecimalFormat("#.#####");
        while (cnt1 <= NoXseg)
        {
            cnt = Xp_d * cnt1;
            g.drawLine(offsetX + (int) cnt, offsetY + 0, offsetX + (int) cnt, offsetY + vpix);
            if(cnt1!=0)
            print = df.format(x_in[(int)(Xsegwidth*cnt1)-1]);
            else
            print = df.format(x_in[0]);
            //System.out.println("print :index= "+((int)(Xsegwidth*cnt1)-1) );
            g.drawString(print, offsetX-30 + (int) cnt, offsetY + (int) vpix / 2);
            cnt1++;
        }
        cnt = 0.0;
        cnt1 = 0;
        while (cnt1 <= NoYseg)
        {
            cnt = Yp_d * cnt1;
            g.drawLine(offsetX + 0, offsetY + (int) cnt, offsetX + (int) hpix, offsetY + (int) cnt);
             print = df.format(maxpos - (Ysegwidth * cnt1));
            g.drawString(print, offsetX - 30, offsetY + (int) cnt);
            cnt1++;

        }

    }

    public void readFx()
    {
        int cnt = 0;
        maxpos = y_in[0];
        maxneg = y_in[0];
        while (cnt<2500)
        {
            
            //System.out.println("x="+x_in[cnt]+"Fx :"+y_in[cnt]);
            if (maxpos < y_in[cnt] && y_in[cnt]>= 0)
            {
                maxpos = y_in[cnt];
            }
            if (maxneg > y_in[cnt] && y_in[cnt] <= 0)
            {
                maxneg = y_in[cnt];
            }
            cnt++;
        }
        System.out.println("Max +=" + maxpos + "Max -=" + maxneg);

    }

    public void input() throws IOException
    {
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader inp=new BufferedReader(r);
        Connection connection = null;
                       int i=1,j;
            		try{
            			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            			Connection con = DriverManager.getConnection( "jdbc:odbc:test" );
            			Statement st = con.createStatement();
            			ResultSet rs = st.executeQuery( "Select * from [F0003CH1$]" );

            			ResultSetMetaData rsmd = rs.getMetaData();
            			int numberOfColumns = rsmd.getColumnCount();
                                System.out.println("Number of Columns= "+numberOfColumns);
                                        while(rs.next())
                                        {
                                             x_in[i]=Double.parseDouble(rs.getString(4));
                                             y_in[i]=Double.parseDouble(rs.getString(5));
                                             i++;
                                        }


                    			st.close();
                    			con.close();
                          }
                          catch(Exception ex)
                          {
                        	System.err.print("Exception: ");
                        	System.err.println(ex.getMessage());
                          }
                       j=i;
                        for(i=1;i<j;i++)
                        {
                            System.out.println("x["+i+"]= "+x_in[i]+"y["+i+"]= "+y_in[i]);
                        }
                

         String s;
       // System.out.print("Enter the screen resolution of your system \n Horizontal pixels ::");
        //s= inp.readLine();
        hpix=1300;
        //System.out.print("Vertical pixels ::");
        //s= inp.readLine();
        vpix=600;
        llmt=x_in[1];
        ulmt=x_in[2499];
        nodx=2500;
        System.out.print("Number of Segments on X axis");
        //s=inp.readLine();
        NoXseg=10;
        System.out.print("Number of Segments on Y axis");
       //s=inp.readLine();
        NoYseg=10;

  }

     public void init(){
        try
        {
            input();
        }
        catch (IOException ex) {}
        offsetX = 50;
        offsetY = 10;
        setSize((offsetX * 2) + hpix + 5, (offsetY * 2) + vpix + 5);
        //System.out.println("Init");
        maxpos = 0;
        maxneg = 0;
        dx = 0.0004;
        System.out.println("DX::="+dx);
        readFx();
        centerx = hpix / 2;
        centery = vpix / 2;
        Xp_dx = hpix / nodx;
        Yp_dy = vpix / (maxpos - maxneg);
        Xsegwidth = nodx / NoXseg;
        Ysegwidth = (maxpos - maxneg) / NoYseg;
        Xp_d = (hpix/NoXseg);
         System.out.println("Xesegwidth"+Xsegwidth+"Xp_d"+Xp_d );
        Yp_d = Yp_dy * Ysegwidth;
        System.out.println("Xsegment width ::"+Xsegwidth);
        fourierCoeff();
    }
}

