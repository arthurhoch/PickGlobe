/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.bit;

import br.unisc.transform.Transform;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author arthurhoch
 */
public class Bit {
    
    private static final boolean pair = true;
    private static final boolean odd = false;
    
    private final String msg;
    private final Transform transform;
    private final boolean type;
    
    public Bit(String msg, boolean type){
        this.msg = msg;
        this.type = type;
        this.transform = new Transform();
    }
    
    public List<String> getBits(){
        return transform.String2Binary(msg);
    }
    
    public List<String> getBitsCotrol(){ 
        
        List<Integer> bitsControl = types();
        
        List<String> control = new LinkedList();
        
        bitsControl.stream().map((i) -> getPairOrOdd(i)).forEach((subType) -> {
            if(type == subType)
                control.add("0");
            else
                control.add("1");
        });
        
        return control;
    }
    
    private List<Integer> types(){
        List<Integer> bitsControl = new LinkedList<>();
        
        
        getBits().stream().map((b) -> {
            int countOne = 0;
            for (char c : b.toCharArray()){
                if(c == '1')
                    countOne++;
            }
            return countOne;
        }).forEach((countOne) -> {
            bitsControl.add(countOne);
        });
        
        return bitsControl;
    }
    
    private boolean getPairOrOdd(int numberOfOne){
        if((numberOfOne % 2) == 0){
            return pair;
        }
        
        return odd;
    }
    
}
