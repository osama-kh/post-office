package components;

/**
 * 
 * @author osama khawaled 319118717
 * @author hosam mkawe 211988191
 *
 */

public class NonStandardPackage extends Package {
	private int width, length, height;


	public NonStandardPackage(Priority priority, Address senderAddress,Address destinationAdress,int width, int length, int height) {
			super( priority, senderAddress,destinationAdress);
			this.width=width;
			this.length=length;
			this.height=height;	
			System.out.println("Creating " + this);
	}
	
	@Override
	public String toString() {
		return "NonStandardPackage ["+super.toString() + ", width=" + width + ", length=" + length + ", height=" + height + "]";
	}
	
	//getters and setters
	public int getWidth() {
		return width;
	}
	public int getLength() {
		return length;
	}
	public int getHeight() {
		return height;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public void setHeight(int height) {
		this.height = height;
	}
}
