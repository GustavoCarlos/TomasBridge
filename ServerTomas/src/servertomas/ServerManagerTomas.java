/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servertomas;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author gustavo
 */
public class ServerManagerTomas {
    
    private ServerSocket sSocket;
    private Socket socket;
    
    private String tomadaID = "";
    private String consumption = "";
    
    private BufferedReader inputSocket;
    private DataOutputStream outputSocket;
    
    
    private final ArrayList<String> dataReceived = new ArrayList<>();
    
    public void createSocketListener(int port) throws IOException{
        
        sSocket = new ServerSocket(port);
        
        socket = sSocket.accept();
        
        socket.setSoTimeout(2000);
        
        inputSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        outputSocket = new DataOutputStream(socket.getOutputStream());
    }
    
    public void closeSocket(){
        try {
            socket.close();
            sSocket.close();
            socket = null;
        } catch (IOException ex) {
            System.out.println("IOEcxception on close sopcket");
        }

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
                System.out.println("inside function");
                String ldataRec = inputSocket.readLine();
                System.out.println(ldataRec);
                if(ldataRec != null)
                    dataReceived.add(ldataRec);
                else{
                    sSocket.close();
                    socket = null;
                    System.out.println("data received = null");
                    return false;
                }
            } while (inputSocket.ready());
        } catch (IOException | NullPointerException ex) {
            
            System.out.println(ex.toString());
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
    
    public int messageType() {
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
            } else if (dataReceived.size() == 1) {
                String localStr = dataReceived.get(0);
                if (localStr.contains("cons")) {

                    tomadaID = localStr.split("cons")[0].substring(2);
                    consumption = localStr.split("cons")[1];
                    dataReceived.clear();
                    return 2;
                } else {
                    dataReceived.clear();
                    return 0;
                }
            } else {
                dataReceived.clear();
                return 0;
            }
        } catch (Exception e) {
            dataReceived.clear();
            return 0;
        }
    }
    
    public String getTomadaID(){
        return tomadaID;
    }
    
    public String getTomadaConsumption(){
        return consumption;
    }
    
}
