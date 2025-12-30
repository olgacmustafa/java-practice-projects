
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/main_menu.fxml"));


        Scene scene = new Scene(loader.load(), 300, 250);
        stage.setScene(scene);
        stage.setTitle("Zincir Magaza");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
