/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.core;

import br.unisc.pickglobe.model.Link;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author arthurhoch
 */
public class Util {

    public List getLinksPage(String url) throws IOException {
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

                    System.out.println(asc);

                    if ((md5String = md5.string2md5(asc)) != null) {
                        Link l = new Link();
                        l.setUrl(asc);
                        l.setCaminho("./" + md5.string2md5(asc) + "/");

                        listaLinks.add(l);
                    }
                }
            }
        } catch (Exception e) {
            System.err.printf("In: " + url, e);
        }

        return listaLinks;
    }
}
