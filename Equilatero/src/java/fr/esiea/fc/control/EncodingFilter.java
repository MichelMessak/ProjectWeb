package fr.esiea.fc.control;

import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * First Filter to manage UTF-8 datas
 * @author Michel Messak
 */

public class EncodingFilter implements javax.servlet.Filter
{

    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Expires", "Tue, 03 Jul 2001 06:00:00 GMT");
        resp.setHeader("Last-Modified", new Date().toString());
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
        resp.setHeader("Pragma", "no-cache");

        
        chain.doFilter(request, response);
        

    }

    public void destroy()
    {
    }

}
