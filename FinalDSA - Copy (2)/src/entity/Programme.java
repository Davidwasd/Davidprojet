// AUTHOR : ONG CHUN ZHAO

package entity;
import entity.*;
import adt.*;

public class Programme{
    
    private String programmeID;
    private String programmeName;
    private String programmeFac;
    private ArrayList<TutorialGroups> tutorialGroups;
    
    
    public Programme(String programmeID, String programmeName, String programmeFac) {
        this.programmeID = programmeID;
        this.programmeName = programmeName;
        this.programmeFac = programmeFac;
        tutorialGroups = new ArrayList<>();
    }


    public String getProgrammeID() {
        return programmeID;
    }

    public String getProgrammeName() {
        return programmeName;
    }

    public String getProgrammeFac() {
        return programmeFac;
    }

    public void setProgrammeID(String programmeID) {
        this.programmeID = programmeID;
    }

    public void setProgrammeName(String programmeName) {
        this.programmeName = programmeName;
    }
    
    public void setProgrammeFac(String programmeFac) {
        this.programmeFac = programmeFac;
    }
    
    public void addTutorialGroupToProgramme(TutorialGroups tut) {
        tutorialGroups.add(tut); // Add the TutorialGroup 'tut' to this Programme
    }

    @Override
    public String toString() {
        return "Programme = " + "Programme ID = " + programmeID + ", Programme Name = " + programmeName + ", Programme Faculty = " + programmeFac + "\n";
    }

    public ArrayList<TutorialGroups> getTutorialGroups() {
        return tutorialGroups;
    }
  
}
