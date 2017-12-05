package ui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author jialin
 * This class represents each line of upload files including the number of line
 */

@SuppressWarnings("restriction")
public class CodeLine {

    private SimpleIntegerProperty lineNumber;
    private SimpleStringProperty code;

    // constructors

    public CodeLine(){}

    public CodeLine(int lineNumber, String code) {
        this.lineNumber = new SimpleIntegerProperty(lineNumber);
        this.code = new SimpleStringProperty(code);
    }

    // getters and setters

    public int getLineNumber() {
        return lineNumber.get();
    }

    public String getCode() {
        return code.get();
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber.set(lineNumber);
    }

    public void setCode(String code) {
        this.code.set(code);
    }
}
