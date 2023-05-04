package org.wruthless;

import javax.swing.table.AbstractTableModel;
import java.sql.*;

public class ResultsSetTableModel extends AbstractTableModel {

    private final Connection connection;
    private final Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    private int numberOfRows;

    // Database connection status.
    private boolean connectedToDatabase = false;

    public ResultsSetTableModel(String url, String username, String password, String query) throws SQLException {

        // Connect.
        connection = DriverManager.getConnection(url, username, password);

        // Statement for query.
        statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        // Update connection status.
        connectedToDatabase = true;

        // Execute query.
        setQuery(query);
    }


    // Class representing the data type in the columns.
    public Class<?> getColumnClass(int column) throws IllegalStateException {

        // Check database connection.
        if (!connectedToDatabase) {
            throw new IllegalStateException("[!] No database connection found.");
        }

        // Column class determination.
        try {
            // Return the string representation of the columns class name.
            // Add one as JTables are indexed from 0.
            // ResultsSet is index from 1.
            String className = metaData.getColumnClassName(column + 1);

            // Class object representing className. Get the class Object and return
            // it back to the JTable.
            return Class.forName(className);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        // Shorthand notation for the Object data type (Java.long)
        // and it's class representation.
        return Object.class;
    }


    public int getColumnCount() throws IllegalStateException {

        // Check database connection.
        if (!connectedToDatabase) {
            throw new IllegalStateException("[!] No database connection found.");
        }

        // Get number of columns
        try {
            return metaData.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // If there is a problem, return 0.

    }


    public String getColumnName(int column) throws IllegalStateException {

        // Check database connection.
        if (!connectedToDatabase) {
            throw new IllegalStateException("[!] No database connection found.");
        }

        try {
            // Add one as JTables are indexed from 0.
            // ResultsSet is index from 1.
            return metaData.getColumnName(column + 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ""; // If there is a problem, return an empty string.

    }


    public int getRowCount() throws IllegalStateException {

        // Check database connection.
        if (!connectedToDatabase) {
            throw new IllegalStateException("[!] No database connection found.");
        }

        return numberOfRows;

    }


    public Object getValueAt(int row, int column) throws IllegalStateException {

        if (!connectedToDatabase) {
            throw new IllegalStateException("[!] No database connection found.");
        }

        // Obtain a value at the specified ResultSet row and column.
        try {
            // absolute goes directly to the specified row.
            resultSet.absolute(row + 1);
            // get the object at the specified column.
            return resultSet.getObject(column + 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ""; // If there is a problem, return an empty string.
    }


    public void setQuery(String query) throws SQLException, IllegalStateException{

        if (!connectedToDatabase) {
            throw new IllegalStateException("[!] No database connection found.");
        }

        // Specify query and execute.
        resultSet = statement.executeQuery(query);

        // Obtain meta data for ResultSet.
        metaData = resultSet.getMetaData();

        // Determine number of rows in ResultSet
        resultSet.last();
        numberOfRows = resultSet.getRow();

        // Tell JTable about any change to the model.
        fireTableStructureChanged();
    }
}
