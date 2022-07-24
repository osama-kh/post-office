package program;

import components.Hub;


/**
 * 
 * @author osama khawaled 319118717
 * @author hosam mkawe 211988191
 *
 */
public class originator {
    Hub state;

    /**
	*functions to set state in 
	*memento and to get the state from it
     */

    public Memento state_to_Memento(){
        return new Memento(state);
    }

    public void get_State_of_Memento(Memento Memento){
        state = Memento.getState();
    }
    
    
    //getters and setters
    public void setState(Hub state){
    	this.state = state;
    }
    
    public Hub getState(){
    	return state;
    }
}
