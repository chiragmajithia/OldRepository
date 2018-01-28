



import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Enumeration;
import java.util.HashMap;
import javax.swing.text.DefaultCaret;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author XPS
 */
public class DimmerClientGUI extends javax.swing.JFrame {
    protected InputStream inputStream;
    public static int BAUD;
    protected String response;
    public static final int TIMEOUTSECONDS=30;
    protected HashMap map=new HashMap();
    public  DimmerClient client ;
    public static boolean graphics_update;
    
    public DimmerClientGUI() {
        
        initComponents();
        DefaultCaret caret = (DefaultCaret)jServerClientComm.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        jIPLabel.setText(DimmerClient.getInetAddress());
        jCommPanel.setVisible(false);
        jControlPanel.setVisible(false);
//      
//        jIncreaseBrightness.setVisible(false);
//        jDecreaseBrightness.setVisible(false);
//        jVoltageLevel.setVisible(false);
//        jOnOff.setVisible(false);
//        jClientIP.setVisible(false);
//        jIPLabel.setVisible(false);
//        jIPLabel.setText("");
//        jConnectServer.setEnabled(false);
//        jConnectServer.setVisible(false);
//        jServerClientComm.setVisible(false);
//        jReceivedChars.setVisible(false);
        
        //initVariable(" ", selectedPortIdentifier,9600);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jClientIP = new javax.swing.JLabel();
        jIPLabel = new javax.swing.JLabel();
        jConnectServer = new javax.swing.JButton();
        jServerIP = new javax.swing.JTextField();
        jServerIPLabel = new javax.swing.JLabel();
        jPortNoLabel = new javax.swing.JLabel();
        jPortNo = new javax.swing.JTextField();
        jDisconnectServer = new javax.swing.JButton();
        jCommPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jServerClientComm = new javax.swing.JTextArea();
        jControlPanel = new javax.swing.JPanel();
        jOnOff = new javax.swing.JToggleButton();
        jReceivedChars = new javax.swing.JLabel();
        jVoltageLevel = new javax.swing.JProgressBar();
        jIncreaseBrightness = new javax.swing.JButton();
        jDecreaseBrightness = new javax.swing.JButton();
        jProgressConnectServer = new javax.swing.JProgressBar();
        jMessage = new javax.swing.JTextField();
        jClientID = new javax.swing.JTextField();
        jSend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        jClientIP.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jClientIP.setText("Client IP :");
        jClientIP.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jIPLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jIPLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jConnectServer.setText("Connect Server");
        jConnectServer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jConnectServerMouseClicked(evt);
            }
        });

        jServerIP.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jServerIP.setText("Enter Server IP");
        jServerIP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jServerIPFocusGained(evt);
            }
        });

        jServerIPLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jServerIPLabel.setText("Server IP:");

        jPortNoLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPortNoLabel.setText(":");

        jPortNo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPortNo.setText("Enter Port No.");
        jPortNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPortNoFocusGained(evt);
            }
        });

        jDisconnectServer.setText("Disconnect Server");
        jDisconnectServer.setEnabled(false);
        jDisconnectServer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDisconnectServerMouseClicked(evt);
            }
        });

        jServerClientComm.setEditable(false);
        jServerClientComm.setColumns(20);
        jServerClientComm.setLineWrap(true);
        jServerClientComm.setRows(5);
        jServerClientComm.setWrapStyleWord(true);
        jServerClientComm.setEnabled(false);
        jScrollPane1.setViewportView(jServerClientComm);

        jOnOff.setText("Off");
        jOnOff.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jOnOffStateChanged(evt);
            }
        });
        jOnOff.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jOnOffItemStateChanged(evt);
            }
        });

        jReceivedChars.setBackground(new java.awt.Color(255, 255, 255));
        jReceivedChars.setToolTipText("");
        jReceivedChars.setOpaque(true);

        jVoltageLevel.setMaximum(4800);
        jVoltageLevel.setIndeterminate(true);

        jIncreaseBrightness.setText("Increase Brightness");
        jIncreaseBrightness.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        jDecreaseBrightness.setText("Decrease Brightness");
        jDecreaseBrightness.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jControlPanelLayout = new javax.swing.GroupLayout(jControlPanel);
        jControlPanel.setLayout(jControlPanelLayout);
        jControlPanelLayout.setHorizontalGroup(
            jControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jControlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jOnOff, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jIncreaseBrightness)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDecreaseBrightness)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jVoltageLevel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jReceivedChars, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jControlPanelLayout.setVerticalGroup(
            jControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jControlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jVoltageLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jDecreaseBrightness, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jIncreaseBrightness, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jOnOff, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jReceivedChars, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jCommPanelLayout = new javax.swing.GroupLayout(jCommPanel);
        jCommPanel.setLayout(jCommPanelLayout);
        jCommPanelLayout.setHorizontalGroup(
            jCommPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jCommPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jCommPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jProgressConnectServer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jControlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jCommPanelLayout.setVerticalGroup(
            jCommPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jCommPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressConnectServer, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jControlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMessageKeyPressed(evt);
            }
        });

        jSend.setText("Send");
        jSend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jSendMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jConnectServer, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                            .addComponent(jDisconnectServer))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(4, 4, 4)
                                    .addComponent(jClientIP))
                                .addComponent(jServerIPLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jIPLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                .addComponent(jServerIP))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPortNoLabel)
                            .addGap(26, 26, 26)
                            .addComponent(jPortNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jClientID, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSend))
                        .addComponent(jCommPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jClientIP)
                    .addComponent(jIPLabel))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jServerIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jServerIPLabel)
                    .addComponent(jPortNoLabel)
                    .addComponent(jPortNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDisconnectServer)
                    .addComponent(jConnectServer, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCommPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jClientID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSend))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
             char typed;
             typed= evt.getKeyChar();
             switch (typed)
             {
                 case 'i':
                 case 'I':
                     jIncreaseBrightness.doClick();
                     client.write(typed);
                     break;
                 case 'd':
                 case 'D':    
                     jDecreaseBrightness.doClick();
                     client.write(typed);
                     break;
             }
        
            // TODO add your handling code here:
    }//GEN-LAST:event_formKeyTyped

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here
      // System.out.println("clicked"); 
    }//GEN-LAST:event_formMouseClicked

    private void jConnectServerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jConnectServerMouseClicked
     jCommPanel.setVisible(true);
     jProgressConnectServer.setIndeterminate(true);
     Thread c = new Thread(){
         @Override
         public void run() 
         {
                    try
                    {
                        Socket client_socket= new Socket(jServerIP.getText(),Integer.parseInt(jPortNo.getText()));
                        jServerClientComm.append("Connection Established to Server "+jServerIP.getText()+"\n");
                        jControlPanel.setVisible(true);
                      //  System.out.println(client_socket.getInetAddress().toString());
                        client= new DimmerClient(client_socket);
                        PrintWriter out = new PrintWriter(client_socket.getOutputStream());
                        out.println(client_socket.getInetAddress()+" Connection Established \n");
                        out.flush();
                        out.println("myID");
                        out.flush();
                        //System.out.println("Asked for id");
                        Thread c = new Thread(client);
                        c.start();
                        jConnectServer.setEnabled(false);
                        jDisconnectServer.setEnabled(true);
                        jProgressConnectServer.setIndeterminate(false);
                        jControlPanel.setVisible(true);
                    }
                    catch(Exception e)
                    {
                        jServerClientComm.append("Server connection failed \n");
                        jProgressConnectServer.setIndeterminate(false);
                    }
         }
        };
     c.start();
  
    }//GEN-LAST:event_jConnectServerMouseClicked

    private void jServerIPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jServerIPFocusGained
                jServerIP.selectAll();        // TODO add your handling code here:
    }//GEN-LAST:event_jServerIPFocusGained

    private void jPortNoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPortNoFocusGained
                jPortNo.selectAll();        // TODO add your handling code here:
    }//GEN-LAST:event_jPortNoFocusGained

    private void jDisconnectServerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDisconnectServerMouseClicked
    try
    {
        client.client_socket.close();
        client.run_thread=false;
        jServerClientComm.append("Client disconnected from server \n");
        jConnectServer.setEnabled(true);
        jDisconnectServer.setEnabled(false);
    }
    catch(IOException e)
    {
        jServerClientComm.append("Cannot disconnect from server \n");
    }// TODO add your handling code here:
    }//GEN-LAST:event_jDisconnectServerMouseClicked

    private void jOnOffItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jOnOffItemStateChanged
        if(graphics_update == false)
        {
            if(jOnOff.isSelected())
            {
                // System.out.println("Client prepared to write 'o'");
                jServerClientComm.append("Client -> Server : 'Turn On Switch' \n");
                client.write('o');
                jOnOff.setText("On");
            }
            else
            {
                // System.out.println("Client prepared to write 'f'");
                jServerClientComm.append("Client -> Server : 'Turn Off Switch' \n");
                client.write('f');
                jOnOff.setText("Off");
            }
        }
        else
        graphics_update=false;// TODO add your handling code here:
    }//GEN-LAST:event_jOnOffItemStateChanged

    private void jOnOffStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jOnOffStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jOnOffStateChanged

    private void jSendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSendMouseClicked
            sendMessage();
    }//GEN-LAST:event_jSendMouseClicked
    
    private void sendMessage()
    {
        String msg = "notifyClient[";
            msg+=jClientID.getText();
            msg+="](";
            msg+=jMessage.getText();
            msg+=")";
            System.out.println("Msg="+msg);
            client.write(msg);
            jMessage.selectAll();
            jMessage.requestFocus();
    }
    private void jMessageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMessageKeyPressed
    if(evt.getKeyChar()=='\n')
    {
        jSend.doClick();
        sendMessage();
    }// TODO add your handling code here:
    }//GEN-LAST:event_jMessageKeyPressed

    public static void updateGUI(String status_report)
    {
           
         //  System.out.println("status_report received by client: "+ status_report);
           status_report = status_report.replace("[", ",");
           status_report = status_report.replace("]", ",");
           String[] status = status_report.split(",");
           if(status[0].contains("true") && !jOnOff.isSelected())
           {
               graphics_update =true;
                jOnOff.setSelected(true);
                jOnOff.setText("On");
           }
           else
           if(status[0].contains("false") && jOnOff.isSelected())
           {
               graphics_update =true;
               jOnOff.setSelected(false);
               jOnOff.setText("Off");
           }
           
           int vl=Integer.parseInt(status[1]);
           int vl_max=Integer.parseInt(status[2]);
           jVoltageLevel.setMaximum(vl_max);
           if(vl>vl_max)
           {
               jVoltageLevel.setIndeterminate(true);
           }
           else
           {
               double v =220.0- (vl * 220/vl_max);
               jVoltageLevel.setValue(vl);
               jVoltageLevel.setIndeterminate(false);
               jVoltageLevel.setString(Double.toString(v));
           }
     //      System.out.println("Graphics  Updated");   
    }
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DimmerClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DimmerClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DimmerClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DimmerClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DimmerClientGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField jClientID;
    private javax.swing.JLabel jClientIP;
    private javax.swing.JPanel jCommPanel;
    private javax.swing.JButton jConnectServer;
    private javax.swing.JPanel jControlPanel;
    public static javax.swing.JButton jDecreaseBrightness;
    private javax.swing.JButton jDisconnectServer;
    public static javax.swing.JLabel jIPLabel;
    public static javax.swing.JButton jIncreaseBrightness;
    private javax.swing.JTextField jMessage;
    public static javax.swing.JToggleButton jOnOff;
    private javax.swing.JTextField jPortNo;
    private javax.swing.JLabel jPortNoLabel;
    private javax.swing.JProgressBar jProgressConnectServer;
    public static javax.swing.JLabel jReceivedChars;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jSend;
    public static javax.swing.JTextArea jServerClientComm;
    private javax.swing.JTextField jServerIP;
    private javax.swing.JLabel jServerIPLabel;
    public static javax.swing.JProgressBar jVoltageLevel;
    // End of variables declaration//GEN-END:variables


     
    
}
