
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import javax.management.RuntimeErrorException;


class BestFirst {
	static class State {
		private Ilayout layout;
		private State father;
		private double g,f;

		public State(Ilayout l, State n, Ilayout goal) {
			layout = l;
			father = n;
			if (father != null) {
				g = father.g + l.getG();
				f = g + l.getH(goal);
			} else {
				g = 0.0;
				f = 0.0;
			}
		}

		public String toString() {
			return layout.toString();
		}

		public double getG() {
			return g;
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
	private Ilayout goal1;
	private Stack<State> visited=new Stack<>();
	private LinkedList<State> path=new LinkedList<>();
	private State test;
	private State inicial;
	
	final private Set<State> sucessores(final State n) {
		Set<State> sucs = new HashSet<>();
		Set<Ilayout> children = (Set<Ilayout>) n.layout.children();
		for(Ilayout e: children) {
			if (n.father == null || !e.equals(n.father.layout)){
				State nn = new State(e, n,goal1);
				sucs.add(nn);
			}
		}
		return sucs;
	}
	
	final public Iterator<State> solve(Ilayout s, Ilayout goal) {
		goal1=goal;
		inicial=new State(s,null,goal);
		while(true){
			path.clear();
			visited.clear();
			State next=search(inicial,inicial.getF(),visited);
			if(next!=null && next.layout.isGoal(goal)){
				return path.iterator();
			}
			inicial=next;
		}
	}

	final private State search(State state, double f, Stack<State> visited) {
		if(state!=null){
			visited.push(state);
			path.add(state);
		}
		if( state.layout.isGoal(goal1)){
			return state;
		}
		if(state.getF()>f){
			return null;
		}
		State test = null;
		Set<State> sucs=sucessores(state);
		for(State s:sucs){
			if(!visited.contains(s)){
				test=search(s, f, visited);
				if(test!=null){
					return test;
				}
				path.remove(state);
			}
		}
		return inicial;
	}
}
