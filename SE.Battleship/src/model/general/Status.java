package model.general;

/** 
 * The Status class can be used to log information about the program flow.
 * @author Dennis Parlak
 */
public class Status {
    private String text;
    private String error;
    private int textCount;
    private int errorCount;
    
    /**
     * Creates a new Status object.
     */
    public Status() {
        text = "";
        error = "";
        textCount = 0;
        errorCount = 0;
    }
    
    /**
     * Get the logged text.
     * @return Text as String
     */
    public String getText() {
        return text;
    }
    
    /**
     * Get the logged errors.
     * @return Error text as String
     */
    public String getError() {
        return error;
    }
    
    /**
     * Clear the logged text.
     */
    public void clearText() {
        textCount = 0;
        text = "";
    }
    
    /**
     * Clear the logged errors.
     */
    public void clearError() {
        errorCount = 0;
        error = "";
    }
    
    /**
     * Add new text
     * @param The text to add.
     */
    public void addText(String input) {
        textCount++;
        text += " ";
        text += input;
    }
    
    /**
     * Add new error
     * @param The error to add.
     */
    public void addError(String input) {
        errorCount++;
        error += " ";
        error += input;
    }
    
    /**
     * Get the number of errors (number of addError(..) calls)
     * return The number of errors calls.
     */
    public int getErrorCount() {
        return errorCount;
    }
    
    /**
     * Get the number of text (number of addText(..) calls)
     * return The number of text calls.
     */
    public int getTextCount() {
        return textCount;
    }
    
    /**
     * clear the text and the error log.
     */
    public void clear() {
        clearText();
        clearError();
    }
    
    /**
     * copy the text and errors from another Status object to this object.
     */
    public void copyStatus(Status from) {
        if (0 < from.getTextCount()) {
            this.addText(from.getText().substring(1));
        }
        if (0 < from.getErrorCount()) {
            this.addError(from.getError().substring(1));
        }
    }
    
    /**
     * Move the text and errors from another Status object to this object.
     * This method clears the log of the other object in difference to copyStatus(..)
     */
    public void moveStatus(Status from) {
        this.copyStatus(from);
        from.clear();
    }
    
    /**
     * Check if any text was logged
     * @return true if text was logged and false if not
     */
    public boolean textExist() {
        return (0 < textCount) ? true : false;
    }
    
    /**
     * Check if any error was logged
     * @return true if errors was logged and false if not
     */
    public boolean errorExist() {
        return (0 < errorCount) ? true : false;
    }
}
