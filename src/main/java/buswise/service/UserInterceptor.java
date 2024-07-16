package buswise.service;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("email") != null && session.getAttribute("roleId") != null && session.getAttribute("userId") != null) {
            int roleId = (int) session.getAttribute("roleId");
            String email = (String) session.getAttribute("email");
            int sessionUserId = (int) session.getAttribute("userId");

            String requestURI = request.getRequestURI();
            String[] uriParts = requestURI.split("/");
            int urlUserId = Integer.parseInt(uriParts[uriParts.length - 1]);


            if (email != null && !email.isEmpty() && roleId == 2 && sessionUserId == urlUserId) {
                return true;
            }
        }
        response.sendRedirect("/BusWise/login"); // Redirect to login page if session is invalid
        return false;
    }

}
