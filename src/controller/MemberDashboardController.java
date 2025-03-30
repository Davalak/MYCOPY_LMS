package controller;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Model.Book;
import Model.BorrowRecord;
import DAO.BookDAO;
import DAO.BorrowRecordDAO;
import Model.User;
import util.SceneManager;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import util.*;

public class MemberDashboardController {
    @FXML 
    private Label welcomeLabel, memberTypeLabel, booksCheckedOutLabel, overdueBooksLabel, statusLabel;
    
    @FXML 
    private TableView<BorrowRecord> currentLoansTable;
    
    @FXML 
    private TableView<Book> availableBooksTable;
    
    @FXML 
    private TextField bookSearchField;
    
    @FXML
    private Pane availableBookPane;

    @FXML
    private Pane borrowedBookPane;

    @FXML
    private Pane returnedBookPane;

    @FXML
    private Text textQuote;
    
    // Current Loans Table Columns
    @FXML private TableColumn<BorrowRecord, String> loanTitleColumn, checkoutDateColumn, dueDateColumn, loanStatusColumn;
    
    // Available Books Table Columns
    @FXML private TableColumn<Book, String> titleColumn, authorColumn, availableColumn;
    
    private User currentUser;
    private final BookDAO bookDAO = new BookDAO();
    private final BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();

    public void setUser(User user) {
        this.currentUser = user;
        if(user != null){
            welcomeLabel.setText("Welcome, " + user.getFullName());
            memberTypeLabel.setText("(" + user.getMemberType() + ")");
            initializeTables();
            loadDashboardData();
        }
        
    }

    private void initializeTables() {
        // Current Loans Table
        loanTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        checkoutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkoutDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        loanStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        // Available Books Table
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("availableQuantity"));
    }

    private void loadDashboardData() {
        try{
            List<BorrowRecord> currentLoans = borrowRecordDAO.getUserActiveLoans(currentUser.getUserId());
        booksCheckedOutLabel.setText(String.valueOf(currentLoans.size()));
        
        long overdueCount = currentLoans.stream()
            .filter(record -> "Overdue".equals(record.getStatus()))
            .count();
        overdueBooksLabel.setText(String.valueOf(overdueCount));
        
        currentLoansTable.getItems().setAll(currentLoans);
        
        // Load available books by default
        availableBooksTable.getItems().setAll(bookDAO.getAvailableBooks());
        }
        catch (Exception e) {
            System.out.println("Error loading dashboard data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    

    @FXML
    private void handleBrowse() {
        availableBooksTable.getItems().setAll(bookDAO.getAvailableBooks());
    }

    @FXML
    private void handleMyBooks() {
        currentLoansTable.getItems().setAll(borrowRecordDAO.getUserActiveLoans(currentUser.getUserId()));
    }

    @FXML
    private void handleBookSearch() {
        String searchTerm = bookSearchField.getText();
        List<Book> results = searchTerm.isEmpty() ? 
            bookDAO.getAvailableBooks() : 
            bookDAO.searchAvailableBooks(searchTerm);
        availableBooksTable.getItems().setAll(results);
    }

    @FXML
    private void handleRequestBook() {
        Book selectedBook = availableBooksTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            boolean success = borrowRecordDAO.createBorrowRequest(
                currentUser.getUserId(),
                selectedBook.getBookId()
            );
            
            if (success) {
                statusLabel.setText("Book request submitted successfully");
                loadDashboardData();
            } else {
                statusLabel.setText("Failed to submit book request");
            }
        } else {
            statusLabel.setText("Please select a book to request");
        }
    }

    @FXML
    void btnAvailableBookOnAction(ActionEvent event) throws IOException {
        MemberMainController.getInstance().btnBooksOnAction(event);
        Navigation.switchPaging(
                MemberMainController.getInstance().pagingPane, "userBorrowBooksForm.fxml");
    }

    @FXML
    void btnBorrowedBookOnAction(ActionEvent event) throws IOException {
        MemberMainController.getInstance().btnCatalogOnAction(event);
        Navigation.switchPaging(
                MemberMainController.getInstance().pagingPane, "userBorrowedBooksForm.fxml");
    }

    @FXML
    void btnReturnedBookOnAction(ActionEvent event) throws IOException {
        MemberMainController.getInstance().btnCatalogOnAction(event);
        Navigation.switchPaging(
                MemberMainController.getInstance().pagingPane, "userReturnedBooksForm.fxml");
    }
}




