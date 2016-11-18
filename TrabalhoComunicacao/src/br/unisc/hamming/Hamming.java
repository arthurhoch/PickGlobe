/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.hamming;

import br.unisc.transform.Transform;
import static java.lang.Math.pow;
import static java.lang.Math.random;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author arthurhoch
 */
public class Hamming {
    
    
    private final Transform transform;
    
    private String msg;
    
    private List<String> binaryList;
    
    public Hamming(){
        transform = new Transform();
        binaryList = new LinkedList();
    }

    public String getMensagem() {
        return msg;
    }

    public void setMensagem(String msg) {
        this.msg = msg;
    }
    
    private int i(char c){
        return Character.getNumericValue(c);
    }
    
    public List<String> getInputBytes(){
        return transform.String2Binary(msg);
    }
    
    public List<String> doHamming(){
        binaryList = getInputBytes();
        List<String> hammingList = new LinkedList<>();
        
        binaryList.stream().map((b) -> {
            String hamming = new String();
            
            hamming += i(b.charAt(0)) ^ i(b.charAt(1)) ^ i(b.charAt(3)) ^ i(b.charAt(4)) ^ i(b.charAt(6)); 
            hamming += i(b.charAt(0)) ^ i(b.charAt(2)) ^ i(b.charAt(3)) ^ i(b.charAt(5)) ^ i(b.charAt(6));
            hamming += i(b.charAt(0));
            hamming += i(b.charAt(1)) ^ i(b.charAt(2)) ^ i(b.charAt(3)) ^ i(b.charAt(7));
            hamming += i(b.charAt(1));
            hamming += i(b.charAt(2));
            hamming += i(b.charAt(3));
            hamming += i(b.charAt(4)) ^ i(b.charAt(5)) ^ i(b.charAt(6)) ^ i(b.charAt(7));
            hamming += i(b.charAt(4));
            hamming += i(b.charAt(5));
            hamming += i(b.charAt(6));
            hamming += i(b.charAt(7));
            return hamming;            
        }).forEach((hamming) -> {
            hammingList.add(hamming);
        });
     
        return hammingList;
    }
    
    public String undoHamming(List<String> hammingList){
        
        binaryList.clear();
        
        hammingList.stream().map((b) -> {
            String letter = new String();
            letter += b.charAt(2);
            letter += b.charAt(4);
            letter += b.charAt(5);
            letter += b.charAt(6);
            letter += b.charAt(8);
            letter += b.charAt(9);
            letter += b.charAt(10);
            letter += b.charAt(11);
            return letter;            
        }).forEach((letter) -> {
            binaryList.add(letter);
        });
        
        return transform.Binary2String(binaryList);
    }
    
    public List<String> putError(List<String> hammingList, double errorPorcentPerBit){
        List<String> hammingWrongList = new LinkedList<>();

        hammingList.stream().forEach((hl) -> {
            String wrongLetter = new String();
            for (char c : hl.toCharArray()) {
                if(errorPorcentPerBit/100 > random())
                    if(c == '1')
                        wrongLetter += '0';
                    else
                        wrongLetter += '1';
                else
                    wrongLetter += c;
            }
            hammingWrongList.add(wrongLetter);
        });
        
        return hammingWrongList;
    }
    
    private int wrongBit(int[] error){
        int bit = 0;
        
        for (int i = 0; i < error.length; i++) {
            if(error[i] == 1) bit += pow(2,i);          
        }
        
        return bit;
    }
    
    private boolean hasError(int[] bits){
        for (int bit : bits) {
            if(bit == 1)
                return true;
        }
        
        return false;
    }
    
    public List<String> checkError(List<String> hammingList){
        List<String> errorList = new LinkedList<>();
        
        int bitControl[] = new int[4];
        for (String hl : hammingList) {
            bitControl[0] = i(hl.charAt(0)) ^ i(hl.charAt(2)) ^ i(hl.charAt(4)) ^ i(hl.charAt(6)) ^ i(hl.charAt(8)) ^ i(hl.charAt(10));
            bitControl[1] = i(hl.charAt(1)) ^ i(hl.charAt(2)) ^ i(hl.charAt(5)) ^ i(hl.charAt(6)) ^ i(hl.charAt(9)) ^ i(hl.charAt(10));
            bitControl[2] = i(hl.charAt(3)) ^ i(hl.charAt(4)) ^ i(hl.charAt(5)) ^ i(hl.charAt(6)) ^ i(hl.charAt(11));
            bitControl[3] = i(hl.charAt(7)) ^ i(hl.charAt(8)) ^ i(hl.charAt(9)) ^ i(hl.charAt(10)) ^ i(hl.charAt(11));
            
            if (hasError(bitControl)){
                errorList.add("In bit "+wrongBit(bitControl));
            }else {
                errorList.add("No");
            }
            
        }
        
        return errorList;
    }

    public List<String> getBinaryList() {
        return binaryList;
    }
    
    
    
}
