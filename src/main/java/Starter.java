import javafx.application.Application;
import javafx.stage.Stage;
import model.PresentationModel;

public class Starter extends Application {



    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        PresentationModel model = new PresentationModel();
    }
}
