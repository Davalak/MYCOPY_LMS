package controller;

import DAO.UserDAO;
import Model.User;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AdminAddUserController {

    @FXML private TextField txtName;  
    @FXML private TextField txtLanguage;  
    @FXML private TextField txtType; 
    @FXML private TextField txtName1;     

    @FXML private Label lblisbnAlert;
    @FXML private Label lblTitleAlert;
    @FXML private Label lblAuthorAlert;
    @FXML private Label lblGenreAlert1;

    private Runnable onUserAdded;
    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }

    public void setOnUserAdded(Runnable onUserAdded) {
        this.onUserAdded = onUserAdded;
    }

    @FXML
    private void btnAddOnAction() {
        clearAlerts();

        String isbn = txtName.getText().trim();
        String title = txtLanguage.getText().trim();
        String author = txtType.getText().trim();
        String genre = txtName1.getText().trim();

        boolean valid = true;

        if (isbn.isEmpty()) {
            lblisbnAlert.setText("ISBN is required");
            valid = false;
        }

        if (title.isEmpty()) {
            lblTitleAlert.setText("Title is required");
            valid = false;
        }

        if (author.isEmpty()) {
            lblAuthorAlert.setText("Author is required");
            valid = false;
        }

        if (genre.isEmpty()) {
            lblGenreAlert1.setText("Genre is required");
            valid = false;
        }

        if (!valid) return;

        User user = new User();
        user.setIsbn(isbn);
        user.setTitle(title);
        user.setAuthor(author);
        user.setGenre(genre);
        user.setQuantity(1);
        user.setAvailableQuantity(1);

        boolean success = new UserDAO().addUser(user);

        if (success) {
            if (onUserAdded != null) {
                onUserAdded.run();
            }
            closeStage();
        } else {
            lblisbnAlert.setText("Failed to add user (maybe ISBN exists)");
        }
    }

    @FXML
    private void btnCancelOnAction() {
        closeStage();
    }

    @FXML
    private void btnCloseOnAction() {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) txtName.getScene().getWindow();
        stage.close();
    }

    private void clearAlerts() {
        lblisbnAlert.setText("");
        lblTitleAlert.setText("");
        lblAuthorAlert.setText("");
        lblGenreAlert1.setText("");
    }

}
