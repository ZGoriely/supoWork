package supoWork.supo3;

public class NQueens {
	
	int n;
	static boolean QUEEN = true;
	static boolean EMPTY = false;

	public NQueens(int nTemp) {
		n = nTemp;
	}
	
	public boolean[][] findBoard() {
		boolean[][] board = new boolean[n][n];
		recurseSearch (board, 0);
		return board;
	}
	
	private boolean recurseSearch (boolean[][] board, int row) {
		// Explored whole board
		if (row >= n) return true;
		
		// Try queen in each position
		for (int i = 0; i < n; i++) {
			if (validPosition(board, row, i)) {
				board[row][i] = QUEEN;
				// Recurse search for each valid queen position
				if (recurseSearch (board, row+1)) return true;
				// Undo queen if not valid
				else board[row][i] = EMPTY;
			}
		}
		// Return false (backtrack) if no position valid
		return false;
	}
	
	private boolean validPosition (boolean[][] board, int row, int col) {
		return rowEmpty(board, row) && colEmpty(board, col) && diagEmpty(board, row, col);
	}
	
	private boolean rowEmpty (boolean[][] board, int row) {
		for (int i = 0; i < n; i++) {
			if (board[row][i] == QUEEN) return false;
		}
		return true;
	}
	
	private boolean colEmpty (boolean[][] board, int col) {
		for (int i = 0; i < n; i++) {
			if (board[i][col] == QUEEN) return false;
		}
		return true;
	}
	
	private boolean diagEmpty (boolean[][] board, int row, int col) {
		// Forward diagonal
		int shiftRow = (row > col) ? row - col : 0;
		int shiftCol = (row < col) ? col - row : 0;
		for (int i = 0; i < n - Math.abs(row-col); i++) {
			if (board[i+shiftRow][i+shiftCol] == QUEEN) return false;
		}
		// Backwards diagonal
		int backRow = (row + col < n) ? 0 : row + col - n + 1;
		int backCol = (row + col < n) ? row + col : n-1;
		for (int i = 0; i < Math.min(n - backRow, backCol + 1); i++) {
			if (board[backRow + i][backCol - i] == QUEEN) return false;
		}
		return true;
	}
	
	public void printBoard (boolean[][] board) {
		for (boolean[] row : board) {
			for (boolean cell : row) {
				System.out.print(cell ? "|Q" : "| ");
			}
			System.out.println("|");
		}
	}
	
	public static void main (String[] args) {
		NQueens imp = new NQueens(20);
		imp.printBoard(imp.findBoard());
	}

}
