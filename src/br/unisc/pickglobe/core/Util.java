/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.core;

import br.unisc.pickglobe.model.Extensao;
import br.unisc.pickglobe.model.Link;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author arthurhoch
 */
public class Util {

    public List getLinksPage(String url, List<Extensao> extensoes) {
        Document doc;

        List<Link> listaLinks = new LinkedList<>();
        Md5helper md5 = new Md5helper();
        String md5String;

        try {
            doc = Jsoup.connect(url).get();
            String asc = null;
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                asc = (link.attr("href"));
                if (asc.contains("http")) {

                    boolean contem = false;
                    for (Extensao extensao : extensoes) {
                        if(asc.contains(extensao.getTipoExtensao()))
                            contem = true;
                    }

                    if ((md5String = md5.string2md5(asc)) != null && contem) {
                        Link l = new Link();
                        l.setUrl(asc);
                        l.setCaminho("./pages/" + md5.string2md5(asc) + "/");

                        listaLinks.add(l);
                    }
                }
            }
        } catch (Exception e) {
            System.err.printf("In: " + url, e);
        }

        return listaLinks;
    }
    
    public boolean saveListLinks(List<Link> listaLink) {
        
        listaLink.stream().forEach((link) -> {
            try {
                String page = Jsoup.connect(link.getUrl()).get().html();
                savePage(link, page);
            } catch (IOException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        return true;
    }
    
    public void savePage(Link link, String page) throws IOException {
        try {
            

            File f = new File(link.getCaminho());
            f.mkdir();

            try (BufferedWriter br = new BufferedWriter(new FileWriter(link.getCaminho() + "page.html"))) {
                br.write(page);
                br.newLine();
                br.write("<!-- Url: " + link.getUrl() + " -->");
                br.newLine();
                br.flush();
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
