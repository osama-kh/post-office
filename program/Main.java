package program;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author osama khawaled 319118717
 * @author hosam mkawe 211988191
 *
 */

public class Main extends JFrame implements ActionListener {
    
	static final long serialVersionUID = 1L;
     PostSystemPanel panel;
	 JMenu clone, Restore, Report;
	 JMenuItem[] menu_items;
	 JMenuBar menu_Bar;


   public static void main(String[]args) {
	   Main fr = new Main();
	   fr.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	   fr.setSize(1200,700);
	   fr.setVisible(true);
   }

   /**
    * Main constructor :
    * generate new post tracking system frame 
    */
   public Main() {
   	   super("Post tracking system");
   	   panel = new PostSystemPanel(this);
   	   add(panel);
   	   panel.setVisible(true);
   	   menu_adder();


   }
   /**
    * menu_adder:
    * to set the menubuttons and add them
    */
   private void menu_adder() {
	   
	   menu_Bar = new JMenuBar();
	   clone = new JMenu("CloneBranch");
	   Restore = new JMenu("Restore");
	   Report = new JMenu("Report");
	   menu_Bar.add(clone);
	   menu_Bar.add(Restore);
	   menu_Bar.add(Report);
	   menu_items = new JMenuItem[3];
	   menu_items[0]= new JMenuItem("CloneBranch");
	   clone.add(menu_items[0]);
	   menu_items[1]= new JMenuItem("Restore");
	   Restore.add(menu_items[1]);
	   menu_items[2]= new JMenuItem("Report");
	   Report.add(menu_items[2]);
	   setJMenuBar(menu_Bar);
	   for(int i=0;i<3;i++)
		   menu_items[i].addActionListener(this);
   }
   
   

	/**
	 * actionperformed:
	 * to perform the action that the button should do
	 *
	 */
	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == menu_items[0] )
			panel.CloneBranch();
		else if( e.getSource() == menu_items[1] )
			panel.Restore();
		else if( e.getSource() == menu_items[2] )
			panel.ShowReports();
			}

}
