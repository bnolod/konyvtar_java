package com.konyvtarasztali;

import com.konyvtarasztali.database.DbUtil;
import com.konyvtarasztali.model.Book;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.*;

public class Statistics implements Runnable{

    private  List<Book> books;

    @Override
    public void run() {
        try {
            books = DbUtil.getAllBooks();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        feladat1();
        feladat2();
        feladat3();
        feladat4();
        feladat5();

        //leállítja a programot
        System.exit(0);
    }

    private  void feladat1() {
        int count = 0;

        for (Book book : books) {
            if (book.getPageCount() > 500) {
                count++;
            }
        }
        System.out.println("500 oldalnál hosszabb könyvek száma: " + count);
    }
    private void feladat2() {
        boolean older = false;

        for (Book book: books) {
            if (book.getPublishYear() < 1950) {
                older = true;
                break;
            }
        }

        System.out.println((older? "Van" : "Nincs") + " 1950-nél régebbi könyv");
    }

    private void feladat3() {
        Book longest = books.get(0);

        for (Book book: books) {
            if (book.getPageCount() > longest.getPageCount()) {
                longest = book;
            }
        }

        System.out.println("A leghosszabb könyv:");
        System.out.println("\tSzerző: " + longest.getAuthor());
        System.out.println("\tCím: " + longest.getTitle());
        System.out.println("\tKiadás éve: " + longest.getPublishYear());
        System.out.println("\tOldalszám: " + longest.getPageCount());
    }

    private void feladat4() {
        Map<String, Integer> map = new HashMap<>();

        for (Book book: books) {
            if (!map.containsKey(book.getAuthor())) {
                map.put(book.getAuthor(),1);
            } else {
                map.put(book.getAuthor(),
                        map.get(book.getAuthor()) + 1
                        );
            }
        }

        Map.Entry<String, Integer> max = null;

        // Az entry csak egy key value pár
        // Ezek segítségével lehet iterálni mapeken :D
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (max == null || entry.getValue() > max.getValue()) {
                max = entry;
            }
        }

        assert max != null;
        System.out.println("A legtöbb könyvvel rendelkező szerző: " + max.getKey());

    }

    private void feladat5() {
        System.out.println("Adjon meg egy könyv címet:");
        String input;

        Scanner s = new Scanner(System.in);
        input = s.nextLine();

        for (Book book: books) {
            if (book.getTitle().equalsIgnoreCase(input)) {
                System.out.println("A megadott könyv szerzője "+ book.getAuthor());
                return;
            }
        }
        System.out.println("A könyv nem található.");
    }


}
