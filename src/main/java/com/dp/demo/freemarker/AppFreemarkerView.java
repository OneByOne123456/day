package com.dp.demo.freemarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

public class AppFreemarkerView extends FreeMarkerView{
	public static final String CONTEXT_PATH = "base";  
    
    @SuppressWarnings("all")  
    protected void exposeHelpers(Map model, HttpServletRequest request)  
            throws Exception {  
        super.exposeHelpers(model, request);  
        model.put(CONTEXT_PATH, request.getSession().getServletContext().getRealPath(""));  
    }
}
