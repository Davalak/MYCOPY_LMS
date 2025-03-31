package controller;

import DAO.BookDAO;
import DAO.BorrowRecordDAO;
import Model.Book;
import Model.BorrowRecord;
import Model.User;
import util.DateTime;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class BorrowBookPopupController {

    @FXML 
    private Label lblId;
            
    @FXML 
    private Label lblTotalBooks;
            
    @FXML 
    private Label lblDueDate;

    private User user;
    private Book book;

    private final BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();
    private final BookDAO bookDAO = new BookDAO();

    public void setData(User user, Book book) {
        this.user = user;
        this.book = book;

        lblId.setText(String.valueOf(user.getUserId()));
        lblTotalBooks.setText(borrowRecordDAO.getBorrowedBooksCount(user.getUserId()) + " Books");
        lblDueDate.setText(DateTime.dateAftermonth());
    }

    @FXML
    private void btnConfirmOnAction() {
        try {
            if (!"Available".equalsIgnoreCase(book.getStatus())) {
                throw new Exception("Book is not available.");
            }

            BorrowRecord record = new BorrowRecord();
            record.setBookId(book.getBookId());
            record.setUserId(user.getUserId());
            record.setBorrowDate(java.sql.Date.valueOf(DateTime.dateNow()));
            record.setDueDate(java.sql.Date.valueOf(DateTime.dateAftermonth()));
            record.setStatus("BORROWED");

            boolean success = borrowRecordDAO.addBorrowRecord(record);

            if (success) {
        //        bookDAO.updateBookStatus(book.getBookId(), "Checked Out");
                bookDAO.updateAvailableQuantity(book.getBookId(), -1);

            }

            closePopup();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @FXML
    private void btnCancelOnAction() {
        closePopup();
    }

    private void closePopup() {
        Stage stage = (Stage) lblId.getScene().getWindow();
        stage.close();
    }

    // Optional: UI hover effects for button labels
    @FXML 
    private void btnConfirmOnMouseEntered() { lblConfirm.setStyle("-fx-text-fill: yellow;"); }
    
    @FXML 
    private void btnConfirmOnMouseExited() { lblConfirm.setStyle("-fx-text-fill: white;"); }
    
    @FXML 
    private void btnCancelOnMouseEntered() { lblCancel.setStyle("-fx-text-fill: red;"); }
    
    @FXML 
    private void btnCancelOnMouseExited() { lblCancel.setStyle("-fx-text-fill: black;"); }

    @FXML 
    private Label lblConfirm;
    
    @FXML 
    private Label lblCancel;
}
