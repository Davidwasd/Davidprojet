// AUTHOR : ONG CHUN ZHAO
package entity;

import adt.CircularArrayQueue;
import adt.CustomHashMap;

public class TutorialGroups {

    private String groupID;
    private String programme;
    private CustomHashMap<String, students> students;
    private int enrolledStudents;
    private int capacity;
    private CircularArrayQueue<TeamInfo> Team;

    public TutorialGroups(String groupID, String programme, CustomHashMap<String, students> students, int enrolledStudents, int capacity) {
        this.groupID = groupID;
        this.programme = programme;
        this.students = students;
        this.enrolledStudents = enrolledStudents;
        this.capacity = capacity;
        this.Team = new CircularArrayQueue<TeamInfo>();
    }

    public TutorialGroups() {
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }
    
    public void addStudent(students student) {
        students.put(student.getStudentID(), student);       
    }

    public CustomHashMap<String, students> getStudents() {
        return students;
    }

    public void setStudents(CustomHashMap<String, students> students) {
        this.students = students;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(int enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public CircularArrayQueue<TeamInfo> getTeam() {
        return Team;
    }

    public void setTeam(CircularArrayQueue<TeamInfo> Team) {
        this.Team = Team;
    }
    
    

    @Override
    public String toString() {
        return "TutorialGroups " + "Group ID = " + groupID + " ProgrammeID = " + programme + " Students = " + students + " EnrolledStudents = " + enrolledStudents + " Capacity = " + capacity ;
    }

    

    
}
