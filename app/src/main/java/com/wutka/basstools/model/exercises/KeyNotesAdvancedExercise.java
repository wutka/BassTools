package com.wutka.basstools.model.exercises;

import com.wutka.basstools.model.music.Key;

/**
 * Created by mark on 5/22/15.
 */
public class KeyNotesAdvancedExercise {
    private String[] availableKeys;
    private int[] availableDegrees;
    private String currentKey = null;
    private String[] keyNotes;
    private int quizType;
    private String targetNote;
    private int targetDegree;

    public KeyNotesAdvancedExercise() {
        availableDegrees = new int[] { 0, 1, 2, 3, 4, 5, 6 };
    }

    public void doNextQuiz() {
        currentKey = availableKeys[(int) (availableKeys.length * Math.random())];
        keyNotes = Key.getKeyNotes(currentKey);
        quizType = (int) (2 * Math.random());
        targetDegree = availableDegrees[(int) (availableDegrees.length * Math.random())];
        targetNote = keyNotes[targetDegree];
    }

    public String getKey()
    {
        return currentKey;
    }

    public int getQuizType()
    {
        return quizType;
    }

    public int getDegree()
    {
        return targetDegree;
    }

    public String getNote()
    {
        return targetNote;
    }

    public String[] getKeyNotes()
    {
        return keyNotes;
    }

    public String[] getAvailableKeys() {
        return availableKeys;
    }

    public void setAvailableKeys(String[] availableKeys) {
        this.availableKeys = availableKeys;
    }

    public int[] getAvailableDegrees() {
        return availableDegrees;
    }

    public void setAvailableDegrees(int[] availableDegrees) {
        this.availableDegrees = availableDegrees;
    }
}
