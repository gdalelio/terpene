package org.geoint.terpene.task.history;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>A task instance.</p>
 * 
 */
public class TaskRun implements Serializable {

    private final static long serialVersionUID = 1L;
    private String taskId;
    private String taskName;
    private double taskVersion;
    private Date scheduledTime;
    private TaskStatus status = TaskStatus.SCHEDULED;
    private Map<String, String> properties;
    private String containerId;
    private Date runTime;
    private long runDuration;
    private List<TaskRunError> errors;

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public List<TaskRunError> getErrors() {
        if (errors == null) {
            this.errors = new ArrayList<TaskRunError>();
        }
        return errors;
    }

    public void setErrors(List<TaskRunError> errors) {
        this.errors = errors;
    }

    public Map<String, String> getProperties() {
        if (properties == null) {
            properties = new HashMap<String, String>();
        }
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public long getRunDuration() {
        return runDuration;
    }

    public void setRunDuration(long runDuration) {
        this.runDuration = runDuration;
    }

    public Date getRunTime() {
        return runTime;
    }

    public void setRunTime(Date runTime) {
        this.runTime = runTime;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public double getTaskVersion() {
        return taskVersion;
    }

    public void setTaskVersion(double taskVersion) {
        this.taskVersion = taskVersion;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TaskRun other = (TaskRun) obj;
        if ((this.taskId == null) ? (other.taskId != null) : !this.taskId.equals(other.taskId)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.taskId != null ? this.taskId.hashCode() : 0);
        return hash;
    }

    public enum TaskStatus {

        SCHEDULED,
        RUNNING,
        SUCCESS,
        FAILED;
    }
}
