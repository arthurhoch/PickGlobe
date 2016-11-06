/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.view.tabelas;

/**
 *
 * @author arthurhoch
 */
public class Temporizador {

    private final int key;
    private int time;

    public Temporizador(int key, int time) {
        this.key = key;
        this.time = time;
    }

    public int getKey() {
        return key;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
