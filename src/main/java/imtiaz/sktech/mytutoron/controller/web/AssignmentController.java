package imtiaz.sktech.mytutoron.controller.web;

import imtiaz.sktech.mytutoron.model.domain.Assignment;
import imtiaz.sktech.mytutoron.model.domain.AssignmentCategory;
import imtiaz.sktech.mytutoron.model.dto.request.CreateAssignmentRequest;
import imtiaz.sktech.mytutoron.model.dto.request.UpdateAssignmentRequest;
import imtiaz.sktech.mytutoron.model.enums.AssignmentStatus;
import imtiaz.sktech.mytutoron.service.AssignmentCategoryService;
import imtiaz.sktech.mytutoron.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import imtiaz.sktech.mytutoron.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

import static imtiaz.sktech.mytutoron.constant.AppConstant.DEFAULT_PAGE_SIZE;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final UserService userService;
    private final AssignmentCategoryService assignmentCategoryService;

    @GetMapping
    public ModelAndView getAssignments(@RequestParam(defaultValue = "0") int page, Principal principal) {
        var modelAndView = new ModelAndView("assignment/list");
        Page<Assignment> assignments = assignmentService.getAll(PageRequest.of(page, Integer.parseInt(DEFAULT_PAGE_SIZE)));
        modelAndView.addObject("pageTitle", "Assignment List");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("assignments", assignments);
        modelAndView.addObject("pagesForPagination", assignments);
        modelAndView.addObject("url", "/assignments");
        return modelAndView;
    }

    @GetMapping("/{id}/")
    public ModelAndView getAssignment(@PathVariable UUID id, Principal principal) {
        var modelAndView = new ModelAndView("assignment/single");
        Assignment assignment = assignmentService.getAssignment(id);
        modelAndView.addObject("pageTitle", "Assignment Details");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("assignment", assignment);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createAssignmentPage(Principal principal) {
        var modelAndView = new ModelAndView("assignment/new-assignment");
        var createAssignmentRequest = new CreateAssignmentRequest();
        Page<AssignmentCategory> assignmentCategories = assignmentCategoryService.getAll(Pageable.unpaged());
        modelAndView.addObject("categories", assignmentCategories);
        modelAndView.addObject("pageTitle", "Add Assignment");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("assignment", createAssignmentRequest);
        return modelAndView;
    }

    @PostMapping
    public String createAssignment(@Valid @ModelAttribute("assignment") CreateAssignmentRequest request, BindingResult bindingResult, Model model, Principal principal) {
        Page<AssignmentCategory> assignmentCategories = assignmentCategoryService.getAll(Pageable.unpaged());
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("pageTitle", "Add Assignment");
                model.addAttribute("loggedInUser", userService.getLoggedInUser(principal));
                model.addAttribute("assignment", request);
                model.addAttribute("categories", assignmentCategories);
                return "assignment/new-assignment";
            }
            assignmentService.createOne(request);
            return "redirect:/assignments";
        } catch (Exception exception) {
            return "redirect:/assignments/create";
        }
    }

    @GetMapping("/{id}/update")
    public ModelAndView updateAssignmentPage(@PathVariable UUID id, Principal principal) {
        var modelAndView = new ModelAndView("assignment/update-assignment");
        var assignment = assignmentService.getAssignment(id);
        Page<AssignmentCategory> assignmentCategories = assignmentCategoryService.getAll(Pageable.unpaged());
        modelAndView.addObject("categories", assignmentCategories);
        modelAndView.addObject("pageTitle", "Update Assignment");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("assignment", assignment);
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String updateAssignment(@Valid @ModelAttribute("assignment") UpdateAssignmentRequest request, @PathVariable UUID id, BindingResult bindingResult, Principal principal, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("pageTitle", "Update Assignment");
                model.addAttribute("loggedInUser", userService.getLoggedInUser(principal));
                model.addAttribute("assignment", request);
                return "assignment/update-assignment";
            }
            assignmentService.updateOne(request, id);
            return "redirect:/assignments";
        } catch (Exception e) {
            return "redirect:/assignments";
        }
    }

    @GetMapping("{id}/delete")
    public String deleteAssignment(@PathVariable UUID id) {
        assignmentService.deleteAssignment(id);
        return "redirect:/assignments";
    }

}
