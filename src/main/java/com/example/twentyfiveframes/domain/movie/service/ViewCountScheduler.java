package com.example.twentyfiveframes.domain.movie.service;

import com.example.twentyfiveframes.domain.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ViewCountScheduler {

    private final MovieRepository movieRepository;
    private final MovieViewCountService movieViewCountService;

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정 자동 실행
    public void updateTotalViews() {
        Map<Long, Long> viewCount = movieViewCountService.getAllViewCounts();

        for (Map.Entry<Long, Long> entry : viewCount.entrySet()) {
            Long movieId = entry.getKey();
            Long addCount = entry.getValue();

            movieRepository.findById(movieId).ifPresent(movie -> {
                movie.addViews(addCount);
            });
        }

    }


}
