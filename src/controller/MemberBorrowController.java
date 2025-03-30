package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import Model.Book;
import util.Navigation;
import Model.Book;
import Model.BorrowRecord;
import DAO.UserDAO;
import DAO.BookDAO;
import DAO.BorrowRecordDAO;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MemberBorrowController {

    @FXML
    private Pane AcquireBookPane;

    @FXML
    private Pane btnRefreshPane;

    @FXML
    private ImageView imgAcquire;

    @FXML
    private Label lblAcquire;

    @FXML
    private Label lblSearchAlert;

    @FXML
    private Pane searchPane;

    @FXML
    private TextField txtSearch;
    
    @FXML private Label welcomeLabel, availableBooksLabel, checkedOutBooksLabel, overdueBooksLabel, statusLabel;
    
    @FXML private TableView<BorrowRecord> todaysCheckoutsTable;
    
    @FXML private TableView<Book> booksTable;
    
    @FXML private TableView<User> membersTable;
    
    @FXML private TextField bookSearchField, memberSearchField;
    
    @FXML private TableColumn<BorrowRecord, String> bookTitleColumn, memberUserNameColumn, dueDateColumn;
    
    @FXML private TableColumn<Book, Integer> bookIdColumn;
    
    @FXML private TableColumn<Book, String> bookTitle, bookAuthorColumn, bookStatusColumn;
    
    @FXML private TableColumn<User, Integer> memberIdColumn, booksOutColumn;
    
    @FXML private TableColumn<User, String> memberFullNameColumn, memberTypeColumn;
    
    private User currentUser;
    private final BookDAO bookDAO = new BookDAO();
    private final UserDAO userDAO = new UserDAO();
    private final BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();

    public void setUser(User user) {
        this.currentUser = user;
        initializeTables();
        loadDashboardData();
    }

    private void initializeTables() {
        // Today's Checkouts Table
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        memberUserNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        
        // Book Management Table
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        // Member Management Table
        memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        memberFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        memberTypeColumn.setCellValueFactory(new PropertyValueFactory<>("memberType"));
        booksOutColumn.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty(cellData.getValue().getBorrowedBooksCount()).asObject());
    }

    private void loadDashboardData() {
        availableBooksLabel.setText(String.valueOf(bookDAO.getAvailableBookCount()));
        checkedOutBooksLabel.setText(String.valueOf(bookDAO.getCheckedOutBookCount()));
        overdueBooksLabel.setText(String.valueOf(borrowRecordDAO.getOverdueBooksCount()));
        
        todaysCheckoutsTable.getItems().setAll(borrowRecordDAO.getTodaysCheckouts());
        booksTable.getItems().setAll(bookDAO.getAllBooks());
        
        // Load only member accounts
        membersTable.getItems().setAll(userDAO.getAllMembers());
    }

    @FXML
    private void handleBookManagement() {
        booksTable.getItems().setAll(bookDAO.getAllBooks());
    }
    
    @FXML
    private void handleBookSearch() {
        String searchTerm = bookSearchField.getText();
        List<Book> results = searchTerm.isEmpty() ? 
            bookDAO.getAllBooks() : 
            bookDAO.searchBooks(searchTerm);
        booksTable.getItems().setAll(results);
    }

    private static MemberBorrowController controller;

    public MemberBorrowController() {
        controller = this;
    }

    public static MemberBorrowController getInstance() {
        return controller;
    }

    @FXML
    void btnAcquireBookOnAction(ActionEvent event) throws IOException {
        Navigation.imgPopUpBackground("userBorrowBookConfirmPopUpForm.fxml");
    }

    @FXML
    void btnRefreshTableOnAction(ActionEvent event) {
        lblSearchAlert.setText(" ");
      //  allBookId();
    }
}
