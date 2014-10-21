package mdap.client;

import java.io.*;
import java.util.*;

public class MDAPClient {
    public static Map<String,CodeMap> msg = new LinkedHashMap<>();
    public static Map<String,String> settings = new LinkedHashMap<>();
    
    public static void main(String[] args) throws IOException { 
        
        /** 
         * Ask for username and password
         * */
        Scanner inputScanner = new Scanner(System.in);
        String keyInput = null;
        System.out.println("Enter your username:");
        keyInput = inputScanner.nextLine();
        settings.put("USER-ID", keyInput);
        System.out.println("Enter your password:");
        keyInput = inputScanner.nextLine();
        settings.put("USER-PWD", keyInput);
    
        msg.put("SEARCH",new CodeMap("ANT-SEARCH MDAP/1.1","46"));
        // msg.put("INFO-EXPANDED","INFO MDAP/1.2\r\nSEQ-NR:1\r\nTO-ANT:1208RA82F\r\nUSER-ID:\r\nUSER-PWD:\r\n0C");
        msg.put("INFO-EXPANDED", new CodeMap("INFO MDAP/1.2",1,true,"0C"));
       //msg.put("BITLOADINGINFO","EXEC-CLI MDAP/1.2\r\nCLI-CMD:xdsl debug bitloadinginfo\r\nSEQ-NR:1\r\nTO-ANT:1208RA82F\r\nUSER-ID:gsf\r\nUSER-PWD:\r\n1F");
      // msg.put("EXEC-CLI MDAP/1.2\r\nSEQ-NR:2\r\nTO-ANT:1208RA82F\r\nUSER-ID:gsf\r\nUSER-PWD:\r\n5F");
      // msg.put("INFO MDAP/1.2\r\nSEQ-NR:1\r\nTO-ANT:1208RA82F\r\nUSER-ID:gsf\r\nUSER-PWD:\r\n0F");
      // msg.put("INFO MDAP/1.2\r\nSEQ-NR:1\r\nTO-ANT:1208RA82F\r\nUSER-ID:gsf\r\nUSER-PWD:\r\n0E");
        
        UdpListener listener = new UdpListener(3235);
        UdpSender sender = new UdpSender();
        
        Thread listenerThread = new Thread(listener);
        listenerThread.start();
        Thread senderThread = new Thread(sender);
        senderThread.start();
    }
}