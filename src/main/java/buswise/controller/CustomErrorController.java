package buswise.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

public class CustomErrorController {

    @RequestMapping("/error1")
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == 404) {
            return "404";
        }
        return "/admin/Error";
    }
}
