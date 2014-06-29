package edunet.virukol.tunniplaan;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class AddClassFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.addclass);
        Preference save_class = (Preference) findPreference("save_class");
        save_class.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference arg0) {

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = prefs.edit();

                int Evenness = 0;

                if (prefs.getBoolean("class_creator_even", false) && prefs.getBoolean("class_creator_odd", false)) {
                    Evenness = 6;
                } else if (prefs.getBoolean("class_creator_odd", false)) {
                    Evenness = 3;
                } else if (prefs.getBoolean("class_creator_even", false)) {
                    Evenness = 2;
                } else {
                    Toast.makeText(getActivity(), getString(R.string.error_evenness), Toast.LENGTH_SHORT).show();
                    return false;
                }

                if (prefs.getString("class_creator_classname", "").length() < 1) {
                    Toast.makeText(getActivity(), getString(R.string.error_classname), Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (prefs.getString("class_creator_room", "").length() < 1) {
                    Toast.makeText(getActivity(), getString(R.string.error_room), Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (prefs.getString("class_creator_faculty", "").length() != 4) {
                    Toast.makeText(getActivity(), getString(R.string.error_faculty), Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (prefs.getString("class_creator_group", "").length() != 2) {
                    Toast.makeText(getActivity(), getString(R.string.error_group), Toast.LENGTH_SHORT).show();
                    return false;
                }


                String newClass = prefs.getString("class_creator_classname", "");
                newClass += "(" + Evenness + "?" + prefs.getString("class_creator_room", "666") + ":" + prefs.getString("class_creator_faculty", "FAIL") + prefs.getString("class_creator_group", "42") + ")" + prefs.getString("class_creator_day", "1") + prefs.getString("class_creator_paarinumber", "1") + 1 + "*" + prefs.getString("class_creator_commentary", "");

                String was = prefs.getString("CustomClasses", "") + newClass + "/";
                editor.putString("CustomClasses", was).apply();

                editor.putString("class_creator_classname", "").apply();
                editor.putBoolean("class_creator_even", false).apply();
                editor.putBoolean("class_creator_odd", false).apply();
                editor.putString("class_creator_room", "").apply();
                editor.putString("class_creator_faculty", "").apply();
                editor.putString("class_creator_group", "").apply();
                editor.putString("class_creator_day", "1").apply();
                editor.putString("class_creator_paarinumber", "1").apply();
                editor.putString("class_creator_commentary", "").apply();

                if(prefs.getString("ClassCreatorTarget", "").equalsIgnoreCase("EDIT")){
                    int uniqID = prefs.getInt("EditedGenuineClass", 0);
                    if(uniqID > 0){
                        String AlteredClasses = prefs.getString("AlteredClassesIDs", "");
                        editor.putString("AlteredClassesIDs", AlteredClasses + uniqID + "/").apply();
                    }
                    else{
                        String oli = prefs.getString("CustomClasses", "");
                        String deleteThis = prefs.getString("deleteCustomClass", "");
                        String newest = oli.replace(deleteThis, "");
                        editor.putString("CustomClasses",newest).apply();
                    }

                    editor.putString("deleteCustomClass", "").apply();
                    editor.putInt("EditedGenuineClass", 0).apply();
                }
                getActivity().finish();
                return true;
            }
        });


    }

    @Override
    public void onPause() {


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if(prefs.getString("ClassCreatorTarget", "").equalsIgnoreCase("EDIT")){
       SharedPreferences.Editor editor = prefs.edit();
            editor.putString("class_creator_classname", "").apply();
            editor.putBoolean("class_creator_even", false).apply();
            editor.putBoolean("class_creator_odd", false).apply();
            editor.putString("class_creator_room", "").apply();
            editor.putString("class_creator_faculty", "").apply();
            editor.putString("class_creator_group", "").apply();
            editor.putString("class_creator_day", "1").apply();
            editor.putString("class_creator_paarinumber", "1").apply();
            editor.putString("class_creator_commentary", "").apply();
        }

        super.onPause();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(Color.rgb(185, 185, 185));

        return view;
    }





}
