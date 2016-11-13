/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.core;

import br.unisc.pickglobe.model.Extensao;
import br.unisc.pickglobe.model.Link;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author arthurhoch
 */
public class Util {

    private static final String FORMATO = ".html";

    public List<Link> getLinksPage(String url, List<Extensao> extensoes) {
        Document doc;

        String https = "https://";
        String http = "http://";

        List<Link> listaLinks = new LinkedList<>();

        try {
            doc = Jsoup.connect(url).get();
            String asc = null;
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                asc = (link.attr("href"));
                if (asc.contains("http")) {

                    if (asc.startsWith(https)) {
                        asc = asc.replaceFirst(https, http);
                    }

                    boolean contem = false;
                    for (Extensao extensao : extensoes) {
                        if (asc.contains(extensao.getNomeExtensao())) {
                            contem = true;
                        }
                    }

                    if (contem) {
                        Link l = new Link();
                        l.setUrl(asc);

                        listaLinks.add(l);
                    }
                }
            }
        } catch (Exception e) {
            System.err.printf("In: " + url, e);
        }

        return listaLinks;
    }

    public void saveListLinks(List<Link> listaLink, String folderName) {

        Md5helper md5 = new Md5helper();
        String md5String;

        for (Link link : listaLink) {

            md5String = md5.string2md5(link.getUrl());

            if (md5String != null) {
                link.setMd5(md5String);

                if (link.getCaminho() == null) {
                    link.setCaminho("./" + folderName + "/");
                }

                hasDownloaded(link);
                saveWebpage(link);
            }
        }
    }

    public int contarPalavras(Link link, String palavra) {

        palavra = "(" + palavra + ")";

        Matcher m = Pattern.compile(palavra, Pattern.DOTALL).matcher(link.getPagina());
        int quantidade = 0;
        while (m.find()) {
            quantidade++;
        }

        return quantidade;

    }

    public int contarPalavrasSemcapitalizacao(Link link, String palavra) {

        int total = 0;

        if (link.getPagina() == null) {
            hasDownloaded(link);

            desCapitalizar(palavra);
            total += contarPalavras(link, palavra);
        }

        return total;
    }

    public int contarPalavrasComcapitalizacao(Link link, String palavra) {

        int total = 0;

        if (link.getPagina() == null) {
            hasDownloaded(link);

            capitalizar(palavra);
            total = contarPalavras(link, palavra);
        }

        return total;
    }

    private static String capitalizar(String s) {
        if (s.length() == 0) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    private static String desCapitalizar(String s) {
        if (s.length() == 0) {
            return s;
        }
        return s.substring(0, 1).toLowerCase() + s.substring(1).toLowerCase();
    }

    private void saveWebpage(Link link) {

        String diretorio = link.getCaminho();
        File f = new File(link.getCaminho());

        if (!f.exists() && !f.isDirectory()) {
            f.mkdir();
        }

        String caminhoParaSalvar = diretorio + link.getMd5() + FORMATO;
        f = new File(caminhoParaSalvar);

        if (!f.exists() && !f.isFile()) {
            try {

                try (BufferedWriter br = new BufferedWriter(new FileWriter(caminhoParaSalvar))) {
                    br.write(link.getPagina());
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

    private void hasDownloaded(Link link) {
        if (link.getPagina() == null) {

            if (link.getCaminho() != null) {
                File f = new File(link.getCaminho() + link.getMd5() + FORMATO);
                if (f.exists() && !f.isDirectory()) {

                    String page = readFile(f);
                    if (page != null) {
                        link.setPagina(page);
                    } else {
                        downloadWebpage(link);
                    }

                } else {
                    downloadWebpage(link);
                }
            } else {
                downloadWebpage(link);
            }
        }
    }

    private void downloadWebpage(Link link) {
        try {
            String page = Jsoup.connect(link.getUrl()).get().html();
            link.setPagina(page);
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String readFile(File f) {

        String fileString = new String();

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));

            String line;

            try {
                while ((line = br.readLine()) != null) {
                    fileString += line;
                }
            } catch (IOException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return fileString;
    }
}
