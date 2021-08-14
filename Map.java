/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;

import javafx.beans.property.*;

/**
 *
 * @author thoma
 */
public class Map {
    private final int WIDTH;
    private final int HEIGHT;
    public static final int EMPTY = 0;
    public static final int VISITED = 1;
    public static final int WALL = 2;
    public static final int START = 3;
    public static final int GOALPATH = 4;
    public static final int GOAL = 5;
    
    private final IntegerProperty[][] grid;
    private int startX;
    private int startY;
    private List<Pair<Integer, Integer>> goal_cords;
    
    Map(int width, int height){
        this(width, height, 0, 0);
    }
    
    Map(int width, int height, int startx, int starty){
        WIDTH = width;
        HEIGHT = height;
        grid = new SimpleIntegerProperty[HEIGHT][WIDTH];
        for(int y = 0; y < HEIGHT; ++y)
            for(int x = 0; x < WIDTH; ++x)
                grid[y][x] = new SimpleIntegerProperty(); 
        startX = ((startx >= 0 && startx < WIDTH) ? startx : 0);
        startY = ((starty >= 0 && starty < HEIGHT) ? starty : 0);
    }
    
    public boolean atStart(int x, int y){
        return (x == startX && y == startY);
    }
    
    public boolean valid(int x, int y){
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }
    
    public void setStart(int x, int y){
        grid[startY][startX].set(EMPTY); //Clear old start.
        startX = x;
        startY = y;
        grid[startY][startX].set(START); //Clear 
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int y = 0; y < HEIGHT; ++y){
            for(int x = 0; x < WIDTH; ++x){
                switch(grid[y][x].get()){
                    case WALL: sb.append("W"); break;
                    case VISITED: sb.append("X"); break;
                    case EMPTY: sb.append(" "); break;
                    case GOAL: sb.append("G"); break;
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }
    
    public void fill_goal_cords(){
        goal_cords = new LinkedList<>();
        for(int y = 0; y < HEIGHT; ++y)
            for(int x = 0; x < WIDTH; ++x)
                if (grid[y][x].get() == GOAL)
                    goal_cords.add(new Pair<>(x, y));
    }
    
    public double min_dist_goal(int x, int y){
        double min = Double.MAX_VALUE;
        for(Pair<Integer, Integer> p : goal_cords){
            double dist = Math.sqrt((p.getKey() - x) * (p.getKey() - x) + (p.getValue() - y) * (p.getValue() - y)); 
            if (dist < min)
                min = dist;
        }
        return min;
    }
    
    public int getStartX(){
        return startX;
    }
    
    public int getStartY(){
        return startY;
    }
    
    public IntegerProperty getProperty(int x, int y){
        return grid[y][x];
    }
    
    public int getField(int x, int y){
        return valid(x, y) ? grid[y][x].get() : WALL; //treat all space outside as a wall.
    }
    
    
    public void setField(int x, int y, int val){
        switch(val){
            case START: setStart(x, y);
            return;
            case GOALPATH:
            case EMPTY:
            case VISITED:
            case WALL:
            case GOAL: grid[y][x].set(val);
        }
    }
    
    public int getWidth(){
        return WIDTH;
    }
    public int getHeight(){
        return HEIGHT;
    }    
}
