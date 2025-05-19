package com.konyvtarasztali;

import com.konyvtarasztali.database.DbUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BookApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BookApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 500);
        stage.setTitle("Book");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            DbUtil.init();
        } catch (SQLException e) {
            throw new Error("Sql connection failed to initialize");
        }
        if (List.of(args).contains("--stat")) {
            new Statistics().run();
        } else {
            launch();
        }
    }
}