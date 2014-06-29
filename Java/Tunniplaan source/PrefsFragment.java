package edunet.virukol.tunniplaan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PrefsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefs);

        Preference show_classes = (Preference) findPreference("show_classes");
        show_classes.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference arg0) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("AlteredClassesIDs", "").apply();
                getActivity().finish();
                return true;
            }
        });


        Preference clear_custom_classes= (Preference) findPreference("clear_custom_classes");
        clear_custom_classes.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference arg0) {


                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle(R.string.question_delete_all_custom_classes);
                alert.setMessage(R.string.question_delete_all_custom_classes_summary);

                final TextView input = new TextView(getActivity());
                alert.setView(input);
                alert.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Accepted

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("CustomClasses", "").apply();
                        editor.putString("AlteredClassesIDs", "").apply();
                        getActivity().finish();
                    }

                }

                );

                alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.

                    }
                });

                AlertDialog alertToShow = alert.create();

                alertToShow.show();

                return false;
            }
        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(Color.rgb(185, 185, 185));

        return view;
    }


}
