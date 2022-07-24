package components;

import program.Customer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.swing.JPanel;


/**
 * 
 * @author osama khawaled 319118717
 * @author hosam mkawe 211988191
 *
 */
public class MainOffice implements Runnable, Observer {
	private static int clock=0;
	public static Hub hub;
	private ArrayList<Package> packages=new ArrayList<Package>();
	private JPanel panel;
	private int maxPackages;
	private boolean threadSuspend = false;
	private static MainOffice instance = null;
	private ArrayList<Customer> customers = new ArrayList<Customer>();
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	final private String file_name="tracking.txt";
	private File file=new File(file_name);
	private Path fileName = file.toPath();
	private int lines = 1;
	private String string=new String();
	private ArrayList<String>reports=new ArrayList<String>();
	private int trucksForBranch;

	/**
	 * MainOffice constructor :
	 * install variables 
	 */
	private MainOffice(int branches, int trucksForBranch, JPanel panel, int maxPack) {
		this.panel = panel;
		this.maxPackages = 4;
		this.trucksForBranch = trucksForBranch;
		addHub(trucksForBranch);
		addBranches(branches, trucksForBranch);



		int numOfCustomer = 2;
		for(int i=0;i<numOfCustomer;i++)
			customers.add(new Customer(this));

		System.out.println("\n\n========================== START ==========================");
	}

	/**
	 * getInstance:
	 * return an single MainOffice object
	 *
	 */
	public static MainOffice getInstance(int branches, int trucks_per_Branch, JPanel panel, int max_package) {
		if(instance == null)
		{
			synchronized (MainOffice.class)
			{
				if(instance == null)
				{
					instance = new MainOffice( branches, trucks_per_Branch, panel, max_package);
					
					return instance;
				}
			}
		}
		return instance;
	}

	//getters and setters
	public static Hub getHub() {
		return hub;
	}
	public static int getClock() {
		return clock;
	}
	public JPanel getPanel() {
		return panel;
	}
	public int getMaxPackages() {
		return maxPackages;
	}
	public ArrayList<Package> getPackages(){
		return this.packages;
	}
	public int getTrucksForBranch() {
		return trucksForBranch;
	}
	public Path getFileName() {
		return fileName;
	}
	public void setHub(Hub hub) {
		this.hub = hub;
	}
	public void setTrucksForBranch(int trucksForBranch) {
		this.trucksForBranch = trucksForBranch;
	}



	@Override
	public void run() {
		Thread hubThrad = new Thread(hub);
		hubThrad.start();
		for (Truck t : hub.listTrucks) {
			Thread trackThread = new Thread(t);
			trackThread.start();
		}
		for (Branch b: hub.getBranches()) {
			Thread branch = new Thread(b);
			for (Truck t : b.listTrucks) {
				Thread trackThread = new Thread(t);
				trackThread.start();
			}
			branch.start();
		}
		for(Customer c : customers)
			new Thread(c).start();

		while(true) {
		    synchronized(this) {
                while (threadSuspend)
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    }
			tick();
		}
		
	}
	
	
	public void printReport() {
		for (Package p: packages) {
			System.out.println("\nTRACKING " +p);
			for (Tracking t: p.getTracking())
				System.out.println(t);
		}
	}
	
	
	public String clockString() {
		String s="";
		int minutes=clock/60;
		int seconds=clock%60;
		s+=(minutes<10) ? "0" + minutes : minutes;
		s+=":";
		s+=(seconds<10) ? "0" + seconds : seconds;
		return s;
	}
	
	
	public void tick() {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(clockString());
		if (clock++%5==0 && maxPackages>0) {
			maxPackages--;
		}

		panel.repaint();
	}
	
	
	
	public void branchWork(Branch b) {
		for (Truck t : b.listTrucks) {
			t.work();
		}
		b.work();
	}
	
	
	public void addHub(int trucksForBranch) {
		hub=new Hub();
		for (int i=0; i<trucksForBranch; i++) {
			Truck t = new StandardTruck();
			hub.addTruck(t);
			t.addObserver(this);
		}
		Truck t=new NonStandardTruck();
		hub.addTruck(t);
		t.addObserver(this);
	}
	
	
	public void addBranches(int branches, int trucks) {
		for (int i=0; i<branches; i++) {
			Branch branch=new Branch();
			branch.addObserver(this);
			for (int j=0; j<trucks; j++) {
				Van v = new Van();
				branch.addTruck(v);
				v.addObserver(this);
			}
			hub.add_branch(branch);		
		}
	}
	public void addBranch(Branch newBranch, int trucks) {
		newBranch.addObserver(this);
		for (int j=0; j<trucks; j++) {
			Van v = new Van();
			newBranch.addTruck(v);
			v.addObserver(this);
		}
		hub.add_branch(newBranch);
	}
	
	

	public void addPackage() {
		Random r = new Random();
		Package p;
		Branch br;
		Priority priority=Priority.values()[r.nextInt(3)];
		Address sender = new Address(r.nextInt(hub.getBranches().size()), r.nextInt(999999)+100000);
		Address dest = new Address(r.nextInt(hub.getBranches().size()), r.nextInt(999999)+100000);

		switch (r.nextInt(3)){
		case 0:
			p = new SmallPackage(priority,  sender, dest, r.nextBoolean() );
			br = hub.getBranches().get(sender.zip);
			br.addPackage(p);
			p.setBranch(br); 
			break;
		case 1:
			p = new StandardPackage(priority,  sender, dest, r.nextFloat()+(r.nextInt(9)+1));
			br = hub.getBranches().get(sender.zip); 
			br.addPackage(p);
			p.setBranch(br); 
			break;
		case 2:
			p=new NonStandardPackage(priority,  sender, dest,  r.nextInt(1000), r.nextInt(500), r.nextInt(400));
			hub.addPackage(p);
			break;
		default:
			p=null;
			return;
		}
		
		this.packages.add(p);
		
	}
	
	
	public synchronized void setSuspend() {
	   	threadSuspend = true;
		for (Truck t : hub.listTrucks) {
			t.setSuspend();
		}
		for (Branch b: hub.getBranches()) {
			for (Truck t : b.listTrucks) {
				t.setSuspend();
			}
			b.setSuspend();
		}
		hub.setSuspend();
	}

	
	
	public synchronized void setResume() {
	   	threadSuspend = false;
	   	notify();
	   	hub.setResume();
		for (Truck t : hub.listTrucks) {
			t.setResume();
		}
		for (Branch b: hub.getBranches()) {
			b.setResume();
			for (Truck t : b.listTrucks) {
				t.setResume();;
			}
		}
	}


	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}

	public ReadWriteLock getLock() {

		return lock;
	}

	@Override
	public void update(Observable observable, Object o) {

		Package pack = (Package)o;
		lock.writeLock().lock();

		try
		{
			String report=""+lines+","+pack.getPackageID()+","+pack.getStatus().toString();
			string += String.valueOf(lines++) + "- Package ID: " + pack.getPackageID() + ", Package Status: " + pack.getStatus().toString() + "\n";
			Files.write(fileName, string.getBytes());
			reports.add(report);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		lock.writeLock().unlock();
	}



	public void delete_Custemr(Customer customer) {
		this.customers.remove(customer);
	}

	public ArrayList<String> getReports() {
		return reports;
	}


}

