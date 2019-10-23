import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;
public class Board {

    private int N;      //dimension of the board
    private int[] board; //board of interest

    public Board(int [][] blocks) {
        N = blocks[0].length;
        board = new int [N * N];
        for(int i=0; i < N; i++) {
            for(int j=0; j < N; j++){
                board[i * N + j] = blocks[i][j];
            }
        }

    }

    private Board(int [] board) {
        N = (int)Math.sqrt(board.length);
        this.board = new int[board.length];
        for(int i=0; i< board.length; i++) 
            this.board[i] = board[i];
    }

    public int dimension() {
        return N;
    }

    public int hamming() {          //number of blocks out of place
        int count = 0;
        for(int i=0;i<N*N; i++) {
            if(board[i] != i + 1 && board[i] != 0)
                count++;
        }
        return count;
    }

    public int manhattan() {
        int sum = 0;
        for(int i=0;i < N * N; i++) {
            if(board[i] != i + 1 && board[i] != 0)
                sum += manhattan(board[i], i);
        }
        return sum;
    }

    private int manhattan(int goal, int current) {
        int row, cols;          // row col count
        row = Math.abs((goal - 1)/N - (current - 1)/ N);     //misplaced row count
        cols = Math.abs((goal - 1) % N - (current - 1) % N);  // misplaced  col count
        return row + cols;
    }

    public boolean isGoal() {
        for(int i=0; i< N * N -1; i++) {
            if(board[i] != i + 1)
                return false;
        }
        return true;
    }
    
    private Board exch(Board a, int i, int j) {
        // obtain the array by exchanging the position
        int temp = a.board[i];
        a.board[i] = a.board[j];
        a.board[j] = temp;
        return a;
    }

    public boolean equals(Object y) {
        //does this board equal to y
        if(y == this)   return true;
        if(y == null)   return false;

        if(y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        return Arrays.equals(this.board, that.board);
    }


    public Iterable<Board> neighbors() {
        int index = 0;
        boolean found = false;
        Board neighbor;

        Queue<Board> q = new LinkedList<Board>();

        for(int i=0;i< board.length; i++) {
            if(board[i] == 0) {
                index = i;
                found = true;
                break;
            }
        }

        if(!found) return null;

        if((index / N) != 0) {          // if not first row
                neighbor = new Board(board);
                exch(neighbor, index, index - N); //exchange it with upper row
                q.add(neighbor);
        }

        if((index / N) != ( N - 1 )) {  // if not last row
            neighbor = new Board(board);
            exch(neighbor, index, index + N);      //exchange it withlower row
            q.add(neighbor);
        }

        if((index % N) != 0) {         // if not the left column
            neighbor = new Board(board);
            exch(neighbor, index, index - 1);   //exchange it left column
            q.add(neighbor);
        }
        if((index % N) != (N-1)) {      // if not rightmost column
            neighbor = new Board(board);
            exch(neighbor, index, index + 1);   //exchange it with right column
            q.add(neighbor);
        }
        return q;
    }


    public String toString() {          // string representation of board

        StringBuilder s = new StringBuilder();

        s.append(N + "\n");

        for(int i = 0; i < board.length; i++) {
            s.append(String.format("%2d", board[i]));
            if(i % N == 0)
                s.append("\n");
        }
        return s.toString();

    }  

}