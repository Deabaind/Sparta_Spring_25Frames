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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

        given(movieRepository.save(any())).willAnswer(invocation -> {
            Movie saved = invocation.getArgument(0);
            ReflectionTestUtils.setField(saved, "id", 1L);
            return saved;
        });

        // when
//        MovieResponseDto.Save result = movieService.saveMovie(user, dto);
//
//        // then
//        assertThat(result.getMessage()).isEqualTo("영화가 등록되었습니다.");
//        assertThat(result.getMovieId()).isEqualTo(1L);
//
//        verify(movieRepository, times(1)).save(any());

    }

    @Test
    @DisplayName("영화 전체 조회")
    void getAllMovies() {
        // given
        Movie movie1 = Movie.builder()
                            .title("기생충")
                            .genre(MovieGenre.DRAMA)
                            .build();
        ReflectionTestUtils.setField(movie1, "id", 1L);

        Movie movie2 = Movie.builder()
                            .title("인터스텔라")
                            .genre(MovieGenre.SF)
                            .build();
        ReflectionTestUtils.setField(movie2, "id", 2L);

        Movie movie3 = Movie.builder()
                            .title("해리포터")
                            .genre(MovieGenre.FANTASY)
                            .build();
        ReflectionTestUtils.setField(movie3, "id", 3L);

        List<Movie> movieList = List.of(movie1, movie2, movie3);
        Pageable pageable = PageRequest.of(0,10);
        Page<Movie> moviePage = new PageImpl<>(movieList, pageable, 3);

        given(movieRepository.findAll(pageable)).willReturn(moviePage);

        // when
        Page<MovieResponseDto.GetAll> result = movieService.getAllMovies(pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("기생충");
        assertThat(result.getContent().get(1).getGenre()).isEqualTo("SF");
        assertThat(result.getContent().get(2).getMovieId()).isEqualTo(3L);
        //todo averageScore 연동 후 검증 코드 추가하기
    }

    @Test
    @DisplayName("영화 단건 조회")
    void getMovie() {
        // given
        Movie movie = Movie.builder()
                .title("해리포터")
                .summary("해리포터 요약")
                .genre(MovieGenre.FANTASY)
                .releaseDate(LocalDate.of(2020,1,1))
                .build();
        ReflectionTestUtils.setField(movie, "id", 1L);

        given(movieRepository.findById(1L)).willReturn(Optional.of(movie));

        // when
        MovieResponseDto.Get result = movieService.getMovie(1L);

        // then
        assertThat(result.getTitle()).isEqualTo("해리포터");
        assertThat(result.getSummary()).isEqualTo("해리포터 요약");
        assertThat(result.getGenre()).isEqualTo("FANTASY");
        assertThat(result.getReleaseDate()).isEqualTo("2020-01-01");
    }

}
