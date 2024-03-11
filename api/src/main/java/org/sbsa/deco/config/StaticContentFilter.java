package org.sbsa.deco.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
@Component
@Slf4j
public class StaticContentFilter implements Filter {

    private final List<String> fileExtensions = Arrays.asList("html", "js", "json", "csv", "css", "png", "svg", "eot", "ttf", "woff", "appcache", "jpg", "jpeg", "gif", "ico");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String path = request.getServletPath();

        boolean isApi = path.startsWith("/api");
        boolean isDocs=path.startsWith("/v2/api-docs") || path.startsWith("/swagger-resources") || path.startsWith("/swagger-ui");
        boolean isResourceFile = !isApi && !isDocs  && fileExtensions.stream().anyMatch(path::contains);
log.info("path: "+ path);
        if (isApi || isDocs ) {
            log.info("id doc or api !!");
            chain.doFilter(request, response);
        } else if (isResourceFile) {
            log.info("isResourceFile!!");
            resourceToResponse("static" + path, response);
        } else {
            log.info("else hitted!!");
            resourceToResponse("static/index.html", response);
        }
    }

    private void resourceToResponse(String resourcePath, HttpServletResponse response) throws IOException {
        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resourcePath);

        if (inputStream == null) {
            response.sendError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
            return;
        }

        //headers
        if (resourcePath.endsWith(".html")) {
            response.setContentType("text/html");
        }
        if (resourcePath.endsWith(".css")) {
            response.setContentType("text/css");
        }
        if (resourcePath.endsWith(".js")) {
            response.setContentType("text/javascript");
        }

        inputStream.transferTo(response.getOutputStream());
    }

}

