package org.geoint.terpene.task.history;

import java.io.Serializable;

/**
 * <p>An error message that was raised during a task run</p>
 * 
 * @author mdgsies
 */
public class TaskRunError implements Serializable {

    private final static long serialVersionUID = 1L;
    private String message;
    private ErrorSeverity severity;
    private String stackTrace;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(ErrorSeverity severity) {
        this.severity = severity;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public enum ErrorSeverity {

        WARNING,
        FATAL;
    }
}
