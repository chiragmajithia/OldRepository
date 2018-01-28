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
public class Refine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AWTException {
        Robot r = new Robot();
        int offset = -33; //0 -33 for mozilla , epic
        int delay=6000;
        while(true)
        {
        PointerInfo m= MouseInfo.getPointerInfo();
        Point p=m.getLocation();
        System.out.println("pixel color"+r.getPixelColor(399,201)+" pointer loc "+p.x+" "+p.y);
        r.mouseMove(365,465+offset);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(delay);
        r.mouseMove(506,325+offset);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(delay);
        r.mouseMove(358,545+offset);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(delay);
        r.mouseMove(506,325+offset);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(delay);
        r.mouseMove(780,574+offset);
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.delay(delay);
       
        r.delay(5000);
        }  // TODO code application logic here
    }

}
