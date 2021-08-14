package student;

import javafx.scene.control.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.List;
import javafx.util.Pair;

public class UIHandler {
    private enum Mode{
        START,
        GOAL,
        WALL,
        SOLVING
    }
    private final int SCALE = 20;
    private final int GAP = 1;
    private final int BTN_WIDTH = 200;
    private final int BTN_HEIGHT = 100;
    private final Map map;
    private Mode mode;
    
    private final Color START_COLOR = Color.YELLOWGREEN;
    private final Color EMPTY_COLOR = Color.LIGHTGRAY;
    private final Color WALL_COLOR = Color.BLACK;
    private final Color GOAL_COLOR = Color.BLUE;
    private final Color VISITED_COLOR = Color.RED;
    private final Color GOALPATH_COLOR = Color.YELLOWGREEN;
    
    private final BorderPane root = new BorderPane();
    
    UIHandler(Map m){
        map = m;
        mode = Mode.START;
        root.setLeft(make_grid());
        root.setRight(make_buttons());
    }
    
    private final EventHandler<MouseEvent> gridMouseClickHandler = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) { 
            int x = (int) e.getX(), y = (int) e.getY();
            if (x % (SCALE + GAP) == 0 || y % (SCALE+GAP) ==0)
                return;
            x /= (SCALE + GAP);
            y /= (SCALE + GAP);
            switch(mode){
                case START: handle_start(x, y); break;
                case GOAL: handle_goal(x, y); break;
                case WALL: handle_wall(x, y); break;
            }
        }
    }; 
    
    private void handle_start(int x, int y){
        if (map.valid(x, y) && !map.atStart(x, y))
            map.setStart(x, y);
    }
    
    private void handle_goal(int x, int y){
        if(map.valid(x, y) && !map.atStart(x, y))
            map.setField(x, y, (map.getField(x, y) == Map.GOAL) ? Map.EMPTY : Map.GOAL);

    }
    private void handle_wall(int x, int y){
        if(map.valid(x, y) && !map.atStart(x, y))
            map.setField(x, y, (map.getField(x, y) == Map.WALL) ? Map.EMPTY : Map.WALL);
    }
    
    private GridPane make_grid(){
        GridPane gp = new GridPane();
        gp.setHgap(GAP);
        gp.setVgap(GAP);
        for(int x = 0; x < map.getWidth(); ++x){
            for(int y = 0; y < map.getHeight(); ++y){
                Rectangle rect = new Rectangle(SCALE, SCALE);
                map.getProperty(x, y).addListener((obv, ov, nv) -> setColor(rect, nv.intValue())); 
                map.setField(x, y, Map.EMPTY); //Doesn't trigger the listener because there was no old value.
                setColor(rect, Map.EMPTY);
                gp.add(rect, x, y);
            }
        }
        map.setStart(0, 0);
        gp.setOnMouseClicked(gridMouseClickHandler);
        return gp;
    }
    
    private void setColor(Rectangle rect, int value){
        switch(value){
            case Map.EMPTY: rect.setFill(EMPTY_COLOR); return;
            case Map.GOALPATH: rect.setFill(GOALPATH_COLOR); return;
            case Map.GOAL: rect.setFill(GOAL_COLOR); return; 
            case Map.START: rect.setFill(START_COLOR); return;
            case Map.VISITED: rect.setFill(VISITED_COLOR); return;
            case Map.WALL: rect.setFill(WALL_COLOR);
        }
    }
    
    private Button start_btn(){
        Button bt = new Button("Set start");
        bt.setMaxSize(BTN_HEIGHT, BTN_WIDTH);
        bt.setOnMouseClicked(e -> mode = (mode == Mode.SOLVING) ? Mode.SOLVING : Mode.START);
        return bt;
    }
    
    private Button goal_btn(){
        Button bt = new Button("Set goals");
        bt.setMaxSize(BTN_HEIGHT, BTN_WIDTH);
        bt.setOnMouseClicked(e -> mode = (mode == Mode.SOLVING) ? Mode.SOLVING : Mode.GOAL);
        return bt;
    }
    
    private Button wall_btn(){
        Button bt = new Button("Set walls");
        bt.setMaxSize(BTN_HEIGHT, BTN_WIDTH);
        bt.setOnMouseClicked(e -> mode = (mode == Mode.SOLVING) ? Mode.SOLVING : Mode.WALL);
        return bt;
    }
    
    private Button exec_btn(){
        Button bt = new Button("Solve");
        bt.setMaxSize(BTN_HEIGHT, BTN_WIDTH);
        bt.setOnMouseClicked(e -> {
            mode = Mode.SOLVING; 
            showSolveAStar();
        });
        return bt;
    }
    
    private void showSolveAStar(){
        AStarSearch s = new AStarSearch(map);
        List<Pair<Integer, Integer>> path = s.solve();
        path.forEach(pair -> map.setField(pair.getKey(), pair.getValue(), Map.GOALPATH));
    }
    
    private Button reset_btn(){
        Button bt = new Button("Reset");
        bt.setMaxSize(BTN_HEIGHT, BTN_WIDTH);
        bt.setOnMouseClicked(e -> {
            root.setLeft(make_grid());
            mode = Mode.START;
        });
        return bt;
    }
    
    private VBox make_buttons(){
        VBox vb = new VBox();
        vb.getChildren().addAll(start_btn(), goal_btn(), wall_btn(), exec_btn(), reset_btn());
        vb.setSpacing(GAP);
        return vb;
    }
    
     public Pane getPane(){
        return root;
    }
};
