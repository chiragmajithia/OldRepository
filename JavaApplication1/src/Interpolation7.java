//x^3+4x^2-39x-40
// saralasweet5@gmail.com
//equation solved
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.*;
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
public class Interpolation7 extends Applet {

    Point loc = new Point();
    double maxpos, maxneg, llmt, ulmt, Fx, x, nodx, nody, dx, min, Xp_d, Yp_d, Xp_dx, Yp_dy, Xsegwidth, Ysegwidth, NoXseg, NoYseg,ch=0.5;
    int index,hpix, vpix, centerx, centery, offsetX, offsetY,in,nf,cnt=0,exprl=0,expno=0,TD,div;
    int length[]=new int[10];
    char Function[][]=new char[10][1000];
    double interval[][]=new double[10][2];
    char expr[]=new char[1000];
    double mul[]=new double[10];
    double powr[]=new double[10];
    double trig[][]=new double[10][3];
    double x_in[]=new double[100];
    double y_in[]=new double[100];
    double m[]=new double[99],pi=3.1415926535897932384626433832795;
    int num_input;
    char op[]=new char[10];

    public void paint(Graphics g) {
        double print;
        g.setColor(Color.black);
        axis(g);
        //   g.drawRect(0,0,hpix,vpix);
        g.setColor(Color.blue);
        //plotFx(g);
        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                double print;
                Graphics g = getGraphics();
                g.setPaintMode();
                Point x = e.getPoint();
               // System.out.println("Clicked");

                g.drawLine(hpix + offsetX, loc.y, loc.x, loc.y);
                g.drawLine(loc.x, loc.y, loc.x, vpix + offsetY);
                print = maxpos - ((loc.y - offsetY) / Yp_dy);
                g.drawString(Double.toString(print), hpix, (int) loc.y + offsetY);
                print = interval[0][0] + ((loc.x - offsetX) / Xp_dx * dx);
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
                if(e.getKeyChar() == 'F')
                {
                    double i=0;
                    while(true)
                    {
                    try
                    {
                    s=inp.readLine();
                    i=Double.parseDouble(s);
                    }
                    catch(IOException exp)
                    {}
                    if(i==-999)
                    break;
                    Interpolation(i);
                    }
                    e.setKeyChar('f');
                }

                if(e.getKeyChar()=='f')
                {
                        g.setColor(Color.blue);
                        plotFx(g);
                }
                else
                if(e.getKeyChar()=='p')
                {
                        plot_points(g);

                }
                else
                if(e.getKeyChar()=='i')
                {
                        plot_Interpolation(g);
                }
                else
                if(e.getKeyChar()=='n')
                {
                    try
                    {
                    input_points();
                    }
                    catch(IOException E)
                    {}
                    e.setKeyChar(' ');
                }
                else
                if(e.getKeyChar()=='c')
                {
                    try
                    {
                        System.out.println("Set the coefficient 0<ch<1");
                        s=inp.readLine();
                        ch=Double.parseDouble(s);
                    }
                    catch(IOException E)
                    {}
                    table();
                    e.setKeyChar(' ');
                }
                else
                 if(e.getKeyChar()=='d')
                {
                    try
                    {
                        System.out.println("Set the number of subdivision");
                        s=inp.readLine();
                        div=Integer.parseInt(s);
                    }
                    catch(IOException E)
                    {}
                    e.setKeyChar(' ');
                }
                else
                    if(e.getKeyChar()=='t')
                {
                        g.setColor(Color.blue);
                        plot_Trapezoid(g);
                        trap();
                        SSM();
                }
            else
                {
                    repaint();
                 }
            }
        }
        );     g.setColor(Color.black);


    }
    public void trap()
    {
        int i;
        double sum=0,h;
        for(i=0;i<num_input-1;i++)
        {
            h=x_in[i+1]-x_in[i];
            sum=sum+(h/2*(y_in[i+1]+y_in[i]));
        }
        System.out.println("Integration by trapezoidal= ");
        System.out.println("Integration upto "+i+"th interval is "+sum);
        sum=sum+((y_in[i]+y_in[i+1])*(x_in[i+1]-x_in[i])/2);
        System.out.println("Integration by trap= "+sum);
    }
    public void SSM()
    {
        int i,j;
        double sum=0,h,div_int=100,y,yn,x,xn,lsum=0;
        for(i=0;i<num_input;i++)
        {
            h=(x_in[i+1]-x_in[i])/div_int;
            x=x_in[i];
            y=y_in[i];
            for(j=1;j<div_int;j++)
            {
                xn=x_in[i]+(h*j);
                yn=Interpolation(xn);
                sum=sum+(h/2*(y+yn));
               // System.out.println("x="+x+"xn="+xn+"h="+h);
                if(i==num_input-1)
                {
                    lsum=lsum+(h/2*(y+yn));
                }
                y=yn;
                x=xn;
            }
            sum=sum+h/2*(y+y_in[i+1]);
            if(i==num_input-1)
            {
                    lsum=lsum+h/2*(y+y_in[i+1]);
            }
        }
        System.out.println("Integration by SSM with last interval SSM= "+sum);
        System.out.println("Integration upto "+(i-1)+"th interval is "+(sum-lsum));
        sum=sum+((y_in[i-1]+y_in[i])*(x_in[i]-x_in[i-1])/2)-lsum;
        System.out.println("Integration by SSM with last interval trapezoid= "+sum);
    }

    public void plot_Trapezoid(Graphics g)
    {
        int i,Xi,Xi1,Yi,Yi1;
        double pixel_dx;
        pixel_dx=(hpix)/(interval[TD-1][1]-interval[0][0]);
        g.setColor(Color.red);
        for(i=0;i<num_input;i++)
        {
            Xi = (int) (((x_in[i]-interval[0][0])* pixel_dx))+offsetX;
            Yi = (int) ((maxpos * Yp_dy) - (y_in[i]* Yp_dy))+offsetY;
            Xi1 = (int) (((x_in[i+1]-interval[0][0]))*pixel_dx)+offsetX;
            Yi1 = (int) ((maxpos * Yp_dy) - (y_in[i+1]* Yp_dy))+offsetY;
            g.drawLine(Xi,Yi,Xi1,Yi1);
        }
    }
     public void plot_Interpolation(Graphics g)
    {
        int i,Xp,Yp,Xu,Yu,j;
        double yu,xu=x_in[0],c,mu,pixel_dx,xpin,dm,stepx,xp,yp,dd;
        pixel_dx=(hpix)/(interval[TD-1][1]-interval[0][0]);
        Xp=0;
        Yp=0;
        g.setColor(Color.green);
        for(i=0;i<num_input-1;i++)
        {

            dm=(m[i+1]-m[i])*ch/div;
            dd=Math.atan(m[i+1])-Math.atan(m[i]);
            if(Math.abs(dd)<=90)
            {
                ch=0.4+(0.15/pi*2*Math.abs(dd));
            }
            if(Math.abs(dd)>=90)
            {
                ch=(0.4-(0.15/pi*Math.abs(dd)));
            }

            xu=x_in[i];
            yu=y_in[i];
            System.out.println("dm = "+dm);
            stepx=(x_in[i+1]-x_in[i])/div;
            for(j=0;j<=div;j++)
            {
               // if(xu>x_in[i+1])
                //break;
                mu=m[i]+dm*j;
                yu=(mu*xu)+y_in[i+1]-(mu*x_in[i+1]);
                Xu = (int) (((xu-interval[0][0])* pixel_dx))+offsetX;
                Yu = (int) ((maxpos * Yp_dy) - (yu* Yp_dy))+offsetY;
                //System.out.println("xu= "+ xu+stepx +" yu= "+yu);
                // System.out.println("xu="+xu+"yu="+yu+"x["+i+"]"+x_in[i]+"y["+i+"]"+y_in[i]+"x["+(i+1)+"]"+x_in[i+1]+"y["+(i+1)+"]"+y_in[i+1]+"mu= "+mu);
                if(j!=0)
                g.drawLine(Xp,Yp,Xu,Yu);
                Xp=Xu;
                Yp=Yu;
                xp=xu;
                yp=yu;
                xu+=stepx;
            }
        }
    }

   public double Interpolation(double xc)
    {
        int i,d;
        double yc,xu,yu,mu,xp,yp,dm,dd,stepx;

        for(i=0;i<num_input-1;i++)
        {
            if(xc<x_in[i+1] && xc>x_in[i])
            break;
        }
            dd=Math.atan(m[i+1])-Math.atan(m[i]);
            if(Math.abs(dd)<=90)
            {
                ch=0.35+(0.15/pi*2*Math.abs(dd));
            }
            if(Math.abs(dd)>=90)
            {
                ch=(0.45-(0.20/pi*Math.abs(dd)));
            }

        dm=(m[i+1]-m[i])*ch/div;
        stepx=(x_in[i+1]-x_in[i])/div;
        d=(int)((xc-x_in[i])/stepx);
        mu=m[i]+dm*d;
        yc=(mu*xc)+y_in[i+1]-(mu*x_in[i+1]);
        return yc;


  }
    public void input_points() throws IOException
    {
        InputStreamReader r=new InputStreamReader(System.in);
        BufferedReader b=new BufferedReader(r);
        System.out.println("Enter the number of input points");
        String s=b.readLine();
        num_input=Integer.parseInt(s);
        int i;
        System.out.println("self input..!(y/n)");
        s=b.readLine();
        if(s.charAt(0)=='y')
        {
            double div=(interval[TD-1][1]-interval[0][0])/num_input;
            for(i=0;i<=num_input;i++)
            {
                    x_in[i]=interval[0][0]+(div*i);
                    y_in[i]=readFx(x_in[i]);
                    System.out.println("x_in["+i+"] ="+x_in[i]);
                    System.out.println("y_in["+i+"] ="+y_in[i]);
            }

        }
        else
        for(i=0;i<=num_input;i++)
        {
             System.out.print("Enter X["+i+"] ::");
             s=b.readLine();
             x_in[i]=Double.parseDouble(s);
             s=b.readLine();
             //y_in[i]=Double.parseDouble(s);
             y_in[i]=readFx(x_in[i]);
        }
        calSlope();
    }

    public void plot_points(Graphics g)
    {
        double pixel_dy,pixel_dx;
        int  flag = 0, pX = 0, pY = 0, X = 0, Y = 0,cnt1=0;
        pixel_dx=(hpix)/(interval[TD-1][1]-interval[0][0]);
        //dx = (ulmt - llmt) / nodx;
        x = interval[0][0];
        cnt = 0;
        g.setColor(Color.magenta);
        for(int i=0;i<num_input;i++)
        {
            x = x_in[i]-interval[0][0];
            Fx =y_in[i];
            //System.out.println("Plotting at x= "+x+"with pixel_dx="+pixel_dx+"Product= "+(int)(x* pixel_dx)+" Sum :: "+(offsetX+(int)(x* pixel_dx))+" y= "+Fx+"Product Y ="+(int) ((maxpos * Yp_dy) - (Fx * Yp_dy)) );
            X = (int) ((x* pixel_dx));
            Y = (int) ((maxpos * Yp_dy) - (Fx * Yp_dy));
            g.drawString("X",offsetX+X-3,offsetY+Y+4);

        }
    }
    public void calSlope()
    {
            int i;
            for(i=0;i<num_input;i++)
            {
                    m[i]=(y_in[i+1]-y_in[i])/(x_in[i+1]-x_in[i]);
                    System.out.println("Slope "+i+" ="+m[i]);
            }
    }
    public double Fx(double x)
    {
    //System.out.print("cnt="+cnt+"x = "+x+":Ans: ");
    int c=0;
    double ans=0;
    switch((int)trig[c][0])
    {
         case 1:
		ans=ans+(mul[c]*Math.pow(x,powr[c])*Math.sin((double)trig[c][1]*Math.pow(x,trig[c][2]))) ;
		break;
	 case 2:
		ans=ans+(mul[c]*Math.pow(x,powr[c])*Math.cos((double)trig[c][1]*Math.pow(x,trig[c][2]))) ;
		break;
	 default:
		ans=ans+(mul[c]*Math.pow(x,powr[c])) ;
    }
    //    System.out.println(ans+"c= "+c);
    while(c!=(cnt-1))
    {
      c++;
        if(op[c-1]=='+')
       {
		switch((int)trig[c][0])
		{
		 case 1:
			ans=ans+(mul[c]*Math.pow(x,powr[c])*Math.sin((double)trig[c][1]*Math.pow(x,trig[c][2]))) ;
			break;
		 case 2:
			ans=ans+(mul[c]*Math.pow(x,powr[c])*Math.cos((double)trig[c][1]*Math.pow(x,trig[c][2]))) ;
			break;
		 default:
			ans=ans+(mul[c]*Math.pow(x,powr[c])) ;

		}

	}
       if(op[c-1]=='/')
       {
		switch((int)trig[c][0])
		{
		 case 1:
			ans=ans/(mul[c]*Math.pow(x,powr[c])*Math.sin((double)trig[c][1]*x)) ;
			break;
		 case 2:
			ans=ans/(mul[c]*Math.pow(x,powr[c])*Math.cos((double)trig[c][1]*x)) ;
			break;
		 default:
			ans=ans/(mul[c]*Math.pow(x,powr[c])) ;

		}
	}


       if(op[c-1]=='-')
       {
		switch((int)trig[c][0])
		{
		 case 1:
			ans=ans-(mul[c]*Math.pow(x,powr[c])*Math.sin((int)trig[c][1]*x)) ;
			break;
		 case 2:
			ans=ans-(mul[c]*Math.pow(x,powr[c])*Math.cos((int)trig[c][1]*x)) ;
			break;
		 default:
			ans=ans-(mul[c]*Math.pow(x,powr[c])) ;

		}
	}

       if(op[c-1]=='*')
       {
		switch((int)trig[c][0])
		{
		 case 1:
			ans=ans*(mul[c]*Math.pow(x,powr[c])*Math.sin((int)trig[c][1]*x)) ;
			break;
		 case 2:
			ans=ans*(mul[c]*Math.pow(x,powr[c])*Math.cos((int)trig[c][1]*x)) ;
			break;
		 default:
			ans=ans*(mul[c]*Math.pow(x,powr[c])) ;

		}
	}

  //              System.out.println("+"+ans+"   c:: "+c);


    }
    return ans;
    }

    public void plotFx(Graphics g) {
        double pixel_dy;
        int cnt = 0, flag = 0, pX = 0, pY = 0, X = 0, Y = 0,cnt1=0;
        //dx = (ulmt - llmt) / nodx;
        x = interval[0][0];
        //System.out.println("hpix_dx"+Xp_dx+"pixel_dx"+pixel_dx);
        cnt = 0;
        for(int i=0;i<TD;i++)
        {
            updateArrays(i);
            flag=0;
            while (interval[0][0] + (dx * cnt1) <= ulmt) {
            x = interval[0][0] + (dx * cnt1);

            Fx = Fx(x);
            //System.out.println("Plotting at x= "+x+"with Xp_dx="+Xp_dx+" y= "+Fx+"with Yp_dy="+Yp_dy );
            X = (int) ((cnt * Xp_dx));
            Y = (int) ((maxpos * Yp_dy) - (Fx * Yp_dy));
            //System.out.println("Fx: "+Fx+" Y: "+(Fx*Yp_dy));
            //System.out.println("Centery+Y"+centery+" + "+(Fx*pixel_dy)+"="+(centery-Y));

            if (flag == 1) {
                g.drawLine(offsetX + pX, offsetY + pY, offsetX + X, offsetY + Y);
            }
            pX = X;
            pY = Y;
            cnt++;
            cnt1++;
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
            print = df.format(interval[0][0] + (Xsegwidth * dx * cnt1));
            //System.out.println("print : "+(interval[0][0] + (Xsegwidth * dx * cnt1))+"at cnt1= "+cnt1+"dx = "+dx );
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

    public void evaluateFx()
    {
       double cnt = 0;
        maxpos = Fx(llmt);
        maxneg = Fx(llmt);
        //System.out.println("dx = "+dx);
        for(int i=0;i<TD;i++)
        {
            updateArrays(i);
            cnt=0;
        while (llmt + (dx * cnt) <= ulmt)
        {
            x = llmt + (dx * cnt);
            Fx = Fx(x);
            //System.out.println("x="+x+"Fx :"+Fx);
            if (maxpos < Fx) {
                maxpos = Fx;
            }
            if (maxneg > Fx) {
                maxneg = Fx;
            }
            cnt++;
        }
        System.out.println("Max +=" + maxpos + "Max -=" + maxneg);


        }
    }

    public void input() throws IOException
    {
           index=0;
           int xf=0,txf;      // x found trignometric x found
           powr[cnt]=0;
           mul[cnt]=0;
           trig[cnt][0]=0;
           trig[cnt][1]=0;
           trig[cnt][2]=0;

        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader inp=new BufferedReader(r);
        String s;

        /*System.out.print("Enter the screen resolution of your system \n Horizontal pixels ::");
        s= inp.readLine();
        hpix=Integer.parseInt(s);
        System.out.print("Vertical pixels ::");
        s= inp.readLine();
        vpix=Integer.parseInt(s);
        */
        hpix=1300;
        vpix=600;
        System.out.println("Enter the number of divisions to define function");
        s=inp.readLine();
        TD=Integer.parseInt(s);
        for(int j=0;j<TD;j++)
        {
        System.out.print("Enter the Expression");
        s=inp.readLine();
        length[j]=s.length()+1;
        for(in=0;in<s.length();in++)
        {
            Function[j][in]=s.charAt(in);
        }
	Function[j][in]='=' ;
        System.out.print("Lower limit");
        s=inp.readLine();
        interval[j][0]=Double.parseDouble(s);
        System.out.print("Upper Limit");
        s=inp.readLine();
        interval[j][1]=Double.parseDouble(s);
        System.out.print("Enter the number if subdivisions");
        s=inp.readLine();
        div=Integer.parseInt(s);
        }

       /* for(int j=0;j<TD;j++)
        {
        System.out.print("Expression"+j+" =");
        for(in=0;Function[j][in]!='=';in++)
        {
            System.out.println(Function[j][in]);
        }
        }
        */
        updateArrays(0);
        display();
        //System.out.print("Number of divisions");
        //s=inp.readLine();
        //nodx=Double.parseDouble(s);
        nodx=3000;
        System.out.print("Number of Segments on X axis");
        s=inp.readLine();
        NoXseg=Integer.parseInt(s);
        System.out.print("Number of Segments on Y axis");
        s=inp.readLine();
        NoYseg=Integer.parseInt(s);
        input_points();

    }

    void updateArrays(int ind)
    {
        index=ind;
        cnt=0;
        int xf=0,txf;      // x found trignometric x found
        llmt=interval[index][0];
        ulmt=interval[index][1];
        for(int i=0;i<10;i++)
        {
            powr[i]=0;
            trig[i][0]=0;
            trig[i][1]=0;
            mul[i]=0;
            op[i]=' ';
        }
        exprl=length[index];

        for(in=0;Function[index][in]!='=';in++)
        {
            expr[in]=Function[index][in];
        }
	expr[in]='=' ;

        for(in=0;in<exprl;in++)
	{
		if(expr[in]>='0' && expr[in]<='9')
		{
			mul[cnt]=getno();
		}
		if(expr[in]=='x')
		{
			xf=1;
		}
		if(expr[in]=='^')
		{
			in++;
			powr[cnt]=getno();
		}
		if(expr[in]=='s' || expr[in]=='c')
		{
			mul[cnt]=1;
			txf=0;
			if(expr[in]=='s')
			trig[cnt][0]=1;
			else
			trig[cnt][0]=2;
			in+=4;

			trig[cnt][1]=getno();
			if(expr[in]=='x')
			{
			txf=1;

			in++;
			}
			if(expr[in]=='^')
			{
			in++;
			trig[cnt][2]=getno();
			}
			if(txf==0)
			trig[cnt][2]=0;
			else
			{
				if(trig[cnt][2]==0)
				{
				trig[cnt][2]=1;
				}
				if(trig[cnt][1]==0)
				{
				trig[cnt][1]=1;
				}
			}

		}
		if(expr[in]=='+' ||expr[in]=='-' ||expr[in]=='*' ||expr[in]=='/' || expr[in]=='=')
		{
			op[cnt]=expr[in];
			if(xf==0)
			{
				powr[cnt]=0;
			}
			else
			{
				if(powr[cnt]==0)
				powr[cnt]=1;
				if(mul[cnt]==0)
				mul[cnt]=1;
			}
			xf=0;
			cnt++;
			powr[cnt]=0;
			mul[cnt]=0;
			trig[cnt][1]=0;
			trig[cnt][2]=0;
		}
	}
       // display();
    }
    void display()
    {
        int i;
        System.out.print("Expression=");
        for(i=0;i<length[index];i++)
        {
            System.out.print(Function[index][i]);
        }
        for(i=0;i<TD;i++)
        {
            System.out.println("Index["+i+"][0]="+interval[i][0]+" Indec["+i+"][1]"+interval[i][1]);
        }
        for(i=0;i<cnt;i++)
        {
            System.out.println("mul["+i+"]="+mul[i]+"powr["+i+"]"+powr[i]+"trig["+i+"][0]"+trig[i][0]+"trig["+i+"][1]"+trig[i][1]+"trig["+i+"][2]"+trig[i][2]+"op["+i+"]="+op[i]);
        }
    }

double getno()
{
  double m=0;
  int p=-1;
  nf=0;        // number found  true=1 and false= 0;
  for(;(expr[in]>='0' && expr[in]<='9');in++)
  {
	if(nf==0)
	{
		if(expr[in]!='0')
		{
		m=expr[in]-'0';
		nf=1;
		}
	}
	else
		m=(m*10)+(expr[in]-'0');
  }
  if(expr[in]=='.')
  {
       for(in=in+1;(expr[in]>='0' && expr[in]<='9');in++)
       m=m+((expr[in]-'0')*Math.pow(10,p--));
  }


  return(m);
}

double readFx(double x)
{
    for(int i=0;i<TD;i++)
    {
        if(x>=interval[i][0] && x<=interval[i][1])
        {
            index =i;
            break;
        }
    }

    updateArrays(index);
    Fx=Fx(x);
    return Fx;
}
    public void init()
    {
       double i=0;
      InputStreamReader r = new InputStreamReader(System.in);
      BufferedReader inp=new BufferedReader(r);
      String s;
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
        //System.out.println("Interval ="+(interval[TD-1][1]-interval[0][0]));
        dx = ((interval[TD-1][1]-interval[0][0])/ nodx);
        //System.out.println("DX::="+dx);
        evaluateFx();
        centerx = hpix / 2;
        centery = vpix / 2;
        Xp_dx = hpix / nodx;
        Yp_dy = vpix / (maxpos - maxneg);
        Xsegwidth = nodx / NoXseg;
        Ysegwidth = (maxpos - maxneg) / NoYseg;
        Xp_d = Xp_dx * Xsegwidth;
        Yp_d = Yp_dy * Ysegwidth;
        index=0;
        updateArrays(index);
    }

    void table()
    {
        double sts=0,k;
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader inp=new BufferedReader(r);
        String s;

        try
        {
            System.out.print("Enter the step size");
            s=inp.readLine();
            sts=Double.parseDouble(s);
        }
        catch (IOException ex) {}

        System.out.println("x\t,yi\t,ya");
        DecimalFormat df = new DecimalFormat("#.###");

        for(k=llmt;k<ulmt;k+=sts)
        {
                System.out.println(df.format(k)+"\t,"+df.format(Interpolation(k))+"\t,"+df.format(readFx(k))+"\t,"+df.format((Interpolation(k)-readFx(k))/readFx(k)));
        }

    }


}

