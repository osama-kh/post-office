package program;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author osama khawaled 319118717
 * @author hosam mkawe 211988191
 *
 */
public class Care
{

    private List<Memento> memento_list = new ArrayList<Memento>();


    public Memento get_memento(){
    	/**
    	 * get
    	 * func to get the last Memento .
    	 *
    	 */
    	Memento temp = memento_list.get(memento_list.size()-1);
        memento_list.remove(temp);
        return temp;
    }
//mementolist getter
    public List<Memento> getMementoList() {
        return memento_list;
    }

    public void add_memento(Memento state){
    	/**
    	 * add_memento:
    	 * add a memento to  memento_List.
    	 */
    	memento_list.add(state);
    }
}
