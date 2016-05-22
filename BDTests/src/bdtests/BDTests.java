/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdtests;

import java.sql.Connection;
import java.sql.DriverManager;
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
                "jdbc:mysql://localhost",
                "root", "tomasunifesp.3309");
        
        System.out.println("Connected!!!");
        con.close();
    }
    
}
