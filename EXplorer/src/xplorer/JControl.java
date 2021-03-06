/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JConnect.java
 *
 * Created on Mar 18, 2012, 8:33:14 PM
 */
package xplorer;

import gnu.io.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chirag
 */
public class JControl extends javax.swing.JFrame implements Runnable {

    SerialComm UD, RL;

    /** Creates new form JConnect */
    public JControl(String nameUD, CommPortIdentifier sPIUD, int baudUD, String nameRL, CommPortIdentifier sPIRL, int baudRL) {
        UD = new SerialComm(nameUD, sPIUD, baudUD);
        RL = new SerialComm(nameRL, sPIRL, baudRL);
        initComponents();
        jDisconnect.setText("Disconnect");
        new Thread(this).start();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonU = new javax.swing.JButton();
        jButtonL = new javax.swing.JButton();
        jButtonD = new javax.swing.JButton();
        jButtonR = new javax.swing.JButton();
        jSpeed = new javax.swing.JProgressBar();
        jAngle = new javax.swing.JProgressBar();
        jDisconnect = new javax.swing.JButton();
        jSpeedL = new javax.swing.JLabel();
        jSpeedL1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(xplorer.XplorerApp.class).getContext().getResourceMap(JControl.class);
        jButtonU.setIcon(resourceMap.getIcon("jUp.icon")); // NOI18N
        jButtonU.setText(resourceMap.getString("jUp.text")); // NOI18N
        jButtonU.setName("jUp"); // NOI18N
        jButtonU.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jButtonKeyTyped(evt);
            }
        });

        jButtonL.setIcon(resourceMap.getIcon("jButtonL.icon")); // NOI18N
        jButtonL.setText(resourceMap.getString("jButtonL.text")); // NOI18N
        jButtonL.setName("jButtonL"); // NOI18N
        jButtonL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jButtonKeyTyped(evt);
            }
        });

        jButtonD.setIcon(resourceMap.getIcon("jButtonD.icon")); // NOI18N
        jButtonD.setText(resourceMap.getString("jButtonD.text")); // NOI18N
        jButtonD.setName("jButtonD"); // NOI18N
        jButtonD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jButtonKeyTyped(evt);
            }
        });

        jButtonR.setIcon(resourceMap.getIcon("jButtonR.icon")); // NOI18N
        jButtonR.setText(resourceMap.getString("jButtonR.text")); // NOI18N
        jButtonR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jButtonKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButtonL, javax.swing.GroupLayout.PREFERRED_SIZE, 84, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonU, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonD, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonR, javax.swing.GroupLayout.PREFERRED_SIZE, 85, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jButtonU, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonL, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonD, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonR, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSpeed.setMaximum(255);
        jSpeed.setOrientation(1);
        jSpeed.setFocusable(false);
        jSpeed.setName("jSpeed"); // NOI18N

        jAngle.setOrientation(1);
        jAngle.setFocusable(false);
        jAngle.setName("jAngle"); // NOI18N

        jDisconnect.setText(resourceMap.getString("jDisconnect.text")); // NOI18N
        jDisconnect.setName("jDisconnect"); // NOI18N
        jDisconnect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDisconnectMouseClicked(evt);
            }
        });

        jSpeedL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jSpeedL.setText(resourceMap.getString("jSpeedL.text")); // NOI18N
        jSpeedL.setName("jSpeedL"); // NOI18N

        jSpeedL1.setName("jSpeedL1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jAngle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(69, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jDisconnect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 224, Short.MAX_VALUE)
                        .addComponent(jSpeedL, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpeedL1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSpeedL1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpeedL, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jDisconnect, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jAngle, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSpeed, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyTyped

    private void jButtonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonKeyTyped
        // TODO add your handling code here:
        char k = evt.getKeyChar();
        int sleep = 10;
        //System.out.println("UD : " + UD.cnt + UD.ready + "RL : " + RL.cnt + RL.ready);
        if (UD.ready) {
            switch (k) {
                case 'S':
                case 's':
                    UD.write('D');
                    jButtonD.doClick(sleep);
                    break;
                case 'w':
                case 'W':
                    UD.write('U');
                    jButtonU.doClick(sleep);
                    break;
                case 'F':
                case 'f':
                    UD.write('F');
                    break;
                case 'B':
                case 'b':
                    UD.write('B');
                    break;
            }
        }
        if (RL.ready) {
            switch (k) {
                case 'A':
                case 'a':
                    //System.out.println("A");
                    RL.write('L');
                    jButtonL.doClick(sleep);
                    break;
                case 'd':
                case 'D':
                    RL.write('R');
                    jButtonR.doClick(sleep);
                    break;
                case ' ':
                    RL.write(' ');
                    jButtonR.doClick(sleep);
                    break;

            }

            
             
            
            //ready = true;
        }
        System.out.println("UD : " + UD.cnt + UD.ready + "RL : " + RL.cnt + RL.ready);

    }//GEN-LAST:event_jButtonKeyTyped

    private void jDisconnectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDisconnectMouseClicked
        try 
        {
            UD.outputStream.flush();
            RL.outputStream.flush();
        }
        catch (IOException ex) {
            Logger.getLogger(JControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        UD.serialPort.close();
        RL.serialPort.close();
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jDisconnectMouseClicked
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar jAngle;
    private javax.swing.JButton jButtonD;
    private javax.swing.JButton jButtonL;
    private javax.swing.JButton jButtonR;
    private javax.swing.JButton jButtonU;
    private javax.swing.JButton jDisconnect;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jSpeed;
    private javax.swing.JLabel jSpeedL;
    private javax.swing.JLabel jSpeedL1;
    // End of variables declaration//GEN-END:variables

    public void run()
    {
        while (true) {
            if (UD.new_read) {

                int in=(int)UD.readInput();
                System.out.println("UD read=" + in);
                jSpeed.setValue(in);
                jSpeedL.setText(Integer.toString(in));
            }
            if(RL.new_read)
            {
                int in=(int)RL.readInput();
                System.out.println("RL read=" + in);
            }
        }
    }
}
