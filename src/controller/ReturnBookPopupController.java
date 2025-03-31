package controller;

import DAO.BookDAO;
import DAO.BorrowRecordDAO;
import Model.BorrowRecord;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.function.Consumer;

public class ReturnBookPopupController {

    @FXML 
    private Label lblId;
    
    @FXML 
    private Label lblDueDate;
    
    @FXML 
    private Label lblTotalBooks;

    private BorrowRecord borrowRecord;
    private final BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();
    private final BookDAO bookDAO = new BookDAO();
    private Consumer<Void> onReturnSuccess;

    public void setBorrowRecord(BorrowRecord record) {
        this.borrowRecord = record;
        lblId.setText(String.valueOf(record.getUserId()));
        lblDueDate.setText(record.getDueDate().toString());
        lblTotalBooks.setText(borrowRecordDAO.getBorrowedBooksCount(borrowRecord.getUserId()) + " Books");
    }

    public void setOnReturnSuccess(Consumer<Void> callback) {
        this.onReturnSuccess = callback;
    }

    @FXML
    private void btnReturnOnAction() {
        try {
            borrowRecord.setReturnDate(new Date(System.currentTimeMillis()));
            borrowRecord.setStatus("RETURNED");

            boolean success = borrowRecordDAO.updateBorrowRecord(borrowRecord);
            if (success) {
                bookDAO.updateAvailableQuantity(borrowRecord.getBookId(), 1);
                if (onReturnSuccess != null) onReturnSuccess.accept(null);
                closePopup();
            }
        } catch (Exception e) {
            System.err.println("Return failed: " + e.getMessage());
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
}
