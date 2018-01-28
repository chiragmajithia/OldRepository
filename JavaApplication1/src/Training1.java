
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chirag
 */
public class Training1 extends Applet {

    Point loc = new Point();
    double maxpos,press1=0,release=0, maxneg, llmt, ulmt, Fx, x, nodx, nody, dx, min, Xp_d, Yp_d, Xp_dx, Yp_dy, Xsegwidth, Ysegwidth, NoXseg, NoYseg,maxCn,T,THD,RMS,p1,r1,phase;
    int index,hpix, vpix, centerx, centery, offsetX, offsetY,in,nf,cnt=0,exprl=0,expno=0,TD,l,f,ps=0,pf=0,ph1=0,ph2=0;
    int length[]=new int[10];
    double x_in[]=new double[2500];
    double y_in[]=new double[2500];
    double y_v[]=new double[2500];
    double an[]=new double[1000];
    double bn[]=new double[1000];
    double cn[]=new double[1000];
    double n[]=new double[1000];


    public void paint(Graphics g) {
        
        g.setColor(Color.gray);
        g.drawLine((ps*1300/2500)+offsetX, 0, (ps*1300/2500)+offsetX, vpix);
        g.drawLine((pf*1300/2500)+offsetX, 0, (pf*1300/2500)+offsetX,vpix);
        g.drawLine((ph1*1300/2500)+offsetX, 0, (ph1*1300/2500)+offsetX, vpix);
        g.drawLine((ph2*1300/2500)+offsetX, 0, (ph2*1300/2500)+offsetX,vpix);
        g.setColor(Color.black);
        if(p1!=0)
        {
            g.drawString("Select the period by clicking across the points on graph to measure phase difference", 550, 250);
            plotCn(g);
        }
        if(press1!=0)
        {
             g.drawString("Select the period by clicking across the points on graph", 600, 250);
        }
        axis(g);
        g.setColor(Color.blue);
        plotFx(g);

        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                double print;
                Graphics g = getGraphics();
                g.setPaintMode();
                Point x = e.getPoint();
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

         public void mouseClicked(MouseEvent e) {

             if(press1==1 )
                {
                    press1=2;
                    release=1;
                    ps=(int)((e.getPoint().x-offsetX)*2500/1300);
                    System.out.println("ps="+ps);
                }
                if(press1==2 && release==2)
                {
                    press1=0;
                    release=0;
                    pf=(int)((e.getPoint().x-offsetX)*2500/1300);
                    System.out.println("pf="+pf);
                    T=(x_in[pf]-x_in[ps]);
                    fourierCoeff();
                    result();
                }
                if(p1==1 )
                {
                    p1=2;
                    r1=1;
                    ph1=(int)((e.getPoint().x-offsetX)*2500/1300);
                    System.out.println("x[ph]= x["+ph1+"]"+x_in[ph1]);
                }
                if(p1==2 && r1==2)
                {
                    p1=0;
                    r1=0;
                    ph2=(int)((e.getPoint().x-offsetX)*2500/1300);
                    System.out.println("x[ph2]= x["+ph2+"]"+x_in[ph2]);
                    phase=(x_in[ph2]-x_in[ph1])/T*360;
                    System.out.println("phase="+phase);
                    System.out.println("cos(phase)"+Math.cos(phase*(Math.PI)/180));
                }
        }
            public void mouseReleased(MouseEvent e) {
                if(release==1)
                    release=2;
                if(r1==1)
                    r1=2;
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
                     press1=1;
                    g.drawString("Select the period by clicking across the points on graph", 600, 250);
                 }
                 else
                if(e.getKeyChar() =='d')
                 {
                     p1=1;
                     g.drawString("Select the period by clicking across the points on graph to get phase displacement", 600, 250);
                 }
                 else
                if(e.getKeyChar()=='f')
                {
                    plotFR(g);
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
                if(e.getKeyChar()=='c')
                {
                    plotCn(g);
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
                    System.out.println("ps="+ps+"pf="+pf);
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
                    result();
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

    void plotFR(Graphics g)
{
    g.clearRect(0,0,2000,2000);
    int c=0,divisions=25,ofX=50,ofY=50,counter=f,Hpix=hpix,Vpix=vpix-20;
    double pperdx=Hpix/(25+1),temp=0;
    double pperdy=(Vpix-ofY)/maxCn;
    float color[]=new float[3];
    float cpd=150/divisions;
    while((c-1)!=divisions)
    {
        g.setColor(Color.black);
        g.drawLine((int)(c*pperdx)+ofX, Vpix,(int)(c*pperdx)+ofX ,Vpix-(int)(pperdy*cn[c]));
        g.drawOval((int)(c*pperdx)+ofX-2 ,Vpix-(int)(pperdy*cn[c])-2,4,4);
        g.setColor(Color.GREEN);
        g.drawString(Integer.toString(counter++),(int)(c*pperdx)+ofX,Vpix+10);
        temp=(cn[c]/cn[1])*10000;
        temp=Math.round(temp);
        temp=temp/100;
        g.setFont(g.getFont().deriveFont(22.5f));
        g.setColor(Color.BLACK);
        g.drawString("["+Double.toString(temp)+"]", (int)(c*pperdx)+30, Vpix-5-(int)(pperdy*cn[c]));
        g.setFont(g.getFont().deriveFont(12.0f));
        c++;
    }

}

    void fourierCoeff()
    {
      int i,c=0;
      calCoeff();
      System.out.println("Sr no,         n,       An,          Bn ,     Cn,     %Cn");
        for(i=f;i<=l;i++)
        {
           System.out.println(i+" ,    "+n[c]+"  ,     "+an[c]+"     ,   "+bn[c]+"     ,     "+Math.sqrt((an[c]*an[c])+(bn[c]*bn[c]))+"     "+(Math.sqrt((an[c]*an[c])+(bn[c]*bn[c]))/cn[1]));
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

    double trapezoidSquare()
    {
        double ans=0,nx,f1,f2,h;
        int cnt=ps;
       // System.out.println("dX="+dx);
        while (cnt<pf-1)
        {
	cnt++;
	f1=y_in[cnt]*y_in[cnt];
	f2=y_in[cnt+1]*y_in[cnt+1];
        ans=ans+((f1+f2)/2*dx);
	}
	return ans;
    }

    void result()
    {
    double tot=0;
    double Irms=Math.sqrt(trapezoidSquare()/T);
    System.out.println("Rms value"+Irms+"Ratio="+(Irms/maxpos));
    double I1=cn[1]/Math.sqrt(2);
    System.out.println("I1 ="+I1);
    double THD=Math.sqrt(Math.abs(Math.pow((Irms/I1),2)-1));
    System.out.println("Math.pow((Irms/I1),2)-1)="+ Math.abs((Math.pow((Irms/I1),2)-1)));
    System.out.println("THD (by above defination)="+THD);
    System.out.println("f="+f);
    for(int i=2;i<l;i++)
    {
        //System.out.println("tot="+tot+"add"+Math.pow(cn[i], 2));
        tot=tot+Math.pow(cn[i], 2);
    }
    THD=Math.sqrt(tot)/cn[1];
    System.out.println("cn[1]="+cn[1]+"tot="+tot);
    System.out.println("Thd ="+THD);
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
        int cnt = 0, flag = 0, pX = 0, pY = 0, X = 0, Y = 0,Y1=0,pY1=0;
        dx = (ulmt - llmt) / nodx;
        x = llmt;
        g.setColor(Color.black);
        cnt = 0;
        while (cnt<2500) {
            Fx = y_in[cnt];
            X = (int) ((cnt * Xp_dx));
            Y = (int) ((maxpos * Yp_dy) - (Fx * Yp_dy));
            Y1=(int) ((maxpos*Yp_dy)-(y_v[cnt]*Yp_dy));
         //   System.out.println("Fx: "+Fx+" Y: "+(Fx*Yp_dy));
           //System.out.println("Centery+Y"+centery+" + "+(Fx*pixel_dy)+"="+(centery-Y));

            if (flag == 1) {
                if(cnt%20<=10)
                {
                    g.fillOval(offsetX + pX, offsetY + pY1,3,4);
                }
                g.drawLine(offsetX + pX, offsetY + pY, offsetX + X, offsetY + Y);
                
            }
            //if(cnt%20==0)
            //g.fillOval(offsetX+pX, offsetY+pY1-2,3, 3);

            pX = X;
            pY = Y;
            pY1=Y1;
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
            Fx = an[i]*Math.cos(2*Math.PI *i*x_in[cnt]/T);
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
    g.setColor(Color.RED);
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
    g.setColor(Color.CYAN);
    double pixel_dy,fx;
    int  flag = 0, pX = 0, pY = 0, X = 0, Y = 0,cnt=ps;
        while (cnt<pf)
        {
            fx=0;
            x = x_in[cnt];
             for(int i=0;i<(l-f+1);i++)
             {
                  fx+= bn[i]*Math.sin((2*Math.PI*i*x/T));
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
    g.setColor(Color.GREEN);
    double pixel_dy,fx;
    int  flag = 0, pX = 0, pY = 0, X = 0, Y = 0,cnt=ps;
        while (cnt<pf)
        {
            fx=0;
            x = x_in[cnt];
             for(int i=0;i<(l-f+1);i++)
             {
                  fx+= an[i]*Math.cos((2*Math.PI*i*x/T));
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
            Fx = bn[i]*Math.sin(2*Math.PI *i*x_in[cnt]/T);
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

    public void plotCn(Graphics g)
    {
        double pixel_dy;
        int cnt = 0, flag = 0, pX = 0, pY = 0, X = 0, Y = 0;
        dx = (ulmt - llmt) / nodx;
        x = llmt;
        //System.out.println("hpix_dx"+Xp_dx+"pixel_dx"+pixel_dx);
        cnt = ps;
        int i=1;
            while (cnt<pf)
            {

            if(i==1)
            {
                g.setColor(Color.green);
            }
            Fx = bn[i]*Math.sin(2*Math.PI *i*x_in[cnt]/T)+an[i]*Math.cos(2*Math.PI *i*x_in[cnt]/T);
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


    public void axis(Graphics g) {
        int cnt1 = 0;
        double cnt =0;
        double delta=x_in[(int)(Xsegwidth*2)]-x_in[(int)(Xsegwidth)];
        String print;
        g.setColor(Color.BLACK);
        Font sanSerif = new Font("SanSerif", Font.PLAIN, 24);
        g.setFont(sanSerif);
        DecimalFormat df = new DecimalFormat("#.#####");
        while (cnt1 <= NoXseg)
        {
            cnt = Xp_d * cnt1;
            if(cnt1==0 || cnt1==NoXseg)
            {
            g.setColor(Color.black);
            }
            else
            g.setColor(Color.orange);
            g.drawLine(offsetX + (int) cnt, offsetY + 0, offsetX + (int) cnt, offsetY + vpix);
            g.setColor(Color.black);
            
            print = df.format(delta*cnt1);
            g.drawString(print, offsetX-15 + (int) cnt, offsetY +25+ (int) vpix);
            cnt1++;
        }
        cnt = 0.0;
        cnt1 = 0;
        while (cnt1 <= NoYseg)
        {
            cnt = Yp_d * cnt1;
            if(cnt1==0 || cnt1==NoXseg)
            {
            g.setColor(Color.black);
            }
            else
            g.setColor(Color.orange);

            g.drawLine(offsetX + 0, offsetY + (int) cnt, offsetX + (int) hpix, offsetY + (int) cnt);
            print = df.format(maxpos - (Ysegwidth * cnt1));
            //g.drawString(print, offsetX - 30, offsetY + (int) cnt);
            cnt1++;

        }
        g.drawString("Time (sec)", hpix/2, vpix+55);
        g.drawRect(hpix-255, vpix+40, 250, 65);
        g.drawString("Current (A):", hpix-250, vpix+60);
        g.drawString("Voltage (V):", hpix-250, vpix+90);

        g.drawLine(hpix-120, vpix+55, hpix-10, vpix+55);
        g.drawLine(hpix-120, vpix+56, hpix-10, vpix+56);
        g.drawLine(hpix-120, vpix+57, hpix-10, vpix+57);
        g.drawLine(hpix-120, vpix+58, hpix-10, vpix+58);

        int cntx=130;
        for(int i=0;i<=10;i++)
        {
        cntx-=10;
        g.drawLine(hpix-cntx, vpix+80, hpix-cntx+5, vpix+80);
        g.drawLine(hpix-cntx, vpix+81, hpix-cntx+5, vpix+81);
        g.drawLine(hpix-cntx, vpix+82, hpix-cntx+5, vpix+82);
        g.drawLine(hpix-cntx, vpix+83, hpix-cntx+5, vpix+83);

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
                       int i=1,j;
                       double scale=1.5/8;
            		try{
            			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            			Connection con = DriverManager.getConnection( "jdbc:odbc:test1" );
            			Statement st = con.createStatement();
            			ResultSet rs = st.executeQuery( "Select * from [Current$]" );
                                
            			ResultSetMetaData rsmd = rs.getMetaData();
            			int numberOfColumns = rsmd.getColumnCount();

                                System.out.println("Number of Columns= "+numberOfColumns);
                                        while(rs.next())
                                        {
                                             x_in[i]=Double.parseDouble(rs.getString(4));
                                             y_in[i]=Double.parseDouble(rs.getString(5));
                                             i++;
                                        }
                                        rs=st.executeQuery("select * from [Voltage$]");
                                        i=1;
                                        while(rs.next())
                                        {
                                             y_v[i]=Double.parseDouble(rs.getString(5))*scale;
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
                        /*for(i=1;i<j;i++)
                        {
                            System.out.println("x["+i+"]= "+x_in[i]+"y["+i+"]= "+y_in[i]+"y_v["+i+"]="+y_v[i]);
                        }*/


         String s;
       // System.out.print("Enter the screen resolution of your system \n Horizontal pixels ::");
        //s= inp.readLine();
        hpix=1200;
        //System.out.print("Vertical pixels ::");
        //s= inp.readLine();
        vpix=550;
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
        setSize((offsetX * 2) + hpix + 100, (offsetY * 2) + vpix + 150);
        //System.out.println("Init");
        maxpos = 0;
        maxneg = 0;
        dx = 0.0004;
        //System.out.println("DX::="+dx);
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

