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

/**
 *
 * @author gustavo
 */
public class ServerManagerTomas {
    
    ServerSocket sSocket;
    Socket socket;
    
    String tomadaID = "";
    
    BufferedReader inputSocket;
    DataOutputStream outputSocket;
    
    
    ArrayList<String> dataReceived = new ArrayList<>();
    
    public void createSocketListener(int port) throws IOException{
        
        sSocket = new ServerSocket(port);
        
        socket = sSocket.accept();
        
        inputSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        outputSocket = new DataOutputStream(socket.getOutputStream());
    }
    
    public boolean isSocketConnected(){
        try {
            return socket.isConnected();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean receiveData() {

        try {
            do {
                String ldataRec = inputSocket.readLine();
                if(ldataRec != null)
                    dataReceived.add(ldataRec);
                else{
                    sSocket.close();
                    socket = null;
                    return false;
                }
            } while (inputSocket.ready());
        } catch (IOException ex) {
            return false;
        }

        return true;
    }
    
    public boolean sendData(String data) {
        try {
            outputSocket.writeBytes(data);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public int messageType(){
        //Type 0 message Failure
        //Type 1 message Status
        //Type 2 current comsuption

        try {

            if (dataReceived.size() == 2) {
                //tomada\n
                //288\n
                if (dataReceived.get(0).contains("tomada")) {
                    tomadaID = dataReceived.get(1);
                    dataReceived.clear();
                    return 1;
                } else {
                    dataReceived.clear();
                    return 0;
                }
            } else {
                dataReceived.clear();
                return 2;
            }
        } catch (Exception e) {
            dataReceived.clear();
            return 0;
        }
    }
    
    public String getTomadaID(){
        return tomadaID;
    }
    
}
