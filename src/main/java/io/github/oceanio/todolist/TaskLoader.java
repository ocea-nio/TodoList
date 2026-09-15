package io.github.oceanio.todolist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class TaskLoader {
    private final String DB_NAME = "database.db";
    private Connection c = null;
    private Statement stmt = null;

    public TaskLoader(){
        try{
            Class.forName("org.sqlite.JDBC");
            this.c = DriverManager.getConnection("jdbc:sqlite:" + this.DB_NAME);

            this.stmt = c.createStatement();  
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void createTable(String tablename){
        try{
            this.stmt.executeQuery(
                "CREATE TABLE " + tablename +
                """
                (
                id UUID,
                description STRING,
                condition.TaskCondition
                )
                """);
            this.c.commit();
        }catch (SQLException e){
           System.out.println("------------------------------------------------");
           System.out.println(tablename+ " already exists.");
           System.out.println("------------------------------------------------");
        }
    }

public void readTable(String tablename){
        try{
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tablename);
            while (rs.next()) {
                UUID id = rs.getInt("id"); // get data of id col.
                String value = rs.getString("value"); // get data of value col.
                TaskCondition condition = rs.getString("condition");// get data of description col.
                System.out.println(id + "," + value + ","+ description); // print out the data.
            }
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertData(String tablename,int id, int value, String description){
        try{
            this.stmt.executeUpdate(
                "INSERT INTO " + tablename +
                """
                (
                \"id\",
                \"value\",
                \"description\"
                )
                """ 
                + 
                "VALUES(" 
                + id + ","
                + value + ","
                + "\"" +  description + "\")"
                );
                
            System.out.println("id"  + "->" + id + ", value"  + "->" + value +  ", description"  + "->" + description + " for " + tablename );
            
        }catch (SQLException e){
          System.out.println(e);
        }
    }

    public void updateTable(String tablename,int id){
        try{
            this.stmt.executeUpdate(
                "UPDATE " + tablename + " SET " + 
                """
                description = \" updated description.\" WHERE id = 
                """ 
                + 
                id
                );
        }catch (SQLException e){
          System.out.println(e);
        }
    }


    
    public void dropTable(String tablename){
        try{
            this.stmt.executeUpdate(
                "DROP TABLE " + tablename
                );
        }catch (SQLException e){
          System.out.println(e);
        }
    }

    public void closeConnection(){
        try {
            if (this.c != null) {
                this.c.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
