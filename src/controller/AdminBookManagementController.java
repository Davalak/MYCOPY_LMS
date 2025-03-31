package controller;

import DAO.BookDAO;
import Model.Book;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import util.Navigation;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AdminBookManagementController {

    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, String> colIsbn;
    @FXML
    private TableColumn<Book, String> colTitle;
    @FXML
    private TableColumn<Book, String> colAuthor;
    @FXML
    private TableColumn<Book, String> colGenre;
    @FXML
    private TableColumn<Book, String> colStatus;

    @FXML
    private TextField txtSearch;
    @FXML
    private Label lblSearchAlert;

    private final BookDAO bookDAO = new BookDAO();
    private ObservableList<Book> allBooks;

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    public void initialize() {
        setupTableColumns();
        loadBooks();
    }

    private void setupTableColumns() {
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadBooks() {
        List<Book> books = bookDAO.getAllBooks();
        books.forEach(Book::calculateStatus); // Ensure status is calculated
        allBooks = FXCollections.observableArrayList(books);
        bookTable.setItems(allBooks);
    }

    @FXML
    private void txtSearchOnAction() {
        String keyword = txtSearch.getText().trim().toLowerCase();

        if (keyword.isEmpty()) {
            lblSearchAlert.setText("Search field cannot be empty.");
            bookTable.setItems(allBooks);
            return;
        }

        lblSearchAlert.setText(""); // Clear alert

        List<Book> filtered = allBooks.stream()
                .filter(book ->
                        book.getIsbn().toLowerCase().contains(keyword) ||
                        book.getTitle().toLowerCase().contains(keyword) ||
                        book.getAuthor().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        bookTable.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void btnAddBookOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("adminAddBookForm.fxml", event);
    }
}
