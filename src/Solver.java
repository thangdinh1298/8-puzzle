import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private boolean isSolvable;
    private int moves;
    private Node lastNode;
    public Solver(Board initial)  {

        MinPQ<Node> initialPQ = new MinPQ<>();
        MinPQ<Node> twinPQ = new MinPQ<>();

        Node pseudoInitNode = new Node(null,null);
        Node pseudoTwinNode = new Node(null,null);

        Node firstInitNode = new Node(initial, pseudoInitNode);
        Node firstTwinNode = new Node(initial, pseudoTwinNode);

        initialPQ.insert(firstInitNode);
        twinPQ.insert(firstTwinNode);

        Node curInitNode = initialPQ.delMin();
        Node curTwinNode = twinPQ.delMin();

        while(! (curInitNode.curBoard.isGoal())
                && !(curTwinNode.curBoard.isGoal())){  //To do: further Optimization can be performed
            for(Board b: curInitNode.curBoard.neighbors()){
                if(b.equals(curInitNode.prevNode.curBoard)) continue;
                Node node = new Node(b,curInitNode);
                node.setMoveCount(curInitNode.movesSofar+1);
                initialPQ.insert(node);
            }
            for(Board b: curTwinNode.curBoard.neighbors()){
                if(b.equals(curTwinNode.prevNode.curBoard)) continue;
                Node node = new Node(b, curTwinNode);
                node.setMoveCount(curTwinNode.movesSofar+1);
                twinPQ.insert(node);
            }
            curInitNode = initialPQ.delMin();
            curTwinNode = twinPQ.delMin();

        }

        firstInitNode.prevNode = null;
        firstTwinNode.prevNode = null;
        if(curInitNode.curBoard.isGoal()) {
            isSolvable = true;
            moves = curInitNode.movesSofar;
            lastNode = curInitNode;
        }
        else if(curTwinNode.curBoard.isGoal()) {
            isSolvable = false;
            moves = -1;
        }
        System.out.println(this.moves());
    }         // find a solution to the initial board (using the A* algorithm)


    private class Node implements Comparable<Node>{
        private Board curBoard;
        private Node prevNode;
        private int movesSofar;

        public Node(Board thatCur, Node thatPrev){
            this.curBoard = thatCur;
            this.prevNode = thatPrev;
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
            if(this.priority() == o.priority()){
                if(this.curBoard.manhattan() > o.curBoard.manhattan()) return 1;
                if(this.curBoard.manhattan() == o.curBoard.manhattan()) return 0;
                return -1;
            }
            return -1;
        }
    }

    public boolean isSolvable() {
        return isSolvable;
    }           // is the initial board solvable?
    public int moves(){
        return moves;
    }                     // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution(){
        if(!isSolvable()) return null;
        Stack<Board> s = new Stack<>();
        s.push(lastNode.curBoard);
        while(lastNode.prevNode != null){
            s.push(lastNode.prevNode.curBoard);
            lastNode = lastNode.prevNode;
        }
        System.out.println(s.size());
        return s;
    }      // sequence of boards in a shortest solution; null if unsolvable
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
        for(Board b: solver.solution()){
            System.out.println(b);
        }
    } // solve a slider puzzle (given below)
}