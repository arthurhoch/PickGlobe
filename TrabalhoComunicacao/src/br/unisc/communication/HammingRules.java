/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.communication;

import br.unisc.hamming.Hamming;
import java.util.List;

/**
 *
 * @author arthurhoch
 */
public class HammingRules {
    
    private String inputMessage;
    private int porcentError;
    
    private final Hamming hamming;
    
    private int messageLenght;
    
    private char[] inputLetters;
    private List<String> inputBytes;
    private List<String> inputHamming;
    
    private List<String> wrongHamming;
            
    private char[] outputLetters;
    private List<String> outputByter;
    private List<String> outputError;
    
    
    public HammingRules(){
        hamming = new Hamming();
    }

    public void setInputMessage(String inputMessage) {
        this.hamming.setMensagem(inputMessage);
        this.inputMessage = inputMessage;
        this.inputLetters = inputMessage.toCharArray();
        this.inputBytes = hamming.getInputBytes();
        this.inputHamming = hamming.doHamming();
        this.messageLenght = inputMessage.length();
    }

    public void setPorcentError(int porcentError) {
        this.porcentError = porcentError;
        this.wrongHamming = hamming.putError(inputHamming, porcentError);
        this.outputLetters = hamming.undoHamming(wrongHamming).toCharArray();
        this.outputByter = hamming.getBinaryList();
        this.outputError = hamming.checkError(wrongHamming);
    }
    
    

    public String getInputMessage() {
        return inputMessage;
    }

    public Hamming getHamming() {
        return hamming;
    }

    public int getInputLenght() {
        return messageLenght;
    }

    public char getInputLetters(int i) {
        return inputLetters[i];
    }

    public String getInputBytes(int i) {
        return inputBytes.get(i);
    }

    public String getInputHamming(int i) {
        return inputHamming.get(i);
    }

    public String getWrongHamming(int i) {
        return wrongHamming.get(i);
    }

    public char getOutputLetters(int i) {
        return outputLetters[i];
    }

    public String getOutputBytes(int i) {
        return outputByter.get(i);
    }

    public String getOutputError(int i) {
        return outputError.get(i);
    }

}
