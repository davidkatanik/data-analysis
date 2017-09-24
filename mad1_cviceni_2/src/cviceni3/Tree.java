package cviceni3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Tree {
	boolean isLeaf;
	Map<String, String> rule = new TreeMap<>();
	List<Entry<String,String>> others = new ArrayList<>();
	boolean value;
	List<Tree> nodes = new ArrayList<>();
	int numberOfLeafs = 0;
	
	
	
	
	void print (int numberOfWhitespaces) {
		rule.entrySet().forEach(e -> {
			createSpaces(numberOfWhitespaces);
			System.out.println(e.getKey()+" "+e.getValue());
		});
		if (isLeaf){
			createSpaces(numberOfWhitespaces);
			System.out.println("[number of leafs " +numberOfLeafs+"]");
		}
		for (int i = 0; i < nodes.size(); i++) {
			if (i == 1) {
				if (!others.isEmpty()) {
					others.forEach(e -> {
						createSpaces(numberOfWhitespaces);
						System.out.print(e.getKey()+"-"+e.getValue());
					});
					System.out.println();
				}
				nodes.get(i).print(numberOfWhitespaces+4);
			}
			else {
				nodes.get(i).print(numberOfWhitespaces+4);
			}
		}
	}

	private void createSpaces(int numberOfWhitespaces) {
		for (int i = 0; i < numberOfWhitespaces; i++) {
			System.out.print(" ");
		}
	}
	
}
