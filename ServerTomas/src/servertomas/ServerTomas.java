/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package servertomas;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gustavo
 */
public class ServerTomas {

    public static void main(String[] args) {
             
        int PORT = 8000;
        
        TomasDataBase database = new TomasDataBase();
        
        ServerManagerTomas serverTomas = new ServerManagerTomas();
        
        if(database.connectToDataBase())
            System.out.println("Connected to database");
        else
            System.out.println("Failure to connect to database");
        
        Thread checkTomadas = new Thread(database);
        
        checkTomadas.start();
        
        
        while(true){
            //Connect to dataBase
            //Create Server Listener
            //Receive Msg
            //Verify Content
            //Get information from database
            //Send answer
            
            System.out.println("Connecting to database");
            if(database.connectToDataBase())
                System.out.println("Connected to database");
            else
                System.out.println("Failure to connect to database");
            
            System.out.println("Waiting for connection on PORT: " + PORT);
            try{
                serverTomas.createSocketListener(PORT);
            }catch(IOException ex){
                System.out.println("Error to create socket...");
            }
            
            while(serverTomas.isSocketConnected()){
                
                System.out.println("Waiting data from tomada");
                if(serverTomas.receiveData()){
                    System.out.println("Data Received");
                    
                    int dataType = serverTomas.messageType();
                    if(dataType == 1){
                        System.out.println("Message of tomada status type");
                          
                        int tomadaStatus = database.getTomadaStatus(serverTomas.getTomadaID());
                        System.out.println("Tomada ID: " + serverTomas.getTomadaID());
                        System.out.println("Tomada Status: " + tomadaStatus);
                        switch(tomadaStatus){
                            case 0:
                                System.out.println("OFF sent");
                                serverTomas.sendData(" OFF\n");
                                break;
                            case 1:
                                System.out.println("ON sent");
                                serverTomas.sendData(" ON\n");
                                break;
                            case -1:
                                //Just send a new line in case of error
                                serverTomas.sendData("\n");
                                break;
                        }
                    }
                    else if(dataType == 2){
                        //id288cons3.234\n
                        System.out.println("Consumption message received");
                        if(database.saveTomasConsumption(serverTomas.getTomadaID(), 
                                serverTomas.getTomadaConsumption()))
                            System.out.println("Data consumption saved with success!!!");
                        else
                            System.out.println("Data consumption NOT saved!!!");
                    }
                    else{
                        System.out.println("Other message received");
                    }
                }
                
                serverTomas.closeSocket();


            
            }
            
        }
    }

    public static void delay(int milis) {
        try {
            Thread.sleep(milis);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerTomas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
