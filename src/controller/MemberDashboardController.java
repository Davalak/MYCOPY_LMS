package controller;

import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MemberDashboardController {

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    private void btnBorrowedBookOnAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MemberBorrowedBooks.fxml"));
            Parent root = loader.load();

            MemberBorrowedBooksController controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Borrowed Books");
            stage.show();

        } catch (Exception e) {
            System.err.println("Failed to load Borrowed Books view: " + e.getMessage());
        }
    }

    @FXML
    private void btnReturnedBookOnAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MemberReturnedBooks.fxml"));
            Parent root = loader.load();

            MemberReturnedBooksController controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Returned Books");
            stage.show();

        } catch (Exception e) {
            System.err.println("Failed to load Returned Books view: " + e.getMessage());
        }
    }

    @FXML
    private void btnAvailableBookOnAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MemberBorrow.fxml"));
            Parent root = loader.load();

            MemberBorrowController controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Available Books");
            stage.show();

        } catch (Exception e) {
            System.err.println("Failed to load Available Books view: " + e.getMessage());
        }
    }
}
