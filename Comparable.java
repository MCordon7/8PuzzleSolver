import java.util.Comparator;

public class Comparable implements Comparator<Node> {
	String type;
	//Used for sorting the queue in Uniform Cost search
	@Override
	public int compare(Node n1, Node n2) {
		if(type == "UniformCost") {
			if(n1.getCost() > n2.getCost()) {
				return -1;
			}
			if(n1.getCost() < n2.getCost()) {
				return 1;
			}
			return 0;
		} else if(type == "BestFirst") {
			if(n1.getDiff() > n2.getDiff()) {
				return -1;
			}
			if(n1.getDiff() < n2.getDiff()) {
				return 1;
			}
			return 0;
		} else if(type == "A*") {
			if(n1.getASTAR() > n2.getASTAR()) {
				return -1;
			}
			if(n1.getASTAR() < n2.getASTAR()) {
				return 1;
			}
			return 0;
		}
		
		return 0;
	}

	public void setType(String type) {
		this.type = type;
	}

}
