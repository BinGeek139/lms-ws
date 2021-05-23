package com.ptit.gateway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

public class AccessControlFilter extends ZuulFilter {
    public AccessControlFilter() {
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
//        ctx.set
        HttpServletRequest request = ctx.getRequest();
//        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(req);
        Set<String> headers = (Set<String>) ctx.get("ignoredHeaders");
        if (headers != null) {
            headers.remove("authorization");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(!Objects.isNull(authentication) && Objects.isNull(authentication.getPrincipal())){
                User principal = (User) authentication.getPrincipal();
                MutableHttpServletRequest  wrapper=new MutableHttpServletRequest(request);
                wrapper.putHeader("user-id",principal.getUsername());
            }

        }




        LoggerFactory.getLogger(AccessControlFilter.class).info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        return null;
    }

    final class MutableHttpServletRequest extends HttpServletRequestWrapper {
        // holds custom header and value mapping
        private final Map<String, String> customHeaders;

        public MutableHttpServletRequest(HttpServletRequest request){
            super(request);
            this.customHeaders = new HashMap<>();
        }

        public void putHeader(String name, String value){
            this.customHeaders.put(name, value);
        }

        public String getHeader(String name) {
            // check the custom headers first
            String headerValue = customHeaders.get(name);

            if (headerValue != null){
                return headerValue;
            }
            // else return from into the original wrapped object
            return ((HttpServletRequest) getRequest()).getHeader(name);
        }

        public Enumeration<String> getHeaderNames() {
            // create a set of the custom header names
            Set<String> set = new HashSet<String>(customHeaders.keySet());

            // now add the headers from the wrapped request object
            @SuppressWarnings("unchecked")
            Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
            while (e.hasMoreElements()) {
                // add the names of the request headers into the list
                String n = e.nextElement();
                set.add(n);
            }

            // create an enumeration from the set and return
            return Collections.enumeration(set);
        }
    }
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
