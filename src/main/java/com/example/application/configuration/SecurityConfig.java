// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.example.application.configuration;

import com.azure.spring.cloud.autoconfigure.aadb2c.AadB2cOidcLoginConfigurer;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
public class SecurityConfig extends VaadinWebSecurity {

    private final AadB2cOidcLoginConfigurer configurer;

    public SecurityConfig(AadB2cOidcLoginConfigurer configurer) {
        this.configurer = configurer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.apply(configurer);
    }
}
