package imtiaz.sktech.mytutoron.controller.web;

import lombok.RequiredArgsConstructor;
import imtiaz.sktech.mytutoron.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/profile")
public class UserProfile {

    private final UserService userService;

    @GetMapping("/view")
    public ModelAndView profileView(Principal principal) {
        var modelAndView = new ModelAndView("profile/profile");
        modelAndView.addObject("pageTitle", "Profile");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        return modelAndView;
    }

    @GetMapping("/update-password")
    public String updatePassword() {
        return "profile/change-password";
    }

    @PostMapping("/change-user-password")
    public String updateUser(@RequestParam("oldPassword") String oldPassword,
                             @RequestParam("newPassword") String newPassword,
                             @RequestParam("confirmPassword") String confirmPassword,
                             Model model, Principal principal) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String encodedOldPassword = userService.encodePasswordUsingString(oldPassword);

        System.out.println(userService.encodePasswordUsingString("super"));
        System.out.println(encodedOldPassword);
        System.out.println(userDetails.getPassword());

        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            model.addAttribute("errorMessage", "All fields are required.");
            return "profile/change-password";
        } else if (!userDetails.getPassword().equals(encodedOldPassword)) {
            model.addAttribute("errorMessage", "Old password is incorrect.");
            return "profile/change-password";
        } else if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Password does not match.");
            return "profile/change-password";
        } else {
            String encodedNewPassword = userService.encodePasswordUsingString(newPassword);
            userService.updateUserPassword(userDetails.getUsername(), encodedNewPassword);
            return "redirect:/dashboard";
        }
    }


}
