import java.io.*;
import java.util.Scanner;
import java.util.Random;

public class findWords {
    private static boolean isRandom, hasNumbers, hasPunctuation;
    private static String[] wordList = new String[500];

    public findWords(boolean isRandom, boolean hasNumbers, boolean hasPunctuation) throws FileNotFoundException {
        this.isRandom = isRandom;
        this.hasNumbers = hasNumbers;
        this.hasPunctuation = hasPunctuation;

        wordList = new String[300];
        randomWords();
    }

    public static void randomWords() throws FileNotFoundException {
        File file = new File("words.txt");
        Scanner scanner = new Scanner(file);
        Random rand = new Random();

        String[] wordArray = fileToArray("words.txt");
        String[] puncArray = fileToArray("punctuation.txt");

        for(int i=1;i<=300;i++){
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
        System.out.println(wordList);
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
//            System.out.println(array1[cnt-1]);
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
