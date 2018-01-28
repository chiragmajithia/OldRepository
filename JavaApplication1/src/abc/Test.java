package abc;

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

/*
 * Test.java
 *
 * Created on 30 May, 2010, 5:52:17 AM
 */


/**
 *
 * @author Chirag
 */
public class Test extends javax.swing.JFrame {
    int Td=1,flag=0,i=0;
    Test5 obj=new Test5();
    JFrame f_out=new JFrame();
    JFrame f_in=new JFrame();
   // JFrame f_r=new JFrame();
   // JScrollPane S=new JScrollPane();
    JTextField expr[]=new JTextField[20];
    JTextField llmt[]=new JTextField[20];
    JTextField ulmt[]=new JTextField[20];
    JLabel exprlbl[]=new JLabel[20];
    JLabel llbl[]=new JLabel[20];
    JLabel ulbl[]=new JLabel[20];
    GridLayout G=new GridLayout(1,0);
    JSlider sX=new JSlider();
    JSlider sY=new JSlider();
    JPanel p=new JPanel();
    JCheckBox F=new JCheckBox();
    public Test() {
        this.setTitle("FourierSeries");
        initComponents();
        /*TextPanel.setLayout(new GridLayout(5,1));
        for(i=0;i<5;i++)
        {
        expr[i]=new JTextField();
        TextPanel.add(expr[i]);
        }*/
        TextPanel.setLayout(G);
        construct();
     
    }
    @SuppressWarnings({"static-access", "static-access"})
    public void input() throws IOException
    {
           obj.index=0;
           int xf=0,txf;      // x found trignometric x found
           obj.powr[obj.cnt]=0;
           obj.mul[obj.cnt]=0;
           obj.trig[obj.cnt][0]=0;
           obj.trig[obj.cnt][1]=0;
           obj.trig[obj.cnt][2]=0;

        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader inp=new BufferedReader(r);
        String s;
        obj.TD=Td-1;
        for(int j=0;j<obj.TD;j++)
        {
        System.out.println("j="+j+"obj.TD"+obj.TD);
        s=expr[j].getText();
        obj.length[j]=s.length()+1;
        for(obj.in=0;obj.in<s.length();obj.in++)
        {
            obj.Function[j][obj.in]=s.charAt(obj.in);
        }
	obj.Function[j][obj.in]='=' ;
        System.out.print("Lower limit");
        s=llmt[j].getText();
        obj.interval[j][0]=obj.cal(s);
        System.out.print("Upper Limit");
        s=ulmt[j].getText();
        obj.interval[j][1]=obj.cal(s);
        }
        obj.updateArrays(0);
        obj.display();
        obj.initVariables();
        f_out.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f_out.setSize(obj.hpix+500,obj.vpix+120);
        BorderLayout b=new BorderLayout();
        f_out.setLayout(b);
        f_out.add(obj,b.CENTER);
        sX.setMaximum(50);
        sX.setMinimum(1);
        sX.setValue((int)(obj.NoXseg));
        f_out.add(sX,b.SOUTH);
        f_out.add(p,b.NORTH);
        p.add(F);
        obj.f=0;
        obj.l=498;
        obj.calCoeff();
        obj.fourierCoeff();
        for(int i=0;i<499;i++)
        {
            T.setValueAt(i, i, 0);
            T.setValueAt(obj.an[i], i, 1);
            T.setValueAt(obj.bn[i], i, 2);
            T.setValueAt(obj.cn[i], i, 3);
            System.out.println("t="+T.getValueAt( i, 0));

        }
        obj.f=0;
        obj.l=10;
        obj.calCoeff();
        obj.fourierCoeff();
        //f_r.add(S);
        //f_r.add(T);
        f_r.setTitle("Frequency Response");
        f_r.setVisible(true);
        S.setVisible(true);
        T.setVisible(true);
        F.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(F.isSelected())
                {
                    obj.calCoeff();
                    obj.fourierCoeff();
                    obj.plotFR(obj.getGraphics());
                }
            }
        });
        sX.addMouseMotionListener(new MouseMotionAdapter()
        {
            public void mouseDragged(MouseEvent e)
            {
                if(!F.isSelected())
                {
                obj.NoXseg=sX.getValue();
                System.out.println();
                obj.Xsegwidth = obj.nodx / obj.NoXseg;
                obj.Xp_d = obj.Xp_dx * obj.Xsegwidth;
                obj.index=0;
                obj.updateArrays(obj.index);
                obj.repaint();
                Graphics g=obj.getGraphics();
                g.drawString("No of X Divisions"+Double.toString(obj.NoXseg),obj.hpix/2, obj.vpix/2);
                }
                else
                {
                    obj.f=sY.getValue()-1;
                    obj.l=obj.f+sX.getValue();
                    obj.calCoeff();
                    obj.fourierCoeff();
                    obj.plotFR(obj.getGraphics());
                }
            }
        }
        );
        sX.addMouseListener(new MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                Graphics g=obj.getGraphics();
                if(!F.isSelected())
                {
                    g.drawString("No of X Divisions"+Double.toString(obj.NoXseg),obj.hpix/2, obj.vpix/2);
                }
            }
        });
        sY.setOrientation(1);
        sY.setMaximum(100);
        sY.setMinimum(1);
        sY.setValue((int)(obj.NoYseg));
        f_out.add(sY,b.EAST);

        sY.addMouseMotionListener(new MouseMotionAdapter()
        {
            public void mouseDragged(MouseEvent e)
            {
                if(!F.isSelected())
                {
                obj.NoYseg=sY.getValue();
                System.out.println();
                obj.Ysegwidth = (obj.maxpos - obj.maxneg) / obj.NoYseg;
                obj.Yp_d = obj.Yp_dy * obj.Ysegwidth;
                obj.index=0;
                obj.updateArrays(obj.index);
                obj.repaint();
                Graphics g=obj.getGraphics();
                g.drawString("No of Y Divisions"+Double.toString(obj.NoYseg),obj.hpix/2, obj.vpix/2);  
                }
                else
                {
                    obj.f=sY.getValue()-1;
                    obj.l=obj.f+sX.getValue();
                    obj.calCoeff();
                    obj.fourierCoeff();
                    obj.plotFR(obj.getGraphics());
                }
            }
        }
        );
        sY.addMouseListener(new MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                Graphics g=obj.getGraphics();
                if(!F.isSelected())
                {
                g.drawString("No of Y Divisions"+Double.toString(obj.NoYseg),obj.hpix/2, obj.vpix/2);
                }
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        f_r = new javax.swing.JFrame();
        S = new javax.swing.JScrollPane();
        T = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        Submit = new javax.swing.JButton();
        TextPanel = new javax.swing.JPanel();
        Reset = new javax.swing.JButton();

        T.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Sr.No", "An", "Bn", "Cn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        S.setViewportView(T);

        javax.swing.GroupLayout f_rLayout = new javax.swing.GroupLayout(f_r.getContentPane());
        f_r.getContentPane().setLayout(f_rLayout);
        f_rLayout.setHorizontalGroup(
            f_rLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f_rLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(S, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        f_rLayout.setVerticalGroup(
            f_rLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f_rLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(S, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("No of Divisions into which Function is Divided: ");

        Submit.setText("Submit");
        Submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitActionPerformed(evt);
            }
        });

        TextPanel.setLayout(null);

        Reset.setText("Reset");
        Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(194, 194, 194)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Submit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Reset)
                .addGap(219, 219, 219))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(TextPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Submit)
                        .addComponent(Reset)))
                .addGap(29, 29, 29)
                .addComponent(TextPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addGap(221, 221, 221))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
        
    private void SubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitActionPerformed
        try
        {
        input();
        }
        catch(IOException ex)
        {}
        f_out.setTitle("Graph");
        f_out.setVisible(true);// TODO add your handling code here:
        
    }//GEN-LAST:event_SubmitActionPerformed

    private void ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetActionPerformed
        // TODO add your handling code here:
        this.dispose();
        f_out.dispose();
        f_in.dispose();
        new Test().setVisible(true);
    }//GEN-LAST:event_ResetActionPerformed

    void construct()
    {
            this.setSize(1300, 100+Td*40);
            G.setRows(i+1);
            System.out.println("Rows"+G.getRows()+Td);
            TextPanel.setSize(500,(i+1)*100);
            exprlbl[i]=new JLabel();
            exprlbl[i].setText("Expression ["+i+"]:");
            TextPanel.add(exprlbl[i]).setLocation(i, i);
            expr[i]=new JTextField();
            TextPanel.add(expr[i]);
            llbl[i]=new JLabel();
            llbl[i].setText("Lower Limit:");
            TextPanel.add(llbl[i]);
            llmt[i]=new JTextField();
            TextPanel.add(llmt[i]);
            if(i!=0)
            {
                llmt[i].setText(ulmt[i-1].getText());
            }
            ulbl[i]=new JLabel();
            ulbl[i].setText("Upper Limit:");
            TextPanel.add(ulbl[i]);
            ulmt[i]=new JTextField();
            TextPanel.add(ulmt[i]);
            TextPanel.setSize(300*(i+1),5);
            i++;
            Td++;
            flag=0;
            System.out.println("Focus being searched");
            ulmt[i-1].addFocusListener(new FocusAdapter()
            {
                public void focusLost(FocusEvent e)
                {
                    if(!(ulmt[i-1].getText().equalsIgnoreCase("2*pi") || ulmt[i-1].getText().equalsIgnoreCase("")))
                    {
                        System.out.println(ulmt[i-1].getText());
                        construct();
                    }
                }
            });
    }
    
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Test().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Reset;
    private javax.swing.JScrollPane S;
    private javax.swing.JButton Submit;
    private javax.swing.JTable T;
    private javax.swing.JPanel TextPanel;
    private javax.swing.JFrame f_r;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

}


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chirag
 */
    class Test5 extends Applet {

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
        this.setBackground(Color.white);
        /*calCoeff();
        fourierCoeff();
        */InputStreamReader r = new InputStreamReader(System.in);
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
                Graphics g=getGraphics();
                if(e.getKeyChar()=='f')
                {
                    calCoeff();
                    plotFR(g);
                }
                else
                if(e.getKeyChar()=='a')
                {
                    calCoeff();
                    plotAn(g);
                }
                else
                if(e.getKeyChar()=='A')
                {
                    calCoeff();
                    plotSumAn(g);
                }
                else
                if(e.getKeyChar()=='b')
                {
                    calCoeff();
                    plotBn(g);
                }
                else
                if(e.getKeyChar()=='B')
                {
                    calCoeff();
                    plotSumBn(g);
                }
                else
                if(e.getKeyChar()=='s')
                {
                    calCoeff();
                    plotSum(g);
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
                if (maxpos < Fx )
                {
                maxpos = Fx;
                }
                if (maxneg > Fx)
                {
                maxneg = Fx;
                }
            cnt++;
            }

        }
    }

    void initVariables()throws IOException
    {
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader inp=new BufferedReader(r);

        hpix=1300;
        vpix=600;
        nodx=1000;
        NoXseg=5;
        NoYseg=5;
        f=0;
        l=10;

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
        fourierCoeff();
        result();
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
double trapezoidSquare()
{
	double x=interval[0][0],ans=0,nx,f1,f2,h;
        int j=0;
        //System.out.println("dX="+dx);
        while (interval[0][0] + (dx * (j+1)) <= interval[TD-1][1])
        {
	j++;
	nx=interval[0][0]+(dx*j);
	f1=Math.pow(readFx(x),2);
	f2=Math.pow(readFx(nx),2);
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

double cal(String s)
    {
        String substring;
        s=s+"=";
        int init=0, finl=0,flag=0,fsflag=0,negflag=0;
        double ans=0,no;
        int opindex=0;
        for(int i=0;i<s.length();i++)
        {
             if(s.charAt(0)=='-' && negflag==0)
             {
                    negflag=1;
                    i=1;
             }
            if((s.charAt(i)<'0' || s.charAt(i)>'9')&& s.charAt(i)!='.' && s.charAt(i)!='p' && s.charAt(i)!='i'  )
            {
                finl=i;
                substring=s.substring(init, finl);
                flag=0;
                if(substring.equalsIgnoreCase("pi"))
                no=3.1415926535897932384626433832795;
                else
                no=Double.valueOf(substring);
               /* System.out.println("Substring"+no);
                System.out.println("init"+init+"final"+finl);
                System.out.println("opindex"+opindex+s.charAt(opindex));
                */if(fsflag==0)
                {
                    if(negflag==1)
                    ans=no*-1;
                    else
                    ans=no;
                    fsflag=1;
                }
                else
                switch(s.charAt(opindex))
                {
                case '+':
                        ans=ans+no;
                        break;
                case '-':
                       ans=ans-no;
                       break;
                case '*':
                       ans=ans*no;
                       break;
                case '/':
                       ans=ans/no;
                       break;
                }
                opindex=i;

            }
            else
            {
              if(flag==0)
              {
                  init=i;
                  flag=1;
              }
            }

        }
        return ans;
    }

void result()
{
    double Irms=Math.sqrt(trapezoidSquare()/(2*3.1415926535897932384626433832795));
    System.out.println("Rms value"+Irms);
    double I1=cn[1]/Math.sqrt(2);
    System.out.println("I1 ="+I1);
    double THD=Math.sqrt(Math.pow((Irms/I1),2)-1);
    System.out.println("Math.pow((Irms/I1),2)-1)="+ (Math.pow((Irms/I1),2)-1));
    System.out.println("THD ="+THD);
}

}