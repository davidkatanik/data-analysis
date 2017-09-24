package cviceni3;

public class RootPredicate {
	String attribute;
	String value = "";
	Long positives = 0L;
	Long negatives = 0L;

	public RootPredicate(String attribute) {
		super();
		this.attribute = attribute;
	}

	@Override
	public String toString() {
		return "RootPredicate [attribute=" + attribute + ", value=" + value + ", positives=" + positives
				+ ", negatives=" + negatives + "]";
	}

	
}
