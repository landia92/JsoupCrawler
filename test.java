import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.text.ParseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class test {
    public static void main(String[] args) {
        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
        LocalDateTime yesterday = now.minusDays(1);
        LocalDateTime articleTime = LocalDateTime.parse("2024-06-27 11:47:01", formatter);
        *//*System.out.println(now);
        System.out.println(articleTime);*//*
        if(articleTime.isBefore(now)) {
            if(articleTime.isAfter(yesterday)) {System.out.println("aaa");}
        }
        else {
            System.out.println("bbb");
        }*/
        // DB Driver
        String dbDriver = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // DB URL
        // IP:PORT/스키마
        String dbUrl = "jdbc:mysql://localhost:3306/itscribe";

        // DB ID/PW
        String dbUser = "root";
        String dbPassword = "1234";

        try {
            DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
