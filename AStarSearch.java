/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.util.*;
import javafx.util.Pair;

/**
 *
 * @author thoma
 */
public class AStarSearch {
    private final Map map;
    private final PriorityQueue<State> queue = new PriorityQueue<>();
    private List<Pair<Integer, Integer>> solutionPath;
    
    public AStarSearch(Map m){
        map = m;
        map.fill_goal_cords();
        solutionPath = new LinkedList<>();
    }
    
    private void solveIteration(){
        while(!queue.isEmpty()){
            State s = queue.remove(); //take the most promising state.
            if (map.getField(s.x, s.y) != Map.VISITED && (!map.atStart(s.x, s.y) || queue.isEmpty())){
                if (map.getField(s.x, s.y) == Map.GOAL){
                    solutionPath = State.reconstructPath(s);
                    return;
                }
                map.setField(s.x, s.y, Map.VISITED);
                for(int dx = -1; dx <= 1; ++dx){
                    for(int dy = (dx %2 == 0) ? -1 : 0; dy <= 1; dy +=2 ){ //(-1, 0), (0, -1), (0, 1), (1, 0)
                        if (map.getField(s.x + dx, s.y + dy) != Map.WALL){
                            State newState = new State(s.x + dx, s.y + dy, s.length + 1, s, map);
                            queue.add(newState);
                        }
                    }
                }   
            }
        }
    }
    
    public List<Pair<Integer, Integer>> solve(){
        State startingState = new State(map.getStartX(), map.getStartY(), 0, null, map);
        queue.add(startingState);
        solveIteration();
        return solutionPath;
    }
}
