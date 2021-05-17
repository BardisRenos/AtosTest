package com.example.demo.controller;
import org.slf4j.*;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.*;

/**
 * A servlet filter to log request and response
 * The logging implementation is pretty native and for demonstration only
 */
@Component
public class RequestResponseLoggingFilter implements Filter {

    private final static Logger LOG = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public void init(final FilterConfig filterConfig) {
        LOG.info("Initializing filter :{}", this);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        LOG.info("Logging Request  {} : {}", req.getMethod(), req.getRequestURI());
        long startingTime = ZonedDateTime.now().toInstant().toEpochMilli();
        chain.doFilter(request, response);
        long endingTime = ZonedDateTime.now().toInstant().toEpochMilli();
        LOG.info("Logging Response :{} Processing time : {}ms ", res.getStatus(), endingTime-startingTime);
    }

    @Override
    public void destroy() {
        LOG.warn("Destructing filter :{}", this);
    }
}