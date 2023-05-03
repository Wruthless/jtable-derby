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


    // Class representing column type.
    public Class<?> getColumnClass(int column) throws IllegalStateException {

        // Check database connection.
        if (!connectedToDatabase) {
            throw new IllegalStateException("[!] No database connection found.");
        }

        // Column class determination.
        try {
            String className = metaData.getColumnClassName(column + 1);

            // Class object representing className.
            return Class.forName(className);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return Object.class;
    }

}
