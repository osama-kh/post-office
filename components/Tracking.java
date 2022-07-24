package components;

public class Tracking {
	public final int time;
	public final Node node;
	public final Status status;

/**
 * 
 * @author osama khawaled 319118717
 * @author hosam mkawe 211988191
 *
 */
	
	public Tracking(int time, Node node, Status status) {
		super();
		this.time = time;
		this.node = node;
		this.status = status;
	}

	@Override
	public String toString() {
		String name = (node==null)? "Customer" : node.getName();
		return time + ": " + name + ", status=" + status;
	}

	
	
}
