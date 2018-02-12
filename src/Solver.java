import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private boolean isSolvable;
    int moveCount = 1;
    public Solver(Board initial)  {

        MinPQ<Node> initialPQ = new MinPQ<>();
        MinPQ<Node> twinPQ = new MinPQ<>();

        initialPQ.insert(new Node(initial, null));
        twinPQ.insert(new Node(initial.twin(), null));

        Node curInitNode = initialPQ.delMin();
        Node curTwinNode = twinPQ.delMin();
        while(! (curInitNode.curBoard.isGoal())
                && !(curTwinNode.curBoard.isGoal())){  //To do: further Optimization can be performed
            for(Board b: curInitNode.curBoard.neighbors()){
                if(b.equals(curInitNode.curBoard)) continue;
                Node node = new Node(b,curInitNode.curBoard);
                initialPQ.insert(node);
                node.setMoveCount(moveCount);
                System.out.println(b);
            }
            for(Board b: curTwinNode.curBoard.neighbors()){
                if(b.equals(curTwinNode.curBoard)) continue;
                Node node = new Node(b, curTwinNode.curBoard);
                twinPQ.insert(node);
                node.setMoveCount(moveCount);
                System.out.println(b);
            }
            moveCount++;
            curInitNode = initialPQ.delMin();
            curTwinNode = twinPQ.delMin();

        }
        if(curInitNode.curBoard.isGoal()) isSolvable = true;
        else if(curTwinNode.curBoard.isGoal()) isSolvable = false;

    }         // find a solution to the initial board (using the A* algorithm)


    private class Node implements Comparable<Node>{
        private Board curBoard;
        private Board prevBoard;
        private int movesSofar;

        public Node(Board thatCur, Board thatPrev){
            this.curBoard = thatCur;
            this.prevBoard = thatPrev;
        }
        private void setMoveCount(int countMoves){
            this.movesSofar = countMoves;
        }
        private int priority(){
            return movesSofar + curBoard.manhattan();
        }

        @Override
        public int compareTo(Node o) {
            if(this.priority() > o.priority()) return 1;
            if(this.priority() == o.priority()) return 0;
            return -1;
        }
    }

//    public boolean isSolvable() {
//        return isSolvable;
//    }           // is the initial board solvable?
//    public int moves(){
//
//    }                     // min number of moves to solve initial board; -1 if unsolvable
//    public Iterable<Board> solution(){
//
//    }      // sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args){
        In in = new In (args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                blocks[i][j] = in.readInt();
            }
        }
        Solver solver = new Solver(new Board(blocks));
        System.out.println(solver.isSolvable);
    } // solve a slider puzzle (given below)
}