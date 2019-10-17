
import java.util.HashSet;
import java.util.Iterator;
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
	private Ilayout goal1;
	private Stack<State> path=new Stack<>();
	private State inicial,next;
	final public Iterator<State> solve(Ilayout s, Ilayout goal) {
		goal1=goal;
		inicial=new State(s,null,goal);
		path.push(inicial);
		while(true){
			next=search(path,0,inicial.layout.getH(goal));
			if(next!=null && next.layout.isGoal(goal)){
				return path.iterator();
			}
			//System.out.println(next.layout);
			inicial=next;
		}
	}

	final private State search(Stack<State> path, double g, double bound) {
		State node=path.lastElement();
		State n=node;
		n.f=g+node.layout.getH(goal1);
		if(n.getF()>bound){return n;}
		if(node!=null && node.layout.isGoal(goal1)){
			return node;
		}
		State finale=null;
		double min=Double.MAX_VALUE;
		Set<State> sucs=sucessores(node);
		for(State s:sucs){
			if(!path.contains(s)){
				path.push(s);
				next=search(path,g+node.layout.getH(s.layout), bound);
				System.out.println(next.layout);
				if(next.layout!=null && next.layout.isGoal(goal1)){
					return next;
				}
				if(next!=null && next.f<min){
					min=next.f;
					finale=next;
				}
				path.pop();
			}
		}
		return finale;
	}
}
