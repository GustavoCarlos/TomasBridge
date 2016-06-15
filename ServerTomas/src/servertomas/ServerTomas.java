/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package servertomas;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

import java.util.Date;


/**
 *
 * @author gustavo
 */
public class ServerTomas {

    public static void main(String[] args) {
        
        int PORT = 8000;
        
        TomasDataBase database = new TomasDataBase();
        
        ServerManagerTomas serverTomas = new ServerManagerTomas();
        
        Date date = new Date();
        
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
                    
                    if(serverTomas.messageType() == 1){
                        System.out.println("Message of tomada status type");
                        
                        
                        database.setTomasConsumption(serverTomas.getTomadaID().toString(), "3.456");
                        
                        int tomadaStatus = database.getTomadaStatus(serverTomas.getTomadaID());
                        System.out.println("Tomada ID: " + serverTomas.getTomadaID());
                        System.out.println("Tomada Status: " + tomadaStatus);
                        switch(tomadaStatus){
                            case 0:
                                serverTomas.sendData("OFF\n");
                                break;
                            case 1:
                                serverTomas.sendData("ON\n");
                                break;
                            case -1:
                                //Do not answer in case of error
                                serverTomas.sendData("\n");
                                break;
                        }
                    }
                    else{
                        //TODO implementation of the other message types
                        
                        
                        //id288cons3.234\n
                        
                        System.out.println("Other message received");
                        
                    }
                }
            
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
