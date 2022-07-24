package components;


/**
 * 
 * @author osama khawaled 319118717
 * @author hosam mkawe 211988191
 *
 */

public class SmallPackage extends Package {
	private boolean acknowledge;

	
	public SmallPackage(Priority priority, Address senderAddress,Address destinationAdress, boolean acknowledge){
		super(priority, senderAddress,destinationAdress);
		this.acknowledge=acknowledge;
		System.out.println("Creating " + this);

	}
	
	
	public boolean isAcknowledge() {
		return acknowledge;
	}
	
	
	public void setAcknowledge(boolean acknowledge) {
		this.acknowledge = acknowledge;
	}
	
	
	@Override
	public String toString() {
		return "SmallPackage ["+ super.toString() +", acknowledge=" + acknowledge + "]";
	}
	
		
}
