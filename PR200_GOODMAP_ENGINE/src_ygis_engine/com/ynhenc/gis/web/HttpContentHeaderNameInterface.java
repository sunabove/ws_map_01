package com.ynhenc.gis.web;

public interface HttpContentHeaderNameInterface {

    public static final String CONTENT_TYPE = "Content-type";


    /**
     * HTTP content disposition header name.
     */
    public static final String CONTENT_DISPOSITION = "Content-disposition";


    /**
     * Content-disposition value for form data.
     */
    public static final String FORM_DATA = "form-data";


    /**
     * Content-disposition value for file attachment.
     */
    public static final String ATTACHMENT = "attachment";


    /**
     * Part of HTTP content type header.
     */
    public static final String MULTIPART = "multipart/";


    /**
     * HTTP content type header for multipart forms.
     */
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";


    /**
     * HTTP content type header for multiple uploads.
     */
    public static final String MULTIPART_MIXED = "multipart/mixed";

}
