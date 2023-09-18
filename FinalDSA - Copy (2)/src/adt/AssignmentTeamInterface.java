/*
Author: Oo Kai Jie
*/

package adt;

import entity.TeamInfo;
import entity.students;

public interface AssignmentTeamInterface<T> {
    
    void createAssignmentTeam(T newTeam);
    void removeAssignmentTeam(T team);
    void amendAssignmentTeam(T team);
    void addStudentToTeam(students student, TeamInfo team);
    void removeStudentFromTeam(T team);
    void filterAssignmentTeams(String keyword, String group);
    void listTeams(String group);
    void listStudentsByAssignmentTeam(T team);
    void generateReport(String group);

}

