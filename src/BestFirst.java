

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.management.RuntimeErrorException;

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
		        @Override
        public boolean equals(Object o){
            if(o instanceof State){
                State state = (State)o;
                return layout.isGoal(state.layout); 
            }
            throw new RuntimeErrorException(null, "Object is not of type State");
        }
		public String toString() { return layout.toString(); }
		public double getG() {return g;}
		public double getF() {return f;}
	}
	protected Queue<State> abertos;
	private State actual;
	//private Ilayout objective;
	private double depth = 0;
    private Ilayout objective;
    private double lowerF;

	
	final private List<State> sucessores(final State n) {
		List<State> sucs = new ArrayList<>();
		List<Ilayout> children =  n.layout.children();
		for(Ilayout e: children) {
				State nn = new State(e, n,objective);
				sucs.add(nn);
		}
		return sucs;
	}

	/*final public Iterator<State> solve(Ilayout s, Ilayout goal) {
		objective =goal;
		Queue<State> abertos = new PriorityQueue<>(10,(s1, s2) -> (int) Math.signum(s1.getF()-s2.getF()));
		abertos.add(new State(s, null,goal));
		double max=new State(s,null,goal).getF();
		double nextmax=-1;
		LinkedList<State> f=new LinkedList<>();
		while(true) {
			if(abertos.isEmpty()) {
				System.exit(0);
			}
			actual=abertos.poll();
			if(actual.layout.isGoal(goal)) {
				while(actual!=null) {
					f.addFirst(actual);
					actual=actual.father;
				}
				return f.iterator();
			}
		}
	}

	public State deep(Queue<State> u,double max,double nextmax){
			List<State> sucs=sucessores(actual);
			for(State suc:sucs){
				if(actual.layout.isGoal(objective)) {
					return actual;
				}
				if(suc.getF()<=max){
					actual=suc;
					return deep(u, max, nextmax);
				}
				else{
					nextmax=suc.getF();
					while(actual.getF()>max){
						actual=actual.father;
					}
					max=actual.getF();
					return deep(u, max, nextmax);
				}
			}
			return null;
	}*/

	 final private State containsNode(State lastNode, State contains){
        
        if(lastNode.equals(contains))
            return lastNode;
        State currNode = lastNode;
        while(currNode.father != null){
            if(currNode.equals(contains))
                return currNode;
            currNode = currNode.father;
        }
        return null;
    }

    final private Iterator<State> invertOrder(State lastNode){
        LinkedList<State> list = new LinkedList<State>();

        State state = lastNode;
        while(state != null){
            list.addFirst(state);
            state = state.father;
        }

        return list.iterator();

    }
    final public State search(State currentNode){

        List<State> childrens = sucessores(currentNode);
        

        for(State curr: childrens){

            if(containsNode(currentNode, curr)!= null){
                continue;
            }

            if(curr.getF()<=depth){
                if(curr.layout.equals(objective)){
                    return curr;
                }
                State found = search(curr);
                if(found !=null){
                    return found;
                }
            }else{
                if(curr.getF()<lowerF){
                    lowerF = curr.getF();
                }
            }
        }

        return null;
    }

    final public Iterator<State> solve(Ilayout s, Ilayout goal) {
    
        objective = goal;
        lowerF = s.getH(goal);
        State currentNode = new State(s,null,goal);
        /*if(currentNode.layout.equals(goal)){
            return invertOrder(currentNode);
		}*/
		//System.out.println("Searching Depth: " + currentNode.getF());
		return null;

        /*while(true){


            depth = lowerF;
            lowerF = Double.MAX_VALUE;

            State found = search(currentNode);
			//System.out.println(found.getF()-found.getG());
            if(found != null){
                return invertOrder(found);
            }

            if(lowerF == Double.MAX_VALUE){
                throw new RuntimeException("No path exists");
            }
        }*/
    }

}
