package edunet.virukol.tunniplaan;

import java.util.ArrayList;
import java.util.List;

public class Class {
    int Day;
    int PaariNumber;
    String ClassName = "";
    int ClassID;
    int PaarisPaaritu;
    String Room;
    List<String> Groups = new ArrayList<String>();
    List<Integer> GroupsIDs = new ArrayList<Integer>();
    int unique_ID;
    int ID_inList;
    String commentary = "";

    int showUp = 1;

    public Class(String TundInfo, int d, int c, Tunniplaan Raspisanie, int ID_in_List) {


        unique_ID = Raspisanie.AmountOfClasses;
        ID_inList = ID_in_List;
        //Here we give Class it's parameters upon creation
        //Basically just slicing the "TundInfo" string
        Day = d + 1;//Move the day counter from 0-4 to 1-5
        PaariNumber = c + 1;//Move the pair counter from 0-4 to 1-5

        //getting ClassName from input string TundInfo
        for (char charN : TundInfo.toCharArray()) {
            if (charN == 40) {
                break;
            } else {
                this.ClassName += charN;
            }
        }

        ClassID = Raspisanie.getClassID(ClassName);//Getting the ID of the class by using it's ClassName string

        TundInfo = TundInfo.replace(ClassName + "(", "");
        switch (TundInfo.toCharArray()[0])//Choose, whether class happens on even or uneven week (or both)
        {
            case '2':
                PaarisPaaritu = 2;
                break;
            case '3':
                PaarisPaaritu = 3;
                break;
            case '6':
                PaarisPaaritu = 6;
                break;
            default:
                PaarisPaaritu = 666;
                break;
        }
        TundInfo = TundInfo.substring(2);//Get rid of first 2 chars

        String temp = "";
        for (char charN : TundInfo.toCharArray()) {
            if (charN == 58) {
                break;
            } else {
                temp += charN;
            }
        }
        TundInfo = TundInfo.replace(temp + ":", "");

        Room = temp;

        temp = "";

        for (char charN : TundInfo.toCharArray()) {
            if (charN == 44) {
                Groups.add(temp);
                GroupsIDs.add(Raspisanie.getGroupID(temp));
                TundInfo = TundInfo.replace(temp + ",", "");
                temp = "";
            } else if (charN == 41) {
                Groups.add(temp);
                GroupsIDs.add(Raspisanie.getGroupID(temp));
                TundInfo = TundInfo.replace(temp + ")", "");
                break; //When the char is ")" meaning it's the end of the string TundInfo
            } else {
                temp += charN;
            }
        }
        commentary = TundInfo;//.substring(4);

    }

    public String toString() {

        String TundString = "";

        String gruppid = "";
        for (String grupp : Groups) {
            gruppid = gruppid + grupp + ",";
        }

        TundString = ClassName + "(" + PaarisPaaritu + "?" + Room + ":" + gruppid.substring(0, gruppid.length() - 1) + ")" + Day + PaariNumber + showUp + "*" + commentary;

        return TundString;
    }

    public List<String> getOtherGroups(String groupName) {
        //selectedGroupName + selectedGroupNumbers
        ArrayList<String> tempGruppid = new ArrayList<String>(this.Groups);

        tempGruppid.remove(groupName);
        return tempGruppid;
    }

    public void changeVisibility() {
        if (showUp == 1) {
            showUp = 0;
        } else {
            showUp = 1;
        }
    }

    public Class(String TundInfo, Tunniplaan Raspisanie, int IDinList, int uniqID) {

        //Here we give Class it's parameters upon creation
        //Basically just slicing the "TundInfo" string

        //getting ClassName from input string TundInfo
        for (char charN : TundInfo.toCharArray()) {
            if (charN == 40) {
                break;
            } else {
                this.ClassName += charN;
            }
        }

        ClassID = Raspisanie.getClassID(ClassName);//Getting the ID of the class by using it's ClassName string

        TundInfo = TundInfo.replace(ClassName + "(", "");
        switch (TundInfo.toCharArray()[0])//Choose, whether class happens on even or uneven week (or both)
        {
            case '2':
                PaarisPaaritu = 2;
                break;
            case '3':
                PaarisPaaritu = 3;
                break;
            case '6':
                PaarisPaaritu = 6;
                break;
            default:
                PaarisPaaritu = 666;
                break;
        }
        TundInfo = TundInfo.substring(2);//Get rid of first 2 chars

        String temp = "";
        for (char charN : TundInfo.toCharArray()) {
            if (charN == 58) {
                break;
            } else {
                temp += charN;
            }
        }
        TundInfo = TundInfo.replace(temp + ":", "");

        Room = temp;

        temp = "";

        for (char charN : TundInfo.toCharArray()) {
            if (charN == 44) {
                Groups.add(temp);
                GroupsIDs.add(Raspisanie.getGroupID(temp));
                TundInfo = TundInfo.replace(temp + ",", "");
                temp = "";
            } else if (charN == 41) {
                Groups.add(temp);
                GroupsIDs.add(Raspisanie.getGroupID(temp));
                TundInfo = TundInfo.replace(temp + ")", "");
                break; //When the char is ")" meaning it's the end of the string TundInfo
            } else {
                temp += charN;
            }
        }

        switch (TundInfo.toCharArray()[0]) {
            case '1':
                Day = 1;
                break;
            case '2':
                Day = 2;
                break;
            case '3':
                Day = 3;
                break;
            case '4':
                Day = 4;
                break;
            case '5':
                Day = 5;
                break;
            default:
                Day = 1;
                break;
        }
        switch (TundInfo.toCharArray()[1]) {
            case '1':
                PaariNumber = 1;
                break;
            case '2':
                PaariNumber = 2;
                break;
            case '3':
                PaariNumber = 3;
                break;
            case '4':
                PaariNumber = 4;
                break;
            case '5':
                PaariNumber = 5;
                break;
            default:
                PaariNumber = 1;
                break;
        }

        commentary = TundInfo.substring(4);

        switch (IDinList) {
            case 0:
                ID_inList = Raspisanie.Tunnid.get(Day - 1).size() + 1;
                break;
            default:
                ID_inList = IDinList;
        }

        switch (uniqID) {
            case 0:
                Raspisanie.AmountOfClasses++;
                unique_ID = Raspisanie.AmountOfClasses;
                break;
            default:
                unique_ID = uniqID;
        }

    }


}