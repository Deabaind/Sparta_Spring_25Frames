package com.example.twentyfiveframes.domain.movie.repository;

import com.example.twentyfiveframes.domain.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
