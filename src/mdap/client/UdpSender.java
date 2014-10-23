/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdap.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luciddream
 */
public class UdpSender implements Runnable {

    private DatagramPacket data;
    private final InetAddress group;
    private final MulticastSocket s;

    public UdpSender() throws IOException {
        group = InetAddress.getByName("224.0.0.103");
        s = new MulticastSocket(3235);
    }
    public void stop() throws IOException {
        s.leaveGroup(group);
        s.close();
    }

    public void send(String input) {
        data = new DatagramPacket(input.getBytes(), input.length(),
                group, 3235);
        System.out.println("Sending Request: " + input);
        try {
            s.send(data);
            Thread.sleep(2000);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(UdpSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            s.joinGroup(group);
        } catch (IOException ex) {
            Logger.getLogger(UdpSender.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("[System]: Error, exiting now...");
            System.exit(-1);
        }
        System.out.println("[System]: ready to send data.");
        send(MDAPClient.msg.get("SEARCH").genCommand());
        try {
            Thread.sleep(2000); // TODO: This needs to be replaced.
        } catch (InterruptedException ex) {
            Logger.getLogger(UdpSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        send(MDAPClient.msg.get("EXEC-CLI").genCommand());
        //send(MDAPClient.msg.get("INFO-EXPANDED").genCommand());
        Scanner inputScanner = new Scanner(System.in);
        while (true) {
            System.out.println("[System]: Press any key to exit");
            String keyInput = inputScanner.next();
            break;
        }
        try {
            stop();
        } catch (IOException ex) {
            Logger.getLogger(UdpSender.class.getName()).log(Level.SEVERE, null, ex);
        } finally { 
            System.exit(-1); 
        }
    }
}
