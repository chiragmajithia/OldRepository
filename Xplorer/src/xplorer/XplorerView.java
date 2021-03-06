/*
 * XplorerView.java
 */

package xplorer;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import gnu.io.*;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * The application's main frame.
 */
public class XplorerView extends FrameView {

    Dimension size=new Dimension();
    protected HashMap map=new HashMap();
    protected CommPortIdentifier selectedPortIdentifier;
    JControl control;
    public XplorerView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        jProgressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    jProgressBar.setVisible(true);
                    jProgressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    jProgressBar.setVisible(false);
                    jProgressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    jProgressBar.setVisible(true);
                    jProgressBar.setIndeterminate(false);
                    jProgressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = XplorerApp.getApplication().getMainFrame();
            aboutBox = new XplorerAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        XplorerApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jCommPorts = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jPopulate = new javax.swing.JButton();
        jBaudRate = new javax.swing.JComboBox();
        jConnect = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        jProgressBar = new javax.swing.JProgressBar();

        jCommPorts.setName("jCommPorts"); // NOI18N
        jCommPorts.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCommPortsItemStateChanged(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(xplorer.XplorerApp.class).getContext().getResourceMap(XplorerView.class);
        jLabel1.setText(resourceMap.getString("jLabelCommPorts.text")); // NOI18N
        jLabel1.setName("jLabelCommPorts"); // NOI18N

        jPopulate.setText(resourceMap.getString("jPopulate.text")); // NOI18N
        jPopulate.setName("jPopulate"); // NOI18N
        jPopulate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPopulateMouseClicked(evt);
            }
        });

        jBaudRate.setEditable(true);
        jBaudRate.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2400", "4800", "9600", "14400", "19200", "28800", "38400", "custom" }));
        jBaudRate.setSelectedIndex(2);
        jBaudRate.setEnabled(false);
        jBaudRate.setName("jBaudRate"); // NOI18N

        jConnect.setText(resourceMap.getString("jConnect.text")); // NOI18N
        jConnect.setName("jConnect"); // NOI18N
        jConnect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jConnectMouseClicked(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText(resourceMap.getString("jLBaudRate.text")); // NOI18N
        jLabel2.setName("jLBaudRate"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jBaudRate, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCommPorts, javax.swing.GroupLayout.Alignment.LEADING, 0, 128, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPopulate, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                    .addComponent(jConnect, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jCommPorts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPopulate, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBaudRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(100, Short.MAX_VALUE))
        );

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(xplorer.XplorerApp.class).getContext().getActionMap(XplorerView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jProgressBar.setName("jProgressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 319, Short.MAX_VALUE)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(jProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jPopulateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPopulateMouseClicked
        jCommPorts.removeAllItems();
        map.clear();
        populate();
        jConnect.setEnabled(true);
        jBaudRate.setEditable(true);
        jBaudRate.setEnabled(true);
        System.out.println("baud rate"+(Integer.parseInt(jBaudRate.getSelectedItem().toString())+10));
        // TODO add your handling code here:
    }//GEN-LAST:event_jPopulateMouseClicked

    private void jCommPortsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCommPortsItemStateChanged
        jProgressBar.setIndeterminate(false);
        jProgressBar.setString("");
        jProgressBar.setVisible(false);
        statusAnimationLabel.setIcon(idleIcon);
        busyIconTimer.stop();        // TODO add your handling code here:
    }//GEN-LAST:event_jCommPortsItemStateChanged

    private void jConnectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jConnectMouseClicked

        System.out.println(map.get(jCommPorts.getSelectedItem().toString()));
        selectedPortIdentifier=(CommPortIdentifier)map.get(jCommPorts.getSelectedItem().toString());
        control = new JControl(jCommPorts.getName(),selectedPortIdentifier,Integer.parseInt(jBaudRate.getSelectedItem().toString()));
        control.setAlwaysOnTop(true);
        control.setVisible(true);
        // Start obj=new Start(jCommPorts.getSelectedItem().toString(),selectedPortIdentifier,(Integer.parseInt(jBaudRate.getSelectedItem().toString())));
       // jStart.insertTab(jCommPorts.getSelectedItem().toString(), idleIcon, obj, null,jStart.getComponentCount());
        // jStart.setSelectedIndex(jStart.getComponentCount()-1);
        //JFrame mainFrame = DataAquisitionSystemApp.getApplication().getMainFrame();
        //mainFrame.setExtendedState(mainFrame.MAXIMIZED_BOTH);        // TODO add your handling code here:
    }//GEN-LAST:event_jConnectMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JComboBox jBaudRate;
    public javax.swing.JComboBox jCommPorts;
    public javax.swing.JButton jConnect;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JButton jPopulate;
    javax.swing.JProgressBar jProgressBar;
    public javax.swing.JPanel mainPanel;
    public javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    public javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
    protected void populate()
        {
            Thread p=new Thread(){
            @Override
                    public void run(){
                        int i=0;
                        Enumeration pList = CommPortIdentifier.getPortIdentifiers();
                        System.out.println("pList="+pList);
                        while (pList.hasMoreElements())
                        {

                            CommPortIdentifier cpi = (CommPortIdentifier)pList.nextElement();
                            if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL)
                            {
                                 map.put(cpi.getName(),cpi);
                                 jCommPorts.setEnabled(true);
                                 jCommPorts.addItem(cpi.getName());
                            }
                        }
                        }
                 };
                 p.start();
                 jProgressBar.setVisible(true);
                 jProgressBar.setIndeterminate(true);
                 jProgressBar.setString("Searching Ports");
                 jProgressBar.setStringPainted(true);
                 busyIconTimer.start();

        }
}
