package com.egitim.halisaha.configs;

import antlr.PrintWriterWithSMAP;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class FilterConfig implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        boolean loginStatus = true;
        String url = request.getRequestURI();
        String[] freeUrls = {"/footballer/footballerRegister","/footballer/login/"};

        for (String item:freeUrls){
            if(url.equals(item)){
                loginStatus=false;
                break;
            }
        }

      if(loginStatus) {
          boolean sessionStatus = request.getSession().getAttribute("admin") != null;
          if (sessionStatus) {
              filterChain.doFilter(request, response);
          } else {
              PrintWriter writer = response.getWriter();

              String json = "{ \"status\": false, \"statusDescription\": \"Login olmadan sayfayı göremezsiniz\", \"result\": \"Please Login\" }";
              writer.println(json);
              response.setContentType("application/json");
              response.setStatus(401);

          }
      }
          else{
          filterChain.doFilter(request,response);
      }



    }
}
