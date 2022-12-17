package com.hsuforum.common.web.jsf.utils;

import jakarta.faces.application.FacesMessage;

/**
 * Show jsf message utility
 * @author Marvin
 *
 */
public class JSFMessageUtils {
	/**
     * Show error message
     * @param message
     */
    public static void showErrorMessage(final String message) {
    	JSFUtils.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
    }
    
    /**
     * Show warn message
     * @param message
     */
    public static void showWarnMessage(final String message) {
        JSFUtils.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, ""));
    }
    
    /**
     * Show info message
     * @param message
     */
    public static void showInfoMessage(final String message) {
        JSFUtils.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
    }
}
