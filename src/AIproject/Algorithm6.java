package AIproject;

import java.util.*;

public class Algorithm6 {
    ///////////////////////////////////////////////////
    //simulated annealing algorithm and some assistant function
    ///////////////////////////////////////////////////
    public boolean annealing(int[][] puzzle)
    {
        fillingAll(puzzle);
        solve(puzzle);
        return true;
    }

    public boolean solve(int[][] puzzle)
    {
        while(evaluateBoard(puzzle) != 0)
        {
            int currentScore = evaluateBoard(puzzle);

            //generate the flip row and column
            int fliprow1 = randomGenerator(0, 8);
            int fliprow2 = randomGenerator(0, 8);
            int flipcol1 = randomGenerator(0, 8);
            int flipcol2 = randomGenerator(0, 8);
            while (fliprow1 == fliprow2 && flipcol1 == flipcol2)
                flipcol2 = randomGenerator(0, 8);

            //flip the grid
            int temp = puzzle[fliprow1][flipcol1];
            puzzle[fliprow1][flipcol1] = puzzle[fliprow2][flipcol2];
            puzzle[fliprow2][flipcol2] = temp;

            //compare score, if new score is larger, flip back
            int nextScore = evaluateBoard(puzzle);
            if (nextScore > currentScore) {
                int temp2 = puzzle[fliprow1][flipcol1];
                puzzle[fliprow1][flipcol1] = puzzle[fliprow2][flipcol2];
                puzzle[fliprow2][flipcol2] = temp2;
            }
        }

        return true;
    }

    public void fillingAll(int[][] puzzle)
    {
        //create a collection that contains 1-9 values on 9 times
        HashMap<Integer, LinkedList<Integer>> elementHM = new HashMap<Integer, LinkedList<Integer>>();
        for(int key = 1; key < 10; key++)
        {
            LinkedList<Integer> temp = new LinkedList<Integer>();
            for(int i = 0; i < 9; i++)
                temp.add(key);
            elementHM.put(key, temp);
        }

        //delete existing element in puzzle
        for(int i = 0; i < (puzzle.length*puzzle[0].length); i++)
        {
            if(puzzle[i/puzzle.length][i%puzzle.length] != 0)
                elementHM.get(puzzle[i/puzzle.length][i%puzzle.length]).poll();
        }

        //push all elements into 1 Arraylist
        ArrayList<Integer> elementAL = new ArrayList<Integer>();
        for(int i: elementHM.keySet())
        {
            while(elementHM.get(i).peek() != null)
                elementAL.add(elementHM.get(i).poll());
        }

        //randomize the ArrayList
        long seed = System.nanoTime();
        Collections.shuffle(elementAL, new Random(seed));

        //push elements in ArrayList into puzzle
        for(int i = 0; i < (puzzle.length*puzzle[0].length); i++)
        {
            if(puzzle[i/puzzle.length][i%puzzle.length] == 0)
            {
                puzzle[i/puzzle.length][i%puzzle.length] = elementAL.get(0);
                elementAL.remove(0);
            }
        }
    }

    public int evaluateBoard(int[][] puzzle)
    {
        int score = 0;
        HashSet<Integer> hs = new HashSet<Integer>();

        //evaluate row
        for(int i = 0; i < 9; i++)
        {
            hs.clear();
            for(int j = 0; j < 9; j++)
            {
                if(!hs.add(puzzle[i][j]))
                    score++;
            }
        }

        //evaluate column
        for(int i = 0; i < 9; i++)
        {
            hs.clear();
            for(int j = 0; j < 9; j++)
            {
                if(!hs.add(puzzle[j][i]))
                    score++;
            }
        }

        return score;
    }

    public int randomGenerator(int min, int max)
    {
        long seed = System.nanoTime();
        Random rand = new Random(seed);
        return rand.nextInt((max - min) + 1) + min;
    }
}
