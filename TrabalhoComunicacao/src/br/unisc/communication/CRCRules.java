/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.communication;

import br.unisc.crc.CRC;

/**
 *
 * @author arthurhoch
 */
public class CRCRules {
    
    private final CRC crc;
    
    private final String msg;
    private final String polinomio;
    
    public CRCRules(String msg, String polinomio){
        this.msg  = msg;
        this.polinomio = polinomio;
        this.crc = new CRC(msg, polinomio);
    }

    public String getMsg() {
        return msg;
    }

    public String getPolinomio() {
        return polinomio;
    }

    public String getCRCcode() {
        return crc.getCrc();
    }

    public String getMsgWithCrc() {
        return msg + crc.getCrc();
    }
    
    
    
    
    
}
