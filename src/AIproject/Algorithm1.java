package AIproject;

public class Algorithm1 {

    ///////////////////////////////////////////////////
    //backtrack algorithm and some assistant function
    ///////////////////////////////////////////////////
    private long backtrackcount;
    public boolean backtrack(int[][] puzzle)
    {
        int count = findNextEmpty(puzzle, 0);
        this.backtrackcount = 0;

        boolean flag = solve(puzzle, count/puzzle.length, count%puzzle.length);
        System.out.println("Total backtracking times: " + backtrackcount);
        return flag;
    }

    public boolean solve(int[][] puzzle, int row, int col)
    {
        //if the puzzle is full, meaning sudoku is successfully solved
        if (isFull(puzzle))
            return true;

        for (int i = 1; i <= 9; i++)
        {
            if (moveIsValid(puzzle, row, col, i))
            {
                //place this value in puzzle
                puzzle[row][col] = i;

                //find next empty slot
                int count = findNextEmpty(puzzle, (row*puzzle.length+col));

                //if solved, then stop and return true
                if (solve(puzzle, count/puzzle.length, count%puzzle.length))
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

}
