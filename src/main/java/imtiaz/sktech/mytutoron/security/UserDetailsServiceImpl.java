package imtiaz.sktech.mytutoron.security;

import lombok.RequiredArgsConstructor;
import imtiaz.sktech.mytutoron.exception.custom.NotFoundException;
import imtiaz.sktech.mytutoron.persistence.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    public static final String COULD_NOT_FIND_USER_WITH_EMAIL = "Could not find user with email: ";
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws NotFoundException {
        var userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(COULD_NOT_FIND_USER_WITH_EMAIL + email));
        return new UserDetailsImpl(userEntity);
    }
}
