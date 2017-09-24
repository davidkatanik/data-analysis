package projekt.domain;

public class NativeCountry implements Attribute {
	public String nativeCountry;

	public NativeCountry(String nativeCountry) {
		this.nativeCountry = nativeCountry;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nativeCountry == null) ? 0 : nativeCountry.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NativeCountry other = (NativeCountry) obj;
		if (nativeCountry == null) {
			if (other.nativeCountry != null)
				return false;
		} else if (!nativeCountry.equals(other.nativeCountry))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nativeCountry;
	}

}
