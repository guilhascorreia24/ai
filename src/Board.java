

import java.util.ArrayList;
import java.util.List;

class Board implements Ilayout, Cloneable {
	private static final int dim=3;
	private int board[][];
	private double g,h;
	public Board(){ board = new int[dim][dim];}
	
	public Board(String str) throws IllegalStateException {
		if (str.length() != dim*dim) throw new IllegalStateException("Invalid arg in Board constructor");
		board = new int[dim][dim];
		int si=0;
		for(int i=0; i<dim; i++)
			for(int j=0; j<dim; j++)
				board[i][j] = Character.getNumericValue(str.charAt(si++));
	}
	
	public String toString() {
		String res="";
		for(int i=0;i<dim;i++) {
			for(int j=0;j<dim;j++) {
				if(board[i][j]==0) res+=" ";
				else res+=board[i][j];
			}
			res+="\n";
		}
		return res;
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
	
	public double impar_par(int a,int b) {
		if(a%2==0 && b%2==0) return 20;
		else if(a%2!=0 && b%2!=0)return 1;
		else return 5;
	}

	public List<Ilayout> children() {
		List<Ilayout> t1=new ArrayList<>();
		for(int i=0;i<dim;i++){
			for(int j=0;j<dim;j++){
				Board cloned=clone();
				cloned.board[0][0]=board[i][j];//left
				cloned.board[i][j]=board[0][0];
				if(board[0][0]!=cloned.board[i][j]){
					cloned.g=impar_par(board[i][j],board[0][0]);
					h+=cloned.g;
					t1.add(cloned);
				}
		
				Board cloned1= clone();
				cloned1.board[0][1]=board[i][j];//right
				cloned1.board[i][j]=board[0][1];
				if(board[0][1]!=cloned.board[i][j]){
					cloned1.g=impar_par(board[i][j],board[0][1]);
					h+=cloned1.g;
					t1.add(cloned1);
				}
		
				Board cloned2=clone();
				cloned2.board[0][2]=board[i][j];//down
				cloned2.board[i][j]=board[0][2];
				if(board[0][2]!=cloned.board[i][j]){
					cloned2.g=impar_par(board[i][j],board[0][2]);
					h+=cloned2.g;
					t1.add(cloned2);
				}
						
				Board cloned3= clone();
				cloned3.board[1][0]=board[i][j];//up
				cloned3.board[i][j]=board[1][0];
				if(board[1][0]!=cloned.board[i][j]){
					cloned3.g=impar_par(board[i][j],board[1][0]);
					h+=cloned3.g;
					t1.add(cloned3);
				}
				
				Board cloned4= clone();
				cloned4.board[1][1]=board[i][j];
				cloned4.board[i][j]=board[1][1];
				if(board[1][1]!=cloned.board[i][j]){
					cloned4.g=impar_par(board[i][j],board[1][1]);
					h+=cloned4.g;
					t1.add(cloned4);
				}
				
				Board cloned5= clone();
				cloned5.board[1][2]=board[i][j];
				cloned5.board[i][j]=board[1][2];
				if(board[1][2]!=cloned.board[i][j]){
					cloned5.g=impar_par(board[i][j],board[1][2]);
					h+=cloned5.g;
					t1.add(cloned5);
				}

				Board cloned6= clone();
				cloned6.board[2][0]=board[i][j];
				cloned6.board[i][j]=board[2][0];
				if(board[2][0]!=cloned.board[i][j]){
					cloned6.g=impar_par(board[i][j],board[2][0]);
					h+=cloned6.g;
					t1.add(cloned6);
				}

				Board cloned7= clone();
				cloned7.board[2][1]=board[i][j];
				cloned7.board[i][j]=board[2][1];
				if(board[2][1]!=cloned.board[i][j]){
					cloned7.g=impar_par(board[i][j],board[2][1]);
					h+=cloned7.g;
					t1.add(cloned7);
				}

				Board cloned8= clone();
				cloned8.board[2][2]=board[i][j];
				cloned8.board[i][j]=board[2][2];
				if(board[2][2]!=cloned.board[i][j]){
					cloned8.g=impar_par(board[i][j],board[2][2]);
					h+=cloned8.g;
					t1.add(cloned8);
				}
			}
		}

		return t1;
	}
	
	
	public boolean isGoal(Ilayout l) {
		Board o=(Board) l;
		for(int i=0;i<dim;i++) {
			for(int j=0;j<dim;j++) {
				if(board[i][j]!=o.board[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public double getG() {
		return g;
	}
	@Override
	public double getH(Ilayout l) {
		if(isGoal(l)){
			return h;
		}
		return 0;
	}
}
