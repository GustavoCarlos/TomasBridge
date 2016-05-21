/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testhttprequest;

import java.io.IOException;

/**
 *
 * @author gustavo
 */
public class TestHTTPRequest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        HttpURLCon httpReq = new HttpURLCon();
        
        try {
            httpReq.sendGet("http://google.com");
            httpReq.sendPost("http://google.com");
        } catch (IOException e) {
            System.out.println("Failure to do operation!!!" + e);
        }
    }
    
}
