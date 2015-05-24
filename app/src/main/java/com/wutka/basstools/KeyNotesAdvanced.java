package com.wutka.basstools;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.wutka.basstools.model.exercises.KeyNotesAdvancedExercise;
import com.wutka.basstools.model.music.Key;

import java.util.HashSet;
import java.util.Set;


public class KeyNotesAdvanced extends ActionBarActivity {

    private int buttonIds[] = new int[]{
            R.id.advancedButton1, R.id.advancedButton2, R.id.advancedButton3,
            R.id.advancedButton4, R.id.advancedButton5, R.id.advancedButton6,
            R.id.advancedButton7};

    private int noteButtonIds[] = new int[] {
            R.id.advancedNoteAb, R.id.advancedNoteA, R.id.advancedNoteASharp,
            R.id.advancedNoteBb, R.id.advancedNoteB, R.id.advancedNoteBSharp,
            R.id.advancedNoteCb, R.id.advancedNoteC, R.id.advancedNoteCSharp,
            R.id.advancedNoteDb, R.id.advancedNoteD, R.id.advancedNoteDSharp,
            R.id.advancedNoteEb, R.id.advancedNoteE, R.id.advancedNoteESharp,
            R.id.advancedNoteFb, R.id.advancedNoteF, R.id.advancedNoteFSharp,
            R.id.advancedNoteGb, R.id.advancedNoteG, R.id.advancedNoteGSharp
    };

    private int minorButtonIds[] = new int[] {
            R.id.chooseKeysAm, R.id.chooseKeysBbm, R.id.chooseKeysBm, R.id.chooseKeysCm,
            R.id.chooseKeysCSharpm, R.id.chooseKeysDm, R.id.chooseKeysDSharpm,
            R.id.chooseKeysEbm, R.id.chooseKeysEm, R.id.chooseKeysFm, R.id.chooseKeysFSharpm,
            R.id.chooseKeysGm, R.id.chooseKeysGSharpm
    };

    private int majorButtonIds[] = new int[] {
        R.id.chooseKeysAb, R.id.chooseKeysA, R.id.chooseKeysBb, R.id.chooseKeysB,
        R.id.chooseKeysC, R.id.chooseKeysCSharp, R.id.chooseKeysDb, R.id.chooseKeysD,
        R.id.chooseKeysEb, R.id.chooseKeysE, R.id.chooseKeysF, R.id.chooseKeysFSharp,
        R.id.chooseKeysGb, R.id.chooseKeysG
    };

    private int degreeButtonIds[] = new int[] {
            R.id.chooseDegreeButton1, R.id.chooseDegreeButton2, R.id.chooseDegreeButton3,
            R.id.chooseDegreeButton4, R.id.chooseDegreeButton5, R.id.chooseDegreeButton6,
            R.id.chooseDegreeButton7
    };

