
import java.util.*;

import javax.management.RuntimeErrorException;

class Board implements Ilayout, Cloneable {
	private static final int dim = 3;
	private int board[][];
	private double g;

	public Board() {
		board = new int[dim][dim];
	}

	public Board(String str) throws IllegalStateException {
		if (str.length() != dim * dim)
			throw new IllegalStateException("Invalid arg in Board constructor");
		board = new int[dim][dim];
		int si = 0;
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++)
				board[i][j] = Character.getNumericValue(str.charAt(si++));
	}

	public String toString() {
		String result = "";
		for (int y = 0; y < dim; y++) {
			for (int x = 0; x < dim; x++) {
				if (board[y][x] == 0) {
					result += " ";
				} else {
					result += board[y][x];
				}
			}
			result += "\n";
		}
		return result;
	}

	public boolean equals(Object object) {
		if (object instanceof Board) {
			Board b = (Board) object;
			int l_board[][] = b.board;

			for (int x = 0; x < dim; x++) {
				for (int y = 0; y < dim; y++) {
					if (l_board[x][y] != board[x][y]) {
						return false;
					}
				}
			}
			return true;
		}
		throw new RuntimeErrorException(null, "Object not instance of ILayout");
	}

	public boolean isGoal(Ilayout l) {
		return equals(l);
	}

	public double getG() {
		return g;
	}

	@Override
	public Board clone() {
		Board o=new Board();
		for(int i=0;i<dim;i++) {
			for(int j=0;j<dim;j++) {
				o.board[i][j]=board[i][j];
			}
		}
		return o;
	}

	public Board exchange(int i,int j,int addline,int addcol){
		Board buffer_board = clone();
		int n=buffer_board.board[i][j];
		buffer_board.board[i][j] = board[((i+addline)%3+3)%3][((j+addcol)%3+3)%3];
		buffer_board.board[((i+addline)%3+3)%3][((j+addcol)%3+3)%3] = n;
		buffer_board.g=impar_par(buffer_board.board[((i+addline)%3+3)%3][((j+addcol)%3+3)%3], buffer_board.board[i][j]);
		return buffer_board;
	}
	public double impar_par(int a,int b) {
		if(a%2==0 && b%2==0) return 20;
		else if(a%2!=0 && b%2!=0)return 1;
		else return 5;
	}
	public List<Ilayout> children() {
	List<Ilayout> childrens=new ArrayList<>();
		for(int i=0;i<dim;i++){
			for(int j=i+1;j<dim;j++){
				childrens.add(exchange(i, j, -1, 0));
				childrens.add(exchange(i, j,0,-1));
				childrens.add(exchange(i, j, 1, 0));
				childrens.add(exchange(i, j, 0, 1));
				childrens.add(exchange(i, j, -1, -1));
				childrens.add(exchange(i, j, -1, 1));
				childrens.add(exchange(i, j, 1, 1));
				childrens.add(exchange(i, j, 1, -1));			
			}
		}
		
		// System.out.println("Children Size: " + childrens.size());
		return childrens;
	}

	
private double getmin(double[] list){
    double max = Double.MAX_VALUE;
    for(int i=0; i<list.length; i++){
        if(list[i] < max){
            max = list[i];
        }
    }
    return max;
}

	@Override
	public double getH(Ilayout l) {
		Board o=(Board) l;
		double h=0;
		for(int i=0;i<dim;i++){
			for(int j=0;j<dim;j++){
				double[] k=new double [8];
				if(o.board[i][j]==exchange(i, j, -1, 0).board[i][j]){
					k[0]=this.impar_par(o.board[i][j],board[i][j]);
				}
				if(o.board[i][j]==exchange(i, j, 0, -1).board[i][j]){
					k[1]=this.impar_par(o.board[i][j],board[i][j]);
				}
				if(o.board[i][j]==exchange(i, j, 1, 0).board[i][j]){
					k[2]=this.impar_par(o.board[i][j], board[i][j]);
				}
				if(o.board[i][j]==exchange(i, j, 0, 1).board[i][j]){
					k[3]=this.impar_par(o.board[i][j], board[i][j]);
				}
				if(o.board[i][j]==exchange(i, j, -1, -1).board[i][j]){
					k[4]=this.impar_par(o.board[i][j], board[i][j]);
				}
				if(o.board[i][j]==exchange(i, j, -1, 1).board[i][j]){
					k[5]=this.impar_par(o.board[i][j], board[i][j]);
				}
				if(o.board[i][j]==exchange(i, j, 1, 1).board[i][j]){
					k[6]=this.impar_par(o.board[i][j], board[i][j]);
				}
				if(o.board[i][j]==exchange(i, j, 1, -1).board[i][j]){
					k[7]=this.impar_par(o.board[i][j], board[i][j]);
				}
				h+=getmin(k);
			}
		}
		return h;
	}*/
}
