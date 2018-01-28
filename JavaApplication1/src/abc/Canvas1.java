package abc;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics; 
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseMotionAdapter; 

public class Canvas1 {
        JFrame f = new JFrame("Swing Paint Demo");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Canvas1();
            }
        });
    }

        Canvas1()
        {
        System.out.println("Created GUI on EDT? "+
        SwingUtilities.isEventDispatchThread());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new MyPanel());
        f.pack();
        f.setVisible(true);
        }
}

class MyPanel extends JPanel {

    private int squareX = 50;
    private int squareY = 50;
    private int squareW = 20;
    private int squareH = 20;

    public MyPanel() {

        setBorder(BorderFactory.createLineBorder(Color.black));
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                moveSquare(e.getX(),e.getY());
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                moveSquare(e.getX(),e.getY());
            }
        });
        
    }
    
    private void moveSquare(int x, int y) {
        int OFFSET = 1;
        if ((squareX!=x) || (squareY!=y)) {
            repaint(squareX,squareY,squareW+OFFSET,squareH+OFFSET);
            squareX=x;
            squareY=y;
            repaint(squareX,squareY,squareW+OFFSET,squareH+OFFSET);
        } 
    }
    

    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);       
        g.drawString("This is my custom Panel!",10,20);
        g.setColor(Color.RED);
        g.fillRect(squareX,squareY,squareW,squareH);
        g.setColor(Color.BLACK);
        g.drawRect(squareX,squareY,squareW,squareH);
    }  
}
/*import javax.swing.*;
import java.awt.*;

public class Canvas1 extends JFrame {
  JLabel Title;
  CustomCanvas DrawingArea;

  public Canvas1 () {
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      Container Pane;
    Pane = getContentPane();
    Title = new JLabel ("The JLabel is in the SOUTH area");
    DrawingArea = new CustomCanvas();
    Pane.add (Title, BorderLayout.SOUTH);
    Pane.add (DrawingArea, BorderLayout.CENTER);
  }

    public static void main(String[] args) {
        new Canvas1().setVisible(true);

    }
}

class CustomCanvas extends Canvas {

  public CustomCanvas () {
    setBackground (Color.yellow);
  }

  public void paint (Graphics g) {
   
    int Height;

    Height = getHeight();
    g.drawString ("The CustomCanvas is in the CENTER area", 10, 1000);
  }
}*/