    private KeyNotesAdvancedExercise exercise;
    private Set<String> keys = new HashSet<>();
    private Set<Integer> degrees = new HashSet<>();
    private boolean delaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_notes_advanced);

        String[] majorKeys = Key.majorKeys;
        for (String k: majorKeys) keys.add(k);

        for (int i=0; i < 7; i++) degrees.add(i);

        initExercise();
        startNextExercise();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_key_notes_advanced_pick_degree, menu);
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

    private void initExercise() {
        exercise = new KeyNotesAdvancedExercise();
        exercise.setAvailableKeys(keys.toArray(new String[keys.size()]));

        int[] degreeNums = new int[degrees.size()];
        int pos = 0;
        for (int d: degrees) degreeNums[pos++] = d;

        exercise.setAvailableDegrees(degreeNums);
    }

    private Button getButton(int i) {
        return (Button) findViewById(i);
    }

    private void startNextExercise() {
        delaying = false;
        exercise.doNextQuiz();

        switch (exercise.getQuizType()) {
            case 0:
                setContentView(R.layout.activity_key_notes_advanced);
                TextView kt = (TextView) findViewById(R.id.advancedKeyTextView);
                TextView tt = (TextView) findViewById(R.id.advancedTaskTextView);
                kt.setText("Key: " + exercise.getKey());
                tt.setTextColor(Color.BLACK);
                tt.setText("Degree for note " + exercise.getNote());
                break;
            case 1:
                setContentView(R.layout.advanced_key_notes);
                kt = (TextView) findViewById(R.id.advancedNotesKeyTextView);
                tt = (TextView) findViewById(R.id.advancedNotesTaskTextView);
                kt.setText("Key: " + exercise.getKey());
                tt.setTextColor(Color.BLACK);
                tt.setText("Note for degree " + (exercise.getDegree() + 1));
                break;
        }
    }

    private void delayUntilNextExercise() {
        delaying = true;
        new CountDownTimer(2000, 2000) {
            public void onTick(long l) {
            }

            ;

            public void onFinish() {
                startNextExercise();
            }
        }.start();
    }

    public void handleNoteButtonClick(View v) {
        if (delaying) return;
        Button b = (Button) v;
        boolean isCorrect = false;
        switch (exercise.getQuizType()) {
            case 0:
                if (Integer.parseInt(b.getText().toString()) == (exercise.getDegree() + 1)) {
                    isCorrect = true;
                }
                TextView tt = (TextView) findViewById(R.id.advancedTaskTextView);
                if (isCorrect) {
                    tt.setTextColor(Color.GREEN);
                    delayUntilNextExercise();
                } else {
                    tt.setTextColor(Color.RED);
                }
                break;
            case 1:
                if (b.getText().toString().equals(exercise.getNote())) {
                    isCorrect = true;
                }
                tt = (TextView) findViewById(R.id.advancedNotesTaskTextView);
                if (isCorrect) {
                    tt.setTextColor(Color.GREEN);
                    delayUntilNextExercise();
                } else {
                    tt.setTextColor(Color.RED);
                }
        }
    }

    private ToggleButton getToggleButton(int i) {
        return (ToggleButton) findViewById(i);
    }
    public void handleChooseKeysMajorClick(View v) {
        ToggleButton tb = (ToggleButton) v;

        if (tb.isChecked()) {
            for (int m: majorButtonIds) getToggleButton(m).setChecked(true);
        }
        else {
            for (int m : majorButtonIds) getToggleButton(m).setChecked(false);
        }
    }

    public void handleChooseKeysMinorClick(View v) {
        ToggleButton tb = (ToggleButton) v;

        if (tb.isChecked()) {
            for (int m : minorButtonIds) getToggleButton(m).setChecked(true);
        }
        else {
            for (int m : minorButtonIds) getToggleButton(m).setChecked(false);
        }
    }

    public void handleChooseKeyLongClick(ToggleButton b) {
        b.setChecked(true);
        for (int i: majorButtonIds) {
            ToggleButton tb = getToggleButton(i);
            if (!tb.getText().equals(b.getText())) {
                tb.setChecked(false);
            }
        }
        for (int i: minorButtonIds) {
            ToggleButton tb = getToggleButton(i);
            if (!tb.getText().equals(b.getText())) {
                tb.setChecked(false);
            }
        }
    }

    public void handleChooseDegreeLongClick(ToggleButton b) {
        b.setChecked(true);
        for (int i: degreeButtonIds) {
            ToggleButton tb = getToggleButton(i);
            if (!tb.getText().equals(b.getText())) tb.setChecked(false);
        }
    }

    public void handleChooseKeysClick(View v) {
        if (delaying) return;
        setContentView(R.layout.advanced_choose_keys);
        boolean allMajorsChecked = true;
        boolean allMinorsChecked = true;
        for (int i: majorButtonIds) {
            ToggleButton tb = getToggleButton(i);
            tb.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    handleChooseKeyLongClick((ToggleButton) v);
                    return true;
                }
            });
            if (keys.contains(tb.getText().toString())) {
                tb.setChecked(true);
            }
            else {
                tb.setChecked(false);
                allMajorsChecked = false;
            }
        }
        for (int i: minorButtonIds) {
            ToggleButton tb = getToggleButton(i);
            tb.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    handleChooseKeyLongClick((ToggleButton) v);
                    return true;
                }
            });

            if (keys.contains(tb.getText().toString())) {
                tb.setChecked(true);
            }
            else {
                tb.setChecked(false);
                allMinorsChecked = false;
            }
        }
        ToggleButton majorButton = (ToggleButton) findViewById(R.id.majorButton);
        majorButton.setChecked(allMajorsChecked);
        ToggleButton minorButton = (ToggleButton) findViewById(R.id.minorButton);
        minorButton.setChecked(allMinorsChecked);
    }

    public void handleChooseDegreesClick(View v) {
        if (delaying) return;
        setContentView(R.layout.advanced_choose_degrees);
        for (int i: degreeButtonIds) {
            ToggleButton tb = getToggleButton(i);
            int d = Integer.parseInt(tb.getText().toString())-1;
            if (degrees.contains(d)) {
                tb.setChecked(true);
            }
            else {
                tb.setChecked(false);
            }
            tb.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    handleChooseDegreeLongClick((ToggleButton) v);
                    return true;
                }
            });
        }
    }

    public void handleChooseKeysOkayClick(View v) {
        keys.clear();
        for (int i: majorButtonIds) {
            ToggleButton tb = getToggleButton(i);
            if (tb.isChecked()) keys.add(tb.getText().toString());
        }
        for (int i: minorButtonIds) {
            ToggleButton tb = getToggleButton(i);
            if (tb.isChecked()) keys.add(tb.getText().toString());
        }
        if (keys.isEmpty()) {
            keys.add("C");
        }
        exercise.setAvailableKeys(keys.toArray(new String[keys.size()]));
        startNextExercise();
    }

    public void handleChooseDegreesOkayClick(View v)
    {
        degrees.clear();
        for (int i: degreeButtonIds) {
            ToggleButton tb = getToggleButton(i);
            if (tb.isChecked()) degrees.add(Integer.parseInt(tb.getText().toString())-1);
        }
        if (degrees.isEmpty()) {
            degrees.add(0);
        }
        setContentView(R.layout.activity_key_notes_advanced);
        int[] degreeNums = new int[degrees.size()];
        int pos = 0;
        for (int d: degrees) degreeNums[pos++] = d;

        exercise.setAvailableDegrees(degreeNums);
        startNextExercise();
    }
}
