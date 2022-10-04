package com.example.application.views;

import com.example.application.configuration.SecurityUtils;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * Application main class that is hidden to user before authentication.
 */
@Route("")
@AnonymousAllowed
public class IndexView extends VerticalLayout {

    public IndexView(Environment environment) {
        add(new H3(environment.getProperty("spring.cloud.azure.active-directory.b2c.base-uri")));
        add(new RouterLink("RouterLink to VaadinHelloView", VaadinHelloView.class));
        add(SecurityUtils.setRouterIgnoreAttribute(new RouterLink("RouterLink to VaadinHelloView (router-ignore)", VaadinHelloView.class)));
        add(new Paragraph("Authenticated: " + !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)));
        add(SecurityUtils.setRouterIgnoreAttribute(new Anchor("/logout", "/logout")));
    }
}
