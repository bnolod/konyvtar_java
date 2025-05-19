package com.konyvtarasztali.database;

import com.konyvtarasztali.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUtil {

    private static Connection connection;

    // url felepites: jdbc:<mysql>://<localhost>:<3306>/<adatbazis nev>
    private static String url = "jdbc:mysql://localhost:3306/x";

    public static void init() throws SQLException {
        connection = DriverManager.getConnection(url,"root","");
    }

    public static List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM BOOKS";
        ResultSet result = connection.createStatement().executeQuery(sql);

        while (result.next()) {
            int id = result.getInt("id");
            String title = result.getString("title");
            String author = result.getString("author");
            int pubyear = result.getInt("publish_year");
            int pagecount = result.getInt("page_count");

            books.add(new Book(id,title,author,pubyear,pagecount));
        }

        return books;
    }

    public static boolean deleteBook(int id) throws SQLException {
        String sql = "delete from books where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);

        return preparedStatement.executeUpdate() > 0;
    }

}