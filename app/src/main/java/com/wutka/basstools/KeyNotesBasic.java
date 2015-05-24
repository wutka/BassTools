package com.wutka.basstools;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.wutka.basstools.model.exercises.KeyNotesBasicExercise;
import com.wutka.basstools.model.music.Key;


public class KeyNotesBasic extends ActionBarActivity {

    public KeyNotesBasicExercise exercise;
    public int[] radioGroupIds = new int[] { R.id.radioGroupA, R.id.radioGroupB, R.id.radioGroupC,
        R.id.radioGroupD, R.id.radioGroupE, R.id.radioGroupF, R.id.radioGroupG};
    public RadioGroup[] radioGroups;
    public String[] checkedNotes = new String[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_notes_basic);
        radioGroups = new RadioGroup[7];
        for (int i=0; i < 7; i++) {
            radioGroups[i] = (RadioGroup) findViewById(radioGroupIds[i]);
            radioGroups[i].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    handleNoteClick(group);
                }
            });
        }
        TextView t = (TextView)findViewById(R.id.textView);
        t.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showKeyNotes();
                return true;
            }
        });

        initExercise();
        startNextExercise();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_key_notes_basic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initExercise()
    {
        exercise = new KeyNotesBasicExercise();
        exercise.setAvailableKeys(new String[] { "Ab", "A", "Bb", "B", "C", "C#", "Db", "D", "Eb", "E",
            "F", "F#", "Gb", "G"});
    }

    public void handleNoteClick(View view)
    {
        for (int i=0; i < 7; i++) {
            int checkedId = radioGroups[i].getCheckedRadioButtonId();
            if (checkedId < 0) return;
            RadioButton radioButton = (RadioButton) findViewById(checkedId);
            checkedNotes[i] = radioButton.getText().toString();
        }
        TextView t = (TextView)findViewById(R.id.textView);
        if (exercise.matches(checkedNotes)) {
            t.setTextColor(Color.GREEN);
            for (int i=0; i < 7; i++) {
                radioGroups[i].clearCheck();
            }
            delayUntilNextExercise();
        }
        else {
            t.setTextColor(Color.RED);
        }
    }

    private void showKeyNotes()
    {
        for (int i=0; i < 7; i++) {
            radioGroups[i].clearCheck();
            for (int j=0; j < radioGroups[i].getChildCount(); j++) {
                View v = radioGroups[i].getChildAt(j);
                if (v instanceof RadioButton) {
                    RadioButton rb = (RadioButton) v;
                    if (exercise.isNoteInKey(rb.getText().toString())) {
                        rb.setTextColor(Color.BLUE);
                    }
                }
            }
        }

    }
    private void delayUntilNextExercise()
    {
        new CountDownTimer(2000, 2000) {
            public void onTick(long l) {};
            public void onFinish() {
                startNextExercise();
            }
        }.start();
    }

    public void handleKeyChange(View v)
    {
        startNextExercise();
    }

    private void startNextExercise()
    {
        ToggleButton majorButton = (ToggleButton) findViewById(R.id.majorButton);
        ToggleButton minorButton = (ToggleButton) findViewById(R.id.minorButton);

        if (!majorButton.isChecked() && !minorButton.isChecked()) {
            exercise.setAvailableKeys(Key.majorKeys);
        }
        else if (majorButton.isChecked() && minorButton.isChecked()) {
            exercise.setAvailableKeys(Key.allKeys);
        }
        else if (majorButton.isChecked()) {
            exercise.setAvailableKeys(Key.majorKeys);
        }
        else {
            exercise.setAvailableKeys(Key.minorKeys);
        }
        exercise.doNextQuiz();
        TextView t = (TextView)findViewById(R.id.textView);
        t.setText("Key " + exercise.getKey());
        t.setTextColor(Color.BLACK);
        for (int i=0; i < 7; i++) {
            radioGroups[i].clearCheck();
            for (int j=0; j < radioGroups[i].getChildCount(); j++) {
                View v = radioGroups[i].getChildAt(j);
                if (v instanceof RadioButton) {
                    ((RadioButton) v).setTextColor(Color.BLACK);
                }
            }
        }
    }
}
