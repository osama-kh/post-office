package program;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * 
 * @author osama khawaled 319118717
 * @author hosam mkawe 211988191
 *
 */
public class CreatePostSystemlDialog extends JDialog  implements  ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel p1,p2;
    private JButton ok, cancel;
    private JLabel lbl_branch, lbl_truck, lbl_pack;
    private JSlider sl_branch, sl_truck, sl_pack;
    private PostSystemPanel rs;

    public CreatePostSystemlDialog(Main parent, PostSystemPanel pan, String title) {
    	/**
    	 *CreatePostSystemlDialog:
    	 *creat a new dialog and install its variables
    	 *
    	 */
    	super((Main)parent,title,true);
    	rs = pan;

    	setSize(600,400);
	
		setBackground(new Color(100,230,255));
		p1 = new JPanel();
		p2 = new JPanel();
	
		p1.setLayout(new GridLayout(6,1,10,5));
		lbl_branch = new JLabel("Number of branches",JLabel.CENTER);
		p1.add(lbl_branch);
		lbl_truck = new JLabel("Number of trucks per branch",JLabel.CENTER);
		lbl_pack = new JLabel("Number of packages",JLabel.CENTER);
		
		sl_branch = new JSlider(1,10);
		sl_branch.setMajorTickSpacing(1);
		sl_branch.setMinorTickSpacing(1);
		sl_branch.setPaintTicks(true);
		sl_branch.setPaintLabels(true);
		p1.add(sl_branch);
		
		p1.add(lbl_truck);
		sl_truck = new JSlider(1,10);
		sl_truck.setMajorTickSpacing(1);
		sl_truck.setMinorTickSpacing(1);
		sl_truck.setPaintTicks(true);
		sl_truck.setPaintLabels(true);
		p1.add(sl_truck);
	
		p1.add(lbl_pack);
		sl_pack = new JSlider(2,20);
		sl_pack.setMajorTickSpacing(2);
		sl_pack.setMinorTickSpacing(1);
		sl_pack.setPaintTicks(true);
		sl_pack.setPaintLabels(true);
		p1.add(sl_pack);
		
		p2.setLayout(new GridLayout(1,2,5,5));
		ok=new JButton("OK");
		ok.addActionListener(this);
		ok.setBackground(Color.lightGray);
		p2.add(ok);		
		cancel=new JButton("Cancel");
		cancel.addActionListener(this);
		cancel.setBackground(Color.lightGray);
		p2.add(cancel);
		
		setLayout(new BorderLayout());
		add("North" , p1);
		add("South" , p2);
    }
    
    

    public void actionPerformed(ActionEvent e) {
 		if(e.getSource() == ok){
		    rs.createNewPostSystem(sl_branch.getValue(),sl_truck.getValue(),sl_pack.getValue());
		    setVisible(false);
		}
		else 
		    setVisible(false);
    }
}
