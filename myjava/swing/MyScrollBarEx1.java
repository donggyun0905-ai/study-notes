package swing;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

public class MyScrollBarEx1 extends JFrame {
	
  JScrollPane jScrollPane1;
  JTextArea jTextArea1;

  public MyScrollBarEx1() {
    initSwingProperties();
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void initSwingProperties() {
    UIDefaults defaults = UIManager.getDefaults();
    defaults.put("ScrollBar.thumb", Color.red);
    defaults.put("ScrollBar.width", new Integer("20"));
  }

  public static void main(String[] args) {
	  try {
			// 1. 자바 내장 룩앤필
			 //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

			// 2. quaqua.jar : Quaqua Look and Feel
			//UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
			//JFrame.setDefaultLookAndFeelDecorated(true);

			// 3. liquidlnf.jar : Liquid Look and Feel
			UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");

			// 4. idw-gpl.jar : InfoNode Look and Feel
			 //UIManager.setLookAndFeel("net.infonode.gui.laf.InfoNodeLookAndFeel");

			// 5. JTattoo.jar : JTattoo Look and Feel
			//UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
			//UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		
	    }  catch (Exception e) { }

    MyScrollBarEx1 test = new MyScrollBarEx1();
    test.setSize(300,300);
    test.setVisible(true);
  }
  private void jbInit() throws Exception {
    jScrollPane1 = new JScrollPane();
    jTextArea1 = new JTextArea();
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    this.setForeground(Color.red);
    this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(jTextArea1, null);
  }
}







