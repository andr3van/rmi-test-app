package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import remoteinterface.RemoteInterface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main  extends Application implements RemoteInterface {

    public Main() {}

    @Override
    public void connect() {
        Platform.runLater(()->{
            GuiController guiController = GuiController.instanceOf();
            guiController.getMsgStat().setText(guiController.getMsgStat().getText() + "\n" + "Client connected...");
        });
    }

    @Override
    public void disconnect() {
        Platform.runLater(()->{
            GuiController guiController = GuiController.instanceOf();
            guiController.getMsgStat().setText(guiController.getMsgStat().getText() + "\n" + "Client disconnected...");
        });
    }

    @Override
    public String sayHello(String msg) {
        String response = "Hello there...";

        GuiController guiController = GuiController.instanceOf();
        guiController.getMsgStat().setText(guiController.getMsgStat().getText() + "\n" + "Client message: " + msg
                + " | Server response: " + response);

        return response;
    }

    @Override
    public String introduceName(String msg) {
        String response = "My name is John.";

                GuiController guiController = GuiController.instanceOf();
        guiController.getMsgStat().setText(guiController.getMsgStat().getText() + "\n" + "Client message: "+ msg
                + " | Server response: " + response);

        return response;
    }

    @Override
    public String giveAge(String msg) {
        String response = "My name is John.";

        GuiController guiController = GuiController.instanceOf();
        guiController.getMsgStat().setText(guiController.getMsgStat().getText() + "\n" + "Client message: "
                + " | Server response: " + response);

        return response;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Controller");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        root.setCenter(GuiController.getInstance().getAp());
        GuiController.instanceOf().getMsgStat().setText("Server ready...");
    }

    public static void main(String[] args) {
        try {
            Main obj = new Main();
            int port = 1100;
            RemoteInterface stub = (RemoteInterface) UnicastRemoteObject.exportObject(obj, port);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("RemoteInterface", stub);
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }

        launch(args);
    }

}
