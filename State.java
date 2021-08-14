/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;


/**
 *
 * @author thoma
 */
public class State implements Comparable<State>{
        public final int x, y;
        public final State parent;
        public final Map map;
        public final int length;
        
        public State(int x, int y, int length, State parent, Map m){
            this.x = x;
            this.y = y;
            this.length = length;
            this.parent = parent;
            map = m;
        } 
        
        static public List<Pair<Integer, Integer>> reconstructPath(State s){
            List<Pair<Integer, Integer>> l = new LinkedList<>();
            l.add(new Pair<>(s.x, s.y));
            while(s.parent != null){
                s = s.parent;
                l.add(new Pair<>(s.x, s.y));
            }
            return l;
        }
        
        public double heuristicValue(){
            return length + map.min_dist_goal(x, y);
        }
        
        @Override
        public int compareTo(State state){
            if (heuristicValue() == state.heuristicValue())
                return 0;
            return heuristicValue() < state.heuristicValue() ? -1 : 1;
        }
        @Override
        public int hashCode(){
            return x*y;
        }
        
        @Override
        public boolean equals(Object obj){
            if (this == obj)
                return true;
            if (getClass() != obj.getClass())
                return false;
            State other = (State) obj;
            return (x == other.x && y == other.y && length == other.length);
        }
        
    }
