import java.util.Scanner;

public class Sudoku
{
    // boolean flag is inserted to tell the program when to stop the do/while loop.
    
    public static boolean flag;
    static int zeroes = 0;
    static Scanner input = new Scanner(System.in);
    
    public static void main(String[] args)
    {
        // Initialize sudokuGrid array and layout path through the methods
        // by using a do/while loop to be able to analyze multiple arrays at once.
        
        int[][] sudokuGrid = new int[9][9];
        
        System.out.println("=== Sudoku Solver ===");
        System.out.println("This program solves Sudoku puzzles with 1-3 missing values.");
        System.out.println("Enter 0 for empty cells that need to be solved.\n");
        
        do
        {
            getValues(sudokuGrid);
        }
        while (zeroes < 81);
        
        System.out.println("\nEND");	
    }
    
    // getValues insert the numbers from the console to the sudokuGrid array.
    
    public static void getValues(int[][] sudokuGrid)
    {
        System.out.println("Please enter your Sudoku puzzle:");
        System.out.println("Enter numbers 1-9 for filled cells, and 0 for empty cells.");
        System.out.println("You can enter all 81 numbers separated by spaces, or enter them row by row.\n");
        
        // Display input format example
        System.out.println("Example format:");
        System.out.println("Row 1: 5 3 0 0 7 0 0 0 0");
        System.out.println("Row 2: 6 0 0 1 9 5 0 0 0");
        System.out.println("... (continue for all 9 rows)");
        System.out.println("\nOr enter all 81 numbers in one line separated by spaces.\n");
        
        // Ask user for input preference
        System.out.print("Would you like to enter the puzzle row by row? (y/n): ");
        String choice = input.nextLine().toLowerCase();
        
        if (choice.equals("y") || choice.equals("yes")) {
            enterRowByRow(sudokuGrid);
        } else {
            enterAllAtOnce(sudokuGrid);
        }
        
        // Display the entered puzzle
        System.out.println("\nYour entered Sudoku puzzle:");
        displayGrid(sudokuGrid);
        
        detectZeroes(sudokuGrid);
    }
    
    // Method to enter puzzle row by row
    public static void enterRowByRow(int[][] sudokuGrid) {
        for (int row = 0; row < sudokuGrid.length; row++) 
        {
            System.out.print("Enter row " + (row + 1) + " (9 numbers separated by spaces): ");
            for (int column = 0; column < sudokuGrid[row].length; column++) 
            {
                sudokuGrid[row][column] = input.nextInt();
                
                // Validate input
                while (sudokuGrid[row][column] < 0 || sudokuGrid[row][column] > 9) {
                    System.out.print("Invalid input! Please enter a number between 0-9: ");
                    sudokuGrid[row][column] = input.nextInt();
                }
            }
        }
        input.nextLine(); // Consume remaining newline
    }
    
    // Method to enter all numbers at once
    public static void enterAllAtOnce(int[][] sudokuGrid) {
        System.out.println("Enter all 81 numbers separated by spaces:");
        for (int row = 0; row < sudokuGrid.length; row++) 
        {
            for (int column = 0; column < sudokuGrid[row].length; column++) 
            {
                sudokuGrid[row][column] = input.nextInt();
                
                // Validate input
                while (sudokuGrid[row][column] < 0 || sudokuGrid[row][column] > 9) {
                    System.out.print("Invalid input! Please enter a number between 0-9: ");
                    sudokuGrid[row][column] = input.nextInt();
                }
            }
        }
        input.nextLine(); // Consume remaining newline
    }
    
    // Method to display the Sudoku grid in a readable format
    public static void displayGrid(int[][] sudokuGrid) {
        System.out.println("  ┌─────────┬─────────┬─────────┐");
        for (int row = 0; row < 9; row++) {
            System.out.print("  │ ");
            for (int col = 0; col < 9; col++) {
                if (sudokuGrid[row][col] == 0) {
                    System.out.print("· ");
                } else {
                    System.out.print(sudokuGrid[row][col] + " ");
                }
                
                if (col == 2 || col == 5) {
                    System.out.print("│ ");
                }
            }
            System.out.println("│");
            
            if (row == 2 || row == 5) {
                System.out.println("  ├─────────┼─────────┼─────────┤");
            }
        }
        System.out.println("  └─────────┴─────────┴─────────┘");
    }
    
