package com.example.shopproject.user.repository;

import com.example.shopproject.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void checkIfUserRepositoryIsNotNull() {
        assertThat(userRepository).isNotNull();
    }

    @Test
    public void whenFindByEmailThenReturnUser() {
        User testObject = new User("some", "some", "some", "some", 12.0);

        entityManager.persistAndFlush(testObject);

        User found = userRepository.findByEmail(testObject.getEmail());
        assertThat(found.getEmail()).isEqualTo(testObject.getEmail());
    }

    @Test
    public void whenFindByIdThenReturnUser() {
        User testObject = new User("some", "some", "some", "some", 12.0);

        entityManager.persistAndFlush(testObject);

        Optional<User> found = userRepository.findById(testObject.getId());
        assertThat(found.get().getId()).isEqualTo(testObject.getId());
    }
}
