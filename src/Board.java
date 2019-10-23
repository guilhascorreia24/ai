
import java.util.ArrayList;
import java.util.List;

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

	

	@Override
	public double getH(Ilayout l) {
		Board o=(Board) l;
		Board p=this;
		double h=0;
		for(int i=0;i<dim;i++){
			for(int j=0;j<dim;j++){
				int n=p.board[i][j];
				if(n==o.board[i][j]){
				}
				else{
					double h1=0;
					find(n,o);
					h1=impar_par(n,p.board[x][y]);
					int x1=x,y1=y;
					double h2=0;
					n=o.board[i][j];
					find(n, p);
					h2=impar_par(n,p.board[i][j]);
					System.out.println(p.board[i][j]+" "+p.board[x1][y1]+"//"+p.board[i][j]+" "+p.board[x][y]+" "+h1+" "+h2);
					if(n==p.board[i][j]){}
					else if(h1<=h2){
						int u=p.board[i][j];
						p.board[i][j]=p.board[x1][y1];
						p.board[x1][y1]=u;
						if(h1==20){
							desvio_do_20(Board p, int i, int j);
						}
						h+=h1;
					}
					else{
						int u=p.board[i][j];
						p.board[i][j]=p.board[x][y];
						p.board[x][y]=u;
						h+=h2;
					}
					System.out.println(p);
				}
			}
		}
		System.out.println(h);
		return h;
	}


	private void desvio_do_20(Board p,int i,int j){
		if(p.board[i+2][j])
	}

	private void ao_rendor(int i,int j,Board p){
		if(p.board[i+1][j]%2!=0){
						x=i+1;
			y=j;
		}
		else if(p.board[i-1][j]%2!=0) {
						x=i-1;
			y=j;
		}
		else if(p.board[i][j+1]%2!=0 ) {
						x=i;
			y=j+1;
		}
		else if(p.board[i][j-1]%2!=0){
						x=i;
			y=j-1;
		}
		else if( p.board[i+1][j+1]%2!=0){
						x=i+1;
			y=j+1;
		}
		else if(p.board[i-1][j+1]%2!=0){
						x=i-1;
			y=j+1;
		}
		else if(p.board[i+1][j-1]%2!=0){
						x=i+1;
			y=j-1;
		}
		else if(p.board[i-1][j-1]%2!=0){
			x=i-1;
			y=j-1; 
		}
	]
private int x,y;
	private void find(int m,Board p){
		for(int i=0;i<dim;i++){
			for(int j=0;j<dim;j++){
				if(m==p.board[i][j]){
					x=i;y=j;
				}
			}
		}
	}
}
