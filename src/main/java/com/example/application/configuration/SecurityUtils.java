package com.example.application.configuration;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SecurityUtils {

    /**
     * Tests if the request is an internal framework request. The test consists of
     * checking if the request parameter is present and if its value is consistent
     * with any of the request types know.
     *
     * @param request {@link HttpServletRequest}
     * @return true if is an internal framework request. False otherwise.
     */
    static boolean isFrameworkInternalRequest(HttpServletRequest request) {
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
//        return parameterValue != null && Stream.of(HandlerHelper.RequestType.values()).anyMatch(r -> r.getIdentifier().equals(parameterValue));
        return false;
    }

    public static boolean isAuthenticated() {
//        SecurityContextHolder.getContext().getAuthentication().isAuthenticated() //Always true (bug?)
        return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    public static DefaultOidcUser getAuthenticatedOidcUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof DefaultOidcUser) {
            return (DefaultOidcUser) principal;
        }
        // Anonymous or no authentication.
        return null;
    }

    public static Map<String, Object> getClaims() {
        var authenticatedOidcUser = getAuthenticatedOidcUser();
        if (authenticatedOidcUser != null) {
            return authenticatedOidcUser.getClaims();
        }
        // Anonymous or no authentication.
        return null;
    }

    public static Collection<? extends GrantedAuthority> getAuthorities() {
        var authenticatedOidcUser = getAuthenticatedOidcUser();
        if (authenticatedOidcUser != null) {
            return authenticatedOidcUser.getAuthorities();
        }
        // Anonymous or no authentication.
        return null;
    }

    public static boolean isAccessGranted(Class<?> securedClass) {
        // Allow if no roles are required.
        Secured secured = AnnotationUtils.findAnnotation(securedClass, Secured.class);
        if (secured == null) {
            return true; //
        }

        // lookup needed role in user roles
        List<String> allowedRoles = Arrays.asList(secured.value());
        Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();

        return userAuthentication.getAuthorities().stream() //
            .map(GrantedAuthority::getAuthority)
            .anyMatch(allowedRoles::contains);
    }

    public static List<String> getAccessGranted(Class<?> securedClass) {
        Secured secured = AnnotationUtils.findAnnotation(securedClass, Secured.class);
        if (secured == null) {
            return null; //
        }
        List<String> allowedRoles = Arrays.asList(secured.value());
        Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();

        return userAuthentication.getAuthorities().stream() //
            .map(GrantedAuthority::getAuthority)
            .filter(allowedRoles::contains).collect(Collectors.toList());
    }

    public static <T extends Component> T setRouterIgnoreAttribute(T htmlContainer) {
        htmlContainer.getElement().setAttribute("router-ignore", true);
        return htmlContainer;
    }
}
