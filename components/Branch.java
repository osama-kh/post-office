package components;

import java.awt.Color;
import java.awt.Graphics;
import java.beans.beancontext.BeanContext;
import java.util.*;

/**
 * 
 * @author osama khawaled 319118717
 * @author hosam mkawe 211988191
 *
 */


public class Branch extends Observable implements Node, Runnable,Cloneable {
	private static int counter=0;
	private int branchId;
	private String branchName;
	protected ArrayList <Package> unsafeListPackages = new ArrayList<Package>();
	protected List<Package> listPackages = unsafeListPackages; //Collections.synchronizedList(unsafeListPackages);
	protected ArrayList <Truck> listTrucks = new ArrayList<Truck>();
	private Point hubPoint;
	private Point branchPoint;
	protected boolean threadSuspend = false;
	private ArrayList<Observable> observableArray = new ArrayList<Observable>();
	private Queue<Integer> workingQueue = new LinkedList<Integer>();


	public Branch() {
		this("Branch "+counter);
	}

	
	public Branch(String branchName) {
		this.branchId=counter++;
		this.branchName=branchName;
		System.out.println("\nCreating "+ this);
	}

	public Branch(String branchName, List<Package> plist, ArrayList<Truck> tlist) {
		this.branchId=counter++;
		this.branchName=branchName;
		addPackages(plist);
		addTrucks(tlist);
	}


	public Branch(Branch other)
	{
		if(this != other)
		{
			this.branchId = other.branchId;
			this.branchName = other.branchName;
			this.listPackages = new ArrayList<>(listPackages);
			this.listTrucks = new ArrayList<>(listTrucks);
			this.hubPoint = new Point(hubPoint);
			this.branchPoint = new Point(branchPoint);
			this.observableArray = new ArrayList<>(observableArray);
		}
	}






	public void printBranch() {
		System.out.println("\nBranch name: "+branchName);
		System.out.println("Packages list:");
		for (Package pack: listPackages)
			System.out.println(pack);
		System.out.println("Trucks list:");
		for (Truck trk: listTrucks)
			System.out.println(trk);
	}
	
	
	public synchronized void addPackage(Package pack) {
		listPackages.add(pack);
		setChanged();
		notifyObservers(pack);
	}

	public void addTruck(Truck trk) {
		listTrucks.add(trk);
	}

	public synchronized void addPackages(List<Package> plist) {
		for (Package pack: plist)
			listPackages.add(pack);
	}
	
	
	public void addTrucks(ArrayList<Truck> tlist) {
		for (Truck trk: tlist)
			listTrucks.add(trk);
	}


	@Override
	public String toString() {
		return "Branch " + branchId + ", branch name:" + branchName + ", packages: " + listPackages.size()
				+ ", trucks: " + listTrucks.size();
	}

	
	@Override
	public synchronized void  collectPackage(Package p) {
		for (Truck v : listTrucks) {
			if (v.isAvailable()) {
				synchronized(v) {
					v.notify();
				}
				v.collectPackage(p);
				return;
			}
		}
	}

	@Override
	public synchronized void deliverPackage(Package p) {
		for (Truck v : listTrucks) {
			if (v.isAvailable()) {
				synchronized(v) {
					v.notify();
				}
				v.deliverPackage(p);
				return;
			}
		}	
	}

	@Override
	public void work() {	
	
	}

	
	private boolean arePackagesInBranch() {
		for(Package p: listPackages) {
			if (p.getStatus() == Status.BRANCH_STORAGE)
				return true;
		}
		return false;
	}
	
	public void paintComponent(Graphics g, int y, int y2) {
		if (arePackagesInBranch())
			g.setColor(new Color(0,0,153));
		else
			g.setColor(new Color(51,204,255));
   		g.fillRect(20, y, 40, 30);
   		
   		g.setColor(new Color(0,102,0));
   		g.drawLine(60, y+15, 1120, y2);
   		branchPoint = new Point(60,y+15);
   		hubPoint = new Point(1120,y2);
	}

	@Override
	public void run() {
		while(true) {
		    synchronized(this) {
                while (threadSuspend)
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    }
			synchronized(this) {
				for (Package p: listPackages) {
						if (p.getStatus()==Status.CREATION) {
							collectPackage(p);
						}
						if (p.getStatus()==Status.DELIVERY) {
							deliverPackage(p);
						}
				}
			}
		}
	}

	@Override
	public Object clone() {
		return new Branch(this);
	}
	public void upgrade()
	{
		setBranchId(counter++);
		setListTrucks(listTrucks);
	}

	//getter and setter
	public synchronized List <Package> getPackages(){
		return this.listPackages;
	}
	public ArrayList <Truck> getTrucks(){
		return this.listTrucks;
	}
	public Point getHubPoint() {
		return hubPoint;
	}
	public Point getBranchPoint() {
		return branchPoint;
	}
	public int getBranchId() {
		return branchId;
	}
	public String getName() {
		return branchName;
	}
	public ArrayList<Truck> getListTrucks() {
		return listTrucks;
	}
	public synchronized void setSuspend() {
		threadSuspend = true;
	}
	public synchronized void setResume() {
		threadSuspend = false;
		notify();
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public void setListTrucks(ArrayList<Truck> listTrucks) {
		this.listTrucks = listTrucks;
	}
	public void setHubPoint(Point hubPoint) {
		this.hubPoint = hubPoint;
	}
	public void setBranchPoint(Point branchPoint) {
		this.branchPoint = branchPoint;
	}

}
