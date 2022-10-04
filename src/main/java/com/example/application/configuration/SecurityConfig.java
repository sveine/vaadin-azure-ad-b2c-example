// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.example.application.configuration;

import com.azure.spring.cloud.autoconfigure.aadb2c.AadB2cOidcLoginConfigurer;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
public class SecurityConfig extends VaadinWebSecurity {

    private final AadB2cOidcLoginConfigurer configurer;
    private final String signUpOrSignInFlowName;

    public SecurityConfig(AadB2cOidcLoginConfigurer configurer, Environment environment) {
        this.configurer = configurer;
        signUpOrSignInFlowName = "/oauth2/authorization/" + environment.getProperty("spring.cloud.azure.active-directory.b2c.user-flows.sign-up-or-sign-in");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.apply(configurer);
        setOAuth2LoginPage(http, signUpOrSignInFlowName);
    }
}
