/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdtests;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author gustavo
 */
public class BDTests {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException{
        // TODO code application logic here
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost/tomas",
                "root", "tomasunifesp.3309");
        
        System.out.println("Connected!!!");
        
        /*DatabaseMetaData md = con.getMetaData();
        ResultSet rs = md.getTables(null, null, "%", null);
        
        while(rs.next()){
            System.out.println(rs.getString(3));
        }*/
        PreparedStatement stmt = con.prepareStatement("select * from tomadas");
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next())
        {
            System.out.println(rs.getString(3));
        }
        con.close();
    }
    
}
