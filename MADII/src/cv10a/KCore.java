package cv10a;

import java.util.ArrayList;
import java.util.List;

public class KCore {

	public static void calculate(int[][] data, int k) {
		boolean changed = false;
		int c = 0;

		do {
			changed = false;
			for (int i = 0; i < data.length; i++) {
				int nodeDegree = 0;

				for (int j = i; j < data.length; j++) {
					nodeDegree += data[i][j];
				}
				
				if (nodeDegree > 0 && nodeDegree < k) {
					purgeNode(data, i);
					changed = true;
					System.out.println(++c);
				}
			}
		} while (changed);

		StringBuilder sb = new StringBuilder();
		StringBuilder sbNodes = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data.length; j++) {
				sb.append(data[i][j] + " ");
				if (data[i][j] == 1 && i < j) {
					sbNodes.append("Edge: ");
					sbNodes.append(i);
					sbNodes.append(" ");
					sbNodes.append(j);
					sbNodes.append("\n");
				}
			}
			System.out.println(sb.toString());
			sb.setLength(0);
		}
		System.out.println(sbNodes.toString());
	}

	private static void purgeNode(int[][] data, int i) {
		for (int j = 0; j < data.length; j++) {
			data[i][j] = data[j][i] = 0;
		}
	}
	
	
    public static List<Integer> kcore(List<Instances> instances, int k) {
        List<Instances> copy = new ArrayList<>(instances);
        List<Integer> result = new ArrayList<>();
        boolean removedVertex = false;

        while (!removedVertex && copy.size() > 0) {
            List<Instances> instancesCopy = new ArrayList<>(copy);
            for (Instances inst : instancesCopy) {
                if (inst.getListNeighbours().size() < k) {
                    copy.remove(inst);
                    removedVertex = true;
                }
            }
        }

        copy.forEach(i -> result.add(i.getId() + 1));
        return result;
    }

}
