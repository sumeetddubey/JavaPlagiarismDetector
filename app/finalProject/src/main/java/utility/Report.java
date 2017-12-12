package utility;

/**
 * Each layer will create an object of Report for itself that includes all data sent to front-end
 * @Author Jialin
 */

public class Report {
	
	public enum ComparisonLayer {
		HASHCODE,
		FUNCTION_SIGNATURE,
		AST
	}
    private ComparisonLayer layer;   // one of the comparator layers as defined above   
    private float score;   // computed similarity score
    private String message; // different per layer


    /**
     * constructors
     */
    public Report(ComparisonLayer layer, float score, String message) {
        this.layer = layer;
        this.score = score;
        this.message = message;
    }

    /**
     * getters and setters
     */
    public float getScore() {
        return score;
    }

    public ComparisonLayer getLayer() {
        return layer;
    }

    public String getMessage() {
        return message;
    }
}
