package com.example.twentyfiveframes.user.service;

import com.example.twentyfiveframes.domain.auth.dto.AuthRequestDto;
import com.example.twentyfiveframes.domain.user.dto.UserResponseDto;
import com.example.twentyfiveframes.domain.user.entity.User;
import com.example.twentyfiveframes.domain.user.entity.UserType;
import com.example.twentyfiveframes.domain.user.repository.UserRepository;
import com.example.twentyfiveframes.domain.user.service.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("유저를 DB에 저장한다.")
    public void userSave() {
        // given
        AuthRequestDto.Signup signupDto = new AuthRequestDto.Signup(
                "testEmail@email.com",
                "QWERASDF1",
                "누구세용",
                UserType.ROLE_USER
        );
        User savedUser = new User(
                signupDto.getEmail(),
                signupDto.getPassword(),
                signupDto.getUsername(),
                signupDto.getRole()
        );
        ReflectionTestUtils.setField(savedUser, "id", 1L);

        given(userRepository.save(any(User.class))).willReturn(savedUser);

        // when
        UserResponseDto.Signup response = userService.save(signupDto);

        // then
        assertThat(response.getUserId()).isEqualTo(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }
}
