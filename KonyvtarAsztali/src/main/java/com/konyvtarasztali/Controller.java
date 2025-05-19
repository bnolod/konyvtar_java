package com.konyvtarasztali;

import com.konyvtarasztali.database.DbUtil;
import com.konyvtarasztali.model.Book;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Controller {
    @FXML
    private TableView<Book> table = new TableView<>();

    // kell ilyen fura szar lista
    private ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadBooks();
        setupTable();
    }

    private void setupTable() {
        TableColumn<Book, String> titleCol = new TableColumn<>("Cím");
        titleCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTitle()));

        TableColumn<Book, String> authorCol = new TableColumn<>("Szerző");
        authorCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAuthor()));

        TableColumn<Book, Integer> yearCol = new TableColumn<>("Kiadás éve");
        yearCol.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getPublishYear()));

        TableColumn<Book, Integer> pagesCol = new TableColumn<>("Oldalszám");
        pagesCol.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getPageCount()));

        table.getColumns().addAll(titleCol, authorCol, yearCol, pagesCol);
        table.setItems(books);
    }

    private void loadBooks() {
        try {
            books = FXCollections.observableArrayList(DbUtil.getAllBooks());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSelectedBook() {
        Book selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Figyelem", "Törléshez előbb válasszon ki egy könyvet!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Biztos szeretné törölni a kiválasztott könyvet?",
                ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Megerősítés");
        confirm.showAndWait();

        if (confirm.getResult() == ButtonType.YES) {
            try {
                DbUtil.deleteBook(selected.getId());
                books.remove(selected);
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Hiba", "Nem sikerült törölni a könyvet az adatbázisból!");
            }
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

}