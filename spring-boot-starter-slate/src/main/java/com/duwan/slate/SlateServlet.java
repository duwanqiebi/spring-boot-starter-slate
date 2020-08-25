package com.duwan.slate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import com.alibaba.fastjson.JSON;

/**
 * @author duwan.zq
 * @date 2020/3/6
 */
public class SlateServlet extends HttpServlet {

    private SlateProperties slateProperties;

    public final static String SLATE_RESOURCE_PATH = "web";

    public SlateServlet(SlateProperties slateProperties){
        this.slateProperties = slateProperties;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(slateProperties.getAuthenticate()){
            String authorization = request.getHeader("Authorization");
            if(StringUtils.isEmpty(authorization)){
                response.setHeader("WWW-Authenticate", "Basic realm=\"\"");
                response.sendError(401);
                return;
            }
            String decoded = new String(Base64.getDecoder().decode(authorization.substring(6)));
            String[] users = decoded.split(":");
            if(users.length != 2){
                response.setHeader("WWW-Authenticate", "Basic realm=\"\"");
                response.sendError(401);
                return;
            }
            String userName = users[0];
            String password = users[1];

            if(! (Objects.equals(userName, slateProperties.getUserName()) && Objects.equals(password, slateProperties.getPassword()))){
                response.setHeader("WWW-Authenticate", "Basic realm=\"\"");
                response.sendError(401);
                return;
            }
        }
        
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String requestURI = request.getRequestURI();

        String path = requestURI.substring(contextPath.length() + servletPath.length());

        String[] segments = path.split("\\.");
        ContentType contentType = ContentType.parse(segments[segments.length - 1]);

        response.setCharacterEncoding("utf-8");

        if(contentType == null || contentType == ContentType.HTML){
            InputStream is = new ClassPathResource("layout.html").getInputStream();
            Context context = new Context();

            Attribute attribute = MarkdownParser.parse(slateProperties.getResourcePath(), slateProperties.getIncludes(), Utils.readStringFromResource(slateProperties.getResourcePath() + "/" + slateProperties.getIndex()));
            context.setVariable("pageContent", attribute.getHtml());
            context.setVariable("contents", attribute.getContents());
            context.setVariable("defaultTitle", slateProperties.getDefaultTitle());
            context.setVariable("languages", slateProperties.getLanguages());
            context.setVariable("languageArray", JSON.toJSONString(slateProperties.getLanguages()));
            String html = new SpringTemplateEngine().process(Utils.toString(is), context);
            response.setContentType(ContentType.HTML.getContentType());
            response.getWriter().write(html);
        }else{
            response.setContentType(contentType.getContentType());
            response.getOutputStream().write(Utils.readByteArrayFromResource(SLATE_RESOURCE_PATH + path));
        }
    }



}
