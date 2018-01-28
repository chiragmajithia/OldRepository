
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chirag
 */
public class Solar2 extends Applet {

    Point loc = new Point();
    double maxpos,maxneg, llmt, ulmt, Fx, x, nodx, nody, dx, Xp_d, Yp_d, Xp_dx, Yp_dy, Xsegwidth, Ysegwidth, NoXseg, NoYseg,sumX,sumX2,sumXY,sumY,m,c,mxFx,mnFx,mxLFx,mnLFx,mnY,mxY,mxX,mnX;
    int hpix, vpix,offsetX,offsetY,exprl=0,expno=0,num_input,int_input,intervalf=0;
    int length[]=new int[10];
    double x_in[]=new double[2500];
    double y_in[]=new double[2500];
    double X[]=new double[2500];
    double Y[]=new double[2500];
    double interval[][]=new double[10][2];
    double m_i[]=new double[10];
    double c_i[]=new double[10];
    char k='n';
    Color arr[]={Color.CYAN,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.DARK_GRAY,Color.DARK_GRAY,Color.RED,Color.yellow};

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
                        intervalf=1;
                        plotRT(g);
                        e.setKeyChar('X');
                }
                if(e.getKeyChar()=='3')
                {
                        result();
                        e.setKeyChar('x');
                }

                if(e.getKeyChar()=='4')
                {
                        regress2();
                        e.setKeyChar('x');
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
        int flag = 0, paX = 0, paY = 0, prX = 0, prY = 0,cnt=0;
        double pixel_dx,maxX,minX;
        llmt=mnX;
        ulmt=mxX;
        pixel_dx=hpix/(ulmt-llmt);
        nodx=num_input;
        dx = (ulmt - llmt) / nodx;
        x = llmt;
        maxpos=mxY;
        maxneg=mnY;
        Yp_dy = vpix / (maxpos - maxneg);
        Ysegwidth = (maxpos - maxneg) / NoYseg;
        Yp_d = Yp_dy * Ysegwidth;
        for(int i=0;i<int_input;i++)
        {
            System.out.println("interval["+i+"][0]"+interval[i][0]);
            System.out.println("interval["+i+"][1]"+interval[i][1]);
        }
        for(int i=0;i<int_input;i++)
        {
            maxX=X[(int)interval[i][0]];
            minX=X[(int)interval[i][0]];
            for(int j=(int)interval[i][0];j<(int)interval[i][1];j++)
            {
                if(maxX<X[j])
                    maxX=X[j];
                if(minX>X[j])
                    minX=X[j];
            }
            Fx = m_i[i]*minX+c_i[i];
            paX = (int)((minX-llmt)*pixel_dx);
            paY = (int) ((maxpos * Yp_dy) - (Fx * Yp_dy));
            prX= (int)((maxX-llmt)*pixel_dx);
            Fx = m_i[i]*maxX+c_i[i];
            prY=(int) ((maxpos * Yp_dy) - (Fx * Yp_dy));
            g.setColor(arr[i]);
            g.drawLine(offsetX + paX, offsetY+paY , offsetX + prX, offsetY + prY);
            paX=prX;
            paY=prY;
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException ex) {
                Logger.getLogger(Solar1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void plotR(Graphics g)
    {
        int flag = 0, pX = 0, pY = 0, X = 0, Y = 0,cnt=0,expc=0;
        double pixel_dx;
        llmt=mnX;
        ulmt=mxX;
        pixel_dx=hpix/(ulmt-llmt);
        nodx=num_input;
        dx = (ulmt - llmt) / nodx;
        x = llmt;
        maxpos=mxY;
        maxneg=mnY;
        Yp_dy = vpix / (maxpos - maxneg);
        Ysegwidth = (maxpos - maxneg) / NoYseg;
        Yp_d = Yp_dy * Ysegwidth;
        g.setColor(Color.BLACK);
        Fx = m*mnX+c;
        //System.out.println("Fx="+Fx);
        pX = (int)((mnX-llmt)*pixel_dx);
        pY = (int) ((maxpos * Yp_dy) - (Fx * Yp_dy));
        X= (int)((mxX-llmt)*pixel_dx);
        Fx=m*mxX+c;
        Y=(int) ((maxpos * Yp_dy) - (Fx * Yp_dy));
     //   System.out.println("X="+X+"Y="+Y);
     // System.out.println("pX="+pX+"pY="+pY);
        g.drawLine(offsetX + pX, offsetY + pY , offsetX + X, offsetY + Y);
    }

    public void plotXY(Graphics g)
    {
        int flag = 0, paX = 0, paY = 0, prX = 0, prY = 0,cnt=0,i=0,exc=0;
        double pixel_dx;
        llmt=mnX;
        ulmt=mxX;
        nodx=num_input;
        pixel_dx=hpix/(ulmt-llmt);
        x = llmt;
        maxpos=mxY;
        maxneg=mnY;
        Yp_dy = vpix / (maxpos - maxneg);
        Ysegwidth = (maxpos - maxneg) / NoYseg;
        Yp_d = Yp_dy * Ysegwidth;

       // System.out.println("ulmt="+ulmt+"llmt="+llmt+"pixel_dx="+pixel_dx);

        axis(g);
        while (cnt< num_input-1) {
            Fx = Y[cnt];
            prX = (int) ( (X[cnt]-llmt) * pixel_dx);
       //     System.out.println("X["+cnt+"] = "+X[cnt]+" X[cnt]-llmt = "+(X[cnt]-llmt)+" prX="+prX+"prY="+prY);
            prY = (int) ((maxpos * Yp_dy) - (Fx * Yp_dy));

            if(intervalf==0)
            g.setColor(arr[i]);
            if(intervalf==1)
            {
                try{
                if(cnt>interval[i][1] && exc==0)
                i++;
                 g.setColor(arr[i]);
                }
                catch(Exception e)
                {
                    g.setColor(Color.BLUE);
                   // System.out.println("Exception caught i:: "+i);
                    exc=1;
                    intervalf=1;
                }

            }
            String no= new DecimalFormat("###").format(cnt);
            g.drawString(no, offsetX+prX-3,offsetY+prY+4);
            if (flag == 1)
            {
                g.setColor(Color.green);
                g.drawLine(offsetX + paX, offsetY + paY, offsetX + prX, offsetY + prY);
            }
            paX = prX;
            paY = prY;
            cnt++;
            flag = 1;
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Solar1.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    void result()
    {
        double pointf=0,pointl=0,Rs = 0,n = 0,sum = 0,lnI0,sumI=0,I0;
        int i,first=0,last=0;
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader inp=new BufferedReader(r);
        try{
        System.out.println("Enter first point (pointf):: ");
        String s=inp.readLine();
        pointf=Double.parseDouble(s);
        System.out.println("Enter last point (pointl):: ");
        s=inp.readLine();
        pointl=Double.parseDouble(s);
        System.out.println("Enter Rs");
        s=inp.readLine();
        Rs=Double.parseDouble(s);
        System.out.println("Rs= "+Rs);
        }
        catch(Exception ex)
        {}
        for(i=0;i<num_input-1;i++)
        {
            if(x_in[i]<=pointf)
            {
                first=i+1;
                //System.out.println("x_in["+i+"]="+x_in[i]+"first = "+i);
            }
            if(x_in[i]<=pointl)
            {
                last=i;
                //System.out.println("x_in["+i+"]="+x_in[i]+"last = "+i);
            }
        }
        for(i=first;i<=last;i++)
        {
            n=-1*Rs/0.02585*(Y[i]-(X[i]/Rs));
            sum=sum+n;
            System.out.println("n= "+n+" = -1/.0.2585* "+Rs+"("+Y[i]+" - ( "+X[i]+"/"+Rs);
            lnI0=(Math.log(y_in[i])-(1/0.2585/n*(x_in[i])-(y_in[i]*Rs)));
            I0=Math.exp(lnI0);
            //System.out.println("I0="+I0);
            sumI=sumI+I0;
        }

        sum=sum/(last-first+1);
        System.out.println("avg n1 = "+sum);
        sumI=sumI/(last-first+1);
        System.out.println("avg Iod = "+sumI);
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
            if (mxX < X[cnt] && cnt<num_input-1)
            {
                mxX = X[cnt];
            }
            if (mnX > X[cnt]&& cnt<num_input-1)
            {
                mnX= X[cnt];
            }

            cnt++;
        }
        maxpos=mxFx;
        maxneg=mnFx;
        mxLFx=Math.log(mxFx);
        mnLFx=Math.log(mnFx);
     //   System.out.println("Max +=" + maxpos + "Max -=" + maxneg);
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
                first=i+1;
                //System.out.println("x_in["+i+"]="+x_in[i]+"first = "+i);
            }
            if(x_in[i]<=pointl)
            {
                last=i;
                //System.out.println("x_in["+i+"]="+x_in[i]+"last = "+i);
            }
        }
        n=(last-first+1);
        sumX=0;
        sumY=0;
        sumXY=0;
        sumX2=0;
        for(i=first;i<=last;i++)
        {
            sumX+=X[i];
            sumXY+=(X[i]*Y[i]);
            sumX2+=Math.pow(X[i],2);
            sumY+=Y[i];
        }
        m=(     (n*sumXY)-(sumX*sumY)    )/(     (n*(sumX2))-Math.pow(sumX, 2)   );
        c=(     (sumY*sumX2)-(sumX*sumXY)    )/(     (n*(sumX2))-Math.pow(sumX, 2)   );

    }


    void regressT()
    {

        int j=0,n,i,first=0,last=0,cnt=0;
        double rs = 0;
        char contd='y';
        String s;
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader inp=new BufferedReader(r);

        while(contd=='y')
        {
        try{
        System.out.println("Enter first point (pointf):: ");
        s=inp.readLine();
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
                        first=i+1;
                        //System.out.println("x_in["+i+"]="+x_in[i]+"first = "+i);
                    }
                   if(x_in[i]<=interval[j][1])
                    {
                       last=i;
                       //System.out.println("x_in["+i+"]="+x_in[i]+"last = "+i);
                    }
              }
            n=(last-first+1);
            sumX=0;
            sumY=0;
            sumXY=0;
            sumX2=0;
            System.out.println("X[i]\tY[i]\t for interval "+first+" to "+ last );
            for(i=first;i<=last;i++)
            {
                    System.out.println(X[i]+"\t"+Y[i]);
                    sumX+=X[i];
                    sumXY+=(X[i]*Y[i]);
                    sumX2+=Math.pow(X[i],2);
                    sumY+=Y[i];
            }
            System.out.println("SumX="+sumX+"sumY="+sumY+"sumXY="+sumXY+"sumX2="+sumX2);
        interval[j][0]=first;
        interval[j][1]=last;
        System.out.println("Interval from "+first+" to "+ last+"at i="+j);
        m_i[j]=(     (n*sumXY)-(sumX*sumY)    )/(     (n*(sumX2))-Math.pow(sumX, 2)   );
        c_i[j]=(     (sumY*sumX2)-(sumX*sumXY)    )/(     (n*(sumX2))-Math.pow(sumX, 2)   );
        System.out.println("Y = "+m_i[j]+"X  "+c_i[j]);
        System.out.println("__________________________________________________");
        rs=1/m_i[j];
        System.out.println("rs=1/"+m_i[j]+" = "+rs);
        System.out.println("n= "+(-1*c_i[j]*rs/0.0258));
        }
        int_input=cnt;
   }

    void regress2()
    {
        double pointf=0,pointl=0,rs = 0,rp = 0,Iod = 0,n1 = 0,n2,v,x,y;
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
        System.out.println("Enter Rs ");
        s=inp.readLine();
        rs=Double.parseDouble(s);
        System.out.println("Enter Rp ");
        s=inp.readLine();
        rp=Double.parseDouble(s);
        System.out.println("Enter Iod ");
        s=inp.readLine();
        Iod=Double.parseDouble(s);
        System.out.println("Enter n1");
        s=inp.readLine();
        n1=Double.parseDouble(s);
        }
        catch(Exception ex)
        {}
        for(i=0;i<num_input-1;i++)
        {
            if(x_in[i]<=pointf)
            {
                first=i+1;
                //System.out.println("x_in["+i+"]="+x_in[i]+"first = "+i);
            }
            if(x_in[i]<=pointl)
            {
                last=i;
                //System.out.println("x_in["+i+"]="+x_in[i]+"last = "+i);
            }
        }
        n=(last-first+1);
        sumX=0;
        sumY=0;
        sumXY=0;
        sumX2=0;
        for(i=first;i<=last;i++)
        {
            v= x_in[i]-(y_in[i]*rs);
            y=y_in[i]-(v/rp)-(Iod*(Math.exp(38.6816865*v/n1)-1));
            System.out.println("V= "+v+"y= "+y);
            System.out.println(y_in[i]+" - "+(v/rp)+" - "+Iod*(Math.exp(38.6816865*v/n1)-1));
            y=Math.abs(Math.log(y));
            x=v;

            sumX+=x;
            sumXY+=(x*y);
            sumX2+=Math.pow(x,2);
            sumY+=y;
        }
        m=(     (n*sumXY)-(sumX*sumY)    )/(     (n*(sumX2))-Math.pow(sumX, 2)   );
        c=(     (sumY*sumX2)-(sumX*sumXY)    )/(     (n*(sumX2))-Math.pow(sumX, 2)   );
        System.out.println("Y= "+m+"X +"+c);
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
            System.out.println("y_in["+(cnt+1)+"]/y_in["+cnt+" : "+y_in[cnt+1]+"/"+y_in[cnt]+" = "+(y_in[cnt+1]/y_in[cnt]));
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

