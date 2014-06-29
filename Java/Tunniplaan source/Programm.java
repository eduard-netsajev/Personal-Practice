package edunet.virukol.tunniplaan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class Programm extends ListActivity {

    //Added 18.10.2013
    ///////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////
    //DONE 18.10.2013 11:10
    //It probably will be more efficient to separate all 5 days in the Tunniplaan class
    //in order to reduce the search time while finding a classes for a day
    //Making it from find from 332 classes
    //to something like find from 66 classes

    //DONE 19.10.2013
    //onLongTouch listener for ClassRows with an ability to hide that class
    //from showing up to user. And then somehow make him able to show it back up
    //Not restricted to locked-group only

    //DONE 20.10.2013
    //User to be able to add classes on his own
    //And this one is working for all groups
    //Maybe even adding own groups?

    //DONE 26.10.2013
    //user-guide on his first launch or by pressing a Help option in the menu
    //Show the user how to swipe between days, weeks, how to hide and add classes
    //Hint at the end that user can setup some Settings in the Preference menu
    //like choose his foreign language or hide some layout details

    //TODO
    //Feedback form. Maybe just an ability to write me a message
    //in both anonymous and personalized ways
    //but get some type of ID so I can track who is writing me
    //without user's knowledge ofc
    //Maybe some device ID or randomly generated ID on the first launch of the program?
    //But somehow make sure they do not repeat and are real identification tools

    //sub-TODO
    //What about an ability to answer on the user feedback personally?
    //So that it works like messenger between me and the user
    //These two last could be used later in other projects

    //TODO
    //add statistics gathering
    //Can be easily done by using the Google Analytics tools
    //https://support.google.com/googleplay/android-developer/answer/3389759

    //TODO
    //It would be cool to make the pages SLIDE from one day to another
    //Implementation way - PageFragments
    ///////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////

    //Added 20.10.2013
    //DONE 25.10.2013
    //Add a Preferences Action button to erase all custom changes (string "CustomClasses" = "")
    //Demand a confirmation from user, warning him about the damage to custom library

    //Added 24.10.2013
    //DONE 24.10.2013
    //Make user to be able to add or change comments on the classes, both custom and standard

    //Added 26.10.2013
    //TODO
    //Make the group input in AddClass preference activity accepting more than 1 group

    //Init objects needed for using from inner functions (Singletons)
    Tunniplaan Raspisanie;// = new Tunniplaan();//creating new object of Tunniplaan
    protected final String textSource = "http://money.vnet.ee/tunniplaan52.txt";
    ListView ClassList;
    ArrayAdapter<String> numbersAdapt;
    ArrayAdapter<String> otdelAdapt;
    ArrayList<Class> ClassesThisDay = new ArrayList<Class>();
    int dayOfWeek;
    int evenOddWeek;
    ImageButton Lock;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    TextView DayLine;
    Spinner spinnerGroups;
    Spinner spinnerGroupsIDs;
    String TunniplaanRAW = "Faaail";
    String selectedGroupName;
    String selectedGroupNumbers;
    TextView yourGroup;
    Object mActionMode;
    ClassListArrayAdapter ListAdapter;
    int HelperStage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);

        DayLine = (TextView) findViewById(R.id.TextInformation);
        yourGroup = (TextView) findViewById(R.id.textViewGrupp);
        getOverflowMenu();

        //Find is it even or odd week, and current day

        Calendar today = new GregorianCalendar(Locale.UK);
        dayOfWeek = today.get(Calendar.DAY_OF_WEEK) - 1;//returns int Day of Week


        if (today.get(Calendar.WEEK_OF_YEAR) % 2 == 0) {
            evenOddWeek = 3;
        } else {
            evenOddWeek = 2;
        }

        PreferenceManager.setDefaultValues(this, R.xml.prefs, false);

    }

    //awful hack
    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onResume() {
        super.onResume();
        Raspisanie = new Tunniplaan();
        InputStream input;

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        spinnerGroups = (Spinner) findViewById(R.id.groups_spinner);
        spinnerGroupsIDs = (Spinner) findViewById(R.id.groupsID_spinner);

        spinnerGroups.setPadding(10, 0, 0, 0);
        spinnerGroupsIDs.setPadding(5, 0, 0, 0);

        numbersAdapt = new ArrayAdapter<String>(this, R.layout.custom_spinner_list);
        otdelAdapt = new ArrayAdapter<String>(this, R.layout.custom_spinner_list);

        numbersAdapt.setDropDownViewResource(R.layout.customer_spinner);
        otdelAdapt.setDropDownViewResource(R.layout.customer_spinner);

        ClassList = (ListView) findViewById(android.R.id.list);


        ListAdapter = new ClassListArrayAdapter(this, R.layout.classrow, R.id.textViewClassName, ClassesThisDay);

        spinnerGroups.setAdapter(otdelAdapt);
        spinnerGroupsIDs.setAdapter(numbersAdapt);

        ClassList.setAdapter(ListAdapter);
        ClassList.setLongClickable(true);
        ClassList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        Lock = (ImageButton) findViewById(R.id.lock);

        ClassList.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (mActionMode != null) {
                    return false;
                }


                ClassList.setItemChecked(position, true);
                mActionMode = startActionMode(mActionModeCallback);
                view.setSelected(true);


                return true;
            }
        });


        ClassList.setOnTouchListener(new OnSwipeTouchListener() {


            public void onSwipeTop() {

                if (mActionMode == null) {
                    changeEvenness(ListAdapter);
                    UpdateDayLine();
                }
            }

            public void onSwipeRight() {
                if (mActionMode == null) {
                    dayOfWeek--;
                    getDayOfWeek(ListAdapter);
                    UpdateDayLine();
                }
            }

            public void onSwipeLeft() {
                if (mActionMode == null) {

                    dayOfWeek++;
                    getDayOfWeek(ListAdapter);
                    UpdateDayLine();
                }
            }

            public void onSwipeBottom() {
                if (mActionMode == null) {

                    changeEvenness(ListAdapter);
                    UpdateDayLine();
                }
            }

        });


        Lock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                editor.putString("lockedGroup", "" + selectedGroupName + selectedGroupNumbers).apply();
                updateLockView();
                UpdateListView(ListAdapter, Raspisanie, dayOfWeek, evenOddWeek);
            }
        });

        yourGroup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String yourgroup = prefs.getString("lockedGroup", "none");
                if (yourgroup.length() > 4) {
                    spinnerGroups.setSelection(otdelAdapt.getPosition(yourgroup.substring(0, 4)));
                    spinnerGroupsIDs.setSelection(numbersAdapt.getPosition(yourgroup.substring(4, 6)));
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.No_group), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Here the program execution starts


        URL textUrl;
        try {
            textUrl = new URL(textSource);
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(textUrl.openStream()));
            String StringBuffer;
            String stringText = "";
            while ((StringBuffer = bufferReader.readLine()) != null) {
                stringText += StringBuffer;
            }
            bufferReader.close();
            TunniplaanRAW = stringText;
            editor.putString("TunniplaanTXT", TunniplaanRAW.substring(1)).apply(); //Delete first character if the gotten file from internet, because of UTF-8 DOM

        } catch (IOException e2) {

            if (prefs.getBoolean("firstrun", true)) {

                // Do first run stuff here then set 'firstrun' as false
                // using the following line to edit/commit prefs
                try {

                    AssetManager assetManager = getAssets();
                    input = assetManager.open("tunniplaan52.txt");

                    int size = input.available();
                    byte[] buffer = new byte[size];
                    input.read(buffer);
                    input.close();

                    // byte buffer into a string
                    TunniplaanRAW = new String(buffer);
                    if (prefs.getString("TunniplaanTXT", "true").equals("true")) {

                        String temp = new String(buffer);
                        editor.putString("TunniplaanTXT", temp.substring(1)).apply();//Delete first character of a string gotten from Asset file, because of UTF-8 DOM
                    }
                } catch (IOException e) {
                    //Here is what happens if student doesn't have internet nor the schedule plan
                    //Which actually never happens
                }

                ProgramGuide();
                editor.putBoolean("firstrun", false).apply();
            }
        }

        String rawText = prefs.getString("TunniplaanTXT", "Something failed, please report!");
        //Here we start our parsing of the text input (tunniplaan)
        String[][] TimedData = SplitTime(rawText); //Here we get [5][5] array with string for every class(time space)

        ParseClasses(TimedData, Raspisanie);//Raspisanie gets its classes using the chunked [5][5] string array with data

        mergeTunniplaan();

        String selectedGroup = prefs.getString("lockedGroup", "none");

        if (selectedGroup.length() > 4) {
            selectedGroupName = selectedGroup.substring(0, 4);

            UpdateOtdelSpinner(otdelAdapt, Raspisanie);
            spinnerGroups.setSelection(otdelAdapt.getPosition(selectedGroup.substring(0, 4)));

            selectedGroupNumbers = selectedGroup.substring(4, 6);

            UpdateNumbersSpinner(numbersAdapt, Raspisanie, selectedGroup.substring(0, 4));
            spinnerGroupsIDs.setSelection(numbersAdapt.getPosition(selectedGroup.substring(4, 6)));
        } else {

            UpdateOtdelSpinner(otdelAdapt, Raspisanie);
            spinnerGroups.setSelection(otdelAdapt.getPosition("RDKR"));

            UpdateNumbersSpinner(numbersAdapt, Raspisanie, "RDKR");
            spinnerGroupsIDs.setSelection(numbersAdapt.getPosition("11"));
        }

        spinnerGroups.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGroupName = parent.getItemAtPosition(position).toString();

                UpdateNumbersSpinner(numbersAdapt, Raspisanie, selectedGroupName);
                UpdateListView(ListAdapter, Raspisanie, dayOfWeek, evenOddWeek);

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spinnerGroupsIDs.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGroupNumbers = parent.getItemAtPosition(position).toString();
                UpdateListView(ListAdapter, Raspisanie, dayOfWeek, evenOddWeek);

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        UpdateDayLine();
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        private Class SelectedClass;


        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);

            //Get the class selected

            SelectedClass = ListAdapter.getItem(getListView().getCheckedItemPosition());

            // Toast.makeText(getApplicationContext(), SelectedClass.toString(), Toast.LENGTH_SHORT).show(); //Show info about chosen class
            ListAdapter.setSelection(getListView().getCheckedItemPosition());

            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }


        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_hide:

                    if (SelectedClass.unique_ID <= Raspisanie.originalClassesCount) {
                        addAlteredClassID(SelectedClass.unique_ID);
                    } else {
                        String oli = prefs.getString("CustomClasses", "");
                        String tund = SelectedClass.toString();
                        oli = oli.replace(tund + "/", "");
                        editor.putString("CustomClasses", oli).apply();
                    }
                    Raspisanie.Tunnid.get(SelectedClass.Day - 1).get(SelectedClass.ID_inList - 1).changeVisibility();
                    mode.finish();
                    return true;
                case R.id.menu_add:

                    editor.putString("ClassCreatorTarget", "ADD").apply();

                    Intent addclass = new Intent("edunet.virukol.tunniplaan.ADDCLASS");
                    startActivity(addclass);
                    mode.finish();
                    return true;
                case R.id.menu_edit:

                    editor.putString("ClassCreatorTarget", "EDIT").apply();

                    if (SelectedClass.unique_ID <= Raspisanie.originalClassesCount) {
                        editor.putInt("EditedGenuineClass", SelectedClass.unique_ID).apply();
                    } else {
                        editor.putInt("EditedGenuineClass", 0).apply();
                        editor.putString("deleteCustomClass", SelectedClass.toString() + "/").apply();
                    }
                    Raspisanie.Tunnid.get(SelectedClass.Day - 1).get(SelectedClass.ID_inList - 1).changeVisibility();
                    //pre-setting the data

                    editor.putString("class_creator_classname", SelectedClass.ClassName).apply();

                    switch (SelectedClass.PaarisPaaritu) {
                        case 2:
                            editor.putBoolean("class_creator_even", true).apply();
                            editor.putBoolean("class_creator_odd", false).apply();
                            break;
                        case 3:
                            editor.putBoolean("class_creator_even", false).apply();
                            editor.putBoolean("class_creator_odd", true).apply();
                            break;
                        case 6:
                            editor.putBoolean("class_creator_even", true).apply();
                            editor.putBoolean("class_creator_odd", true).apply();
                            break;
                    }

                    editor.putString("class_creator_room", SelectedClass.Room).apply();
                    editor.putString("class_creator_faculty", selectedGroupName).apply();//TODO make it work correctly for multiple groups
                    editor.putString("class_creator_group", selectedGroupNumbers).apply();//TODO make it work correctly for multiple groups
                    editor.putString("class_creator_day", SelectedClass.Day + "").apply();
                    editor.putString("class_creator_paarinumber", SelectedClass.PaariNumber + "").apply();
                    editor.putString("class_creator_commentary", SelectedClass.commentary).apply();


                    Intent editclass = new Intent("edunet.virukol.tunniplaan.ADDCLASS");
                    startActivity(editclass);
                    mode.finish();

                    return true;

                case R.id.menu_add_note:

                    AlertDialog.Builder alert = new AlertDialog.Builder(Programm.this);


                    LayoutInflater inflater = getLayoutInflater();
                    View view=inflater.inflate(R.layout.dialog_title, null);
                    alert.setCustomTitle(view);



                    alert.setMessage(R.string.commentary_summary);

                    final EditText input = new EditText(Programm.this);
                    alert.setView(input);
                    alert.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {


                        public void onClick(DialogInterface dialog, int whichButton) {
                            //Accepted
                            if (SelectedClass.unique_ID <= Raspisanie.originalClassesCount) {
                                addAlteredClassID(SelectedClass.unique_ID);
                            } else {
                                String oli = prefs.getString("CustomClasses", "");
                                String tund = SelectedClass.toString();
                                oli = oli.replace(tund + "/", "");
                                editor.putString("CustomClasses", oli).apply();
                            }

                            String comment = input.getText().toString();
                            comment = comment.replace("(", "[");
                            comment = comment.replace(")", "]");
                            comment = comment.replace(":", " -");
                            SelectedClass.commentary = comment.replace("/", "|");
                            editor.putString("CustomClasses", prefs.getString("CustomClasses", "") + SelectedClass.toString() + "/").apply();
                        }

                    }

                    );

                    alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });

                    AlertDialog alertToShow = alert.create();
                    alertToShow.getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    input.setText(SelectedClass.commentary);
                    alertToShow.show();

                    mode.finish();

                    return true;
                default:
                    return false;


            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {

            ListAdapter.setSelection(-1);
            ListAdapter.notifyDataSetChanged();
            UpdateListView(ListAdapter, Raspisanie, dayOfWeek, evenOddWeek);
            mActionMode = null;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.programm, menu);


        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.preferences:
                Intent pref = new Intent("edunet.virukol.tunniplaan.PREFS");
                startActivity(pref);
                break;
            case R.id.about:
                Intent ab = new Intent("edunet.virukol.tunniplaan.ABOUT");
                startActivity(ab);
                break;
            case R.id.exit:
                finish();
                break;
            case R.id.help:
                ProgramGuide();

                break;
        }


        return false;
    }


    //Methods
    public void updateLockView() {
        if (prefs.getString("lockedGroup", "none").equals("" + selectedGroupName + selectedGroupNumbers)) {
            Lock.setImageResource(R.drawable.lockclosed);
        } else {
            Lock.setImageResource(R.drawable.lockopened);
        }
    }

    void UpdateOtdelSpinner(ArrayAdapter adapter, Tunniplaan Raspisanie) {

        adapter.clear();

        for (String otdel : Raspisanie.getFaculties()) {
            adapter.add(otdel);
        }

    }

    void UpdateNumbersSpinner(ArrayAdapter adapter, Tunniplaan Raspisanie, String otdelenie) {

        adapter.clear();

        for (String ID : Raspisanie.getGroupNumbers(otdelenie)) {
            adapter.add(ID);
        }
    }

    void UpdateListView(ClassListArrayAdapter adapter, Tunniplaan Raspisanie, int dayOfWeek, int evenOddWeek) {

        Object ID = spinnerGroupsIDs.getSelectedItem();
        if (ID instanceof String) {
            selectedGroupNumbers = ID.toString();
        }
        Object Facultet = spinnerGroups.getSelectedItem();
        if (Facultet instanceof String) {
            selectedGroupName = Facultet.toString();
        }


        adapter.clear();
        ArrayList<Class> ShowedClasses = Raspisanie.getFilteredClasses(selectedGroupName + selectedGroupNumbers, dayOfWeek, evenOddWeek, prefs, spinnerGroupsIDs);

        for (Class tund : ShowedClasses) {
            adapter.add(tund);
        }

        updateLockView();
    }

    private void getDayOfWeek(ClassListArrayAdapter adapter) {

        if (dayOfWeek == 0) {
            dayOfWeek = 5;
            changeEvenness(adapter);
        } else if (dayOfWeek == 6) {
            dayOfWeek = 1;
            changeEvenness(adapter);
        } else {
            UpdateListView(adapter, Raspisanie, dayOfWeek, evenOddWeek);
        }
    }

    private void changeEvenness(ClassListArrayAdapter adapter) {
        switch (evenOddWeek) {
            case 2:
                evenOddWeek = 3;
                break;
            case 3:
                evenOddWeek = 2;
                break;
        }
        UpdateListView(adapter, Raspisanie, dayOfWeek, evenOddWeek);
    }


    ArrayList<Integer> getAlteredClassesIDs() {
        ArrayList<Integer> AlteredClassesIDs = new ArrayList<Integer>();
        for (String ID : prefs.getString("AlteredClassesIDs", "").split("/")) {
            if (!ID.isEmpty()) {
                AlteredClassesIDs.add(Integer.parseInt(ID));
            }
        }
        return AlteredClassesIDs;
    }

    void addAlteredClassID(int ID) {
        String AlteredClasses = prefs.getString("AlteredClassesIDs", "");
        editor.putString("AlteredClassesIDs", AlteredClasses + ID + "/").apply();
    }

    void addToCustomClasses(String newClass) {
        String was = prefs.getString("CustomClasses", "") + newClass + "/";

        editor.putString("CustomClasses", was).apply();
    }

    String[][] SplitTime(String RawTunniplaanTXT) {
        String[][] TempData = new String[5][5];//Array [Day, Class]
        String[] substrings = RawTunniplaanTXT.split("Day");// Split on "Day"
        int d = 0; //Day
        int c = 0; //Class

        for (int i = 0; i < 25; i++) {
            TempData[d][c] = substrings[i];
            if (c == 4) {
                d = d + 1;
                c = -1;
            }
            c = c + 1;
        }
        return TempData;
    }

    void ParseClasses(String[][] ChunkedData, Tunniplaan Raspisanie) {
        ArrayList<Class> tempTunnid = new ArrayList<Class>();

        for (int d = 0; d < 5; d++) {

            int ID_in_List = 0;

            for (int c = 0; c < 5; c++) {
                String[] tunnidtekst = ChunkedData[d][c].split("/");// Split on "/"
                for (int i = 0; i < (tunnidtekst.length); i++) {
                    tunnidtekst[i] = tunnidtekst[i].replace("\\n", "");
                    if (tunnidtekst[i].length() > 4) {
                        Raspisanie.AmountOfClasses++;
                        ID_in_List++;
                        tempTunnid.add(new Class(tunnidtekst[i], d, c, Raspisanie, ID_in_List));
                    }
                }
            }
            Raspisanie.Tunnid.add(d, tempTunnid);
            tempTunnid = new ArrayList<Class>();
        }
        Raspisanie.originalClassesCount = Raspisanie.AmountOfClasses;

    }

    void UpdateDayLine() {
        String message;
        int dayString = 0;

        switch (dayOfWeek) {
            case 2:
                dayString = R.string.Tuesday;
                break;
            case 3:
                dayString = R.string.Wednesday;
                break;
            case 4:
                dayString = R.string.Thursday;
                break;
            case 5:
                dayString = R.string.Friday;
                break;
            case 6:
            case 0:
                if (evenOddWeek == 2) {
                    evenOddWeek = 3;
                } else {
                    evenOddWeek = 2;
                }
                dayOfWeek = 1;
            case 1:
                dayString = R.string.Monday;
                break;

        }
        if (evenOddWeek == 2) {
            message = "2,4 - ";
        } else {
            message = "1,3 - ";
        }
        DayLine.setText(message + getString(dayString));
    }

    void ProgramGuide() {

        final Dialog dialog = new Dialog(this, R.style.TTUthemeDialog);

        dialog.setContentView(R.layout.guide_layout);
        dialog.setTitle(R.string.guide);

        HelperStage = 0;

        // set the custom dialog components
        TextView text = (TextView) dialog.findViewById(R.id.textViewTop);
        text.setText(R.string.guide1);
        ImageView image = (ImageView) dialog.findViewById(R.id.tutorial_imageLeft);
        image.setImageResource(android.R.drawable.ic_menu_help);
        image.setPadding(125, 40, 0, 40);
        TextView textOR = (TextView) dialog.findViewById(R.id.dialogTextOR);
        textOR.setVisibility(View.VISIBLE);
        textOR.setText(R.string.help);
        textOR.setPadding(0, 40, 0, 40);
        textOR.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        TextView text2 = (TextView) dialog.findViewById(R.id.textViewBot);
        text2.setText(R.string.guide2);

        View dialogview = (View) dialog.findViewById(R.id.dialogView);

        dialogview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            TextView text = (TextView) dialog.findViewById(R.id.textViewTop);
            ImageView image = (ImageView) dialog.findViewById(R.id.tutorial_imageLeft);
            ImageView image2 = (ImageView) dialog.findViewById(R.id.tutorial_imageRight);
            TextView textOR = (TextView) dialog.findViewById(R.id.dialogTextOR);
            TextView text2 = (TextView) dialog.findViewById(R.id.textViewBot);

            @Override
            public void onDismiss(DialogInterface dialog1) {

                switch (HelperStage) {

                    case 0:
                        dialog.setTitle(R.string.Days);
                        text.setText(R.string.guide3);
                        text2.setText(R.string.guide4);
                        textOR.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                        textOR.setText(R.string.or);
                        image.setPadding(0, 0, 0, 0);
                        textOR.setPadding(0, 0, 0, 0);
                        image.setImageResource(R.drawable.swipeleft);
                        image2.setImageResource(R.drawable.swiperight);
                        dialog.show();
                        HelperStage++;
                        break;
                    case 1:
                        dialog.setTitle(R.string.Weeks);
                        text.setText(R.string.guide5);
                        text2.setText(R.string.guide6);
                        image.setImageResource(R.drawable.swipeup);
                        image2.setImageResource(R.drawable.swipedown);
                        dialog.show();
                        HelperStage++;
                        break;
                    case 2:
                        dialog.setTitle(R.string.Lock);
                        text.setText(R.string.guide7);
                        text2.setText(R.string.guide8);
                        image.setImageResource(R.anim.lock);
                        image2.setVisibility(View.GONE);
                        textOR.setVisibility(View.GONE);

                        AnimationDrawable lockAnimation = (AnimationDrawable) image.getDrawable();
                        lockAnimation.start();
                        dialog.show();
                        HelperStage++;
                        break;
                    case 3:
                        dialog.setTitle(R.string.Cuztomization);
                        text.setText(R.string.guide9);
                        text2.setText(R.string.guide10);
                        image.setImageResource(R.drawable.pressandhold);
                        dialog.show();
                        HelperStage++;
                        break;
                    case 4:
                        dialog.setTitle(R.string.EditMenu);
                        text.setText(R.string.guide11);
                        text2.setVisibility(View.GONE);
                        image.setImageResource(R.drawable.actionbar);
                        dialog.show();
                        HelperStage++;
                        break;
                    case 5:
                        dialog.setTitle(R.string.preferences);
                        text.setText(R.string.guide12);
                        text2.setText(R.string.guide13);
                        textOR.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        text2.setVisibility(View.VISIBLE);
                        image.setImageResource(android.R.drawable.ic_menu_preferences);
                        image.setPadding(125, 40, 0, 40);
                        textOR.setVisibility(View.VISIBLE);
                        textOR.setText(R.string.preferences);
                        textOR.setPadding(0, 40, 0, 40);


                        image.setOnClickListener(new View.OnClickListener() {
                            // @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Intent pref = new Intent("edunet.virukol.tunniplaan.PREFS");
                                startActivity(pref);
                            }
                        });
                        textOR.setOnClickListener(new View.OnClickListener() {
                            // @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Intent pref = new Intent("edunet.virukol.tunniplaan.PREFS");
                                startActivity(pref);
                            }
                        });


                        dialog.show();
                        HelperStage++;

                    default:

                        break;
                }


            }
        });

    }


    class ClassListArrayAdapter extends ArrayAdapter<Class> {

        private Context mContext;

        private int selectedItem = -1;

        public void setSelection(int position) {
            selectedItem = position;
        }

        public ClassListArrayAdapter(Context context, int resource, int textViewResourceId, List<Class> objects) {
            super(context, resource, textViewResourceId, objects);
            mContext = context;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ClassView classView;

            //Take the item at position
            Class currentListItem = getItem(position);

            if (convertView == null) {
                classView = new ClassView(mContext);
            } else {
                classView = (ClassView) convertView;
            }

            //Set ClassName field text to currentListItem.ClassName
            classView.setClassName(currentListItem.ClassName);

            //set other 3 fields
            classView.setClassNumber(currentListItem.PaariNumber + ".");
            classView.setClassRoom(currentListItem.Room);


            if (prefs.getBoolean("hide_groups", false) && prefs.getBoolean("hide_comments", false)) {
                classView.delGroups();
            } else {
                String withGroups = "";

                List<String> otherGroups = new ArrayList<String>();

                if (!prefs.getBoolean("hide_groups", false)) {
                    otherGroups = currentListItem.getOtherGroups(selectedGroupName + selectedGroupNumbers);
                    for (String grupp : otherGroups) {
                        withGroups = withGroups + ", " + grupp;
                    }
                    if (otherGroups.size() > 1) {
                        withGroups = getString(R.string.With_groups) + withGroups.substring(1) + ".";
                    } else if (otherGroups.size() == 1) {
                        withGroups = getString(R.string.With_group) + withGroups.substring(1) + ".";
                    }
                }
                classView.setGruppidega(withGroups);
                if (currentListItem.commentary.length() > 0 && !prefs.getBoolean("hide_comments", false)) {
                    if (otherGroups.size() > 0) {
                        classView.setGruppidega(Html.fromHtml(withGroups + "<br />" + "<i>" + currentListItem.commentary + "</i>"));
                    } else {
                        classView.setGruppidega(Html.fromHtml("<i>" + currentListItem.commentary + "</i>"));
                    }
                }
            }

            if (position == selectedItem) {
                classView.setBackgroundColor(Color.WHITE);
            } else {
                classView.setBackgroundColor(Color.rgb(185, 185, 185));
            }

            return classView;

        }

    }

    class ClassView extends TableLayout {

        private TextView ClassNumber;
        private TextView ClassName;
        private TextView ClassRoom;
        private TextView Gruppidega;


        private TableLayout classRow;
        private TableRow gruppirida;


        public ClassView(Context context) {
            super(context);

            LayoutInflater inflater = getLayoutInflater();

            inflater.inflate(R.layout.classrow, this);

            this.ClassNumber = (TextView) findViewById(R.id.textViewClassNumber);
            this.ClassName = (TextView) findViewById(R.id.textViewClassName);
            this.ClassRoom = (TextView) findViewById(R.id.textViewClassRoom);
            this.Gruppidega = (TextView) findViewById(R.id.textViewGruppidega);


            this.gruppirida = (TableRow) findViewById(R.id.textRowGroups);
            this.classRow = (TableLayout) findViewById(R.id.classRow);
        }

        public void setClassName(String Name) {
            this.ClassName.setText(Name);
        }

        public void setClassNumber(String Number) {
            this.ClassNumber.setText(Number);
        }

        public void setClassRoom(String Room) {
            this.ClassRoom.setText(Room);
        }

        public void setGruppidega(String Groups) {
            this.Gruppidega.setText(Groups);
        }

        public void setGruppidega(Spanned Groups) {
            this.Gruppidega.setText(Groups);
        }

        public void delGroups() {
            this.classRow.removeView(this.gruppirida);
        }
    }


    void mergeTunniplaan() {

        String[] customClassesText = prefs.getString("CustomClasses", "").split("/");
        for (String CustomClass : customClassesText) {
            if (CustomClass.length() > 4) {
                Class customTund = new Class(CustomClass, Raspisanie, 0, 0);
                Raspisanie.Tunnid.get(customTund.Day - 1).add(customTund);
            }
        }

        for (List<Class> Day : Raspisanie.Tunnid) {

            for (Class Tund : Day) {

                if (getAlteredClassesIDs().contains(Tund.unique_ID)) {
                    Tund.showUp = 0;
                }
            }
        }
    }


}


