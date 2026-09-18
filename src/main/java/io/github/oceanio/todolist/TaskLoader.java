package io.github.oceanio.todolist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
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

    public void createTable(String tablename,TaskData data){
        if (data == null) {
            return; 
        }
        try{
            stmt.executeQuery("CREATE TABLE " + tablename +"""
            (
                name TEXT,
                uuid TEXT,
                description TEXT,
                condition TEXT
            )
            """);
            
            c.commit();
        }catch (SQLException e){
           System.out.println("------------------------------------------------");
           System.out.println(tablename+ " already exists.");
           System.out.println("------------------------------------------------");
        }
    }

public Map<String,TaskData> readTable(String tablename){
    Map<String,TaskData> results;
        try{
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tablename);
            while (rs.next()) {
                String name = rs.getString("name");
                String uuid = rs.getString("uuid"); // get data of id col.
                String description = rs.getString("description"); // get data of value col.
                String condition = rs.getString("condition");// get data of description col.
                TaskData data = new TaskData(UUID.fromString(uuid),description,)
                results.put(name, data); // print out the data.
            }
            rs.close();
            return results;
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertData(String tablename,String name,TaskData data){
        
        try{
            stmt.executeUpdate(
                "INSERT INTO " + tablename +
                """
                (
                \"name\",
                \"uuid\",
                \"description\",
                 \"condition\"
                )
                """ 
                + 
                "VALUES(" + name + ","+ data.getUUID().toString() + "," +  data.getContents() + ","+ "\"" + data.getCondition().toString() + "\")"
            );
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
