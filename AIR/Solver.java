import java.io.IOException;
import java.io.File;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Stack;
import java.util.Scanner;
public class Solver {

    private SearchNode goal;

    private class SearchNode {
        private int moves;
        private Board board;
        private SearchNode prev;

        public SearchNode(Board initial) {
            moves = 0;
            prev = null;
            board = initial;
        }
    }

    public Solver(Board initial) {
        PriorityOrder order  = new PriorityOrder();
        PriorityQueue<SearchNode> PQ = new PriorityQueue<SearchNode>(order);
        PriorityQueue<SearchNode> twinPQ = new PriorityQueue<SearchNode>(order);

        SearchNode Node = new SearchNode(initial);
        SearchNode twinNode = new  SearchNode(initial);         //twin created to detect infeasible cases

        PQ.add(Node);
        twinPQ.add(twinNode);

        SearchNode min = PQ.remove();
        SearchNode twinMin = twinPQ.remove();

        while(!min.board.isGoal() || !twinMin.board.isGoal()) {         //repeat till goal state not achieved

            System.out.println(min.board);
            System.out.println("=================");
            //for each neighbor 
            for(Board b: min.board.neighbors()) {
                if(min.prev == null || !(b.equals(min.prev.board))) {
                    SearchNode n = new SearchNode(b);
                    n.moves = min.moves + 1;
                    n.prev = min;
                    PQ.add(n);
                }

            }

            for(Board b: twinMin.board.neighbors()) {
                if(twinMin.prev == null || !(b.equals(twinMin.prev.board))) {
                    SearchNode n = new SearchNode(b);
                    n.moves = twinMin.moves + 1;
                    n.prev = twinMin;
                    twinPQ.add(n);
                }

            }

            min = PQ.remove();
            twinMin = twinPQ.remove();

        }

        if(min.board.isGoal()) goal = min;
        else goal = null;

    }

    private class PriorityOrder implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode a, SearchNode b) {
            int pa = a.board.manhattan() + a.moves;
            int pb = b.board.manhattan() + b.moves;

            if(pa > pb) return 1;
            else if(pa < pb) return -1;
            else return 0;

        }
    }

    public boolean isSolvable() {
        return goal != null;
    }

    public int moves() {
        if(!isSolvable())
            return -1;
        return goal.moves;
    }

    public Iterable<Board> solution() {
        if(!isSolvable()) return null;
        Stack<Board> s = new Stack<Board>();

        for(SearchNode n = goal; n != null; n = n.prev)
            s.push(n.board);
        return s;
    }



    public static void main(String []args) throws IOException {
        File file = new File(args[0]);
        Scanner sc = new Scanner(file);
        int N = sc.nextInt();

        int [][]blocks = new int[N][N];

        for(int i=0; i< N; i++){
            for(int j=0; j<N; j++){
                blocks[i][j] = sc.nextInt();               
            }
        }
        
        Board initial = new Board(blocks);

        // solver class
        Solver solver = new Solver(initial);

        if(!solver.isSolvable())
            System.out.println("No solution available");
        else {
            System.out.println("Minimum number of moves: "+ solver.moves());
            for(Board board : solver.solution()) {
                System.out.println(board);
            }
        }

    }

}