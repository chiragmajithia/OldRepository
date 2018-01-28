
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Calendar;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author XPS
 */
public class ABs {

    static Robot r;
     static int[] ABH= new int[2];
     static int[] ABM= new int[2];
     static int[] EABH= new int[2];
     static int[] EABM= new int[2];
     static int currentHour,currentMinute;
     static int MozX=270,MozY=745,ChrX=330,ChrY=745,ChrABX=985,ChrABY=210,MozABX=985,MozABY=210+20,CIX=250,CIY=200;
     static Calendar c;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AWTException {
        r= new Robot();
        int[] mindelay= new int[2];
        mindelay[0]=0;
        mindelay[1]=0;
        ABH[0]=23;
        ABM[0]=29;
        ABH[1]=23;
        ABM[1]=31;
        EABH[0]=23;
        EABM[0]=30;
        EABH[1]=23;
        EABM[1]=32;
        c = Calendar.getInstance();
        System.out.println("Time:= "+c.get(Calendar.HOUR)+12+" "+c.get(Calendar.MINUTE)+ c.getTime());
        currentHour=c.get(Calendar.HOUR)+12;
        currentMinute=c.get(Calendar.MINUTE);
        
        int AB=0;
        for(AB=0;AB<2;AB++)
        {
        System.out.println("AB time "+ ABH[AB]+":"+ABM[AB]);
        waitForIt(ABH[AB],ABM[AB]);
        System.out.println("Launched AB"+AB);
        launchAB();
        int cnt=0;
        while(true)
        {
        System.out.println("cnt="+cnt);
        System.out.println("inspire 1");
        inspire('c',0,5);
        r.mouseMove(MozX, MozY);  //Ate AB lauch
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(2000);
        inspire('m',0,5);
        r.mouseMove(ChrX, ChrY);  //Ate AB lauch
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(2000);
        minuteDelay(mindelay[AB]);
        System.out.println(r.getPixelColor(CIX+40, CIY).getRed());
        while(r.getPixelColor(CIX+40, CIY).getRed()<100)
        {
            System.out.println("in rcd");
//            r.delay(60000);
        }
        cnt++;
        c=Calendar.getInstance();
        System.out.println("Checking time"+(c.get(Calendar.HOUR)+12)+c.get(Calendar.MINUTE)+c.get(Calendar.SECOND));
        if(c.get(Calendar.HOUR)+12==EABH[AB] &&c.get(Calendar.MINUTE)>=EABM[AB])
        {
            break;
        }
        if(c.get(Calendar.HOUR)+12>EABH[AB])
        {
            break;
        }
        else
        {
            System.out.println("Time left : "+(c.get(Calendar.HOUR)-EABH[AB]+12)+":"+(c.get(Calendar.MINUTE)-EABM[AB]));
        }
        }
        System.out.println("waiting to end");
        c=Calendar.getInstance();
        currentHour=c.get(Calendar.HOUR)+12;
        currentMinute=c.get(Calendar.MINUTE);
        waitForIt(EABH[AB],EABM[AB]+1);
        cnt=0;
        System.out.println("Ended");
        System.out.println("Rerreshing");
        r.mouseMove(76, 40);  //Ate AB lauch
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(2000);
        r.mouseMove(MozX, MozY);  //Ate AB lauch
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(2000);
        r.mouseMove(1027, 45);  //Ate AB lauch
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(2000);
        r.mouseMove(ChrX, ChrY);  //Ate AB lauch
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(2000);
        }
        while(true)
        {
        PointerInfo m= MouseInfo.getPointerInfo();
        Point p=m.getLocation();
        System.out.println("pixel color"+r.getPixelColor(399,201)+" pointer loc "+p.x+" "+p.y);
        
        //System.out.println("poked!!"+r.getPixelColor(399,201).getRed());
            /*r.mouseMove(399, 201);
            r.mousePress(InputEvent.BUTTON1_MASK);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
            r.delay(2000);*/
         r.delay(5000);
        }


        // TODO code application logic here
    }
     public static void minuteDelay(int min)
     {
            for(int i =0;i<min;i++)
            {
                for(int j=0;j<6;j++)
                    r.delay(10000);
            }
     }
     public static void inspire(char browser,int AB,int times)
     {
         int x,y;
         if(browser == 'c')
         {
             x=CIX;
             y=CIY;
         }
         else
         {
             x=CIX;
             y=CIY+20;
         }
         System.out.println("diff"+ (c.get(Calendar.MINUTE)-ABM[AB]));
         while(times>=0)
         {
             times--;
             c= Calendar.getInstance();
             System.out.println("Time "+ c.get(Calendar.SECOND));
             r.mouseMove(x, y);  //Ate AB lauch
             r.mousePress(InputEvent.BUTTON1_MASK);
             r.mouseRelease(InputEvent.BUTTON1_MASK);
             r.delay(1000);
         }
     }
    public static void waitForIt(int expectedHour,int expectedMinute)
    {
        minuteDelay((expectedHour-currentHour)*60+(expectedMinute-currentMinute));
        System.out.println("Ready");
    }

    public static void launchAB()
    {
        r.mouseMove(ChrABX, ChrABY);  //Ate AB lauch
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(2000);
        r.mouseMove(MozX, MozY);  //Cratus AB Launch
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(2000);
        r.mouseMove(MozABX, MozABY);  //Ate AB lauch
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(2000);
        r.mouseMove(ChrX, ChrY);  //Ate AB lauch
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(2000);
    }

}
