package com.evertec.ecommerce.serviceImpl;

import com.evertec.ecommerce.dto.UserCreateAndLoginDTO;
import com.evertec.ecommerce.entities.User;
import com.evertec.ecommerce.exceptions.EmailAlreadyExistsException;
import com.evertec.ecommerce.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    /**
     * Tests the successful creation of a user in the system.
     * <p>
     * This test ensures that a new user can be created when the provided email does not
     * already exist in the database. It verifies that the user is saved correctly with
     * the specified email and password, and that the created user object is returned as expected.
     * <p>
     * Test steps:
     * 1. A `UserCreateAndLoginDTO` is instantiated and populated with a valid email and password.
     * 2. A `User` object is built based on the provided DTO data.
     * 3. Mock behavior is defined to simulate an empty response from the `findByEmail`
     *    repository method and a successful save operation.
     * 4. The `createUser` method of the `userService` is invoked with the DTO.
     * 5. Assertions are performed to ensure:
     *    - The created user object is not null.
     *    - The email and password of the created user match the input data.
     */
    @Test
    void createUser_SuccessfullyCreatesUser() {
        UserCreateAndLoginDTO userCreateAndLoginDTO = new UserCreateAndLoginDTO();
        userCreateAndLoginDTO.setEmail("test@example.com");
        userCreateAndLoginDTO.setPassword("password123");

        User user = User.builder()
                .email(userCreateAndLoginDTO.getEmail())
                .password(userCreateAndLoginDTO.getPassword())
                .build();

        Mockito.when(userRepository.findByEmail(userCreateAndLoginDTO.getEmail())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(userCreateAndLoginDTO);

        assertNotNull(createdUser);
        assertEquals(userCreateAndLoginDTO.getEmail(), createdUser.getEmail());
        assertEquals(userCreateAndLoginDTO.getPassword(), createdUser.getPassword());
    }

    /**
     * Tests that the createUser method of the UserService properly throws an EmailAlreadyExistsException
     * when attempting to create a user with an email that already exists in the system.
     * <p>
     * This test validates the following behaviors:
     * - If the UserRepository indicates that a user with the provided email already exists,
     *   the createUser method should throw an EmailAlreadyExistsException.
     * - The UserRepository's save method is not called when the exception is thrown.
     * <p>
     * Test Steps:
     * 1. Arrange: Create a UserCreateAndLoginDTO object with an email corresponding to an
     *    already existing user, mock the UserRepository to return an existing user for that email.
     * 2. Act: Call the createUser method of the UserService.
     * 3. Assert:
     *    - Ensure that an EmailAlreadyExistsException is thrown.
     *    - Verify that the save method of the UserRepository is never invoked.
     */
    @Test
    void createUser_ThrowsEmailAlreadyExistsException() {
        UserCreateAndLoginDTO userCreateAndLoginDTO = new UserCreateAndLoginDTO();
        userCreateAndLoginDTO.setEmail("existing@example.com");
        userCreateAndLoginDTO.setPassword("password123");

        User existingUser = User.builder()
                .email(userCreateAndLoginDTO.getEmail())
                .password(userCreateAndLoginDTO.getPassword())
                .build();

        Mockito.when(userRepository.findByEmail(userCreateAndLoginDTO.getEmail())).thenReturn(Optional.of(existingUser));

        assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(userCreateAndLoginDTO));

        Mockito.verify(userRepository, Mockito.never()).save(any(User.class));
    }
}