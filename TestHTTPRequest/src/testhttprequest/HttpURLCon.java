/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testhttprequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author gustavo
 */
public class HttpURLCon {

    public static final String USER_AGENT = "Mozilla/5.0";

    //Method to send HTTP get request
    public void sendGet(String url) throws IOException {

        URL dURL = new URL(url);
        HttpURLConnection con = (HttpURLConnection) dURL.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //Success
            //Read response data
            BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = input.readLine()) != null) {
                response.append(inputLine);
            }

            input.close();

            //Show data 
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }

    }

    public void sendPost(String url) throws IOException {
        URL dURL = new URL(url);
        HttpURLConnection con = (HttpURLConnection) dURL.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);

        con.setDoOutput(true);
        OutputStream output = con.getOutputStream();
        output.write("tomas_Status=ON".getBytes());
        output.flush();
        output.close();

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = input.readLine()) != null) {
                response.append(inputLine);
            }
            input.close();
            
            System.out.println(response.toString());
        }else{
            System.out.println("POST request not worked");
        }

    }

}
