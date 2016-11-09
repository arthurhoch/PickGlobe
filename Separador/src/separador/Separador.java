/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package separador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author arthurhoch
 */
public class Separador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        String nomeArquivoLer = "/home/arthurhoch/pt_BR.dic";
        String nomeArquivoGravar = "/home/arthurhoch/pt_BR3.dic";

        BufferedReader br = new BufferedReader(new FileReader(nomeArquivoLer));
        BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivoGravar));

        br.readLine();

        String line = "";

        while ((line = br.readLine()) != null) {
            if (!line.contains("-")) {
                if (line.contains("/")) {
                    String[] palavras = line.split("/");
                    bw.write(palavras[0]);
                } else {
                    bw.write(line);
                }
                bw.write('\n');

            }

        }

    }

}
