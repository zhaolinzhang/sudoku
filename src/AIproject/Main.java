package AIproject;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        //set an Array to store the puzzle
        int[][] puzzle = new int[9][9];

        //input the file
        //read in txt file for the sudoku puzzle into the array puzzle
        String data = readtxtasstring("/Users/JackZhang/Documents/OneDrive/文档/CS580/project/Hard1.txt");
        String[] dataParts = data.split("\\s+");
        for (int i = 0; i < 81; i++) {
            puzzle[i / 9][i % 9] = Integer.parseInt(dataParts[i]);
        }

        //set time start
        long startTime, endTime;

        //run the algorithm
        Scanner in = new Scanner(System.in);
        System.out.println("Please select the algorithm to run (choose from 1-5): ");
        String command = in.nextLine();
        startTime = System.nanoTime();
        if (command.equals("1")) {
            Algorithm1 a1 = new Algorithm1();
            a1.backtrack(puzzle);
        }
        else if (command.equals("2")) {
            Algorithm2 a2 = new Algorithm2();
            a2.backtrack(puzzle);
        }
        else if (command.equals("3")){
            Algorithm3 a3 = new Algorithm3();
            a3.backtrack(puzzle);
        }
        else if (command.equals("4")){
            Algorithm4 a4 = new Algorithm4();
            a4.backtrack(puzzle);
        }
        else if (command.equals("5")){
            Algorithm5 a5 = new Algorithm5();
            a5.backtrack(puzzle);
        }
        else
            System.out.println("Invalid command");
        in.close();

        //calculate the time slot
        endTime = System.nanoTime();
        long timeSlot = endTime - startTime;
        System.out.println("Total time taking: " + timeSlot);

        //check result
        if(checkResult(puzzle))
        {
            System.out.println("Solution successfully found...");
        }
        else
        {
            System.out.println("Solution NOT found...");
        }

        //output the result
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
                System.out.print(puzzle[i][j] + " ");
            System.out.println();
        }
    }

    // read the sudoku puzzle
    public static String readtxtasstring(String fileName)throws Exception
    {
        String data ="";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    public static boolean checkResult(int[][] inputpuzzle)
    {
        HashSet<Integer> hs = new HashSet<>();

        for(int i = 0; i < 9; i++)
        {
            hs.clear();
            int sum = 0;
            for(int j = 0; j < 9; j++)
            {
                sum += inputpuzzle[i][j];
                if(!hs.add(inputpuzzle[i][j]))
                    return false;
            }
            if(sum!=45)
                return false;
        }

        for(int i = 0; i < 9; i++)
        {
            hs.clear();
            int sum = 0;
            for(int j = 0; j < 9; j++)
            {
                sum += inputpuzzle[j][i];
                if(!hs.add(inputpuzzle[j][i]))
                    return false;
            }
            if(sum!=45)
                return false;
        }

        return true;
    }
}
