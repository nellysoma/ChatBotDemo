/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Chat;

/**
 *
 * @author Harmony
 */
public class DbConnection {
    
    private static final String DATABASE_URL = "jdbc:mysql://localhost/logistic_chat_bot?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Connection connection = null;//manage connection
    
    private Statement viewChats = null;
    
    private PreparedStatement insertChat = null;
    
    private ResultSet viewChatsResult = null;
    
    public static void connectToDataBase() throws ClassNotFoundException {

        try {

            // Load (and therefore register) the  Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Get a Connection to the database
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);

        } catch (SQLException error) {

            System.out.print(error.getMessage());
            error.printStackTrace();
            System.out.print("What is trying to say here is that, it could not connect to the database");

        }
    }
    
    private void closeConnection(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException ex) {
        }
    }
    
    public List<Chat> viewChats(String session_id) throws ClassNotFoundException {

        List<Chat> result = new ArrayList();

        try {

            String sql = "SELECT * FROM chat WHERE session_id = '"+session_id+"'";
            connectToDataBase();

            viewChats = connection.createStatement();

            viewChatsResult = viewChats.executeQuery(sql);

            while (viewChatsResult.next()) {

                Chat c = new Chat();

                c.setSessionID(viewChatsResult.getString("session_id"));
                c.setQuery(viewChatsResult.getString("query"));
                c.setRespond(viewChatsResult.getString("respond"));
                
                result.add(c);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return result;
    }
    
    public void insertChat(String session_id, String query, String respond) throws ClassNotFoundException, FileNotFoundException, SQLException {
        try {

            connectToDataBase();

            insertChat = connection.prepareStatement("INSERT INTO chat(session_id,query,respond)VALUES(?,?,?)");

            insertChat.setString(1, session_id);
            insertChat.setString(2, query);
            insertChat.setString(3, respond);
            
            insertChat.executeUpdate();

        } catch (SQLException error) {

            error.printStackTrace();
            System.out.print(error);
        } finally {
            closeConnection(connection);
        }
    }
}
