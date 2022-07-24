package components;

/**
 * 
 * @author osama khawaled 319118717
 * @author hosam mkawe 211988191
 *
 */

public interface Node {
	public void collectPackage(Package p);
	public void deliverPackage(Package p);
	public void work();
	public String getName();
}
