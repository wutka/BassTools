package com.wutka.basstools.model.music;

/**
 * Created by mark on 5/21/15.
 */
public class Key {
    static int[] keyOffsetsMajor = new int[] { 2, 2, 1, 2, 2, 2, 1};
    static int[] keyOffsetsMinor = new int[] { 2, 1, 2, 2, 1, 2, 2};

    public static String[] majorKeys = new String[] { "Ab", "A", "Bb", "B", "C", "C#", "Db", "D", "Eb", "E",
            "F", "F#", "Gb", "G"};
    public static String[] minorKeys = new String[] { "Am", "Bbm", "Bm", "Cm", "C#m", "Dm", "D#m", "Ebm",
            "Em", "Fm", "F#m", "Gm", "G#"};

    public static String[] allKeys;

    static {
        allKeys = new String[majorKeys.length + minorKeys.length];
        for (int i=0; i < majorKeys.length; i++) {
            allKeys[i] = majorKeys[i];
        }
        for (int i=0; i < minorKeys.length; i++) {
            allKeys[majorKeys.length+i] = minorKeys[i];
        }
    }
    public static String[] getKeyNotes(String key) {
        int[] keyOffsets = keyOffsetsMajor;
        if (key.endsWith("m")) {
            keyOffsets = keyOffsetsMinor;
            key = key.substring(0, key.length()-1);
        }
        String[] keyNotes = new String[7];
        char noteLetter = key.charAt(0);
        int currNoteNumber = Note.noteToNumber(key);
        keyNotes[0] = key;
        for (int i=0; i < 6; i++) {
            currNoteNumber = (currNoteNumber + keyOffsets[i]) % 12;
            noteLetter = Note.nextNoteLetter(noteLetter);
            keyNotes[i+1] = Note.numberToNote(currNoteNumber, noteLetter);
        }
        return keyNotes;
    }

    public static void main(String[] args) {
        String[] keys = new String[] { "Ab", "A", "Bb", "B", "C", "C#", "Db", "D", "Eb", "E",
                "F", "F#", "Gb", "G", "Am", "Em", "Cm", "C#m"};
        for (int i=0; i < keys.length; i++) {
            String[] notes = getKeyNotes(keys[i]);
            System.out.print("Key "+keys[i]+": ");
            for (int j=0; j < 7; j++) {
                System.out.print(notes[j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
