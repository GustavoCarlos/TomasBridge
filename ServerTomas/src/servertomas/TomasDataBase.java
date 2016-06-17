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
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author gustavo
 */
public class TomasDataBase implements Runnable{
    
    private final String dbUser = "root";
    private final String dbPwd = "tomasunifesp.3309";
    private final String DB = "jdbc:mysql://localhost/tomas";
    //private final String DBtestes = "jdbc:mysql://tomas.no-ip.org/tomas";
    
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
    
    public boolean saveTomasConsumption(String id, String cons){
        //
        //INSERT INTO `tomadas_consumo`(`id`, `id_tomada`, `dia`, `mes`, `ano`, `hora`, `minuto`, `consumo`) VALUES ([value-1],[value-2],[value-3],[value-4],[value-5],[value-6],[value-7],[value-8])
        //
        
        GregorianCalendar dateHour = new GregorianCalendar();
        
        try{
            int day = dateHour.get(Calendar.DAY_OF_MONTH);
            int month = dateHour.get(Calendar.MONTH) + 1;
            int year = dateHour.get(Calendar.YEAR);
            int hour = dateHour.get(Calendar.HOUR_OF_DAY);
            int min = dateHour.get(Calendar.MINUTE);
            
            if(hour >= 3)
                hour -= 3;
            else
                hour += 21;
            
            String SQL = "INSERT INTO `tomadas_consumo`(`id_tomada`, `dia`, `mes`, `ano`, `hora`, `minuto`, `consumo`) "
                    + "VALUES ("
                    + id + ","
                    + day + ","
                    + month + ","
                    + year + ","
                    + hour + ","
                    + min + ","
                    + cons + ")";
            
            System.out.println(SQL);
            dbStmt = dbCon.prepareStatement(SQL);
            dbStmt.executeUpdate(SQL);
            
            return true;
        }
        catch(SQLException ex){
            System.out.println(ex.toString());
            System.out.println("Exception select");
            return false;
        }
        catch(NullPointerException npEx){
            System.out.println("Exeption null pointer");
            return false;
        }
    }
    
    public boolean isConnected(){
        
        try {
            return dbCon.isValid(3);
            
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public void checkSchedulesTomas(){
        
        
        try{
            String SQL = "select * from programacao_horario";
            dbStmt = dbCon.prepareStatement(SQL);
            dbResult = dbStmt.executeQuery();
            
            while(dbResult.next()){
      
                Integer[] progTime = new Integer[5];
                progTime[0] = dbResult.getInt("dia");
                progTime[1] = dbResult.getInt("mes");
                progTime[2] = dbResult.getInt("ano");
                progTime[3] = dbResult.getInt("hora");
                progTime[4] = dbResult.getInt("minuto");
                
                System.out.println("dia: " + progTime[0] + 
                        " mes: " + progTime[1]+
                        " ano: " + progTime[2]+
                        " hora: " + progTime[3] +
                        " minuto: "+progTime[4]);
                
                if(compareProgCurrent(progTime)){
                
                    int id_tom = dbResult.getInt("id_tomada");
                    int status = dbResult.getInt("status");
                    
                    updateTomadaStatus(status, id_tom);
                    
                }

            }
            
            
            
        }catch(SQLException ex){
            System.out.println("SQL Exception...");
        }
        catch(NullPointerException nEx){
            System.out.println("Null pointer Exception... ");
        }
        
    }
    
    public void updateTomadaStatus(int status, int ID){
        
        String SQL = "update tomadas set status = "+ status + " where id = " + ID; 
        
        System.out.println("SQL: " + SQL);
                    
        
        try {
            
            dbStmt = dbCon.prepareStatement(SQL);
            dbStmt.executeUpdate(SQL);

            System.out.println("Right...");
        } catch (SQLException ex) {
            System.out.println("SQL exception on UPDATE");
        } catch (NullPointerException nEx) {
            System.out.println("Null pointer exception on UPDATE");
        }
                
    }
    
    public boolean compareProgCurrent(Integer[] progDate){
        
        GregorianCalendar dateHour = new GregorianCalendar();
        
        Integer[] currentTime = new Integer[5];
        currentTime[0] = dateHour.get(Calendar.DAY_OF_MONTH);
        currentTime[1] = dateHour.get(Calendar.MONTH) + 1;
        currentTime[2] = dateHour.get(Calendar.YEAR);
        currentTime[3] = dateHour.get(Calendar.HOUR_OF_DAY);
        currentTime[4] = dateHour.get(Calendar.MINUTE);
        
        if(currentTime[3] >= 3)
            currentTime[3] -= 3;
        else
            currentTime[3] += 21;
        
        System.out.println("************Current Time************");
        System.out.println("dia: " + currentTime[0] + 
                        " mes: " + currentTime[1]+
                        " ano: " + currentTime[2]+
                        " hora: " + currentTime[3] +
                        " minuto: "+currentTime[4]);
        
        System.out.println("**************************************");
        
        return Arrays.equals(currentTime, progDate);
    }
    
    //Method to check the programable plugs
    @Override
    public void run() {
        
        int minuteBefore = 0;
        int i = 0;
        //TODO
        while(true){
            
            GregorianCalendar dateHour = new GregorianCalendar();
            
            if(i == 0){
                minuteBefore = dateHour.get(Calendar.MINUTE);
                i = 1;
            }
            
            if(minuteBefore != dateHour.get(Calendar.MINUTE)){
                checkSchedulesTomas();
                minuteBefore = dateHour.get(Calendar.MINUTE);
            }
            
        }
    }
}
