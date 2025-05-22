package com.example.twentyfiveframes.domain.user.service;

import com.example.twentyfiveframes.domain.auth.dto.AuthRequestDto;
import com.example.twentyfiveframes.domain.user.dto.UserRequestDto;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        UserRequestDto.Signup signupDto = new UserRequestDto.Signup(
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

    @Test
    @DisplayName("유저 정보 조회 비지니스 로직")
    public void get() {
        //given
        User user = new User(
                "testEmail@email.com",
                "QWERASDF1",
                "누구세용",
                UserType.ROLE_USER
        );
        ReflectionTestUtils.setField(user, "id", 1L);

        Long userId = 1L;

        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        // when
        UserResponseDto.Get getUserDto = userService.get(userId);

        assertEquals(user.getId(), getUserDto.getUserId());
        assertEquals(user.getEmail(), getUserDto.getEmail());
        assertEquals(user.getUsername(), getUserDto.getName());
        assertEquals(user.getRole().toString(), getUserDto.getUserType());
        assertEquals(user.getCreatedAt(), getUserDto.getCreatedAt());
    }
}
