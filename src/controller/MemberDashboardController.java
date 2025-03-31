package controller;

import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import util.Navigation;

public class MemberDashboardController {

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    private void btnBorrowedBookOnAction(ActionEvent event) {
        try {
            Navigation.switchNavigation("MemberBorrowedBooks.fxml", event, (MemberBorrowedBooksController controller) -> {
                controller.setUser(currentUser);
            });
        } catch (Exception e) {
            System.err.println("Navigation to Borrowed Books failed: " + e.getMessage());
        }
    }

    @FXML
    private void btnReturnedBookOnAction(ActionEvent event) {
        try {
            Navigation.switchNavigation("MemberReturnedBooks.fxml", event, (MemberReturnedBooksController controller) -> {
                controller.setUser(currentUser);
            });
        } catch (Exception e) {
            System.err.println("Navigation to Returned Books failed: " + e.getMessage());
        }
    }

    @FXML
    private void btnAvailableBookOnAction(ActionEvent event) {
        try {
            Navigation.switchNavigation("MemberBorrow.fxml", event, (MemberBorrowController controller) -> {
                controller.setUser(currentUser);
            });
        } catch (Exception e) {
            System.err.println("Navigation to Available Books failed: " + e.getMessage());
        }
    }
}
