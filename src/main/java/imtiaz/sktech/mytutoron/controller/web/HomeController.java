package imtiaz.sktech.mytutoron.controller.web;

import java.security.Principal;

import imtiaz.sktech.mytutoron.service.AssignmentCategoryService;
import imtiaz.sktech.mytutoron.service.AssignmentService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import imtiaz.sktech.mytutoron.service.UserService;

@RequiredArgsConstructor
@Controller
public class HomeController {
	private final UserService userService;
	private final AssignmentService assignmentService;
    private final AssignmentCategoryService assignmentCategoryService;

    @GetMapping
    public String afterLoginAdminPanel() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard(Principal principal) {
        var modelAndView = new ModelAndView("dashboard/index");
        modelAndView.addObject("pageTitle", "Dashboard");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("totalUser", userService.countTotalUser());
        modelAndView.addObject("totalAssignment", assignmentService.countTotalAssignment());
        modelAndView.addObject("totalAssignmentCategory", assignmentCategoryService.countAssignmentCategory());
        return modelAndView;
    }

    @GetMapping("/login")
    @Secured("!isAuthenticated()")
    public String viewLoginPage() {
        return "login";
    }
}
