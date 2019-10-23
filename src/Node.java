/*
 * Author: Surendra Chekuri
 * File name: Node.java
 * Dependencies: CommonConstants.java
 * Description: Maintains the state of the puzzle, cost, heuristic value of the puzzle,cost
 * */
import java.util.Arrays;

public class Node {
	int cost;
	int heuristicValue;
	int fValue;
	int blankPosition;
	int puzzle[] = new int[CommonConstants.PUZZLE_TYPE];
    
	public void setBlankPosition(int blankPosition) {
		this.blankPosition = blankPosition;
	}

	Node(int cost, int heuristicValue, int fValue, int[] puzzle) {
		this.cost = cost;
		this.puzzle = puzzle;
		this.heuristicValue = IDAStarHelper.findHeuristic(puzzle);// findHeuristic(puzzle);
		this.fValue = this.cost + this.heuristicValue;
		this.blankPosition = IDAStarHelper.blankPosition(this.puzzle);
	}

	public int getBlankPosition() {
		return blankPosition;
	}

	public int getCost() {
		return cost;
	}

   public int getHeuristicValue() {
		return this.heuristicValue;
	}
	public int getfValue() {
		return this.fValue;
		// return 7;
	}

	public int[] getPuzzle() {
		return puzzle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(puzzle);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		
		int[] other = (int[]) obj;
		if (!Arrays.equals(puzzle, other))
			return false;
		return true;
	}

	/*
	 * public void moveTiles(String move,int position) { int swapIndex=0;
	 * if(move=="U") swapIndex=position+3; if(move=="D") swapIndex=position-3;
	 * if(move=="L") swapIndex=position+1; if(move=="R") swapIndex=position-1;
	 * int temp=this.puzzle[swapIndex];
	 * this.puzzle[swapIndex]=this.puzzle[position]; this.puzzle[position]=temp;
	 * }
	 */
/*	private int findHeuristic(int[] puzzle) {
		if (puzzle == null)
			return 1;
		int tilesOutOfPlace = 0;
		for (int i = 0; i < puzzle.length; i++) {
			if (puzzle[i] != CommonConstants.BLANK
					&& CommonConstants.goalState[i] != puzzle[i])
				tilesOutOfPlace++;
		}
		return tilesOutOfPlace;

	}*/
}
