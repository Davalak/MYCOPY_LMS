package util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import controller.AdminMainController;
import controller.MemberMainController;

import java.io.IOException;

public class Navigation {

    private static Stage stage;

    public static void switchNavigation(String path, ActionEvent event) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(Navigation.class.getResource("/view/" + path)));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void switchPaging(Pane pane, String path) throws IOException {
        pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(Navigation.class.getResource("/view/" + path));
        Parent root = loader.load();
        pane.getChildren().add(root);
    }

    public static void close(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public static void exit() {
        System.exit(0);
    }

    public static void closePopUpPane() {
        AdminMainController.getInstance().popUpPane.getChildren().clear();
        AdminMainController.getInstance().popUpPane.setVisible(false);
        AdminMainController.getInstance().imgTransparent.setVisible(false);
    }

    public static void closeUserPopUpPane() {
        MemberMainController.getInstance().popUpPane.getChildren().clear();
        MemberMainController.getInstance().popUpPane.setVisible(false);
        MemberMainController.getInstance().imgTransparent.setVisible(false);
    }

    public static void closePopUpLargePane() {
        AdminMainController.getInstance().popUpLargePane.getChildren().clear();
        AdminMainController.getInstance().popUpLargePane.setVisible(false);
        AdminMainController.getInstance().imgTransparent.setVisible(false);
    }

    public static void closeUserPopUpLargePane() {
        MemberMainController.getInstance().popUpLargePane.getChildren().clear();
        MemberMainController.getInstance().popUpLargePane.setVisible(false);
       MemberMainController.getInstance().imgTransparent.setVisible(false);
    }

    public static void closeAdminSettingsPane() {
        AdminMainController.getInstance().settingsPane.getChildren().clear();
        AdminMainController.getInstance().settingsPane.setVisible(false);
        AdminMainController.getInstance().imgTransparent.setVisible(false);
    }

    public static void closeUserSettingsPane() {
        MemberMainController.getInstance().settingsPane.getChildren().clear();
        MemberMainController.getInstance().settingsPane.setVisible(false);
        MemberMainController.getInstance().imgTransparent.setVisible(false);
    }

    public static void imgPopUpBackground(String path) throws IOException {
        if (path.startsWith("user")) {
            MemberMainController.getInstance().imgTransparent.setVisible(true);

            if (path.equals("userChangeCredentialsPopUpForm.fxml") | path.equals("userDeleteConfirmationForm.fxml")) {
                MemberMainController.getInstance().popUpPane.setVisible(true);
                switchPaging(MemberMainController.getInstance().popUpPane, path);
            } else if (path.equals("userSettingsPopUpForm.fxml")) {
                MemberMainController.getInstance().settingsPane.setVisible(true);
                switchPaging(MemberMainController.getInstance().settingsPane, path);
            } else {
                MemberMainController.getInstance().popUpLargePane.setVisible(true);
                switchPaging(MemberMainController.getInstance().popUpLargePane, path);
            }
        }
        else {
            AdminMainController.getInstance().imgTransparent.setVisible(true);

            if (path.equals("adminBorrowedBooksViewPopUpForm.fxml") | path.equals("adminOverdueBooksViewPopUpForm.fxml")) {
                AdminMainController.getInstance().popUpLargePane.setVisible(true);
                switchPaging(AdminMainController.getInstance().popUpLargePane, path);
            } else if (path.equals("adminSettingsPopUpForm.fxml")) {
                AdminMainController.getInstance().settingsPane.setVisible(true);
                switchPaging(AdminMainController.getInstance().settingsPane, path);
            } else {
                AdminMainController.getInstance().popUpPane.setVisible(true);
                switchPaging(AdminMainController.getInstance().popUpPane, path);
            }
        }
    }
}
