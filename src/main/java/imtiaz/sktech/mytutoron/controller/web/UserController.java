package imtiaz.sktech.mytutoron.controller.web;

import lombok.RequiredArgsConstructor;
import imtiaz.sktech.mytutoron.model.domain.User;
import imtiaz.sktech.mytutoron.model.dto.request.CreateUserRequest;
import imtiaz.sktech.mytutoron.model.dto.request.UpdateUserRequest;
import imtiaz.sktech.mytutoron.model.dto.response.ResponseMessage;
import imtiaz.sktech.mytutoron.service.RoleService;
import imtiaz.sktech.mytutoron.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

import static imtiaz.sktech.mytutoron.constant.AppConstant.DEFAULT_PAGE_SIZE;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public ModelAndView getUsers(@RequestParam(defaultValue = "0") int page, Principal principal) {
        var modelAndView = new ModelAndView("user/list");
        Page<User> users = userService.getUsers(PageRequest.of(page, Integer.parseInt(DEFAULT_PAGE_SIZE)));
        modelAndView.addObject("pageTitle", "View Users");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("users", users);
        modelAndView.addObject("pagesForPagination", users);
        modelAndView.addObject("url", "/users");
        return modelAndView;
    }

    @GetMapping("/{id}/")
    public ModelAndView getUser(@PathVariable UUID id, Principal principal) {
        var modelAndView = new ModelAndView("user/single");
        User user = userService.getUserById(id);
        modelAndView.addObject("pageTitle", "User Profile");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView viewCreateUserPage(Principal principal) {
        var modelAndView = new ModelAndView("user/new-user");
        var createUserRequest = new CreateUserRequest();
        modelAndView.addObject("pageTitle", "Add User");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("user", createUserRequest);
        modelAndView.addObject("roles", roleService.getAll(Pageable.unpaged()));
        return modelAndView;
    }

    @PostMapping
    public String createUser(@Valid @ModelAttribute("user") CreateUserRequest request,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, Principal principal) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("pageTitle", "Add User");
                model.addAttribute("loggedInUser", userService.getLoggedInUser(principal));
                model.addAttribute("user", request);
                model.addAttribute("roles", roleService.getAll(Pageable.unpaged()));
                return "user/new-user";
            }

            if (request.getRoleIds().isEmpty()) {
                throw new Exception("Please select a role and submit again.");
            }

            userService.createUser(request);
            return "redirect:/users";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("responseMessage", new ResponseMessage("alert-danger", "Something went wrong. " + e.getMessage()));
            return "redirect:/users/create";
        }
    }

    @GetMapping("/{id}/update")
    public ModelAndView viewUpdateUserPage(@PathVariable UUID id, Principal principal) {
        var modelAndView = new ModelAndView("user/update-user");
        User user = userService.getUserById(id);
        UpdateUserRequest request = new UpdateUserRequest();
        request.setId(user.getId());
        request.setEmail(user.getEmail());
        request.setFirstName(user.getFirstName());
        request.setLastName(user.getLastName());
        modelAndView.addObject("pageTitle", "Update User");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("user", request);
        modelAndView.addObject("roles", roleService.getAll(Pageable.unpaged()));
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String updateUser(@Valid @ModelAttribute("user") UpdateUserRequest request, @PathVariable UUID id, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, Principal principal) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("pageTitle", "Update User");
                model.addAttribute("loggedInUser", userService.getLoggedInUser(principal));
                model.addAttribute("user", request);
                model.addAttribute("roles", roleService.getAll(Pageable.unpaged()));
                return "user/update-user";
            }

            request.setId(id);
            userService.updateUser(request);
            return "redirect:/users";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("responseMessage", new ResponseMessage("alert-danger", "Something went wrong. " + e.getMessage()));
            return "redirect:/users/" + id + "/update";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
