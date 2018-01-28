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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AWTException {

        
        
        Robot r = new Robot();
        while(true)
        {
        PointerInfo m= MouseInfo.getPointerInfo();
        Point p=m.getLocation();
       System.out.println("pixel color"+r.getPixelColor(399,201)+" pointer loc "+p.x+" "+p.y);
        if(r.getPixelColor(399,201).getRed() < 100)
        {
            //System.out.println("poked!!"+r.getPixelColor(399,201).getRed());
            r.mouseMove(399, 201);
            r.mousePress(InputEvent.BUTTON1_MASK);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
            r.delay(2000);
        }
        r.delay(5000);
        }
        // TODO code application logic here
    }

}
