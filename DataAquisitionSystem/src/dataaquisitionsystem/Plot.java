/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Start.java
 *
 * Created on Nov 8, 2010, 12:00:58 AM
 */

package dataaquisitionsystem;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop.Action;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Chirag
 */
public class Plot extends javax.swing.JPanel
{

    /** Creates new form Start */
    /** Creates new form Start */
    Draw obj;
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jClose = new javax.swing.JButton();
        jScrollPlot = new java.awt.ScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jnum_h_seg = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jnum_v_seg = new javax.swing.JSlider();
        jlabelnum_h_seg = new javax.swing.JLabel();
        jlabelnum_v_seg = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jsec_frame = new javax.swing.JSlider();
        jlabelsec_frame = new javax.swing.JLabel();
        jlabel5 = new javax.swing.JLabel();
        jv_pix = new javax.swing.JSlider();
        jlabelv_pix = new javax.swing.JLabel();
        jlabel6 = new javax.swing.JLabel();
        jh_pix = new javax.swing.JSlider();
        jlabelh_pix = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButtonHold = new javax.swing.JButton();
        jButtonRefresh = new javax.swing.JButton();

        jFrame1.setName("jFrame1"); // NOI18N

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setAlignmentX(0.0F);
        setName("Form"); // NOI18N
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(dataaquisitionsystem.DataAquisitionSystemApp.class).getContext().getResourceMap(Plot.class);
        jClose.setIcon(resourceMap.getIcon("jClose.icon")); // NOI18N
        jClose.setMaximumSize(new java.awt.Dimension(23, 23));
        jClose.setMinimumSize(new java.awt.Dimension(23, 23));
        jClose.setName("jClose"); // NOI18N
        jClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCloseMouseClicked(evt);
            }
        });

        jScrollPlot.setName("jScrollPlot"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        jnum_h_seg.setMinimum(1);
        jnum_h_seg.setOrientation(javax.swing.JSlider.VERTICAL);
        jnum_h_seg.setSnapToTicks(true);
        jnum_h_seg.setValue(10);
        jnum_h_seg.setInverted(true);
        jnum_h_seg.setName("jnum_h_seg"); // NOI18N
        jnum_h_seg.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jnum_h_segStateChanged(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jnum_v_seg.setMinimum(1);
        jnum_v_seg.setOrientation(javax.swing.JSlider.VERTICAL);
        jnum_v_seg.setSnapToTicks(true);
        jnum_v_seg.setValue(10);
        jnum_v_seg.setInverted(true);
        jnum_v_seg.setName("jnum_v_seg"); // NOI18N
        jnum_v_seg.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jnum_v_segStateChanged(evt);
            }
        });

        jlabelnum_h_seg.setText(resourceMap.getString("jlabelnum_h_seg.text")); // NOI18N
        jlabelnum_h_seg.setName("jlabelnum_h_seg"); // NOI18N

        jlabelnum_v_seg.setText(resourceMap.getString("jlabelnum_v_seg.text")); // NOI18N
        jlabelnum_v_seg.setName("jlabelnum_v_seg"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(42, 42, 42))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jnum_h_seg, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                                .addGap(7, 7, 7)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jlabelnum_h_seg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jlabelnum_v_seg))
                                .addGap(10, 10, 10)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jnum_v_seg, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel3))))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jnum_v_seg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jnum_h_seg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jlabelnum_h_seg)
                        .addGap(60, 60, 60)
                        .addComponent(jlabelnum_v_seg)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setName("jPanel2"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jsec_frame.setMaximum(3600);
        jsec_frame.setMinimum(10);
        jsec_frame.setOrientation(javax.swing.JSlider.VERTICAL);
        jsec_frame.setSnapToTicks(true);
        jsec_frame.setValue(600);
        jsec_frame.setInverted(true);
        jsec_frame.setName("jsec_frame"); // NOI18N
        jsec_frame.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jsec_frameStateChanged(evt);
            }
        });

        jlabelsec_frame.setText(resourceMap.getString("jlabelsec_frame.text")); // NOI18N
        jlabelsec_frame.setName("jlabelsec_frame"); // NOI18N

        jlabel5.setText(resourceMap.getString("jlabel5.text")); // NOI18N
        jlabel5.setName("jlabel5"); // NOI18N

        jv_pix.setMaximum(9000);
        jv_pix.setMinimum(300);
        jv_pix.setOrientation(javax.swing.JSlider.VERTICAL);
        jv_pix.setSnapToTicks(true);
        jv_pix.setValue(650);
        jv_pix.setInverted(true);
        jv_pix.setName("jv_pix"); // NOI18N
        jv_pix.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jv_pixStateChanged(evt);
            }
        });

        jlabelv_pix.setText(resourceMap.getString("jlabelv_pix.text")); // NOI18N
        jlabelv_pix.setName("jlabelv_pix"); // NOI18N

        jlabel6.setText(resourceMap.getString("jlabel6.text")); // NOI18N
        jlabel6.setName("jlabel6"); // NOI18N

        jh_pix.setMaximum(9000);
        jh_pix.setMinimum(900);
        jh_pix.setOrientation(javax.swing.JSlider.VERTICAL);
        jh_pix.setSnapToTicks(true);
        jh_pix.setValue(1200);
        jh_pix.setInverted(true);
        jh_pix.setName("jh_pix"); // NOI18N
        jh_pix.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jh_pixStateChanged(evt);
            }
        });

        jlabelh_pix.setText(resourceMap.getString("jlabelh_pix.text")); // NOI18N
        jlabelh_pix.setName("jlabelh_pix"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jlabelsec_frame, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jsec_frame, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jlabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jlabelv_pix, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jv_pix, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jh_pix, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(jlabelh_pix, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jlabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jlabel5)
                    .addComponent(jlabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jsec_frame, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                    .addComponent(jv_pix, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                    .addComponent(jh_pix, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlabelsec_frame)
                    .addComponent(jlabelv_pix)
                    .addComponent(jlabelh_pix, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setAlignmentX(0.5F);
        jLabel5.setName("jLabel5"); // NOI18N

        jButtonHold.setText(resourceMap.getString("jButtonHold.text")); // NOI18N
        jButtonHold.setName("jButtonHold"); // NOI18N
        jButtonHold.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonHoldMouseClicked(evt);
            }
        });

        jButtonRefresh.setText(resourceMap.getString("jButtonRefresh.text")); // NOI18N
        jButtonRefresh.setName("jButtonRefresh"); // NOI18N
        jButtonRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonRefreshMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPlot, javax.swing.GroupLayout.DEFAULT_SIZE, 1053, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonHold, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(307, 307, 307)
                        .addComponent(jClose, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jClose, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonHold, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPlot, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        JFrame mainFrame = DataAquisitionSystemApp.getApplication().getMainFrame();
        mainFrame.setExtendedState(mainFrame.MAXIMIZED_BOTH);        // TODO add your handling code here:
        System.out.println("Focused Gaained");
        obj.update();
    }//GEN-LAST:event_formComponentShown

    private void jCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCloseMouseClicked
        System.out.println("active= "+Thread.activeCount());
        Runtime r=Runtime.getRuntime();
        System.out.println(r.freeMemory());
        r.gc();
        removeAll();
        repaint();// TODO add your handling code here:
        this.getParent().remove(this);
        System.out.println("active= "+Thread.activeCount());
        //        this.getParent().repaint();
}//GEN-LAST:event_jCloseMouseClicked

    private void jnum_h_segStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jnum_h_segStateChanged
        // TODO add your handling code here:
        handle();
    }//GEN-LAST:event_jnum_h_segStateChanged

    private void jnum_v_segStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jnum_v_segStateChanged
        // TODO add your handling code here:
        handle();
    }//GEN-LAST:event_jnum_v_segStateChanged

    private void jsec_frameStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jsec_frameStateChanged
        // TODO add your handling code here:
        handle();
    }//GEN-LAST:event_jsec_frameStateChanged

    private void jv_pixStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jv_pixStateChanged
        // TODO add your handling code here:
        handle();
    }//GEN-LAST:event_jv_pixStateChanged

    private void jh_pixStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jh_pixStateChanged
        // TODO add your handling code here:
        handle();
    }//GEN-LAST:event_jh_pixStateChanged

    private void jButtonHoldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonHoldMouseClicked
        obj.hold=!obj.hold;
        if(obj.hold)
            jButtonHold.setText("Play");
        else
            jButtonHold.setText("Hold");
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonHoldMouseClicked

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
        System.out.println("Focused Gaained");
        obj.update();
    }//GEN-LAST:event_formFocusGained

    private void jButtonRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonRefreshMouseClicked
        obj.update();
    }//GEN-LAST:event_jButtonRefreshMouseClicked

    void handle()
    {
        obj.updateInit(jv_pix.getValue(), jh_pix.getValue(),obj.maxY, jsec_frame.getValue(), jnum_h_seg.getValue(),jnum_v_seg.getValue());
        jlabelnum_h_seg.setText(Integer.toString(jnum_h_seg.getValue()));
        jlabelnum_v_seg.setText(Integer.toString(jnum_v_seg.getValue()));
        jlabelsec_frame.setText(Integer.toString(jsec_frame.getValue()));
        jlabelv_pix.setText(Integer.toString(jv_pix.getValue()));
        jlabelh_pix.setText(Integer.toString(jh_pix.getValue()));


    }
     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButtonHold;
    public javax.swing.JButton jButtonRefresh;
    private javax.swing.JButton jClose;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private java.awt.ScrollPane jScrollPlot;
    public javax.swing.JSlider jh_pix;
    private javax.swing.JLabel jlabel5;
    private javax.swing.JLabel jlabel6;
    private javax.swing.JLabel jlabelh_pix;
    private javax.swing.JLabel jlabelnum_h_seg;
    private javax.swing.JLabel jlabelnum_v_seg;
    private javax.swing.JLabel jlabelsec_frame;
    private javax.swing.JLabel jlabelv_pix;
    public javax.swing.JSlider jnum_h_seg;
    public javax.swing.JSlider jnum_v_seg;
    public javax.swing.JSlider jsec_frame;
    public javax.swing.JSlider jv_pix;
    // End of variables declaration//GEN-END:variables

  public Plot(java.awt.Frame parent,Draw obj1)
    {
      obj=obj1;
    //  System.out.println("plot");
      initComponents();
      JRootPane r=(JRootPane) parent.getComponent(0);
      JLayeredPane l=r.getLayeredPane();
      JPanel cp=(JPanel) l.getComponent(0);
      JPanel mp=(JPanel) cp.getComponent(0);
      JTabbedPane tp=(JTabbedPane) mp.getComponent(0);
      //System.out.println("parent.getComponent(0)"+mp.getComponent(0).getName());
      jScrollPlot.add(obj);
      this.setBorder(BorderFactory.createLineBorder(Color.yellow));
     // this.setBackground(Color.BLACK);
      tp.insertTab("Graph Plot", null,this, null, tp.getComponentCount());
      this.jButtonHold.setText("Hold");
      this.jButtonRefresh.setText("Refresh");

    }

    


}
