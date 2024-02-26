package sockets;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class example1 {
    public static void main(String args[]){
        //client
        try {
            Socket sock = new Socket("url.com", 25);//mean connect to url.com port 25(SMTP mail service)
            //url.com can replace by IP, e.g.'192.168.xx.xx'
        } catch (UnknownHostException uhe){
            System.out.println("Can't find host");
        } catch (IOException ioe){
            System.out.println("Error connecting to host.");
        }
    }
}
