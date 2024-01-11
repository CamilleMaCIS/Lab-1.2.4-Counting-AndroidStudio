package com.example.counting_android;

import java.util.ArrayList;
import java.util.Collections;
public class Counter {
    //uncommon words are words that are not in the commonWordList provided
    //the Counter class counts the occurrences of these uncommon words
    //methods return the top five most frequently appearing words, as well as their respective frequencies

    //these two ArrayLists will be parallel ArrayLists. Index 1 will match with index 1, etc
    private ArrayList<String> wordsExcludingCommonWords = new ArrayList<>();
    private ArrayList<Integer> frequencyOfWords = new ArrayList<>();

    public Counter(ArrayList<String> processedList, ArrayList<String> commonWordList){
        for (String word : processedList){
            //if the word IS NOT in the common word list
            if (!commonWordList.contains(word)){
                //if word has already been added to the ArrayList, then add 1 to the counter
                if (wordsExcludingCommonWords.contains(word)){
                    //get the index of word in wordsExcludingCommonWords
                    int index = wordsExcludingCommonWords.indexOf(word);

                    //counter goes up by 1
                    //because the ArrayLists are parallel
                    //the word and its corresponding frequency share the same index
                    frequencyOfWords.set(index, frequencyOfWords.get(index) + 1);
                }
                //else word hasn't been added to the ArrayList yet
                else{
                    wordsExcludingCommonWords.add(word);
                    frequencyOfWords.add(1);
                }
            }
        }
    }

    public ArrayList<String> getWordsExcludingCommonWords() {
        return wordsExcludingCommonWords;
    }
    public ArrayList<Integer> getFrequencyOfWords() {
        return frequencyOfWords;
    }

    //returns top 5 common words, including tied places
    public ArrayList<ArrayList<String>> getTopFiveWords(){
        ArrayList<ArrayList<String>> topFiveWords = new ArrayList<>();
        ArrayList<String> firstPlace = new ArrayList<>();
        ArrayList<String> secondPlace = new ArrayList<>();
        ArrayList<String> thirdPlace = new ArrayList<>();
        ArrayList<String> fourthPlace = new ArrayList<>();
        ArrayList<String> fifthPlace = new ArrayList<>();
        Collections.addAll(topFiveWords, firstPlace, secondPlace, thirdPlace, fourthPlace, fifthPlace);

        //i loops from 1st place to 5th place
        for (int i = 0; i < 5; i++){
            for (Integer index : getTopFiveIndexes().get(i)){
                //adds the corresponding word using the pair ArrayList index
                topFiveWords.get(i).add(wordsExcludingCommonWords.get(index));
            }
        }
        return topFiveWords;
    }
    //returns most common word, including tied first places
    public ArrayList<String> getTopWords(){
        return getTopFiveWords().get(0);
    }

    //returns top 5 frequencies of the common words, including tied places
    public ArrayList<ArrayList<Integer>> getTopFiveFrequencies(){
        ArrayList<ArrayList<Integer>> topFiveFrequencies = new ArrayList<>();
        ArrayList<Integer> firstPlace = new ArrayList<>();
        ArrayList<Integer> secondPlace = new ArrayList<>();
        ArrayList<Integer> thirdPlace = new ArrayList<>();
        ArrayList<Integer> fourthPlace = new ArrayList<>();
        ArrayList<Integer> fifthPlace = new ArrayList<>();
        Collections.addAll(topFiveFrequencies, firstPlace, secondPlace, thirdPlace, fourthPlace, fifthPlace);

        //i loops from 1st place to 5th place
        for (int i = 0; i < 5; i++){
            for (Integer index : getTopFiveIndexes().get(i)){
                //adds the corresponding frequency using the pair ArrayList index
                topFiveFrequencies.get(i).add(frequencyOfWords.get(index));
            }
        }
        return topFiveFrequencies;
    }
    //returns the most common frequency
    public int getTopFrequency(){
        return getTopFiveFrequencies().get(0).get(0);
    }

    public ArrayList<ArrayList<Integer>> getTopFiveIndexes(){
        //first step: order the frequencies in descending order
        ArrayList<Integer> orderedFrequencies = new ArrayList<>();
        for (Integer freq : frequencyOfWords){
            orderedFrequencies.add(freq);
        }
        Collections.sort(orderedFrequencies, Collections.reverseOrder());

        //second step: remove duplicates frequencies; remove tied places
        ArrayList<Integer> uniqueFrequencies = new ArrayList<>();
        for (Integer freq : orderedFrequencies){
            if (!uniqueFrequencies.contains(freq)){
                uniqueFrequencies.add(freq);
            }
        }

        //third step: make an ArrayList of ArrayList, size of 5
        //1st ArrayList holds all tied first places
        //2nd ArrayList holds all tied second places, etc
        ArrayList<ArrayList<Integer>> topFiveIncludingTied = new ArrayList<>();
        ArrayList<Integer> firstPlace = new ArrayList<>();
        ArrayList<Integer> secondPlace = new ArrayList<>();
        ArrayList<Integer> thirdPlace = new ArrayList<>();
        ArrayList<Integer> fourthPlace = new ArrayList<>();
        ArrayList<Integer> fifthPlace = new ArrayList<>();
        Collections.addAll(topFiveIncludingTied, firstPlace, secondPlace, thirdPlace, fourthPlace, fifthPlace);

        for (int index = 0; index < frequencyOfWords.size(); index++){
            //j the index of uniqueFrequencies
            //looping from 0 - 4 will provide only the top 5 largest frequencies
            //since uniqueFrequencies is in descending order
            for (int j = 0; j < 5; j++){
                if (frequencyOfWords.get(index).equals(uniqueFrequencies.get(j))){
                    topFiveIncludingTied.get(j).add(index);
                }
            }
        }
        return topFiveIncludingTied;
    }

}
