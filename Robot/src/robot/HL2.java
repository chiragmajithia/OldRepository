/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robot;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;

/**
 *
 * @author XPS
 */
public class HL2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AWTException {
        // TODO code application logic here
        Robot r = new Robot();
        while(true)
        {
        PointerInfo m= MouseInfo.getPointerInfo();
        Point p=m.getLocation();
        System.out.println("pixel color"+r.getPixelColor(p.x,p.y)+" pointer loc "+p.x+" "+p.y);
        r.delay(2000);

        
        r.mouseMove(350,550);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(6000);
        r.mouseMove(275,326);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(6000);

        r.mouseMove(990,600);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(6000);
        r.mouseMove(915,375);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(6000);
        

        r.mouseMove(410,567);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(6000);
        r.mouseMove(774,384);
        while(r.getPixelColor(774, 384).getRed()<90)
        {
            System.out.println("pixel color = "+r.getPixelColor(815, 384).getRed());
            r.delay(5000);
            r.mouseMove(774,384);
        }
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(6000);
        
         while(r.getPixelColor(226, 408).getRed()<120)
        {
            System.out.println("pixel color = "+r.getPixelColor(226, 408).getRed());
            r.delay(5000);
            r.mouseMove(226,408);
        }
        r.mouseMove(410,567);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(6000);
        }
    }

}
