package program;

import components.Hub;

/**
 * 
 * @author osama khawaled 319118717
 * @author hosam mkawe 211988191
 *
 */

public class Memento {
    Hub state;

    /**
     * main class of memento
     */
    public Memento(Hub state){
        this.state = new Hub(state);
    }
    //state getter and setters
    public Hub getState(){
        return state;
    }
	public void setState(Hub state) {
		this.state = state;
	}
}