    // By using reading the sudokuGrid array, detectZeros will determine how many
    // total zeros are in the matrix, and apply them to the numOfZeroes variable.
    
    public static void detectZeroes(int[][] sudokuGrid)
    {
        int numOfZeroes = 0;
        
        for (int row = 0; row < sudokuGrid.length; row++) 
        {
            for (int column = 0; column < sudokuGrid[row].length; column++) 
            {
                if (sudokuGrid[row][column] == 0)
                {
                    numOfZeroes = numOfZeroes + 1;
                }
            }
        }
        
        // After the nested for loop, the program sends the variable numOfZeroes and the 
        // sudokuGrid to the method problemPicker.
        
        System.out.println("\nFound " + numOfZeroes + " empty cell(s) to solve.");
        System.out.print("Solution: ");
        problemPicker(sudokuGrid, numOfZeroes);
    }
    
    // By using the number of zeros, problemPicker will determine what type of problem that
    // will determine the solution of the sudokuGrid.
    
    public static void problemPicker(int[][] sudokuGrid, int numOfZeroes)
    {
        if (numOfZeroes == 1)
        {
            System.out.print("Type 1 problem - ");
            oneZero(sudokuGrid);
            
        }
        else if (numOfZeroes == 2)
        {
            System.out.print("Type 2 problem - ");
            twoZero(sudokuGrid);
            
        }
        else if (numOfZeroes == 3)
        {
            System.out.print("Type 3 problem - ");
            threeZero(sudokuGrid);
            
        }
        else if (numOfZeroes == 0)
        {
            System.out.println("Puzzle is already complete!");
            zeroes = 81; // Set to exit condition
        }
        else
        {
            System.out.println("This solver only handles 1-3 missing values. Found " + numOfZeroes + " missing values.");
            zeroes = 81; // Set to exit condition
        }
        
        // Ask if user wants to solve another puzzle
        if (numOfZeroes > 0 && numOfZeroes <= 3) {
            System.out.print("\nWould you like to solve another puzzle? (y/n): ");
            String response = input.nextLine().toLowerCase();
            if (!response.equals("y") && !response.equals("yes")) {
                zeroes = 81; // Set to exit condition
            }
        }
    }
    
    // oneZero will be selected from problemPicker if only one zero in the sudokuGrid array.
    
    public static void oneZero(int[][] sudokuGrid)
    {
        int sum = 0;
        int locatedRow = 0;
        int locatedColumn = 0;
        int missingNumber = 0;
        
        // The oneZero method's nested for loop scans the sudokuGrid array by row and column
        //  to calculate the exact placement of the zero.
        
        for (int row = 0; row < sudokuGrid.length; row++) 
        {
            for (int column = 0; column < sudokuGrid[row].length; column++) 
            {
                if(sudokuGrid[row][column] == 0)
                {
                    locatedRow = row;
                    locatedColumn = column;
                }
            }
        }
        
        // The oneZero method then takes the locatedColumn and the Row to find the sum.
        // By using the sum, the number is subtracted from 45 (the total of the whole column)
        // to determine the missing number in the sudokuGrid array.
        
        for(int row = 0; row < sudokuGrid.length; row++)
        {
            sum = sum + sudokuGrid[row][locatedColumn];
        }
        missingNumber = 45 - sum;
        System.out.print("(" + locatedRow + "," + locatedColumn + "," + missingNumber + ")");
    }
    
    // twoZero will be selected from problemPicker if there are only two zero in the
    // sudokuGrid array.
    
    public static void twoZero(int[][] sudokuGrid)
    {
        
        // The oneZero method's nested for loop scans the sudokuGrid array by row and column
        // to calculate the exact placement of the zeroes. solveRow will be called if the zeroes
        // lie in the same row. solveColumn will be called if the zeroes lie in the same column.
        
        for (int row = 0; row < sudokuGrid.length; row++) 
        {
            for (int column = 0; column < sudokuGrid[row].length; column++)
            {
                if(sudokuGrid[row][column] == 0)
                {
                    if(column != 8)
                    {
                        if(sudokuGrid[row][column + 1] == 0)	
                        { 
                            solveRow(sudokuGrid, row, column);
                        }
                    }
                    if(row != 8)
                    {
                        if(sudokuGrid[row + 1][column] == 0 && row != 8)
                        {											
                            solveColumn(sudokuGrid, row, column);
                        }
                    }
                }
            }
        }
    }
    
