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

        Socket clientSocket = new Socket("tomas.no-ip.org", 8000);

        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while (true) {
            out.writeBytes("tomada\n");
            out.writeBytes("288\n");
            System.out.println("Received from Server: " + in.readLine());
 
          //  ServerTomas.delay(500);
        }

    }

}
