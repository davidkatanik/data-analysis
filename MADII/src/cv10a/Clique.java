package cv10a;

import java.util.ArrayList;
import java.util.List;

public class Clique {

	private List<Integer> elements;
	
	private void init() {
		elements = new ArrayList<>();
	}
	
 	public void addElement(int e) {
 		if (elements == null) {
 			init();
 		}
 		elements.add(e);
 	}
 	
 	public void removeElement(int e) {
 		if (elements == null) {
 			init();
 			return;
 		}
 		elements.remove(e);
 	}
 	
}
