package imtiaz.sktech.mytutoron.controller.web;

import imtiaz.sktech.mytutoron.model.domain.AssignmentCategory;
import imtiaz.sktech.mytutoron.model.dto.request.CreateAssignmentCategoryRequest;
import imtiaz.sktech.mytutoron.model.dto.request.UpdateAssignmentCategoryRequest;
import imtiaz.sktech.mytutoron.service.AssignmentCategoryService;
import lombok.RequiredArgsConstructor;
import imtiaz.sktech.mytutoron.model.dto.response.ResponseMessage;
import imtiaz.sktech.mytutoron.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping(value = "/assignment-categories")
public class AssignmentCategoryController {
    private final UserService userService;
    private final AssignmentCategoryService assignmentCategoryService;

    @GetMapping
    public ModelAndView getAssignmentCategories(@RequestParam(defaultValue = "0") int page, Principal principal) {
        var modelAndView = new ModelAndView("assignmentcategory/list");
        Page<AssignmentCategory> assignmentCategories = assignmentCategoryService.getAll(PageRequest.of(page, Integer.parseInt(DEFAULT_PAGE_SIZE)));
        modelAndView.addObject("pageTitle", "View Assignment Categories");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("assignmentCategories", assignmentCategories);
        modelAndView.addObject("pagesForPagination", assignmentCategories);
        modelAndView.addObject("url", "/assignment-categories");
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getAssignmentCategory(@PathVariable UUID id, Principal principal) {
        var modelAndView = new ModelAndView("assignmentcategory/single");
        AssignmentCategory assignmentCategory = assignmentCategoryService.getOne(id);
        modelAndView.addObject("pageTitle", "Assignment Category Details");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("assignmentCategory", assignmentCategory);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView viewCreateAssignmentCategoryPage(Principal principal) {
        var modelAndView = new ModelAndView("assignmentcategory/new-category");
        var createAssignmentCategoryRequest = new CreateAssignmentCategoryRequest();
        modelAndView.addObject("pageTitle", "Add Category");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        modelAndView.addObject("assignmentCategory", createAssignmentCategoryRequest);
        return modelAndView;
    }

    @PostMapping
    public String createAssignmentCategory(@Valid @ModelAttribute("assignmentCategory") CreateAssignmentCategoryRequest request, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, Principal principal) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("pageTitle", "Add Category");
                model.addAttribute("loggedInUser", userService.getLoggedInUser(principal));
                model.addAttribute("assignmentCategory", request);
                return "assignmentcategory/new-category";
            }
            assignmentCategoryService.createOne(request);
            return "redirect:/assignment-categories";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("responseMessage", new ResponseMessage("alert-danger", "Something went wrong. " + e.getMessage()));
            return "redirect:/assignment-categories/create";
        }
    }

    @GetMapping("/{id}/update")
    public ModelAndView viewUpdateAssignmentCategoryPage(@PathVariable UUID id, Principal principal) {
        var modelAndView = new ModelAndView("assignmentcategory/update-category");
        AssignmentCategory assignmentCategory = assignmentCategoryService.getOne(id);
        modelAndView.addObject("assignmentCategory", assignmentCategory);
        modelAndView.addObject("pageTitle", "Update Assignment Category");
        modelAndView.addObject("loggedInUser", userService.getLoggedInUser(principal));
        return modelAndView;
    }


    @PostMapping("/update/{id}")
    public String updateAssignmentCategory(@Valid @ModelAttribute("assignmentCategory") UpdateAssignmentCategoryRequest request, @PathVariable UUID id, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, Principal principal) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("pageTitle", "Update Assignment Category");
                model.addAttribute("loggedInUser", userService.getLoggedInUser(principal));
                model.addAttribute("assignmentCategory", request);
                return "assignmentcategory/update-category";
            }
            assignmentCategoryService.updateAssignmentCategory(request, id);
            return "redirect:/assignment-categories";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("responseMessage", new ResponseMessage("alert-danger", "Something went wrong. " + e.getMessage()));
            return "redirect:/assignment-categories/update" + id;
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteAssignmentCategory(@PathVariable UUID id) {
        assignmentCategoryService.deleteAssignment(id);
        return "redirect:/assignment-categories";
    }
}