    // The solveRow method calculates the sum of the column of each zero through a nested
    // for loop. The zeroes lie in the same row so the column must be calculate to determine
    // each missing number.
    
    public static void solveRow(int[][] sudokuGrid, int row, int column)
    {
        int result[] = new int [2];
        
        for(int x = 0 ; x < 2 ; x++)
        {	
            int sum = 0;
            for(int y = 0 ; y < sudokuGrid[0].length ; y++)
            {
                sum = sum + sudokuGrid[y][column + x];
                
            }
            result[x] = 45 - sum;
            System.out.print("(" + row + "," + (column + x) + "," + result[x] + ") ");
        }
    }
    
    // The solveColumn method calculates the sum of the row of each zero through a nested
    // for loop. The zeroes lie in the same column so the row must be calculate to determine
    // each missing number.
    
    public static void solveColumn(int[][] sudokuGrid, int row, int column)
    {
        
        int result[] = new int [2];
        
        for(int x = 0 ; x < 2 ; x++)
        {
            int sum = 0;
            for(int y = 0 ; y < sudokuGrid[0].length; y++)
            {
                sum = sum + sudokuGrid[row + x][y];
            }
            result[x] = 45 - sum;
            System.out.print("(" + (row + x) + "," + column + "," + result[x] + ") ");
        }
    }
    
    // threeZero will be selected from problemPicker if there are three zeroes in the sudokuGrid
    // array. Three zeros are missing but the zeros are in a L shape (Two in same row and two in 
    // same column) By using a nested for loop to scan through the sudokuGrid array and if/else
    // statements to determine which block the third zero is located in.
    
        public static void threeZero(int[][] sudokuGrid)
        {
            int locatedRow = 0;
            int locatedColumn = 0;
            int[] threeZeros = new int[3];
            int zeroIndex = 0;
            
            for (int r = 0; r < 9; r++)
            {
                for (int c = 0; c < 9; c++)
                {
                    if (sudokuGrid[r][c] == 0)
                    {
                        // This math equation (whichBox = (r/3) * 3 + (c/3)), splits the
                        // sudokuGrid array into 3x3 blocks.
                        
                        int whichBox = (r/3) * 3 + (c/3);
                        threeZeros[zeroIndex] = whichBox;
                        zeroIndex++;
                    }
                }
            }
            
            int blockA = threeZeros[0];
            int blockB = threeZeros[1];
            int blockC = threeZeros[2];
            
            if (blockA == blockB)
            {
                 solveBlock(sudokuGrid, blockC);
            }
            else
            {
                if (blockA == blockC)
                {
                    solveBlock(sudokuGrid, blockB);
                }
                else
                {
                    solveBlock(sudokuGrid, blockA);
                }
            }
            
            // Sends the information of the two zeros that are next to each other to be solved
            // in the twoZero method and the missing numbers of each being returned.
            
            twoZero(sudokuGrid);
        }
        
        // The third zero's information is sent to the solveBlock method. Then the sudokuGrid
        // array is scanned through by a nested for loop to determine its row (zeroRow) and 
        // column (zeroCol). The missing number is determined using these variables to find the
        // sum and then subtracting from 45 (the total of the whole column) to determine the
        // missing number in the sudokuGrid array.
        
        public static void solveBlock(int[][] sudokuGrid, int block)
        {
            int sum = 0;
            int row = (block / 3) * 3;
            int col = (block % 3) * 3;
            int zeroRow = 0;
            int zeroCol = 0;
            
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    sum += sudokuGrid[row + i][col + j];
                    
                    if (sudokuGrid[row + i][col + j] == 0)
                    {
                        zeroRow = row + i;
                        zeroCol = col + j;
                    }
                }
            }
            
            int missingNumber = 45 - sum;
            sudokuGrid[zeroRow][zeroCol] = missingNumber;
            System.out.print("(" + zeroRow + "," + zeroCol + "," + missingNumber + ") ");
        }
}