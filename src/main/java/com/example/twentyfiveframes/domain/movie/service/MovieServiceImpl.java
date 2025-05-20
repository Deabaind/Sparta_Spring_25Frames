package com.example.twentyfiveframes.domain.movie.service;

import com.example.twentyfiveframes.domain.movie.cache.KeywordCounter;
import com.example.twentyfiveframes.domain.movie.dto.MovieRequestDto;
import com.example.twentyfiveframes.domain.movie.dto.MovieResponseDto;
import com.example.twentyfiveframes.domain.movie.entity.Movie;
import com.example.twentyfiveframes.domain.movie.repository.MovieRepository;
import com.example.twentyfiveframes.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;
    private final KeywordCounter keywordCounter;

    // 영화 등록
    @Override
    public MovieResponseDto.Save saveMovie(User user, MovieRequestDto.Save dto) {
        Movie movie = new Movie(user, dto);
        movieRepository.save(movie);

        return new MovieResponseDto.Save("영화가 등록되었습니다.", movie.getId());
    }

    // 영화 전체 조회
    @Override
    public Page<MovieResponseDto.GetAll> getAllMovies(Pageable pageable) {

        return movieRepository.findAll(pageable)
                .map(MovieResponseDto.GetAll::from);
    }

    // 영화 단건 조회

    // 영화 수정

    // 영화 삭제

    // 키워드 기반 영화 검색
    @Override
    public List<Movie> search(String title, String genre) {
        if (title != null) keywordCounter.record(title);
        if (genre != null) keywordCounter.record(genre);

        String t = (title == null || title.isBlank()) ? "" : title;
        String g = (genre == null || genre.isBlank()) ? "" : genre;

        return movieRepository.search(t, g);
    }

    @Override
    public List<String> topKeywords(int limit) {
        return keywordCounter.topKeywords(limit);
    }

    // 인기 검색어 조회
}
