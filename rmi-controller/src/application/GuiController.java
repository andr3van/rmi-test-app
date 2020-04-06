package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class GuiController {

    @FXML
    private AnchorPane ap;
    @FXML
    private TextArea msgStat;

    private static GuiController instance;

    public static GuiController getInstance() {
        if (instance == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(GuiController.class.getResource("gui.fxml"));
            try {
                fxmlLoader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            instance = fxmlLoader.getController();
        }

        return instance;
    }

    public static GuiController instanceOf() {
        return instance;
    }

    public TextArea getMsgStat() {
        return msgStat;
    }

    @FXML
    void onExit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    public AnchorPane getAp() {
        return ap;
    }
}
