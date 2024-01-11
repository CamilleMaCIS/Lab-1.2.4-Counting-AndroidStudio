package com.example.counting_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView displayText;
    EditText inputFileText;
    private ArrayList<String> punctuations = new ArrayList<>(Arrays.asList("!", ".", ",", "?", "\"", "/", "&", "(", ")", "@", ";", ":"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //textView is the id of the TextView in the xml file
        displayText = (TextView) findViewById(R.id.textView);
        //editTextFileName is the id of the EditText in the xml file, where the user types in the file name
        inputFileText = (EditText) findViewById(R.id.editTextFileName);

    }

    public void goToMostCommon(View v) throws FileNotFoundException {
        //reading user input file
        String fileContents = "";
        //using getAssets() to read the file
        BufferedReader reader = null;
        try {
            //inputFileText.getText().toString() is the user input, the file name
            reader = new BufferedReader(new InputStreamReader(getAssets().open(inputFileText.getText().toString())));

            // do reading, loop through each line
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                fileContents += mLine + " ";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //reading commonWords.txt
        String commonWordsTXT = "";
        //using getAssets() to read the file
        BufferedReader reader2 = null;
        try {
            reader2 = new BufferedReader(new InputStreamReader(getAssets().open("commonWords.txt")));

            // do reading, loop through each line
            String mLine;
            while ((mLine = reader2.readLine()) != null) {
                //process line
                commonWordsTXT += mLine + " ";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TextReader will take in a string instead of a file
        TextReader unprocessedFile = new TextReader(fileContents);
        StringAnalyser processedWords = new StringAnalyser(unprocessedFile.getWordList(), punctuations);
        TextReader commonWords = new TextReader(commonWordsTXT);
        StringAnalyser processedCommon = new StringAnalyser(commonWords.getWordList(), punctuations);
        Counter counter = new Counter(processedWords.getProcessedList(), processedCommon.getProcessedList());
        //Arraylist of the most common word, or words if theres a tie
        ArrayList<String> topWords = counter.getTopWords();
        int topFrequency = counter.getTopFrequency();
        //this means more there are tied first places, for example:
        //"ant ant ant bug bug bug fly" ant and bug both appear 3 times, and are tied for first place
        //both and and bug will get displayed as most common word
        if (topWords.size() > 1){
            String allTiedWords = "";
            for (int i = 0; i < topWords.size() - 1; i++){
                allTiedWords += "\"" + topWords.get(i) + "\", ";
            }
            allTiedWords += "and \"" + topWords.get(topWords.size() - 1) + "\"";
            displayText.setTextSize(30);
            displayText.setText("The most common words in the text file \"" + inputFileText.getText().toString() + "\" are " + allTiedWords + " with " + topFrequency + " occurrences.");
        }
        //this means there is only one first place
        else if (topWords.size() == 1){
            String topWord = topWords.get(0);
            displayText.setTextSize(20);
            displayText.setText("The most common word in the text file \"" + inputFileText.getText().toString() + "\" is \"" + topWord + "\" with " + topFrequency + " occurrences.");
        }
        else{
            Toast.makeText(this, "FILE NOT FOUND", Toast.LENGTH_SHORT).show();
        }

    }

    public void goToTopFive(View v) throws FileNotFoundException {
        //reading user input file
        String fileContents = "";
        //using getAssets() to read the file
        BufferedReader reader = null;
        try {
            //inputFileText.getText().toString() is the user input, the file name
            reader = new BufferedReader(new InputStreamReader(getAssets().open(inputFileText.getText().toString())));

            // do reading, loop through each line
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                fileContents += mLine + " ";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //reading commonWords.txt
        String commonWordsTXT = "";
        //using getAssets() to read the file
        BufferedReader reader2 = null;
        try {
            reader2 = new BufferedReader(new InputStreamReader(getAssets().open("commonWords.txt")));

            // do reading, loop through each line
            String mLine;
            while ((mLine = reader2.readLine()) != null) {
                //process line
                commonWordsTXT += mLine + " ";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TextReader will take in a string instead of a file
        TextReader unprocessedFile = new TextReader(fileContents);
        StringAnalyser processedWords = new StringAnalyser(unprocessedFile.getWordList(), punctuations);
        TextReader commonWords = new TextReader(commonWordsTXT);
        StringAnalyser processedCommon = new StringAnalyser(commonWords.getWordList(), punctuations);
        Counter counter = new Counter(processedWords.getProcessedList(), processedCommon.getProcessedList());
        //ArrayList of potential tied places, e.g. third place might have 3 words tied
        ArrayList<ArrayList<String>> topFiveWordsWithTie = counter.getTopFiveWords();
        ArrayList<ArrayList<Integer>> topFiveFreqWithTie = counter.getTopFiveFrequencies();

        //for simplicity, I won't add the tied-places feature, e.g. 1st, 1st, 2nd, 3rd, 3rd will be the top five if there is a tie
        String topFiveWordsString = "";
        int countToFive = 0;
        for (int i = 0; i < topFiveWordsWithTie.size(); i++){
            for (int j = 0; j < topFiveWordsWithTie.get(i).size(); j++){
                if (countToFive >= 5){
                    break;
                }
                topFiveWordsString += (countToFive + 1) + ". \"" + topFiveWordsWithTie.get(i).get(j) + "\" with " + topFiveFreqWithTie.get(i).get(j) + " occurrences\n";
                countToFive++;
            }
        }
        displayText.setTextSize(15);
        displayText.setText("The top five most common words in the text file \"" + inputFileText.getText().toString() + "\" are \n\n" + topFiveWordsString);
    }

}