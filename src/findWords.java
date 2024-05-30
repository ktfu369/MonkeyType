import java.io.*;
import java.util.Scanner;
import java.util.Random;

public class findWords {
    private static boolean hasNumbers, hasPunctuation;
    private static boolean hasTimer, hasWords;
    private static int choice;
    private static int choices[] = {0,20,33,66};

    private static String[] wordList;


    public findWords(boolean hasNumbers, boolean hasPunctuation, boolean hasTimer, boolean hasWords, int choice) throws FileNotFoundException {
        this.hasNumbers = hasNumbers;
        this.hasPunctuation = hasPunctuation;
        this.hasTimer = hasTimer;
        this.hasWords = hasWords;
        this.choice = choice;

        if(hasWords){
            wordList = new String[choices[choice]];
        }else{
            wordList = new String[300];
        }

        randomWords();
    }

    public static void randomWords() throws FileNotFoundException {
        File file = new File("words.txt");
        Scanner scanner = new Scanner(file);
        Random rand = new Random();

        String[] wordArray = fileToArray("words.txt");
        String[] puncArray = fileToArray("punctuation.txt");

        int max;
        if(hasWords) max = choices[choice];
        else max = 300;

        for(int i=1;i<=max;i++){
            int randIndex = rand.nextInt(fileLength("words.txt"));
            wordList[i-1] = wordArray[randIndex];
            if(hasPunctuation && i%12==0){
                int randPunc = rand.nextInt(fileLength("punctuation.txt"));
                wordList[i-1] += puncArray[randPunc];
            }
            if(hasNumbers && i%10 == 0){
                int randNum = rand.nextInt(10000);
                wordList[i-1] = "" +randNum;
            }
        }
    }

    public static String[] getWords(){
        return wordList;
    }

    public static String[] fileToArray(String fileName) throws FileNotFoundException{
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        int fileLength = fileLength(fileName);
        String[] array1 = new String[fileLength];
        int cnt = 0;
        while (scanner.hasNextLine()) {
            array1[cnt++] = scanner.nextLine();
        }
        return array1;
    }

    public static int fileLength(String fileName) throws FileNotFoundException{
        int cnt = 0;
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            cnt++;
            scanner.nextLine();
        }
        scanner.close();
        return cnt;
    }
}
