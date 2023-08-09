package imtiaz.sktech.mytutoron.controller.rest;

import lombok.RequiredArgsConstructor;
import imtiaz.sktech.mytutoron.model.dto.request.CreateUserRequest;
import imtiaz.sktech.mytutoron.model.dto.request.UpdateUserRequest;
import imtiaz.sktech.mytutoron.model.dto.response.Response;
import imtiaz.sktech.mytutoron.model.pagination.AscOrDesc;
import imtiaz.sktech.mytutoron.model.pagination.PaginationArgs;
import imtiaz.sktech.mytutoron.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

import static imtiaz.sktech.mytutoron.constant.AppConstant.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/users")
public class UserRestController {

    private final UserService userService;

    @GetMapping(value = "/all")
    public ResponseEntity<Response> getAllUsers() {
        return Response.getResponseEntity(
                true,
                "Users loaded successfully.",
                userService.getAllUsers()
        );
    }

    @GetMapping(value = "/paginated")
    public ResponseEntity<Response> getAllPaginatedUsers(
            @RequestParam(name = PAGE_NO, defaultValue = DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(name = PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = SORT_BY, defaultValue = DEFAULT_SORT_BY_FIELD, required = false) String sortBy,
            @RequestParam(name = ASC_OR_DESC, defaultValue = DEFAULT_ASC_OR_DESC_VALUE, required = false) AscOrDesc ascOrDesc,
            @RequestParam(required = false) Map<String, Object> parameters
    ) {
        PaginationArgs paginationArgs = new PaginationArgs(pageNo, pageSize, sortBy, ascOrDesc, parameters);
        return Response.getResponseEntity(
                true,
                "Users loaded successfully.",
                userService.getAllPaginatedUsers(paginationArgs)
        );
    }

    @GetMapping(value = "/id/{userId}")
    public ResponseEntity<Response> findById(@PathVariable UUID userId) {
        return Response.getResponseEntity(
                true,
                "User loaded successfully.",
                userService.getUserById(userId)
        );
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Response> createUser(@Valid @RequestBody CreateUserRequest request) {
        return Response.getResponseEntity(
                true,
                "User created successfully.",
                userService.createUser(request)
        );
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Response> updateUser(@Valid @RequestBody UpdateUserRequest request) {
        return Response.getResponseEntity(
                true,
                "User updated successfully.",
                userService.updateUser(request)
        );
    }

    @DeleteMapping(value = "/id/{userId}/delete")
    public ResponseEntity<Response> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return Response.getResponseEntity(
                true,
                "User deleted successfully."
        );
    }
}
