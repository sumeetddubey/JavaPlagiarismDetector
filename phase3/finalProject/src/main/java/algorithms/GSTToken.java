package algorithms;

public class GSTToken {
	private String value;
	private boolean marked;
	private int location;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public GSTToken(String value, boolean marked, int location) {
		this.value = value;
		this.marked = marked;
		this.location = location;
	}
}
