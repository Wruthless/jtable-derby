package org.wruthless;

import java.sql.*;

public class DisplayQueryResults {

    private static final String DATABASE_URL = "jdbc:derby:books";
    private static final String USERNAME = "wruth";
    private static final String PASSWORD = "term";
    private static final String DEFAULT_QUERY = "SELECT * FROM authors";

    private static ResultsSetTableModel tableModel;

    public static void main(String[] args) {

        try {
            // Create TableModel for results from the default query.
            tableModel = new ResultsSetTableModel(DATABASE_URL, USERNAME, PASSWORD, DEFAULT_QUERY);




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}


//    QUICK DATABASE TEST
//    public static void main(String[] args) {
//
//
//        final String DATABASE_URL = "jdbc:derby:books";
//        final String USERNAME = "wruth";
//        final String PASSWORD = "term";
//
//        final String DEFAULT_QUERY = "SELECT * FROM authors";
//
//
//        try (
//                Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
//                Statement statement = connection.createStatement();
//                ResultSet resultSet = statement.executeQuery(DEFAULT_QUERY)) {
//
//            ResultSetMetaData metaData = resultSet.getMetaData();
//            int numberOfColumns = metaData.getColumnCount();
//
//            System.out.printf("Authors Table of Books Data: %n%n");
//
//            for (int i = 1; i <= numberOfColumns; i++) {
//                System.out.printf("%-8s\t", metaData.getColumnName(i));
//            }
//            System.out.println();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
