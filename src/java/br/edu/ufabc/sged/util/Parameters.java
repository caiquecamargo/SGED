/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.util;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Caique
 */
public final class Parameters {
    public static final String LOG            = "errorSTR";
    public static final String PAGE_SELECTION = "pagina";
    public static final String OBJECT_LIST    = "objectList";
    public static final String SESSION_NAME   = "usuario";
    public static final String UPLOAD_DIR     = "uploads";
    
    public static final HttpServletRequest setNullAttributesToRequest(HttpServletRequest request){
        request.setAttribute(LOG, LOGMessage.NULL);
        request.setAttribute(PAGE_SELECTION, HomePageSelector.NULL);
        request.setAttribute(OBJECT_LIST, new ArrayList<>());
        return request;
    }
}
