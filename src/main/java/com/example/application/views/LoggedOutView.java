package com.example.application.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@Route
@AnonymousAllowed
public class LoggedOutView extends VerticalLayout {
    public LoggedOutView() {
        add(new RouterLink("HOME", IndexView.class));
        add(new H1("Logged out"));

        add(new Paragraph("Authenticated: " + !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)));
    }
}
