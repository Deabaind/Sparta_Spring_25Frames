package com.example.twentyfiveframes.domain.movie.repository;

import com.example.twentyfiveframes.domain.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    // 제목과 장르를 대소문자 구분 없이 포함하는 영화 검색
    @Query("SELECT m FROM Movie m WHERE " +
            "(:title IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:genre IS NULL OR LOWER(m.genre) LIKE LOWER(CONCAT('%', :genre, '%')))")
    List<Movie> search(@Param("title") String title, @Param("genre") String genre);
}
