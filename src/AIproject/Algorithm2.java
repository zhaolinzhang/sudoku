package AIproject;

import java.util.*;

public class Algorithm2 {
    ///////////////////////////////////////////////////
    //backtrack algorithm and some assistant function
    ///////////////////////////////////////////////////
    private long backtrackcount;
    public boolean backtrack(int[][] puzzle)
    {
        HashMap<Integer, ArrayList<Integer>> hm = new HashMap<Integer, ArrayList<Integer>>();
        FC_setup(hm);
        FC_initialize(puzzle, hm);

        int count = findNextEmpty(puzzle, 0);
        this.backtrackcount = 0;

        boolean flag = solve(puzzle, count/puzzle.length, count%puzzle.length, hm);
        System.out.println("Total backtracking times: " + backtrackcount);
        return flag;
    }

    public boolean solve(int[][] puzzle, int row, int col, HashMap<Integer, ArrayList<Integer>> hm)
    {
        //if the puzzle is full, meaning sudoku is successfully solved
        if (isFull(puzzle))
            return true;

        for (int i: hm.get(row*9+col))
        {
            if (moveIsValid(puzzle, row, col, i) && !FC_checkEmpty(hm))
            {
                //place this value in puzzle
                puzzle[row][col] = i;
                HashMap<Integer, ArrayList<Integer>> hm2 = new HashMap<Integer, ArrayList<Integer>>();
                hm2 = copy(hm);
                FC_add(row, col, i, hm2);

                //find next empty slot
                int count = findNextEmpty(puzzle, (row*puzzle.length+col));

                //if solved, then stop and return true
                if (solve(puzzle, count/puzzle.length, count%puzzle.length, hm2))
                    return true;

                //if unsolved, then undo
                puzzle[row][col] = 0;
                this.backtrackcount++;
            }
        }
        return false;
    }

    public boolean isFull(int[][] puzzle)
    {
        for (int i = 0; i < puzzle.length; i++)
        {
            for (int j = 0; j < puzzle[0].length; j++)
            {
                if (puzzle[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    public boolean moveIsValid(int[][] puzzle, int row, int col, int val)
    {
        //if the slot is occupied
        if (puzzle[row][col] != 0)
            return false;
        //if the row has duplication
        for (int i = 0; i < puzzle[0].length; i++)
        {
            if (puzzle[row][i] == val)
                return false;
        }
        //if the column has duplication
        for (int i = 0; i < puzzle.length; i++)
        {
            if (puzzle[i][col] == val)
                return false;
        }

        //if the 3X3 cube has duplication
        for (int i = 0; i < puzzle.length; i++)
        {
            if(puzzle[3*(row/3)+(i/3)][3*(col/3)+(i%3)] == val)
                return false;
        }

        return true;
    }

    public int findNextEmpty(int[][] puzzle, int count)
    {
        int i = count;
        if (count <81 ) {
            while (i < 81 && puzzle[i / puzzle.length][i % puzzle.length] != 0)
                i++;
        }
        return i;
    }

    ///////////////////////////////////////////////////
    //forward checking algorithm and some assistant function
    ///////////////////////////////////////////////////
    public void FC_setup(HashMap<Integer, ArrayList<Integer>> hm)
    {
        hm.clear();
        for (int i = 0; i < 81; i++)
        {
            ArrayList<Integer> temp = new ArrayList<Integer>();
            for (int j = 1; j <= 9; j++)
            {
                temp.add(j);
            }
            hm.put(i,temp);
        }
    }
    public void FC_initialize(int[][] puzzle, HashMap<Integer, ArrayList<Integer>> hm)
    {
        for (int i = 0; i < 81; i++)
        {
            if(puzzle[i/9][i%9] != 0 )
            {
                FC_add(i/9, i%9, puzzle[i/9][i%9], hm);
            }
        }
    }
    public void FC_add(int row, int col, int input, HashMap<Integer, ArrayList<Integer>> hm)
    {
        //test if hash map contains the key, if not, return false
        if(hm.containsKey(row*9+col))
        {
            //test the key contains value input, if not, return false
            if(hm.get(row*9+col).contains(input))
            {
                //delete all other elements
                hm.get(row*9+col).clear();

                //eliminate same element in same col
                for(int i = 0; i < 9; i++)
                {
                    if (hm.get(i*9+col).contains(input))
                    {
                        hm.get(i*9+col).remove(new Integer(input));
                    }
                }

                //eliminate same element in same row
                for (int i = 0; i < 9; i++)
                {
                    if (hm.get(row*9+i).contains(input))
                    {
                        hm.get(row*9+i).remove(new Integer(input));
                    }
                }

                //add input back
                hm.get(row*9+col).add(input);
            }
        }

    }

    public boolean FC_checkEmpty(HashMap<Integer, ArrayList<Integer>> hm)
    {
        for(int key: hm.keySet())
        {
            if(hm.get(key).isEmpty())
                return true;
        }
        return false;
    }

    private static HashMap<Integer, ArrayList<Integer>> copy(
            HashMap<Integer, ArrayList<Integer>> original)
    {
        HashMap<Integer, ArrayList<Integer>> copy = new HashMap<Integer, ArrayList<Integer>>();
        for (Map.Entry<Integer, ArrayList<Integer>> entry : original.entrySet())
        {
            copy.put(entry.getKey(), new ArrayList<Integer>(entry.getValue()));
        }
        return copy;
    }

}
