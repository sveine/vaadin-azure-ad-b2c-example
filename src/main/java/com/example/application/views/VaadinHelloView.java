package com.example.application.views;

import com.example.application.configuration.SecurityUtils;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.security.PermitAll;
import java.util.List;

@Route("vaadin-hello")
@PermitAll()
public class VaadinHelloView extends VerticalLayout {
    public VaadinHelloView() {
        add(new RouterLink("HOME", IndexView.class));
        add(new H1("Hello from VAADIN"));
        add(new H2("Hallo"));
        add(new Paragraph("Authenticated: " + SecurityContextHolder.getContext().getAuthentication().isAuthenticated()));
        List<String> accessGranted = SecurityUtils.getAccessGranted(this.getClass());
        add(String.valueOf(accessGranted));
    }

}
