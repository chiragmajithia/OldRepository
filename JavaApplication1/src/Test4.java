
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.*;
import java.text.DecimalFormat;

import javax.swing.*;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chirag
 */
public class Test4 extends Applet {

    Point loc = new Point();
    double maxpos, maxneg, llmt, ulmt, Fx, x, nodx, nody, dx, min, Xp_d, Yp_d, Xp_dx, Yp_dy, Xsegwidth, Ysegwidth, NoXseg, NoYseg,maxCn;
    int index,hpix, vpix, centerx, centery, offsetX, offsetY,in,nf,cnt=0,exprl=0,expno=0,TD,f,l;
    int length[]=new int[10];
    char Function[][]=new char[10][1000];
    double interval[][]=new double[10][2];
    char expr[]=new char[1000];
    double mul[]=new double[10];
    double powr[]=new double[10];
    double trig[][]=new double[10][3];
    double an[]=new double[1000];
    double bn[]=new double[1000];
    double cn[]=new double[1000];
    double n[]=new double[1000];
    char op[]=new char[10];

    public void paint(Graphics g) {
        double print;
        char c;
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader inp=new BufferedReader(r);
        String s;
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
                    plotSumAn(g);
                }
                else
                if(e.getKeyChar()=='b')
                {
                    plotBn(g);
                }
                else
                if(e.getKeyChar()=='B')
                {
                    plotSumBn(g);
                }
                else
                if(e.getKeyChar()=='s')
                {
                    plotSum(g);
                }
                else
                if(e.getKeyChar()=='x')
                {
                    try{
                    initVariables();
                    System.out.println("Press on the graph any key to continue");
                    s=inp.readLine();
                    }
                    catch(IOException ex){}
                    e.setKeyChar('e');
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
                if(e.getKeyChar()=='g')
                {
                    try
                    {
                         System.out.print("Number of divisions");
                         s=inp.readLine();
                         nodx=Double.parseDouble(s);
                         System.out.print("Number of Segments on X axis");
                         s=inp.readLine();
                         NoXseg=Integer.parseInt(s);
                         System.out.print("Number of Segments on Y axis");
                         s=inp.readLine();
                         NoYseg=Integer.parseInt(s);
                         System.out.println("Set Max and Min values on graph?");
                         s=inp.readLine();
                         if(s.charAt(0)=='y')
                         {
                         System.out.print("Maxpos::=");
                         s=inp.readLine();
                         maxpos=Double.parseDouble(s);
                         System.out.print("Maxneg::=");
                         s=inp.readLine();
                         maxneg=Double.parseDouble(s);
                         }
                         dx = ((interval[TD-1][1]-interval[0][0])/ nodx);
                         Xp_dx = hpix / nodx;
                         Yp_dy = vpix / (maxpos - maxneg);
                         Xsegwidth = nodx / NoXseg;
                         Ysegwidth = (maxpos - maxneg) / NoYseg;
                         Xp_d = Xp_dx * Xsegwidth;
                         Yp_d = Yp_dy * Ysegwidth;
                         index=0;
                         updateArrays(index);

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
        g.setColor(Color.RED);
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
       // System.out.println("dx = "+dx);
        for(int i=0;i<TD;i++)
        {
            updateArrays(i);
            cnt=0;
        while (llmt + (dx * cnt) <= ulmt)
        {
            x = llmt + (dx * cnt);
            Fx = Fx(x);
            //System.out.println("x="+x+"Fx :"+Fx);
            if (maxpos < Fx && Fx >= 0) {
                maxpos = Fx;
            }
            if (maxneg > Fx && Fx <= 0) {
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

        }
        
       /* for(int j=0;j<TD;j++)
        {
       System.out.print("Expression"+j+" =");
        for(in=0;Function[j][in]!='=';in++)
        {
            System.out.println(Function[j][in]);
        }
        }*/
        updateArrays(0);
    //    display();
        initVariables();
    }
    void initVariables()throws IOException
    {
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader inp=new BufferedReader(r);

        System.out.print("Enter the screen resolution of your system \n Horizontal pixels ::");
        String s= inp.readLine();
        hpix=Integer.parseInt(s);
        System.out.print("Vertical pixels ::");
        s= inp.readLine();
        vpix=Integer.parseInt(s);
        System.out.print("Number of divisions");
        s=inp.readLine();
        nodx=Double.parseDouble(s);
        System.out.print("Number of Segments on X axis");
        s=inp.readLine();
        NoXseg=Integer.parseInt(s);
        System.out.print("Number of Segments on Y axis");
        s=inp.readLine();
        NoYseg=Integer.parseInt(s);
        System.out.print("Initial value of n for Frequency Response ::");
        s=inp.readLine();
        f=Integer.parseInt(s);
        System.out.print("Final value of n for Frequency Response ::");
        s=inp.readLine();
        l=Integer.parseInt(s);

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
        //System.out.println("Integration="+trapezoid());
        index=0;
        updateArrays(index);

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
      char c;
      InputStreamReader r = new InputStreamReader(System.in);
      BufferedReader inp=new BufferedReader(r);
      String s;
        try
        {
            input();
        }
        catch (IOException ex) {}

        fourierCoeff();
        

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
            an[c]=1/(interval[TD-1][1]-interval[0][0])*trapezoid();
        }
        else
        an[c]=2/(interval[TD-1][1]-interval[0][0])*trapezoidC(i);
        bn[c]=2/(interval[TD-1][1]-interval[0][0])*trapezoidS(i);
        cn[c]=Math.sqrt((an[c]*an[c])+(bn[c]*bn[c]));
        if(maxCn<cn[c])
        {
            maxCn=Math.sqrt((an[c]*an[c])+(bn[c]*bn[c]));
        }
        c++;
    }
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
	double x=interval[0][0],ans=0,nx,f1,f2,h;
        int j=0;
        //System.out.println("dX="+dx);
        while (interval[0][0] + (dx * (j+1)) <= interval[TD-1][1])
        {
	j++;
	nx=interval[0][0]+(dx*j);
	f1=readFx(x);
	f2=readFx(nx);
        ans=ans+((f1+f2)/2*dx);
	x=nx;
       // System.out.println("Ans="+ans +"j="+j);
	}
	return ans;
}

double trapezoidS(int n)
{
	double x=interval[0][0],ans=0,nx,f1,f2,h;
        int j=0;
       // System.out.println("dX="+dx);
        while (interval[0][0] + (dx * (j+1)) <= interval[TD-1][1])
        {
	j++;
	nx=interval[0][0]+(dx*j);
	f1=readFx(x)*Math.sin((n*x));
	f2=readFx(nx)*Math.sin((n*nx));
        ans=ans+((f1+f2)/2*dx);
	x=nx;
        //System.out.println("Ans="+ans +"j="+j);
	}
	return ans;
}

double trapezoidC(int n)
{
	double x=interval[0][0],ans=0,nx,f1,f2,h;
        int j=0;
        //System.out.println("dX="+dx);
        while (interval[0][0] + (dx * (j+1)) <= interval[TD-1][1])
        {
	j++;
	nx=interval[0][0]+(dx*j);
	f1=readFx(x)*Math.cos((n*x));
	f2=readFx(nx)*Math.cos((n*nx));
        ans=ans+((f1+f2)/2*dx);
	x=nx;
        //System.out.println("Ans="+ans +"j="+j);
	}
	return ans;
}

void plotFR(Graphics g)
{
    g.clearRect(0,0,2000,2000);
    int c=0,divisions=l-f,ofX=50,ofY=50,counter=f,Hpix=hpix,Vpix=vpix-20;
    double pperdx=Hpix/(l-f+1);
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
        g.drawString(Double.toString(cn[c]), (int)(c*pperdx)+(c*ofX/divisions), Vpix-5-(int)(pperdy*cn[c]));
        c++;
    }

}

void plotAn(Graphics g)
{
    getGraphics();
    g.setColor(Color.blue);
    double pixel_dy,fx;
        int  flag = 0, pX = 0, pY = 0, X = 0, Y = 0,cnt1=0;
        //dx = (ulmt - llmt) / nodx;
        x = interval[0][0];
        //System.out.println("hpix_dx"+Xp_dx+"pixel_dx"+pixel_dx);

        for(int i=0;i<(l-f+1);i++)
        {
            flag=0;
            cnt1=0;
            while (interval[0][0] + (dx * cnt1) <= interval[TD-1][1]) {
            x = interval[0][0] + (dx * cnt1);

            fx = an[i]*Math.cos((i*x));
            //System.out.println("Plotting at x= "+x+"with Xp_dx="+Xp_dx+" y= "+Fx+"with Yp_dy="+Yp_dy );
            X = (int) ((cnt1 * Xp_dx));
            Y = (int) ((maxpos * Yp_dy) - (fx * Yp_dy));
            //System.out.println("X= "+x+"Fx: "+fx+" An "+an[i]+"i="+i);
            //System.out.println("Centery+Y"+centery+" + "+(Fx*pixel_dy)+"="+(centery-Y));

            if (flag == 1) {
                g.drawLine(offsetX + pX, offsetY + pY, offsetX + X, offsetY + Y);
            }
            cnt1++;
            pX = X;
            pY = Y;
            flag = 1;
        }
        }
}
void plotBn(Graphics g)
{
    getGraphics();
    g.setColor(Color.blue);
    double pixel_dy,fx;
        int  flag = 0, pX = 0, pY = 0, X = 0, Y = 0,cnt1=0;
        //dx = (ulmt - llmt) / nodx;
        x = interval[0][0];
        //System.out.println("hpix_dx"+Xp_dx+"pixel_dx"+pixel_dx);

        for(int i=0;i<(l-f+1);i++)
        {
            flag=0;
            cnt1=0;
            while (interval[0][0] + (dx * cnt1) <= interval[TD-1][1]) {
            x = interval[0][0] + (dx * cnt1);

            fx = bn[i]*Math.sin((i*x));
            //System.out.println("Plotting at x= "+x+"with Xp_dx="+Xp_dx+" y= "+Fx+"with Yp_dy="+Yp_dy );
            X = (int) ((cnt1 * Xp_dx));
            Y = (int) ((maxpos * Yp_dy) - (fx * Yp_dy));
            //System.out.println("X= "+x+"Fx: "+fx+" An "+an[i]+"i="+i);
            //System.out.println("Centery+Y"+centery+" + "+(Fx*pixel_dy)+"="+(centery-Y));

            if (flag == 1) {
                g.drawLine(offsetX + pX, offsetY + pY, offsetX + X, offsetY + Y);
            }
            cnt1++;
            pX = X;
            pY = Y;
            flag = 1;
        }
        }
}
void plotSumBn(Graphics g)
{
    getGraphics();
    g.setColor(Color.magenta);
    double pixel_dy,fx;
    int  flag = 0, pX = 0, pY = 0, X = 0, Y = 0,cnt1=0;
        //dx = (ulmt - llmt) / nodx;
        x = interval[0][0];
        //System.out.println("hpix_dx"+Xp_dx+"pixel_dx"+pixel_dx);

        while (interval[0][0] + (dx * cnt1) <= interval[TD-1][1])
        {
            fx=0;
            x = interval[0][0] + (dx * cnt1);
             for(int i=0;i<(l-f+1);i++)
             {
                  fx+= bn[i]*Math.sin((i*x));
             }
            //System.out.println("Plotting at x= "+x+"with Xp_dx="+Xp_dx+" y= "+Fx+"with Yp_dy="+Yp_dy );
            X = (int) ((cnt1 * Xp_dx));
            Y = (int) ((maxpos * Yp_dy) - (fx * Yp_dy));
            //System.out.println("X= "+x+"Fx: "+fx+" An "+an[i]+"i="+i);
            //System.out.println("Centery+Y"+centery+" + "+(Fx*pixel_dy)+"="+(centery-Y));

            if (flag == 1) {
                g.drawLine(offsetX + pX, offsetY + pY, offsetX + X, offsetY + Y);
            }
            cnt1++;
            pX = X;
            pY = Y;
            flag = 1;
        
        }
}
void plotSumAn(Graphics g)
{
    getGraphics();
    g.setColor(Color.magenta);
    double pixel_dy,fx;
    int  flag = 0, pX = 0, pY = 0, X = 0, Y = 0,cnt1=0;
        //dx = (ulmt - llmt) / nodx;
        x = interval[0][0];
        //System.out.println("hpix_dx"+Xp_dx+"pixel_dx"+pixel_dx);

        while (interval[0][0] + (dx * cnt1) <= interval[TD-1][1])
        {
            fx=0;
            x = interval[0][0] + (dx * cnt1);
             for(int i=0;i<(l-f+1);i++)
             {
                  fx+= an[i]*Math.cos((i*x));
             }
            //System.out.println("Plotting at x= "+x+"with Xp_dx="+Xp_dx+" y= "+Fx+"with Yp_dy="+Yp_dy );
            X = (int) ((cnt1 * Xp_dx));
            Y = (int) ((maxpos * Yp_dy) - (fx * Yp_dy));
            //System.out.println("X= "+x+"Fx: "+fx+" An "+an[i]+"i="+i);
            //System.out.println("Centery+Y"+centery+" + "+(Fx*pixel_dy)+"="+(centery-Y));

            if (flag == 1) {
                g.drawLine(offsetX + pX, offsetY + pY, offsetX + X, offsetY + Y);
            }
            cnt1++;
            pX = X;
            pY = Y;
            flag = 1;

        }
}
void plotSum(Graphics g)
{
    getGraphics();
    g.setColor(Color.magenta);
    double pixel_dy,fx;
    int  flag = 0, pX = 0, pY = 0, X = 0, Y = 0,cnt1=0;
        //dx = (ulmt - llmt) / nodx;
        x = interval[0][0];
        //System.out.println("hpix_dx"+Xp_dx+"pixel_dx"+pixel_dx);

        while (interval[0][0] + (dx * cnt1) <= interval[TD-1][1])
        {
            fx=0;
            x = interval[0][0] + (dx * cnt1);
             for(int i=0;i<(l-f+1);i++)
             {
                  fx+= an[i]*Math.cos((i*x))+bn[i]*Math.sin((i*x));
             }
            //System.out.println("Plotting at x= "+x+"with Xp_dx="+Xp_dx+" y= "+Fx+"with Yp_dy="+Yp_dy );
            X = (int) ((cnt1 * Xp_dx));
            Y = (int) ((maxpos * Yp_dy) - (fx * Yp_dy));
            //System.out.println("X= "+x+"Fx: "+fx+" An "+an[i]+"i="+i);
            //System.out.println("Centery+Y"+centery+" + "+(Fx*pixel_dy)+"="+(centery-Y));

            if (flag == 1) {
                g.drawLine(offsetX + pX, offsetY + pY, offsetX + X, offsetY + Y);
            }
            cnt1++;
            pX = X;
            pY = Y;
            flag = 1;

        }
}
}