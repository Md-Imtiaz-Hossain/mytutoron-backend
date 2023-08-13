package imtiaz.sktech.mytutoron.controller.rest.frontend;

import imtiaz.sktech.mytutoron.model.domain.AssignmentCategory;
import imtiaz.sktech.mytutoron.model.dto.request.CreateAssignmentCategoryRequest;
import imtiaz.sktech.mytutoron.persistence.entity.AssignmentCategoryEntity;
import imtiaz.sktech.mytutoron.service.AssignmentCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/categories")
public class CategoryRestController {

    private final AssignmentCategoryService assignmentCategoryService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<AssignmentCategory>> getAllUsers() {
        List<AssignmentCategory> allAssignmentCategory = assignmentCategoryService.getAllAssignmentCategory();
        return ResponseEntity.ok(allAssignmentCategory);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<AssignmentCategory> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(assignmentCategoryService.getOne(id));
    }


    //
//    @GetMapping(value = "/paginated")
//    public ResponseEntity<Response> getAllPaginatedUsers(
//            @RequestParam(name = PAGE_NO, defaultValue = DEFAULT_PAGE_NO, required = false) int pageNo,
//            @RequestParam(name = PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
//            @RequestParam(name = SORT_BY, defaultValue = DEFAULT_SORT_BY_FIELD, required = false) String sortBy,
//            @RequestParam(name = ASC_OR_DESC, defaultValue = DEFAULT_ASC_OR_DESC_VALUE, required = false) AscOrDesc ascOrDesc,
//            @RequestParam(required = false) Map<String, Object> parameters
//    ) {
//        PaginationArgs paginationArgs = new PaginationArgs(pageNo, pageSize, sortBy, ascOrDesc, parameters);
//        return Response.getResponseEntity(
//                true,
//                "Users loaded successfully.",
//                userService.getAllPaginatedUsers(paginationArgs)
//        );
//    }
//

    @PostMapping(value = "/create")
    public ResponseEntity<AssignmentCategoryEntity> createUser(@RequestBody CreateAssignmentCategoryRequest request) {
        AssignmentCategoryEntity assignmentCategoryEntity = assignmentCategoryService.create(request);
        return new ResponseEntity<>(assignmentCategoryEntity, HttpStatus.CREATED);
    }

//
//    @PutMapping(value = "/update")
//    public ResponseEntity<Response> updateUser(@Valid @RequestBody UpdateUserRequest request) {
//        return Response.getResponseEntity(
//                true,
//                "User updated successfully.",
//                userService.updateUser(request)
//        );
//    }
//
//    @DeleteMapping(value = "/id/{userId}/delete")
//    public ResponseEntity<Response> deleteUser(@PathVariable UUID userId) {
//        userService.deleteUser(userId);
//        return Response.getResponseEntity(
//                true,
//                "User deleted successfully."
//        );
//    }
}
