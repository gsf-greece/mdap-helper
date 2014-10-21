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
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luciddream
 */
public class UdpListener implements Runnable {

    private final byte[] buf;
    private final MulticastSocket s;
    private DatagramPacket recv;
    private final InetAddress group;
    private final InetAddress routerAddress;

    public UdpListener(int port) throws IOException {

        Enumeration<NetworkInterface> a = NetworkInterface.getNetworkInterfaces();
        while (a.hasMoreElements()) {
            System.out.println("Network Interface: " + a.nextElement());
        }

        group = InetAddress.getByName("224.0.0.103");
        routerAddress = InetAddress.getByName("192.168.1.1");
        s = new MulticastSocket(port);
        s.setNetworkInterface(NetworkInterface.getByName("eth1"));
        s.joinGroup(group);
        buf = new byte[1500];
    }

    @Override
    public void run() {
        recv = new DatagramPacket(buf, buf.length);
        try {
            System.out.println("[System]: ready to listen.");
            while (true) {
                s.receive(recv);
                //System.out.println("Received from: " + recv.getAddress());
                if (recv.getAddress().equals(routerAddress)) {
                    String received = new String(recv.getData(), 0, recv.getLength());
                    // Commands Logging
                    System.out.println("Received: " + received);
                    String[] routerData = received.split("\r\n");
                    for (String a : routerData) {
                        String[] tempData = a.split(":",2);
                        if (tempData.length > 1) {
                            MDAPClient.settings.put(tempData[0], tempData[1]);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(UdpListener.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                s.leaveGroup(group);
                s.close();
            } catch (IOException ex) {
                Logger.getLogger(UdpListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
