package edunet.virukol.tunniplaan;

import android.content.SharedPreferences;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Tunniplaan {

    int AmountOfClasses = 0;
    int originalClassesCount;

    //9:22 started restructure of Tunniplaan

    ArrayList<List<Class>> Tunnid = new ArrayList<List<Class>>(5);


    //List<Class> Tunnid = new ArrayList<Class>();

    List<String> ClassNames = new ArrayList<String>();
    List<String> GroupsNames = new ArrayList<String>();

    //Function that checks if the group exist in the list, if yes - returns ID
    int groupExist(String gruppiNimi) {
        int id = 1;
        for (String gruppinimetus : GroupsNames) {
            if (gruppiNimi.equals(gruppinimetus)) {
                return id;
            } else id++;
        }
        return 0;
    }

    //Function that checks if the group exist in the list and returns ID, if not - adds it
    int getGroupID(String gruppiNimi) {
        int id = 1;
        for (String gruppinimetus : GroupsNames) {
            if (gruppiNimi.equals(gruppinimetus)) {
                return id;
            } else id++;
        }
        GroupsNames.add(gruppiNimi);
        return id;
    }

    //Function with input of string "ClassName" and output Class' ID
    int getClassID(String TundiNimi) {
        int id = 1;
        for (String nimetus : ClassNames) {
            if (TundiNimi.equals(nimetus)) {
                return id;
            } else id++;
        }
        ClassNames.add(TundiNimi);
        return id;
    }

    //function for returning a list of strings containing all the faculties (format: "RDIR", "RDKR", "RDER", etc)
    List<String> getFaculties() {
        List<String> teaduskonnad = new ArrayList<String>();
        for (String gruppinimetus : GroupsNames) {
            if (!teaduskonnad.contains(gruppinimetus.substring(0, 4))) {
                teaduskonnad.add(gruppinimetus.substring(0, 4));
            }
        }
        Collections.sort(teaduskonnad);
        return teaduskonnad;
    }

    List<String> getGroupNumbers(String teaduskond) {
        List<String> ruhmanumbrid = new ArrayList<String>();
        for (String gruppinimetus : GroupsNames) {
            if (teaduskond.equals(gruppinimetus.substring(0, 4))) {
                ruhmanumbrid.add(gruppinimetus.substring(4, 6));
            }
        }
        Collections.sort(ruhmanumbrid);
        return ruhmanumbrid;
    }

    ArrayList<Class> getFilteredClasses(String GroupNeeded, int DayNeeded, int EvennessNeeded, SharedPreferences prefs, Spinner GroupID) {
        ArrayList<Class> filteredClasses = new ArrayList<Class>();
        ArrayList<Class> sortedClasses = new ArrayList<Class>();

        int GroupNeededID = this.groupExist(GroupNeeded);

        if (GroupNeededID == 0) {
            GroupID.setSelection(0);
        } else {

            for (Class Tund : this.Tunnid.get(DayNeeded - 1)) {
                if (Tund.showUp == 1 && (Tund.PaarisPaaritu == 6 || Tund.PaarisPaaritu == EvennessNeeded) && Tund.GroupsIDs.contains(GroupNeededID)) {
                    filteredClasses.add(Tund);
                }
            }


            for (int paar = 1; paar < 6; paar++) { //sort Classes by PaariNumber using simple for cycle
                for (Class Urok : filteredClasses) {
                    if (Urok.PaariNumber == paar) {
                        sortedClasses.add(Urok);
                    }
                }
            }
            filteredClasses.clear();


            if (prefs.getString("lockedGroup", "none").equals(GroupNeeded)) {
                //Apply personal preferences

                ArrayList<Class> FilteredPersonally = new ArrayList<Class>();

                for (Class Tund : sortedClasses) {
                    //loads of empty IF bodies, but it's made so that upgrading the application would be easier with other user preferences

                    if (prefs.getBoolean("hide_est", false) && Tund.ClassName.toUpperCase().contains("EESTI")) {
                        //del this tund
                    } else if (prefs.getString("my_foreign_lang", "English").equals("English") && Tund.ClassName.toUpperCase().contains("SAKSA")) {
                        //del this tund
                    } else if (prefs.getString("my_foreign_lang", "English").equals("German") && Tund.ClassName.toUpperCase().contains("INGLISE")) {
                        //del this tund
                    } else {
                        FilteredPersonally.add(Tund);
                    }
                }


                return FilteredPersonally;

            }
        }


        return sortedClasses;
    }


}