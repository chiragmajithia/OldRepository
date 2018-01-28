import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class StarterApplication extends Frame
{
    public StarterApplication(String frameTitle)
    {
        super(frameTitle);
        add (new Label("Starter",Label.RIGHT),"West");
        addWindowListener(new WindowAdapter()
        {public void windowClosing(WindowEvent e)
        {
         dispose();
         System.out.println("Exit TO Net Beans");
         System.exit(0);
        }
        }
        );
    }
    public void paint(Graphics g)
    {
        g.drawRect(2, 2, 4, 4);
    }
    public static void main(String[] args)
    {
        StarterApplication app=new StarterApplication("Starter Application");
        app.setSize(300,100);
        app.show();

        System.out.println("Starter Application::main()");
    }
}
