/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdap.client;

/**
 *
 * @author Luciddream
 */
public class CodeMap {
    String command;
    String hexcode;
    boolean toAnt;
    boolean secure;
    int seq;
    public CodeMap() {
        secure = false;
        seq = 0;
        toAnt = true;
    }
    
    public CodeMap(String command,String hexcode) {
        this.command = command;
        this.hexcode = hexcode;
        if ("ANT-SEARCH MDAP/1.1".equals(this.command)) {
            toAnt = false;
        }
    }
    public CodeMap(String command,int seq, boolean secure,String hexcode) {
        this(command, hexcode);
        this.seq = seq;
        this.secure = secure;
    }
    public String genCommand() {
        StringBuilder a = new StringBuilder();
        a.append(command).append("\r\n");
        if (seq > 0) a.append("SEQ-NR:").append(seq).append("\r\n");
        if (toAnt) a.append("TO-ANT:").append(MDAPClient.settings.get("TO-ANT")).append("\r\n");
        if (secure) a.append("USER-ID:").append(MDAPClient.settings.get("USER-ID")).append("\r\n").append("USER-PWD:").append(MDAPClient.settings.get("USER-PWD")).append("\r\n");
        a.append(hexcode);
        return a.toString();
        }
}
