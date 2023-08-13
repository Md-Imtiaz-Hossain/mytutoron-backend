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
    private final AssignmentCategoryRepository assignmentCategoryRepository;

    @GetMapping(value = "/all")
    public ResponseEntity<List<AssignmentCategory>> getAllUsers() {
        List<AssignmentCategory> allAssignmentCategory = assignmentCategoryService.getAllAssignmentCategory();
        return ResponseEntity.ok(allAssignmentCategory);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<AssignmentCategory> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(assignmentCategoryService.getOne(id));
    }

    @GetMapping(value = "/paginated")
    public ResponseEntity<List<AssignmentCategory>> getAllPaginatedUsers(
            @RequestParam(name = PAGE_NO, defaultValue = DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(name = PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = SORT_BY, defaultValue = DEFAULT_SORT_BY_FIELD, required = false) String sortBy,
            @RequestParam(name = ASC_OR_DESC, defaultValue = DEFAULT_ASC_OR_DESC_VALUE, required = false) String sortDirection) {
        List<AssignmentCategory> assignmentCategories = assignmentCategoryService.findAllWithPageable(pageNo, pageSize, sortBy, sortDirection).getContent();
        return ResponseEntity.ok(assignmentCategories);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<AssignmentCategoryEntity> createUser(@RequestBody CreateAssignmentCategoryRequest request) {
        AssignmentCategoryEntity assignmentCategoryEntity = assignmentCategoryService.create(request);
        return new ResponseEntity<>(assignmentCategoryEntity, HttpStatus.CREATED);
    }


    @PutMapping(value = "/update/{id}")
    public ResponseEntity<AssignmentCategoryEntity> updateUser(@Valid @RequestBody UpdateAssignmentCategoryRequest request, @PathVariable UUID id) {
        AssignmentCategoryEntity assignmentCategoryEntity = assignmentCategoryService.updateAssignmentCategory(request, id);
        return new ResponseEntity<>(assignmentCategoryEntity, HttpStatus.OK);
    }

    @DeleteMapping(value = "/id/{userId}/delete")
    public ResponseEntity<Response> deleteUser(@PathVariable UUID userId) {
        assignmentCategoryService.deleteAssignmentCategory(userId);
        return Response.getResponseEntity(
                true,
                "Assignment Category deleted successfully."
        );
    }

}
