package imtiaz.sktech.mytutoron.controller.rest.frontend;

import imtiaz.sktech.mytutoron.model.domain.AssignmentCategory;
import imtiaz.sktech.mytutoron.model.dto.request.CreateAssignmentCategoryRequest;
import imtiaz.sktech.mytutoron.model.dto.request.UpdateAssignmentCategoryRequest;
import imtiaz.sktech.mytutoron.model.dto.response.Response;
import imtiaz.sktech.mytutoron.persistence.entity.AssignmentCategoryEntity;
import imtiaz.sktech.mytutoron.persistence.repository.AssignmentCategoryRepository;
import imtiaz.sktech.mytutoron.service.AssignmentCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static imtiaz.sktech.mytutoron.constant.AppConstant.*;
import static imtiaz.sktech.mytutoron.constant.AppConstant.DEFAULT_ASC_OR_DESC_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/categories")
public class CategoryRestController {

    private final AssignmentCategoryService assignmentCategoryService;

    @GetMapping(value = "/all")
    public ResponseEntity<Response> getAllAssignmentCategory() {
        return Response.getResponseEntity(
                true,
                "All Assignment Category loaded successfully.",
                assignmentCategoryService.getAllAssignmentCategory()
        );
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<Response> findById(@PathVariable UUID id) {
        return Response.getResponseEntity(
                true,
                "Assignment Category loaded successfully.",
                assignmentCategoryService.getOne(id)
        );
    }

    @GetMapping(value = "/paginated")
    public ResponseEntity<Response> getAllPaginatedAssignmentCategory(
            @RequestParam(name = PAGE_NO, defaultValue = DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(name = PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = SORT_BY, defaultValue = DEFAULT_SORT_BY_FIELD, required = false) String sortBy,
            @RequestParam(name = ASC_OR_DESC, defaultValue = DEFAULT_ASC_OR_DESC_VALUE, required = false) String sortDirection) {
        return Response.getResponseEntity(
                true,
                "Assignment Category loaded successfully through Page by page",
                assignmentCategoryService.findAllWithPageable(pageNo, pageSize, sortBy, sortDirection).getContent()
        );
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Response> createAssignmentCategory(@RequestBody CreateAssignmentCategoryRequest request) {
        return Response.getResponseEntity(
                true,
                "Assignment Category created successfully.",
                assignmentCategoryService.createOne(request)
        );
    }


    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Response> updateAssignmentCategory(@Valid @RequestBody UpdateAssignmentCategoryRequest request, @PathVariable UUID id) {
        return Response.getResponseEntity(
                true,
                "Assignment Category updated successfully.",
                assignmentCategoryService.updateAssignmentCategory(request, id)
        );
    }

    @DeleteMapping(value = "/id/{userId}/delete")
    public ResponseEntity<Response> deleteAssignmentCategory(@PathVariable UUID userId) {
        assignmentCategoryService.deleteAssignmentCategory(userId);
        return Response.getResponseEntity(
                true,
                "Assignment Category deleted successfully."
        );
    }

}
