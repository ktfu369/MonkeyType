import java.io.*;
import java.util.Scanner;
import java.util.Random;

public class findWords {
    private static String[] wordList = new String[500];
    public findWords(boolean isRandom, boolean hasNumbers, boolean hasPunctuation) throws FileNotFoundException {
        wordList = new String[300];
        randomWords();
    }

    public static void randomWords() throws FileNotFoundException {
        File file = new File("words.txt");
        Scanner scanner = new Scanner(file);
        Random rand = new Random();

        String[] wordArray = fileToArray("words.txt");

        for(int i=1;i<=300;i++){
            int randIndex = rand.nextInt(3000);
            wordList[i-1] = wordArray[randIndex];
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
