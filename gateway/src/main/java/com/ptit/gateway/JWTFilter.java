package com.ptit.gateway;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.*;

public class JWTFilter extends GenericFilterBean {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtTokenProvider tokenProvider;

    public JWTFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//httpServletRequest.s
        String jwt = resolveToken(httpServletRequest);
        if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
            Authentication authentication = this.tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if(!Objects.isNull(authentication) && Objects.isNull(authentication.getPrincipal())){
                User principal = (User) authentication.getPrincipal();
                CustomHttpServletRequest addParamsToHeader=new CustomHttpServletRequest(httpServletRequest);
                addParamsToHeader.addHeader("user-id",principal.getUsername());
                filterChain.doFilter(addParamsToHeader, servletResponse);
                return;
            }

            }

        filterChain.doFilter(servletRequest, servletResponse);
    }
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public class CustomHttpServletRequest extends HttpServletRequestWrapper {

        private Map customHeaderMap = null;

        public CustomHttpServletRequest(HttpServletRequest request) {
            super(request);
            customHeaderMap = new HashMap();
        }
        public void addHeader(String name,String value){
            customHeaderMap.put(name, value);
        }

        @Override
        public String getParameter(String name) {
            String paramValue = super.getParameter(name); // query Strings
            if (paramValue == null) {
                paramValue = (String) customHeaderMap.get(name);
            }
            return paramValue;
        }

    }
}
