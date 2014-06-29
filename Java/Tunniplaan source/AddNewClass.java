package edunet.virukol.tunniplaan;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class AddNewClass extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new AddClassFragment()).commit();

        if(prefs.getString("ClassCreatorTarget", "").equalsIgnoreCase("ADD")){
            setTitle(R.string.addClass);
        }
        else if(prefs.getString("ClassCreatorTarget", "").equalsIgnoreCase("EDIT")){
            setTitle(R.string.editClass);
        }





    }
}