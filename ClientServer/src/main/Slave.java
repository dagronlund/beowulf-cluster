/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Owner
 */
public class Slave {
    String address;
    int port;
    
    public Slave(){
        address = "localhost";
        port = 1234;
        bootup();
    }
    
    public Slave(String ad, int po){
        address = ad;
        port = po;
        bootup();
    }
    
    public void bootup(){
        System.out.println("Starting Slave...");
        
    }
}
