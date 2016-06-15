/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servertomas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gustavo
 */
public class TomasDataBase {
    
    private final String dbUser = "root";
    private final String dbPwd = "tomasunifesp.3309";
    private final String DB = "jdbc:mysql://localhost/tomas";
    private final String DBtestes = "jdbc:mysql://tomas.no-ip.org/tomas";
    
    //Used to connect to dataBase
    private Connection dbCon;
    
    private PreparedStatement dbStmt;
    private ResultSet   dbResult;
    
    
    public boolean connectToDataBase(){
        try {
            dbCon = DriverManager.getConnection(DB, dbUser, dbPwd);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public boolean closeConnection(){
    
        try {
            if (dbCon.isValid(5)) {
                dbCon.close();
                if(dbCon.isClosed())
                    return true;
                else
                    return false;
            }
            else
                return true;
        } catch (SQLException ex) {
            return true;
        }
    }
    
    public int getTomadaStatus(String id){
        
        System.out.println("Entered in the getTomadaStatus");
        
        try{
            
            String SQL = "select * from tomadas where id = "+ id;
            dbStmt = dbCon.prepareStatement(SQL);
            
            dbResult = dbStmt.executeQuery();
            
            
            if (dbResult != null && dbResult.next()) {

                boolean response = dbResult.getBoolean("status");

                System.out.println(response);
                if(response)
                    return 1;
                else
                    return 0;
            }
            
            
            return -1;

        }
        catch(SQLException ex){
            System.out.println(ex.toString());
            System.out.println("Exception select");
            return -1;
        }
        catch(NullPointerException npEx){
            System.out.println("Exeption null pointer");
            return -1;
        }
        
    }
    
    public boolean isConnected(){
        
        try {
            return dbCon.isValid(3);
            
        } catch (SQLException ex) {
            return false;
        }
    }
    
}
