

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.management.RuntimeErrorException;


class BestFirst {
	static class State {
		private Ilayout layout;
		private State father;
		private double g,f;
		private Object o;

		public State(Ilayout l, State n, Ilayout goal) {
			layout = l;
			father = n;
			if (father != null) {
				g = father.g + l.getG();
				f = g + l.getH(goal);
			} else {
				g = 0.0;
				f = 0;
			}
		}

		public String toString() {
			return layout.toString();
		}

		public double getG() {
			return g;
		}

		@Override
		public int hashCode() {
			return 0;
		}
		
		@Override
        public boolean equals(Object o){
            if(o instanceof State){
                State state = (State)o;
                return layout.isGoal(state.layout); 
            }
            throw new RuntimeErrorException(null, "Object is not of type State");
        }

		public double getF() {
			return f;
		}
	}
	private State actual;
	private Ilayout objective;
	private Ilayout goal1;
	
	final private List<State> sucessores(final State n) {
		List<State> sucs = new ArrayList<>();
		List<Ilayout> children = n.layout.children();
		for(Ilayout e: children) {
			if (n.father == null || !e.equals(n.father.layout)){
				State nn = new State(e, n,goal1);
				sucs.add(nn);
			}
		}
		return sucs;
	}
	
	final public Iterator<State> solve(Ilayout s, Ilayout goal) {
		objective =s;
		goal1=goal;
		Queue<State> abertos = new PriorityQueue<>(10,(s1, s2) -> (int) Math.signum(s1.getF()-s2.getF()));
		List<State> fechados = new ArrayList<>();
		abertos.add(new State(s, null,goal));
		List<State> sucs; 
		LinkedList<State> f=new LinkedList<>();
		int min=Integer.MAX_VALUE;
		while(true)  {
			if(abertos.isEmpty()) {
				System.exit(0);
			}
			actual=abertos.poll();

			if(actual.layout.isGoal(goal)) {
				objective=actual.layout;
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
					if(!fechados.contains(s1) && !abertos.contains(s1)) {
						abertos.add(s1);
				}
			}
		}
	}
	}

	public double recur_solve(List<State> a,double g,double f){
		
	}
}
