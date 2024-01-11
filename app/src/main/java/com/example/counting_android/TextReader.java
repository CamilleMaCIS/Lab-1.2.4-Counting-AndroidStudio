package com.example.counting_android;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class TextReader {
    //the TextReader class takes in a file name, reads the file,
    //and returns an ArrayList that separates the file by spaces: " "

    //property: an ArrayList that contains all words in the file
    private ArrayList<String> wordList = new ArrayList<>();

    //constructor
    public TextReader(String fileContents) throws FileNotFoundException{
        Scanner s = new Scanner(fileContents);
        while(s.hasNext()){
            String word = s.next();
            wordList.add(word);
        }
        s.close();
    }

    public ArrayList<String> getWordList() {
        return wordList;
    }
}
