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

/**
 *
 * @author gustavo
 */
public class ServerTomas  {
    
    
    public static void main(String[] args) throws IOException {
        
        Random generator = new Random();
        
        String dataSend;
        
        String tomadaId = "";
        
        ArrayList<String> dataReceived = new ArrayList<>();
        
        ServerSocket sSocket = new ServerSocket(8000);
        
        while(true){
            
            System.out.println("Waiting connection...");
            
            Socket socket = sSocket.accept();
          
            BufferedReader in =
               new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            
            delay();
            
            System.out.println("Received from client:");
            
            do{
                dataReceived.add(in.readLine());
            }while (in.ready());
            
            dataReceived.stream().forEach((data) -> {
                System.out.println(data);
            });
            
            if(dataReceived.size() == 2){
                if(dataReceived.get(0).contains("tomada"))
                    tomadaId = dataReceived.get(1);
            }
            
            System.out.println("Tomada ID: " + tomadaId);
            
            
            if(generator.nextInt()%2 == 0)
                dataSend = "ON\n";
            else
                dataSend = "OFF\n";
            
            out.writeBytes(dataSend);
            
            dataReceived.clear();

        }

    }
   
    
    public static void delay(){
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerTomas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
