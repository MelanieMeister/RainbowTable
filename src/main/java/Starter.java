import javafx.application.Application;
import javafx.stage.Stage;
import model.PresentationModel;

import java.security.NoSuchAlgorithmException;

public class Starter extends Application {
    public static void main(final String[] args) {
        launch(args);
    }
    private final String targetHash = "1d56a37fb6b08aa709fe90e12ca59e12";

    @Override
    public void start(Stage primaryStage) throws NoSuchAlgorithmException {
        PresentationModel model = new PresentationModel();
        model.generatePassword();
        String password = model.compare(targetHash);
        if(password == null) System.out.print("Es wurde kein mögliches Passwort gefunden.");
        System.out.printf("Das Passwort könnte %s sein.", password);
    }
}
