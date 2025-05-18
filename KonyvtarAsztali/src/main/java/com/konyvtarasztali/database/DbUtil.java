package com.konyvtarasztali.database;

import com.konyvtarasztali.model.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbUtil {

    private static Connection connection;
    private static String url = "jdbc:mysql://localhost:3306/x";

    public void init() throws SQLException {
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
            int pagecount = result.getInt("title");


        }
    }

}