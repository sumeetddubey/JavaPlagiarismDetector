package algorithms.gst;

/**
 * @author sumeetdubey
 * GST token class. Every object has the token value, a boolean indicating if it is marked or not and its 
 * location in the string
 */
public class GSTToken {
//	Instance Variables
	private String value;
	private boolean marked;
	private int location;
	
	
	/**
	 * Class constructor
	 * @param value
	 * @param marked
	 * @param location
	 */
	public GSTToken(String value, boolean marked, int location) {
		this.value = value;
		this.marked = marked;
		this.location = location;
	}

//	Getters and setters
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
}
