package com.wutka.basstools.model.music;

import java.util.HashMap;

/**
 * Created by mark on 5/21/15.
 */
public class Note {
    private static HashMap<String,Integer> noteToNumberMap = new HashMap<>();
    static {
        noteToNumberMap.put("Cb", 11);
        noteToNumberMap.put("C", 0);
        noteToNumberMap.put("C#", 1);
        noteToNumberMap.put("Db", 1);
        noteToNumberMap.put("D", 2);
        noteToNumberMap.put("D#", 3);
        noteToNumberMap.put("Eb", 3);
        noteToNumberMap.put("E", 4);
        noteToNumberMap.put("E#", 5);
        noteToNumberMap.put("Fb", 4);
        noteToNumberMap.put("F", 5);
        noteToNumberMap.put("F#", 6);
        noteToNumberMap.put("Gb", 6);
        noteToNumberMap.put("G", 7);
        noteToNumberMap.put("G#", 8);
        noteToNumberMap.put("Ab", 8);
        noteToNumberMap.put("A", 9);
        noteToNumberMap.put("A#", 10);
        noteToNumberMap.put("Bb", 10);
        noteToNumberMap.put("B", 11);
        noteToNumberMap.put("B#", 0);
    }

    public static void sortNoteList(String[] noteList) {
        for (int i=0; i < 6; i++) {
            for (int j=i+1; j < 7; j++) {
                int iNum = noteToNumberMap.get(noteList[i]);
                int jNum = noteToNumberMap.get(noteList[j]);
                if (jNum < iNum) {
                    String temp = noteList[i];
                    noteList[i] = noteList[j];
                    noteList[j] = temp;
                }
            }
        }
    }

    public static int noteToNumber(String note) { return noteToNumberMap.get(note); }
    public static String numberToNote(int number, char preferLetter) {
        for (String n: noteToNumberMap.keySet()) {
            if (noteToNumberMap.get(n) == number) {
                if (n.charAt(0) == preferLetter) return n;
            }
        }
        return null;
    }

    public static char nextNoteLetter(char noteLetter) {
        if ((noteLetter >= 'A') && (noteLetter < 'G')) {
            return (char)(1+(int)noteLetter);
        }
        else {
            return 'A';
        }
    }
}
