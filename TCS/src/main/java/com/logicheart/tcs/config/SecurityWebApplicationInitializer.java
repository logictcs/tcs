package com.logicheart.tcs.config;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import com.logicheart.tcs.config.SecurityConfig;

public class SecurityWebApplicationInitializer
      extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer() {
        super(SecurityConfig.class);
    }
}