package com.evertec.ecommerce.serviceImpl;

import com.evertec.ecommerce.dto.UserCreateAndLoginDTO;
import com.evertec.ecommerce.entities.Order;
import com.evertec.ecommerce.entities.User;
import com.evertec.ecommerce.exceptions.EmailAlreadyExistsException;
import com.evertec.ecommerce.exceptions.NotFoundException;
import com.evertec.ecommerce.repositories.OrderRepository;
import com.evertec.ecommerce.repositories.UserRepository;
import com.evertec.ecommerce.service.UserService;
import com.evertec.ecommerce.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * Creates a new user in the system.
     *
     * @param userCreateAndLoginDTO a DTO containing the user's email and password
     * @return the created {@link User} entity
     * @throws EmailAlreadyExistsException if a user with the provided email already exists
     */
    @Override
    @Transactional
    public User createUser(UserCreateAndLoginDTO userCreateAndLoginDTO) {
        if (userRepository.findByEmail(userCreateAndLoginDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }
        return userRepository.save(User.builder()
                .email(userCreateAndLoginDTO.getEmail())
                .password(userCreateAndLoginDTO.getPassword())
                .build());
    }

    /**
     * Logs in a user by verifying their credentials and generating a JSON Web Token (JWT).
     * If successful, updates and returns the user with the newly generated token.
     *
     * @param userCreateAndLoginDTO an object containing the user's email and password
     * @return the authenticated User object with an updated token field
     * @throws NotFoundException if the email is not found in the repository or if the password does not match
     */
    @Override
    @Transactional
    public User loginUser(UserCreateAndLoginDTO userCreateAndLoginDTO) {
        User user = userRepository.findByEmail(userCreateAndLoginDTO.getEmail())
                .orElseThrow(() -> new NotFoundException("Invalid email or password"));

        if (passwordEncoder.matches(userCreateAndLoginDTO.getPassword(), user.getPassword())) {
            String token = jwtTokenUtil.generateToken(user.getEmail());
            user.setToken(token);
            return userRepository.save(user);
        } else {
            throw new NotFoundException("Invalid email or password");
        }
    }

    /**
     * Retrieves a list of orders associated with the specified user ID.
     *
     * @param userId the unique identifier of the user whose orders are to be retrieved
     * @return a list of orders belonging to the specified user, or null if no orders are found
     */
    @Override
    @Transactional
    public List<Order> getOrdersByUserId(UUID userId) {
       return orderRepository.getOrdersByUserId(userId).orElse(null);

    }
}
