package program;
import components.*;
import components.Package;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;


/**
 * 
 * @author osama khawaled 319118717
 * @author hosam mkawe 211988191
 *
 */

public class Customer implements Runnable {
    static int customer_id;
    Address address;
    boolean packs=true;
    ArrayList<Package> packages;
    MainOffice mainOffice;
    int packforcustmer = 2;


    /**
     * customer constructor :
     *  add new address
     *  install all fields.
     */
    public Customer(MainOffice mainOffice){

        customer_id++;
        Random number = new Random();
        this.address = new Address(number.nextInt(MainOffice.getHub().getBranches().size()), number.nextInt(999999)+100000);
        this.packages=new ArrayList<>();
        this.mainOffice=mainOffice;
    }
    
   

    
    public synchronized void newPackage(){
    	
    	/**
    	 * newPackage:
    	 *generate new package
    	 */
    	
        Hub hub=MainOffice.getHub();
        Random num = new Random();
        Package pack;
        Branch branch;
        Priority priority=Priority.values()[num.nextInt(3)];
        Address start = this.address;
        Address end = generate_address();

        
        switch (num.nextInt(3)){
            case 0:
                pack = new SmallPackage(priority,  start, end, num.nextBoolean() );
                branch = hub.getBranches().get(start.zip);
                branch.addPackage(pack);
                pack.setBranch(branch);
                break;
            case 1:
            	
                pack = new StandardPackage(priority,  start, end, num.nextFloat()+(num.nextInt(9)+1));
                branch = hub.getBranches().get(start.zip);
                branch.addPackage(pack);
                pack.setBranch(branch);
                break;
                
            case 2:
            	
                pack=new NonStandardPackage(priority,  start, end,  num.nextInt(1000), num.nextInt(500), num.nextInt(400));
                hub.addPackage(pack);
                break;
                
            default:
                pack=null;
        }

        
        
        
        this.packages.add(pack);
        this.mainOffice.getPackages().add(pack);
    }

    
    
    /**
     * generate_address:
     * generate new address
     *  update the fields.
     *
     */
    public Address generate_address()
    {
    	ArrayList<Customer> customer = new ArrayList<>(this.mainOffice.getCustomers());
    	customer.remove(this);
    	Random r = new Random();
    	int random=r.nextInt(customer.size());
    	return customer.get(random).getAddress();
    }
    
    
    @Override
    
    public void run() {

        Random num=new Random();
        int i;
        for(i=0;i<packforcustmer;i++) {
            try {
                Thread.sleep(1000 * num.nextInt(4)+2);
                        newPackage();
                System.out.println("**************************** Pakage" +i+ " has been Created for "+customer_id+ "****************************");
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        
        
        
        System.out.println("****************************"+i+"*****************************");
        while (i==packforcustmer && packs){
            try {
                Thread.sleep(1000 * 10);

                packs_checker();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        mainOffice.delete_Custemr(this);

    }

    

    
    
    
    
    //getters and setters
    public static int getCustomer_id() {
		return customer_id;
	}
	public static void setCustomer_id(int customer_id) {
		Customer.customer_id = customer_id;
	}
	public boolean isPacks() {
		return packs;
	}
	public void setPacks(boolean packs) {
		this.packs = packs;
	}
	public ArrayList<Package> getPackages() {
		return packages;
	}
	public void setPackages(ArrayList<Package> packages) {
		this.packages = packages;
	}
	public MainOffice getMainOffice() {
		return mainOffice;
	}
	public void setMainOffice(MainOffice mainOffice) {
		this.mainOffice = mainOffice;
	}
	public int getPackagePerC() {
		return packforcustmer;
	}
	public void setPackagePerC(int packagePerC) {
		packforcustmer = packagePerC;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Address getAddress(){
    	return address;
    }
	
	
	
	public void packs_checker() {
		/**
		 * packs_checker:
		 * to check if packages arrived to its destination
		 */
		ReadWriteLock locker = mainOffice.getLock();
		Path file = mainOffice.getFileName();
		String p_status;
		int p_id;
		try {
			locker.readLock().lock();
			Scanner read = new Scanner(file);
			
			while (read.hasNextLine() && packs) {
				String info = read.nextLine();
				p_status = info.split(":")[2].substring(1);
				p_id = Integer.parseInt(info.split("id: ")[1].substring(0, 4));
				
				for (Package pack : packages) {
					if (p_id == pack.getPackageID() && p_status.equals(Status.DELIVERED.toString())) {
						packages.remove(pack);
					}
				}
				if (packages.isEmpty())
					packs = false;
				
			}
			read.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			locker.readLock().unlock();
		}
	}
}
