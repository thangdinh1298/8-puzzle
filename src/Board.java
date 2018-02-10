public class Board {
    public char[] board;
    private int dimension;
    private int hamming;
    private int manhattan;
    public Board(int[][] blocks){
        int count = 0;
        hamming = 0;
        manhattan = 0;
        dimension = blocks.length;
        board = new char[dimension*dimension];
        for(int i = 0; i < dimension(); i++){
            for(int j = 0; j < dimension();j++){
                board[count] =(char) (blocks[i][j]+65);
                count++;
            }
        }
    }       // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public int dimension(){
        return dimension;
    }               // board dimension n
    public int hamming(){
        for(int i = 0; i < dimension*dimension - 1; i++){
            if((int) board[i] - 65 != i+1) {
                hamming++;
            }
        }
        return hamming;
    }           // number of blocks out of place
    private void swap(char a,char b){
        char temp;
        temp = a;
        a = b;
        b = temp;
    }
    public int manhattan(){
        int row = 0;
        int col = 0;
        for(int i = 0; i < dimension()*dimension(); i ++){
            if((int)board[i] - 65 == 0) continue;
            row = i/dimension() - ((int) board[i] - 65 - 1)/dimension();
            col = i%dimension() - ((int) board[i] - 65 - 1)%dimension();
            manhattan += Math.abs(row) + Math.abs(col);
//            System.out.println((int) board[i] - 65 + ": " + "row " + Math.abs(row) + " col " + Math.abs(col)); //for debugging
        }
        return manhattan;
    }                 // sum of Manhattan distances between blocks and goal
    public boolean isGoal(){
        if((int)board[dimension()*dimension() -1 ] - 65 != 0) return false;
        for(int i = 0; i < dimension()*dimension() - 1 ; i ++){
            if ((int)board[i] - 65 != i+1) return false;
        }
        return true;
    }                // is this board the goal board?
//    public Board twin(){
//
//    }                    // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y){
        if(this == y) return true;
        if(this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if(this.dimension() != that.dimension()) return false;
        for(int i = 0; i < dimension()*dimension();i++){
            if(this.board[i] != that.board[i]) return false;
        }
        return true;
    }        // does this board equal y?
//    public Iterable<Board> neighbors(){
//
//    }     // all neighboring boards
    public String toString(){
        int count = 0;
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                s.append(String.format("%2d ",(int) board[count++] - 65));
            }
            s.append("\n");
        }
        return s.toString();
    }               // string representation of this board (in the output format specified below)

    public static void main(String[] args){
        int a[][] = {{1,2,3},{4,0,5},{7,8,6}};
        Board board = new Board(a);
        System.out.println(board);
        System.out.println(board.isGoal());
        System.out.println(board.hamming());
    } // unit tests (not graded)
}
