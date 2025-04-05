package org.c4c.mysql.filter;

import org.c4c.mysql.config.TenantContext;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TenantFilter implements Filter {

    private static final String TENANT_HEADER = "tenant_id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String tenantId = httpRequest.getHeader(TENANT_HEADER);

            if (tenantId != null && !tenantId.isEmpty()) {
                TenantContext.setCurrentTenant(tenantId);
            } else {
                TenantContext.clear();
            }
        }

        try {
            chain.doFilter(request, response);
        } finally {
            TenantContext.clear(); // Clear the context after the request is processed
        }
    }
}