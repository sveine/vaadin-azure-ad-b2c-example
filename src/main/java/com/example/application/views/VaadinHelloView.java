package com.example.application.views;

import com.example.application.configuration.SecurityUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.annotation.security.PermitAll;

@Route("vaadin-hello")
@PermitAll()
public class VaadinHelloView extends VerticalLayout {
    public VaadinHelloView() {
        add(new RouterLink("HOME", IndexView.class));
        add(new H1("Hello from VAADIN"));

        var isAuthenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        add(new Paragraph("Authenticated: " + isAuthenticated));


        if (isAuthenticated) {
            add(new Hr());
            add(new Button("Logout", click -> {
                UI.getCurrent().getPage().setLocation("/");
                new SecurityContextLogoutHandler().logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
                new SecurityContextLogoutHandler().setClearAuthentication(true);
                new SecurityContextLogoutHandler().setInvalidateHttpSession(true);
            }));
            add("Logs me out. Not getting the login flow again. It silently logs me in after logout");
            add(new Hr());
            add(SecurityUtils.setRouterIgnoreAttribute(new Anchor("/logout", "/logout")));
            add("Logs me out. Not getting the logout page from Azure Flow");
        }
    }

}
