/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.communication;

import br.unisc.bit.Bit;
import java.util.List;

/**
 *
 * @author arthurhoch
 */
public class BitRule {
    
    private String msg;
    private boolean type;
    private List<String> bitsControl;
    
    private Bit bit;
    
    public BitRule(String msg, boolean type){
        this.msg = msg;
        this.type = type;
        this.bit = new Bit(msg, type);
        this.bitsControl = bit.getBitsCotrol();
    }
    
    public String getCaracter(int i){
        return String.valueOf(msg.charAt(i));
    }
    
    public String getBitControl(int i){
        return bitsControl.get(i);
    }
    
    public String getBits(int i, int j){
        return String.valueOf(bit.getBits().get(i).charAt(j));
    }
    
    public int length(){
        return msg.length();
    }
    
    
    
    
    
    
    
}
