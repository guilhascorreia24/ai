

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

class BestFirst {
	static class State {
		private Ilayout layout;
		private State father;
		private double g,f;
		public State(Ilayout l, State n,Ilayout goal) {
			layout = l;
			father = n;
			if (father!=null){
				g = father.g + l.getG();}
			else{ 
				g = 0.0;
				f=0.0;
			}
			f=g+l.getH(goal);
		}
		public String toString() { return layout.toString(); }
		public double getG() {return g;}
		public double getF() {return f;}
	}
	protected Queue<State> abertos;
	private State actual;
	private Ilayout objective;
	
	final private Set<State> sucessores(final State n) {
		Set<State> sucs = new HashSet<>();
		Set<Ilayout> children = n.layout.children();
		for(Ilayout e: children) {
			if (n.father == null || !e.equals(n.father.layout)){
				State nn = new State(e, n,objective);
				sucs.add(nn);
			}
		}
		return sucs;
	}
	
	final public Iterator<State> solve(Ilayout s, Ilayout goal) {
		objective =goal;
		Queue<State> abertos = new PriorityQueue<>(10,(s1, s2) -> (int) Math.signum(s1.getF()-s2.getF()));
		List<State> fechados = new ArrayList<>();
		abertos.add(new State(s, null,goal));
		Set<State> sucs; 
		LinkedList<State> f=new LinkedList<>();
		while(true) {
			if(abertos.isEmpty()) {
				System.exit(0);
			}
			actual=abertos.peek();
			abertos.remove(actual);
			System.out.println(actual);
			if(actual.layout.isGoal(goal)) {
				while(actual!=null) {
					f.addFirst(actual);
					actual=actual.father;
				}
				return f.iterator();
			}
			else {
				sucs=sucessores(actual);
				fechados.add(actual);
				for(State s1:sucs) {
					if(!fechados.contains(s1)) {
						abertos.add(s1);
					}
				}
			}
		}
	}
}
