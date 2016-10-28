/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.core;

import br.unisc.pickglobe.model.Link;
import java.io.IOException;
import java.util.LinkedList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author arthurhoch
 */
public class util {
    
    public String getPage(String url) throws IOException {
        Document doc;        
        
        LinkedList<Link> links1 = new LinkedList<>();
        
        
        try {
            doc = Jsoup.connect(url).get();
            String asc = null;
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                asc =(link.attr("href"));
                if (asc.contains("http")){
                    System.out.println(asc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
        
        
    }
    
    
    
    
}
