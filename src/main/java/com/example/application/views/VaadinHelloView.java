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
import org.springframework.security.core.context.SecurityContextHolder;

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
                // Logs me out. Not getting the login flow again. It silently logs me in after logout.
                // Not redirecting to spring.cloud.azure.active-directory.b2c.logout-success-url anyway
                // new SecurityContextLogoutHandler().logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
                // Just do this:
                UI.getCurrent().getPage().setLocation("/logout");
            }));
            add("UI.getCurrent().getPage().setLocation(\"/logout\")");
            add(new Hr());
            add(SecurityUtils.setRouterIgnoreAttribute(new Anchor("/logout", "/logout")));
            add("Logs me out. Not getting the logout page from Azure Flow");
        }
    }

}
