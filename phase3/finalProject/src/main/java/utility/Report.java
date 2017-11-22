package utility;

/**
 * Each layer will create an object of Report for itself that includes all data sent to front-end
 * @Author Jialin
 */

public class Report {

    private String layer;   // "0", "1", or "2"
    private float score;   // computed similarity score
    private String message; // different per layer


    /**
     * constructors
     */
    public Report(){}

    public Report(String layer, float score, String message) {
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

    public void setScore(float score) {
        this.score = score;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
