/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.crc;

/**
 *
 * @author arthurhoch
 */
public class CRC {
    
    
    private final String msg;
    private final String polynomial;
    
    public CRC(String msg, String polimonio){
        this.msg = msg;
        this.polynomial = polimonio;
    }
    
    public String getCrc(){
        return doCRC(msg);
    }
    
    private int i(char c){
        return Character.getNumericValue(c);
    }
    
    private String doCRC(String msg){
        for (int i = 1; i < polynomial.length(); i++) {
            msg+="0";
        }
        
        System.out.println(msg);
        
        String fromMsg = msg.substring(0, polynomial.length());
        String result = null;
        for (int i = 6; i < msg.length(); i++) {
            result = doXor(fromMsg);
            fromMsg = result.substring(1, result.length()) + msg.charAt(i);
        }
        result = doXor(fromMsg);
        return result.substring(1, result.length());
    }
    
    private String doXor(String fromMsg) {
        String returnResult = new String();
        if(fromMsg.startsWith("0")) {
            for (int i = 0; i < fromMsg.length(); i++) {
                returnResult += i(fromMsg.charAt(i)) ^ 0;
                System.out.println("Com:" + i(fromMsg.charAt(i)) + " e 0 = " + returnResult);
            }
        }else{
            for (int i = 0; i < fromMsg.length(); i++) {
                returnResult += i(fromMsg.charAt(i)) ^ i(polynomial.charAt(i));
                System.out.println("Com:" + i(fromMsg.charAt(i)) + " e " + i(polynomial.charAt(i)) + " = "  + returnResult);
            }  
        }
                
        return returnResult;
    }
    
}
