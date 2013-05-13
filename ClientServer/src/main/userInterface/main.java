/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.userInterface;

/**
 *
 * @author abell
 */
public class main {
    
    public static void ser(String[] args){
        try{
            int port = Integer.parseInt(args[2]);
        } catch(java.lang.NumberFormatException ex) {
            System.out.println("Crap! That's not a port! " + ex);
        }
    }
    
    public static void sla(String[] args){
        try{
        String ip = args[2].split(":")[0];
        int port = Integer.parseInt(args[2].split(":")[1]);
        } catch(java.lang.NumberFormatException ex) {
            System.out.println("Crap! That's not a number! " + ex);
        }
    }

    public static void main(String[] args) {
        /*
         * PROGRAM: LCHS-B
         * 
         * SYNTAX----------------
         *      lchs-b [cli|ser]
         *               |   |
         *               |   |- Server ip and port
         *      Select port
         *      
         */
        try {
            if(args.length == 0 || args == null){
                System.out.println("This assumes \"lchs-b sla localhost:1234\"\nIf you don't want this, use \"lchs-b help\"");
                
            }
            if (args[0].toUpperCase().equals("SLA") && args.length > 2) {
                if (args[1].toUpperCase().equals("H") || args[1].toUpperCase().equals("HELP") || args[1].toUpperCase().equals("?")){
                    System.out.println("... sla [SERVER IP]:<PORT NUMBER>"); ///////////////////////////
                } else {
                    sla(args);
                }
            } else if (args[0].toUpperCase().equals("SER") && args.length > 2) {
                if (args[1].toUpperCase().equals("H") || args[1].toUpperCase().equals("HELP") || args[1].toUpperCase().equals("?")){
                    System.out.println("... ser <PORT NUMBER>"); ///////////////////////////
                } else {
                    ser(args);
                }
            } else if (args[0].toUpperCase().equals("--HELP")) {
                System.out.println("'sla ?' OR 'ser ?' for more help");
            } else {
                System.out.println("Input ignored due to error. Please see '--help' for assistance.");
            }
        }catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            System.out.println("Random error problem, not sure how this happened... see this: " + ex);
        }
    }
}
