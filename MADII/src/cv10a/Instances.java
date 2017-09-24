package cv10a;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Instances {
    private int id;
    private List<Integer> listOfNeighbours = new ArrayList<>();
    private Map<Integer, Integer> weights;

    public Instances(int id, List<Integer> listOfNeighbours, Map<Integer, Integer> weights) {
        this.id = id;
        this.listOfNeighbours = listOfNeighbours;
        this.weights = weights;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getListNeighbours() {
        return listOfNeighbours;
    }

    public void setListNeighbours(List<Integer> listOfNeighbours) {
        this.listOfNeighbours = listOfNeighbours;
    }

    public Map<Integer, Integer> getWeight() {
        return weights;
    }

    public void setWeight(Map<Integer, Integer> weights) {
        this.weights = weights;
    }    
    
}
