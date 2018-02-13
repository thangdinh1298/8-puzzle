import edu.princeton.cs.algs4.Stack;

public class Board {
    private char[] board;
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
        int temp = 0;
        for(int i = 0; i < dimension*dimension ; i++){
            if((int) board[i] - 65 == 0) continue;
            if((int) board[i] - 65 != i+1) {
                temp++;
            }
        }
        hamming = temp;
        return hamming;
    }           // number of blocks out of place
    public int manhattan(){
        int row = 0;
        int col = 0;
        int temp = 0;
        for(int i = 0; i < dimension()*dimension(); i ++){
            if((int)board[i] - 65 == 0) continue;
            row = i/dimension() - ((int) board[i] - 65 - 1)/dimension();
            col = i%dimension() - ((int) board[i] - 65 - 1)%dimension();
            temp += Math.abs(row) + Math.abs(col);
        }
        manhattan = temp;
        return manhattan;
    }                 // sum of Manhattan distances between blocks and goal
    public boolean isGoal(){
        if((int)board[dimension()*dimension() -1 ] - 65 != 0) return false;
        for(int i = 0; i < dimension()*dimension() - 1 ; i ++){
            if ((int)board[i] - 65 != i+1) return false;
        }
        return true;
    }                // is this board the goal board?
    public Board twin(){
        int[][] blocks = new int[dimension()][dimension()];
        int count= 0;
        int row = 0,col = 0;
        for(int i = 0; i < dimension();i++) {
            for (int j = 0; j < dimension(); j++) {
                blocks[i][j] = (int) board[count++] - 65;
            }
        }
        count = 0;
        boolean flag = true;
        for(int i = 0; i < dimension(); i++){
            if(flag){
                for(int j = 0; j < dimension(); j++){
                    if(blocks[i][j] == 0) continue;
                    else{
                        count++;
                        if(count >= 2) {
                            swap(blocks,i,j,row,col);
                            flag = false;
                            break;
                        }
                        row = i; col = j;
                    }
                }
            }
        }
        return new Board(blocks);
    }                    // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y){
        if(y == null) return false;
        if(this == null) return false;
        if(this == y) return true;
        if(this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if(this.dimension() != that.dimension()) return false;
        for(int i = 0; i < dimension()*dimension();i++){
            if(this.board[i] != that.board[i]) return false;
        }
        return true;
    }        // does this board equal y?
    public Iterable<Board> neighbors(){
        int[][] blocks = new int[dimension()][dimension()];
        int count = 0;
        int row = 0, col = 0; // fix variable might not have been initialized
        Stack<Board> iterable = new Stack<>();
        for(int i = 0; i < dimension();i++) {
            for (int j = 0; j < dimension(); j++) {
                blocks[i][j] = (int) board[count++] - 65;
                if (blocks[i][j] == 0) {
                    row = i;
                    col = j;
                }
            }
        }
        if (row + 1 < dimension()) {
            swap(blocks, row, col, row + 1, col);
            Board temp = new Board(blocks);
            iterable.push(temp);
            swap(blocks, row, col, row + 1, col);
            }
        if (row - 1 >= 0) {
            swap(blocks, row, col, row - 1, col);
            Board temp = new Board(blocks);
            iterable.push(temp);
            swap(blocks, row, col, row - 1, col);
            }
        if (col + 1 < dimension()) {
            swap(blocks, row, col, row, col + 1);
            Board temp = new Board(blocks);
            iterable.push(temp);
            swap(blocks, row, col, row, col + 1);
            }
        if (col - 1 >= 0) {
            swap(blocks, row, col, row, col - 1);
            Board temp = new Board(blocks);
            iterable.push(temp);
            swap(blocks, row, col, row, col - 1);
            }
        return iterable;
    }     // all neighboring boards

    private void swap(int[][] blocks,int row, int col, int row1, int col1){
        int temp = blocks[row][col];
        blocks[row][col] = blocks[row1][col1];
        blocks[row1][col1] = temp;
    }
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
//        }
    } // unit tests (not graded)
}
