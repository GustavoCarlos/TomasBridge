/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servertomas;

import java.io.*;
import java.net.*;

/**
 *
 * @author gustavo
 */
public class ClientTest {

    public static void main(String[] args) throws IOException {

        //Socket clientSocket = new Socket("tomas.no-ip.org", 8000);//"localhost", 8000);//
        //DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
        //BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        int i = 0;
        while (true) {

            try {
                Socket clientSocket = new Socket("tomas.no-ip.org", 8000);//"localhost", 8000);//

                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                out.writeBytes("tomada\n");
                out.writeBytes("305\n");

                System.out.println("Received from Server: " + in.readLine());
                
                ServerTomas.delay(2000);

               /* if (i >= 10) {
                    out.writeBytes("id300cons5.54\n");
                    ServerTomas.delay(1000);
                    System.out.println("Sent");
                    i = 0;
                }
                i++;*/
            } catch (Exception e) {
                System.out.println("ERROR TO CONNECT");
            }
        }
    }

}
