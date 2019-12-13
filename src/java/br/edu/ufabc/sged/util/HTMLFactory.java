/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.util;

import java.io.File;
import org.apache.commons.io.*;
import java.io.IOException;
import org.apache.commons.lang3.*;
import java.util.ArrayList;

/**
 *
 * @author Caique de Camargo
 */
public class HTMLFactory {
    public static String getFormattedHTML(String page, String path) throws IOException {
        File html = getFile(page, path);
        String htmlNotFormatted = getHTMLNotFormatted(html);
        return htmlNotFormatted;
    }
    
    public static String getFormattedHTML(String page, String path, ArrayList<ArrayList<String>> attributesList) throws IOException {
        File html = getFile(page, path);
        String htmlNotFormatted = getHTMLNotFormatted(html);
        String formatedHTML = formatHTML(htmlNotFormatted, page, attributesList);
        return formatedHTML;
    }

    private static File getFile(String page, String path) {
        String pageSRC = PageSRC.getPageSRCById(page);
        return new File(path + pageSRC);
    }

    private static String getHTMLNotFormatted(File file) throws IOException {
        return FileUtils.readFileToString(file, "utf-8");
    }

    private static String formatHTML(String pageHTMLNotFormatted, String page, ArrayList<ArrayList<String>> attributesList) {
        String title = getTitle(pageHTMLNotFormatted);
        String header = getHeader(pageHTMLNotFormatted);
        String content = getContent(pageHTMLNotFormatted);
        String formattedContent = getFormattedContent(content, page, attributesList);
        return String.join(" ",title, header, formattedContent);
    }

    private static String getTitle(String page) {
        return StringUtils.substringBetween(page, "<!-- title -->");
    }

    private static String getHeader(String page) {
        return (StringUtils.substringBetween(page, "<!-- header -->") != null)?StringUtils.substringBetween(page, "<!-- header -->"):"";
    }

    private static String getContent(String page) {
        return StringUtils.substringBetween(page, "<!-- content -->");
    }

    private static String getFormattedContent(String content, String page, ArrayList<ArrayList<String>> attributesList) {
        ArrayList<String> tags = TagsHTML.getTagsById(page);
        String formatedContent = putAttributesOnHTML(content, tags, attributesList);
        return formatedContent;
    }
    
    private static String putAttributesOnHTML(String content, ArrayList<String> tags, ArrayList<ArrayList<String>> attributesList){
        String htmlFormated = new String();
        for(ArrayList attributes : attributesList){
            htmlFormated += replaceTagFromHTML(content, tags, attributes);
        }
        return htmlFormated;
    }

    private static String replaceTagFromHTML(String content, ArrayList<String> tags, ArrayList<String> attributes) {
        for (String tag : tags) {
            String attribute = attributes.get(0);
            attributes.remove(0);
            content = StringUtils.replace(content, tag, attribute);
        }
        return content;
    }
    
}

