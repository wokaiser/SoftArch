package interfaces;

public interface IStatus {
    /**
     * Get the logged text.
     * @return Text as String
     */
    String getText();
    /**
     * Get the logged errors.
     * @return Error text as String
     */
    String getError();    
    /**
     * Clear the logged text.
     */
    void clearText();
    /**
     * Clear the logged errors.
     */
    void clearError();    
    /**
     * Add new text
     * @param The text to add.
     */
    void addText(String input);
    /**
     * Add new error
     * @param The error to add.
     */
    void addError(String input);
    /**
     * Get the number of errors (number of addError(..) calls)
     * @return The number of errors calls.
     */
    int getErrorCount();
    /**
     * Get the number of text (number of addText(..) calls)
     * @return The number of text calls.
     */
    int getTextCount();
    /**
     * clear the text and the error log.
     */
    void clear();
    /**
     * copy the text and errors from another Status object to this object.
     * @param The status which to copy to this
     */
    void copyStatus(IStatus from);
    /**
     * Move the text and errors from another Status object to this object.
     * This method clears the log of the other object in difference to copyStatus(..)
     * @param the status which to move to this.
     */
    void moveStatus(IStatus from);
    /**
     * Check if any text was logged
     * @return true if text was logged and false if not
     */
    boolean textExist();
    /**
     * Check if any error was logged
     * @return true if errors was logged and false if not
     */
    boolean errorExist();
}
