
package entity;

import entity.students;
import adt.CircularArrayQueue;

public class TeamInfo {
    private String programme;
    private String group;
    private String type;
    private String teamName;
    private String description;
    private int maxNum;
    private CircularArrayQueue<students> studentQueue;
    
    public TeamInfo(String programme, String group, String type ,String teamName, String description, int maxNum) {
        this.programme = programme;
        this.group = group;
        this.type = type;
        this.teamName = teamName;
        this.description = description;
        this.maxNum = maxNum;
        this.studentQueue = new CircularArrayQueue<>();
    }
    

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    
    

    public CircularArrayQueue<students> getStudentQueue() {
        return studentQueue;
    }

    public void setStudentQueue(CircularArrayQueue<students> studentQueue) {
        this.studentQueue = studentQueue;
    }
    
    
    
}