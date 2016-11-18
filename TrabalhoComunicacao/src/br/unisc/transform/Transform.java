/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.transform;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author arthurhoch
 */
public class Transform {
    
    public Transform(){
        
    }
    
    public List<String> String2Binary(String string){
        
        List<String> binaryList = new LinkedList<>();
        
        for (char c : string.toCharArray()) {
            String binary = Integer.toBinaryString(c);
            
            while(binary.length()<8){
                binary = "0" + binary;
            }
            
            binaryList.add(binary);
        }
        
        
        return binaryList;
    }
    
    public String Binary2String(List<String> binaryList){
        
        String phrase = new String();
        int letter;
        
        for (String b : binaryList) {
            letter = Integer.parseInt(b,2);
            phrase += (char) letter;
        }
        
        return phrase;
    }
}
