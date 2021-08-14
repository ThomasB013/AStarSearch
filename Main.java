package student;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Main extends Application{
    public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage){
        Map m = new Map(50, 30);
       
        UIHandler handler = new UIHandler(m);
        Pane root = handler.getPane();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
