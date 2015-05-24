package com.wutka.basstools.model.exercises;

import com.wutka.basstools.model.music.Key;

/**
 * Created by mark on 5/21/15.
 */
public class KeyNotesBasicExercise {
    private String[] availableKeys;
    private String currentKey = null;

    private String[] currentKeyNotes;

    public KeyNotesBasicExercise()
    {

    }

    public void doNextQuiz()
    {
        currentKey = availableKeys[(int)(availableKeys.length * Math.random())];
        currentKeyNotes = Key.getKeyNotes(currentKey);
    }

    public boolean matches(String[] notes) {
        if (notes.length != 7) return false;
        for (int i=0; i < 7; i++) {
            boolean found = false;
            for (int j=0; j < 7; j++) {
                if (notes[i].equals(currentKeyNotes[j])) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    public boolean isNoteInKey(String note) {
        for (int i=0; i < 7; i++) {
            if (note.equals(currentKeyNotes[i])) return true;
        }
        return false;
    }
    public String getKey()
    {
        return currentKey;
    }

    public String[] getAvailableKeys() {
        return availableKeys;
    }

    public void setAvailableKeys(String[] availableKeys) {
        this.availableKeys = availableKeys;
    }
}
