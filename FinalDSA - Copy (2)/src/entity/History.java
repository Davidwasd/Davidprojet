/*
Author: Tneh Kai Qing
*/


package entity;

/**
 *
 * @author user
 */
public class History {
    private String timestamp; // Store the timestamp of the event
    private String action; // Store the type of event (e.g., "Student Added", "Group Removed")
    private String description; // Store a description of the event
    private String group;

    public History(String timestamp, String action, String description, String group) {
        this.timestamp = timestamp;
        this.action = action;
        this.description = description;
        this.group = group;
    }

    public History() {
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "History{" + "timestamp=" + timestamp + ", action=" + action + ", description=" + description + ", group=" + group + '}';
    }

    
    
}
