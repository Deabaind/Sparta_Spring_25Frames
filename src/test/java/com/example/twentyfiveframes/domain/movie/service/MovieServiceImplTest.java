package com.example.twentyfiveframes.domain.movie.service;

import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
import com.example.twentyfiveframes.domain.movie.dto.MovieResponseDto;
import com.example.twentyfiveframes.domain.movie.entity.Movie;
import com.example.twentyfiveframes.domain.movie.entity.MovieGenre;
import com.example.twentyfiveframes.domain.movie.repository.MovieRepository;
import com.example.twentyfiveframes.domain.movie.service.MovieServiceImpl;
import com.example.twentyfiveframes.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

    @Test
    @DisplayName("PROVIDER 유저가 영화 등록")
    void saveMovie() {

        // given
        User user = new User();

        MovieRequestDto.Save dto = new MovieRequestDto.Save("기생충",
                "계획이 다 있음","봉준호",15,
                MovieGenre.DRAMA,131, LocalDate.of(2019,5,30));

        Movie movie = new Movie(user, dto);
        ReflectionTestUtils.setField(movie, "id", 1L);

        given(movieRepository.save(any())).willAnswer(invocation -> {
            Movie saved = invocation.getArgument(0);
            ReflectionTestUtils.setField(saved, "id", 1L);
            return saved;
        });

        // when
        MovieResponseDto.Save result = movieService.saveMovie(user, dto);

        // then
        assertThat(result.getMessage()).isEqualTo("영화가 등록되었습니다.");
        assertThat(result.getMovieId()).isEqualTo(1L);

        verify(movieRepository, times(1)).save(any());

    }

}
