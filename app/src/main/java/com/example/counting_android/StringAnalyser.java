package com.example.counting_android;

import java.util.ArrayList;
public class StringAnalyser {

    //the StringAnalyser class takes the ArrayList of unprocessed words
    //and removes unwanted characters such as punctuation

    //properties
    private ArrayList<String> processedList = new ArrayList<>();

    //constructor
    public StringAnalyser(ArrayList<String> wordList, ArrayList<String> punctuationList){
        //example punctuationList: ['!', '.', '-', ';']
        //user can choose what punctuations to include or exclude
        for (String word: wordList){
            processedList.add(charChecker(word.toLowerCase(), punctuationList));
        }
    }

    //methods
    public ArrayList<String> getProcessedList() {
        return processedList;
    }

    private String charChecker(String str, ArrayList<String> punctuationList){
        for (String punctuation : punctuationList){
            if (str.contains(punctuation)){
                str = str.replace(punctuation, "");
            }
        }
        return str;
    }
}
