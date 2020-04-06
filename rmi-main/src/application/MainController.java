package application;

import javafx.application.Platform;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import remoteinterface.RemoteInterface;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TextArea textArea;
    @FXML
    private Button connect;
    @FXML
    private ComboBox<String> commands;

    private RemoteInterface stub;
    private String selectedCommand;

    public MainController() {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        commands.setItems(observableArrayList("Say hi", "Ask name", "Ask age"));

        commands.valueProperty().addListener((observable, oldValue, newValue) -> {
            selectedCommand = newValue;
        });
    }

    @FXML
    void onConnect(ActionEvent event) {
        String host = null;
        try {
            Registry registry = LocateRegistry.getRegistry(1100);
            stub = (RemoteInterface) registry.lookup("RemoteInterface");

            textArea.setText("Successfully connected to server...");

            Platform.runLater(() -> {
                try {
                    stub.connect();
                    connect.setDisable(true);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @FXML
    void onExit(ActionEvent event) {
        try {
            stub.disconnect();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Platform.exit();
        System.exit(0);
    }

    @FXML
    void onCommandSend(ActionEvent event) {

        switch (selectedCommand) {
            case "Say hi":
                try {
                    textArea.setText(textArea.getText() + "\n" + stub.sayHello(selectedCommand));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case "Ask name":
                try {
                    textArea.setText(textArea.getText() + "\n" + stub.introduceName(selectedCommand));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case "Ask age":
                try {
                    textArea.setText(textArea.getText() + "\n" + stub.giveAge(selectedCommand));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

}
