package buswise.controller;

import buswise.dao.UserDao;
import buswise.dto.LoginDto;
import buswise.dto.RegisterDto;
import buswise.model.User;
import buswise.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserDao userDao;


    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "/admin/login";

    }

    @RequestMapping("/register")
    public String register(HttpServletRequest request, Model model, RegisterDto RegisterDto) {
        model.addAttribute("RegisterDto", new RegisterDto());
        return "/admin/register";

    }

    @PostMapping("/registerUser")
    public String Register(@Valid @ModelAttribute("RegisterDto") RegisterDto RegisterDto, BindingResult bindingResult) throws NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "/admin/register";
        } else {
            projectService.registerUser(RegisterDto);
            return "/admin/login";
        }

    }

    @PostMapping("/success")
    public String securedLogin(@Valid @ModelAttribute("loginDto") LoginDto loginDto, BindingResult bindingResult, RedirectAttributes ra, HttpServletRequest request) throws NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "/admin/login";
        } else {
            boolean status = projectService.login(loginDto);
            if (status) {

                int userId = userDao.getUserId(loginDto.getEmail());
                List<User> userList = userDao.getUserbyUserId(userId);
                User user = userList.get(0);
                int roleId = user.getRoleId().getRoleId();
                String name = userDao.getUsernameByuserId(userId);
                System.out.println(name +"nameeeee");
                String emailIdString = loginDto.getEmail().replace("@", "a");
                HttpSession session = request.getSession();
                if (roleId==1)
                {
                    session.setAttribute("email", emailIdString);
                    session.setAttribute("roleId", roleId);
                    session.setAttribute("name", name);
                    session.setAttribute("userId", userId);

                    return "redirect:/admin/dashboard/" + userId;
                }
                else if (roleId==2)
                {
                    session.setAttribute("email", emailIdString);
                    session.setAttribute("roleId", roleId);
                    session.setAttribute("name", name);
                    session.setAttribute("userId", userId);
                    return "redirect:/user/user-dashboard/" + userId;
                }

            } else {
                ra.addFlashAttribute("message", "wrong credentials, please check!");
                return "redirect:/login";

            }
        }
            return "else";
    }



    @RequestMapping("/forgot")
    public String forgot(HttpServletRequest request) {

        return "/admin/forgetPassword";

    }



    @PostMapping("/successforget")
    @ResponseBody
    public ResponseEntity<Boolean> checkEmailForgetPswrd(@RequestParam("email") String email) {
        boolean exists = projectService.checkEmail(email);
        if(exists){
            projectService.sendMailforReset(email);
        }
        return ResponseEntity.ok(exists);
    }


    @RequestMapping("/reset/{token}")

    public String resetPassword(@PathVariable("token") String token, Model model ) {
        model.addAttribute("token", token);
        boolean check = projectService.checkToken(token);
        if (check) {
            return "/admin/resetPassword";
        } else {
            return "/admin/Error";
        }
    }

    @PostMapping("/resetForm")
    @ResponseBody
    public void resetPasswordForm(@RequestParam("password") String password, @RequestParam("token") String token) throws NoSuchAlgorithmException {
        projectService.resetPassword(password, token);
    }

    @GetMapping("/error")
    public String error()
    {
        return "/admin/error";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request) {
        request.getSession(false).invalidate();
        return "/admin/login";

    }

    @RequestMapping("/checkEMail")
    @ResponseBody
    public boolean checkEmail(String email){
        return projectService.checkEmailRegister(email);
    }



}
