package controller;

import DAO.BookDAO;
import Model.Book;
import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MemberBorrowController {

    @FXML 
    private TableView<Book> booksTable;
    
    @FXML 
    private TableColumn<Book, Integer> bookIdColumn;
    
    @FXML 
    private TableColumn<Book, String> bookTitle, bookAuthorColumn, bookStatusColumn;
    
    @FXML 
    private TextField bookSearchField;
    
    @FXML 
    private Label lblSearchAlert;

    private final BookDAO bookDAO = new BookDAO();
    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
        loadAvailableBooks();
    }

    private void loadAvailableBooks() {
        booksTable.getItems().setAll(bookDAO.getAvailableBooks());
    }

    @FXML
    private void btnBorrowBookOnAction() {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            lblSearchAlert.setText("Please select a book first.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BorrowBookPopup.fxml"));
            Parent root = loader.load();

            BorrowBookPopupController controller = loader.getController();
            controller.setData(currentUser, selectedBook);

            Stage popupStage = new Stage();
            popupStage.setScene(new Scene(root));
            popupStage.setTitle("Borrow Book");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.showAndWait();

            // Refresh book list after popup
            loadAvailableBooks();

        } catch (Exception e) {
            lblSearchAlert.setText("Failed to open borrow popup: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void btnRefreshTableOnAction() {
        loadAvailableBooks();
        lblSearchAlert.setText("");
    }

    @FXML
    private void handleBookSearch() {
        String search = bookSearchField.getText();
        if (search.isEmpty()) {
            loadAvailableBooks();
            return;
        }

        booksTable.getItems().setAll(bookDAO.searchBooks(search));
    }
}
