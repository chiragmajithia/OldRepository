
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
public class Solar extends Applet {

    Point loc = new Point();
    double maxpos,maxneg, llmt, ulmt, Fx, x, nodx, nody, dx, Xp_d, Yp_d, Xp_dx, Yp_dy, Xsegwidth, Ysegwidth, NoXseg, NoYseg,sumX,sumX2,sumXY,sumY,m,c,mxFx,mnFx,mxLFx,mnLFx,mnY,mxY;
    int hpix, vpix,offsetX,offsetY,exprl=0,expno=0,num_input,int_input;
    int length[]=new int[10];
    double x_in[]=new double[2500];
    double y_in[]=new double[2500];
    double X[]=new double[2500];
    double Y[]=new double[2500];
    double interval[][]=new double[10][2];
    double m_i[]=new double[10];
    double c_i[]=new double[10];
    char k='n';


    public void paint(Graphics g) {
        g.setColor(Color.black);

        switch(k)
        {
            case 'n':
                    plotFx(g);
                    break;
            case 'l':
                    plotLFx(g);
                    break;
            case 'r':
                    plotXY(g);
                    break;
            case 'x':
                    plotR(g);
                    break;
            case 'X':
                    plotRT(g);
        }
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
                if(e.getKeyChar()=='l')
                {
                    k='l';
                    repaint();
                }
                if(e.getKeyChar()=='n')
                {
                    k='n';
                    repaint();
                }
                if(e.getKeyChar()=='r')
                {
                    k='r';
                    repaint();
                }
                if(e.getKeyChar()=='1')
                {
                        regress();
                        plotR(g);
                        e.setKeyChar('x');
                }
                if(e.getKeyChar()=='2')
                {
                        regressT();
                        plotRT(g);
                        e.setKeyChar('X');
                }
                if(e.getKeyChar()=='t')
                {
                        plotRT(g);
                        e.setKeyChar('X');
                }
            }
        }
        );
        g.setColor(Color.black);
 }

    public void plotLFx(Graphics g)
        {
        int flag = 0, pX = 0, pY = 0, X = 0, Y = 0,cnt=0;
        llmt=x_in[0];
        ulmt=x_in[num_input];
        nodx=num_input;
        dx = (ulmt - llmt) / nodx;
        x = llmt;
        maxpos=mxLFx;
        maxneg=mnLFx;
        Yp_dy = vpix / (maxpos - maxneg);
        Ysegwidth = (maxpos - maxneg) / NoYseg;
        Yp_d = Yp_dy * Ysegwidth;
        axis(g);
        g.setColor(Color.red);
        // System.out.println("max+"+maxpos+"max-"+maxneg+"ploting");
        while (cnt< num_input) {
            Fx = Math.log(y_in[cnt]);
            X = (int) ((cnt * Xp_dx));
            Y = (int) ((maxpos * Yp_dy) - (Fx * Yp_dy));
            if (flag == 1)
            {
                g.drawLine(offsetX + pX, offsetY + pY, offsetX + X, offsetY + Y);
            }
         //   System.out.println("X="+X+"Y="+Y);
            pX = X;
            pY = Y;
            cnt++;
            flag = 1;
        }
    }

    public void plotFx(Graphics g)
    {
        int flag = 0, pX = 0, pY = 0, X = 0, Y = 0,cnt=0;
        llmt=x_in[0];
        ulmt=x_in[num_input];
        nodx=num_input;
        dx = (ulmt - llmt) / nodx;
        x = llmt;
        maxpos=mxFx;
        maxneg=mnFx;
        Yp_dy = vpix / (maxpos - maxneg);
        Ysegwidth = (maxpos - maxneg) / NoYseg;
        Yp_d = Yp_dy * Ysegwidth;
        axis(g);
        g.setColor(Color.BLUE);
        while (cnt< num_input) {
            Fx = y_in[cnt];
            X = (int) ((cnt * Xp_dx));
            Y = (int) ((maxpos * Yp_dy) - (Fx * Yp_dy));
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

    public void plotRT(Graphics g)
    {
        int flag = 0, pX = 0, pY = 0, X = 0, Y = 0,cnt=0;
        llmt=x_in[0];
        ulmt=x_in[num_input];
        nodx=num_input;
        dx = (ulmt - llmt) / nodx;
        x = llmt;
        maxpos=mxFx;
        maxneg=mnFx;
        Yp_dy = vpix / (maxpos - maxneg);
        Ysegwidth = (maxpos - maxneg) / NoYseg;
        Yp_d = Yp_dy * Ysegwidth;
        for(int i=0;i<int_input;i++)
        {
        cnt=(int)interval[i][0];
        Fx = m_i[i]*x_in[cnt]+c_i[i];
        System.out.println("Fx="+Fx+"cnt="+cnt+"i="+i);
        pX = (int) ((cnt * Xp_dx));
        pY = (int) ((maxpos * Yp_dy) - (Fx * Yp_dy));
        cnt=(int)interval[i][1];
        Fx = m_i[i]*x_in[cnt]+c_i[i];
        System.out.println("Fx="+Fx+"cnt="+cnt+"i="+i);
        X = (int) ((cnt * Xp_dx));
        Y = (int) ((maxpos * Yp_dy) - (Fx * Yp_dy));
        System.out.println("X="+X+"Y="+Y);
        g.setColor(Color.orange);
        g.drawLine(offsetX + pX, offsetY+pY , offsetX + X, offsetY + Y);
        g.setColor(Color.orange);
        g.drawString("X", offsetX-3+pX, offsetY+3+pY);
        g.drawString("X", offsetX-3+X, offsetY+3+Y);
        }
    }

    public void plotR(Graphics g)
    {
        int flag = 0, pX = 0, pY = 0, X = 0, Y = 0,cnt=0;
        llmt=x_in[0];
        ulmt=x_in[num_input];
        nodx=num_input;
        dx = (ulmt - llmt) / nodx;
        x = llmt;
        maxpos=mxFx;
        maxneg=mnFx;
        Yp_dy = vpix / (maxpos - maxneg);
        Ysegwidth = (maxpos - maxneg) / NoYseg;
        Yp_d = Yp_dy * Ysegwidth;
        g.setColor(Color.BLUE);
        cnt=num_input-1;

        Fx = m*x_in[cnt]+c;
        System.out.println("Fx="+Fx);
        X = (int) ((cnt * Xp_dx));
        Y = (int) ((maxpos * Yp_dy) - (Fx * Yp_dy));
        System.out.println("X="+X+"Y="+Y);
        g.drawLine(offsetX + 0, offsetY +(int)(maxpos * Yp_dy) , offsetX + X, offsetY + Y);
    }

    public void plotXY(Graphics g)
    {
        int flag = 0, paX = 0, paY = 0, prX = 0, prY = 0,cnt=0;
        llmt=X[0];
        ulmt=X[num_input-1];
        nodx=num_input;
        dx = (ulmt - llmt) / nodx;
        x = llmt;
        maxpos=mxY;
        maxneg=mnY;
        Yp_dy = vpix / (maxpos - maxneg);
        Ysegwidth = (maxpos - maxneg) / NoYseg;
        Yp_d = Yp_dy * Ysegwidth;
        axis(g);
        g.setColor(Color.BLUE);
        while (cnt< num_input-1) {
            Fx = Y[cnt];
            prX = (int) ((cnt * Xp_dx));
            prY = (int) ((maxpos * Yp_dy) - (Fx * Yp_dy));
            g.setColor(Color.blue);
            g.drawString("X", offsetX+prX-3,offsetY+prY+4);
            /*if (flag == 1)
            {
                g.setColor(Color.green);
                g.drawLine(offsetX + paX, offsetY + paY, offsetX + prX, offsetY + prY);
            }
             * */
            paX = prX;
            paY = prY;
            cnt++;
            flag = 1;
        }

    }
    public void axis(Graphics g)
    {
        int cnt1 = 0;
        double cnt = 0;
        String print;
        g.setColor(Color.black);
        DecimalFormat df = new DecimalFormat("#.##########");
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
        mxFx = y_in[0];
        mnFx = y_in[0];
        mxY=Y[0];
        mnY=Y[0];
       // System.out.println("y_in[0]"+y_in[0]);
        while (cnt<num_input)
        {
            if (mxFx < y_in[cnt])
            {
                mxFx = y_in[cnt];
            }
            if (mnFx > y_in[cnt])
            {
                mnFx = y_in[cnt];
            }
            if (mxY < Y[cnt] && cnt<num_input-1)
            {
                mxY = Y[cnt];
            }
            if (mnY > Y[cnt]&& cnt<num_input-1)
            {
                mnY= Y[cnt];
            }

            cnt++;
        }
        maxpos=mxFx;
        maxneg=mnFx;
        mxLFx=Math.log(mxFx);
        mnLFx=Math.log(mnFx);
        System.out.println("Max +=" + maxpos + "Max -=" + maxneg);
    }

    void regress()
    {
        double pointf=0,pointl=0;
        int n,i,first=0,last=0;
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader inp=new BufferedReader(r);
        try{
        System.out.println("Enter first point (pointf):: ");
        String s=inp.readLine();
        pointf=Double.parseDouble(s);
        System.out.println("Enter last point (pointl):: ");
        s=inp.readLine();
        pointl=Double.parseDouble(s);
        }
        catch(Exception ex)
        {}
        for(i=0;i<num_input-1;i++)
        {
            if(x_in[i]<=pointf)
            {
                first=i;
                System.out.println("x_in["+i+"]="+x_in[i]+"first = "+i);
            }
            if(x_in[i]<=pointl)
            {
                last=i;
                System.out.println("x_in["+i+"]="+x_in[i]+"last = "+i);
            }
        }
        n=(last-first+1);
        sumX=0;
        sumY=0;
        sumXY=0;
        sumX2=0;
        for(i=first;i<=last;i++)
        {
            sumX+=x_in[i];
            sumXY+=(x_in[i]*y_in[i]);
            sumX2+=Math.pow(x_in[i],2);
            sumY+=y_in[i];
        }
        m=(     (n*sumXY)-(sumX*sumY)    )/(     (n*(sumX2))-Math.pow(sumX, 2)   );
        c=(     (sumY*sumX2)-(sumX*sumXY)    )/(     (n*(sumX2))-Math.pow(sumX, 2)   );
        System.out.println("Y = "+m+"X  "+c);
    }

    void regressT()
    {
        double pointf=0,pointl=0;
        int j=0,n,i,first=0,last=0,cnt=0;
        char contd='y';
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader inp=new BufferedReader(r);
        while(contd=='y')
        {
        try{
        System.out.println("Enter first point (pointf):: ");
        String s=inp.readLine();
        interval[cnt][0]=Double.parseDouble(s);
        System.out.println("Enter last point (pointl):: ");
        s=inp.readLine();
        interval[cnt++][1]=Double.parseDouble(s);
        System.out.println("press y to contiue");
        s=inp.readLine();
        contd=s.charAt(0);
        }
        catch(Exception ex)
        {}
        }
        System.out.println(" intput intervals:");
        for(i=0;i<cnt;i++)
            System.out.println("interval:: "+interval[i][0]+" to "+interval[i][1]);

        for(j=0;j<cnt;j++)
        {
              for(i=0;i<num_input-1;i++)
              {
                     if(x_in[i]<=interval[j][0])
                    {
                        first=i;
                           System.out.println("x_in["+i+"]="+x_in[i]+"first = "+i);
                    }
                   if(x_in[i]<=interval[j][1])
                    {
                       last=i;
                         System.out.println("x_in["+i+"]="+x_in[i]+"last = "+i);
                    }
              }
            n=(last-first+1);
            sumX=0;
            sumY=0;
            sumXY=0;
            sumX2=0;
              for(i=first;i<=last;i++)
              {
                    sumX+=x_in[i];
                    sumXY+=(x_in[i]*y_in[i]);
                    sumX2+=Math.pow(x_in[i],2);
                    sumY+=y_in[i];
              }
        interval[j][0]=first;
        interval[j][1]=last;
        System.out.println("Interval from "+first+" to "+ last+"at i="+cnt);
        m_i[j]=(     (n*sumXY)-(sumX*sumY)    )/(     (n*(sumX2))-Math.pow(sumX, 2)   );
        c_i[j]=(     (sumY*sumX2)-(sumX*sumXY)    )/(     (n*(sumX2))-Math.pow(sumX, 2)   );
        System.out.println("Y = "+m_i[j]+"X  "+c_i[j]);
        }
        int_input=cnt;
   }


    void inXY()
    {
        int i,cnt=0;
        double z;
        for(i=0;i<num_input-1;i++)
        {
            z=Math.log(y_in[cnt+1]/y_in[cnt]);
            X[cnt]=(x_in[cnt+1]-x_in[cnt])/z;
            Y[cnt]=(y_in[cnt+1]-y_in[cnt])/z;
            System.out.println(cnt+".  X="+X[cnt]+"   Y="+Y[cnt]);
            cnt++;
        }
    }
    public void input()
    {
                       int i=0,j;
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
                                             x_in[i]=Double.parseDouble(rs.getString(1));
                                             if(x_in[i]<0)
                                             continue;
                                             y_in[i]=Double.parseDouble(rs.getString(2));
                                             System.out.println("i="+i+" x_in="+x_in[i]+"y_in="+y_in[i]);
                                             i++;
                                        }
                                        num_input=i;
                                        System.out.println("num_input:: "+num_input);
                                        st.close();
                    			con.close();
                          }
                          catch(Exception ex)
                          {
                        	System.err.print("Exception: ");
                        	System.err.println(ex.getMessage());
                          }
                       
        String s;
        hpix=1300;
        vpix=600;
        nodx=num_input;
        System.out.print("Number of Segments on X axis");
        NoXseg=10;
        System.out.print("Number of Segments on Y axis");
        NoYseg=10;

  }

     public void init(){
        input();
        inXY();
        offsetX = 50;
        offsetY = 10;
        setSize((offsetX * 2) + hpix + 5, (offsetY * 2) + vpix + 5);
        maxpos = 0;
        maxneg = 0;
        readFx();
        Xp_dx = hpix / nodx;
        Yp_dy = vpix / (maxpos - maxneg);
        Xsegwidth = nodx / NoXseg;
        Ysegwidth = (maxpos - maxneg) / NoYseg;
        Xp_d = (hpix/NoXseg);
         System.out.println("Xesegwidth"+Xsegwidth+"Xp_d"+Xp_d );
        Yp_d = Yp_dy * Ysegwidth;
        System.out.println("Xsegment width ::"+Xsegwidth);

    }
}

