package imtiaz.sktech.mytutoron.controller.rest;

import imtiaz.sktech.mytutoron.service.UserRegistrationService;
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
public class UserRegistrationRestController {

    private final UserRegistrationService userRegistration;

    @PostMapping(value = "/registration")
    public ResponseEntity<Response> userRegistration(@Valid @RequestBody CreateUserRequest request) {
        return Response.getResponseEntity(
                true,
                "User registration successfully.",
                userRegistration.userRegistration(request)
        );
    }

}
