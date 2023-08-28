package com.project.MovieMania.controller;

import com.project.MovieMania.domain.DTO.ReportDTO;
import com.project.MovieMania.domain.DTO.ReviewDTO;
import com.project.MovieMania.domain.Recommend;
import com.project.MovieMania.domain.Report;
import com.project.MovieMania.domain.Review;
import com.project.MovieMania.domain.type.ReportType;
import com.project.MovieMania.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {

    private MovieService movieService;

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/detail/{id}")
    public String movieDetail(@PathVariable Long id, Model model) {

        model.addAttribute("movie", movieService.findById(id));
        model.addAttribute("rate", movieService.reserveRate(id));
        model.addAttribute("audinum", movieService.audiNum(id));
        model.addAttribute("avgScore", movieService.avgScore(id));
        model.addAttribute("audiperday", movieService.audiPerDay(id));
        model.addAttribute("gender_rate", movieService.reserveRateForGender(id));
        model.addAttribute("age_rate", movieService.reserveRateForAge(id));

        return "movie/detail";
    }

    @GetMapping("/review/{id}")
    public String movieReview(@PathVariable Long id, Model model){
        model.addAttribute("userId", 1);

        model.addAttribute("movie", movieService.findById(id));
        model.addAttribute("rate", movieService.reserveRate(id));
        model.addAttribute("audinum", movieService.audiNum(id));
        model.addAttribute("avgScore", movieService.avgScore(id));
        return "movie/review";
    }

    @GetMapping("/trailer/{id}")
    public String movieTrailer(@PathVariable Long id, Model model){
        model.addAttribute("movie", movieService.findById(id));
        model.addAttribute("rate", movieService.reserveRate(id));
        model.addAttribute("audinum", movieService.audiNum(id));
        model.addAttribute("avgScore", movieService.avgScore(id));
        return "movie/trailer";
    }

    @GetMapping("/getReview/{id}")
    @ResponseBody
    public List<ReviewDTO> getReview(@PathVariable Long id){
        return movieService.getReview(id);
    }


    @PostMapping("/review/write")
    public ResponseEntity<Integer> reviewWrite(@RequestParam Long movieId,
                                               @RequestParam int score,
                                               @RequestParam String content,
                                               @RequestParam Long userId){
        System.out.println(movieId);
        System.out.println(score);
        System.out.println(content);
        System.out.println(userId);
        int result = movieService.reviewWrite(movieId, score, content, userId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/review/findRecommend")
    @ResponseBody
    public long findRecommend(@RequestParam Long userId,
                              @RequestParam Long reviewId){
        return movieService.findRecommend(userId, reviewId);
    }

    @PostMapping("/review/deleteRecommend")
    public ResponseEntity<Integer> deleteRecommend(@RequestParam Long userId,
                                                   @RequestParam Long reviewId){
        movieService.deleteRecommend(userId, reviewId);
        return ResponseEntity.ok(0);
    }

    @PostMapping("/review/addRecommend")
    @ResponseBody
    public long addRecommend(@RequestParam Long userId,
                             @RequestParam Long reviewId){
        return movieService.addRecommend(userId, reviewId);
    }

    @PostMapping("/review/findReport")
    @ResponseBody
    public long findReport(@RequestParam Long userId,
                           @RequestParam Long reviewId){
        return movieService.findReport(userId, reviewId);
    }

    @PostMapping("/review/addReport")
    @ResponseBody
    public ReportDTO addReport(@RequestParam Long userId,
                               @RequestParam Long reviewId,
                               @RequestParam ReportType reportType){

        return movieService.addReport(userId, reviewId, reportType);
    }

    @PostMapping("/review/deleteReview")
    @ResponseBody
    public long deleteReview(@RequestParam Long reviewId){
        return movieService.deleteReview(reviewId);
    }

    @PostMapping("/review/countRecommend")
    @ResponseBody
    public long countRecommend(@RequestParam Long reviewId){
        return movieService.countRecommend(reviewId);
    }

    @GetMapping("/getReviewByScore/{id}")
    @ResponseBody
    public List<ReviewDTO> getReviewByScore(@PathVariable Long id){
        return movieService.getReviewByScore(id);
    }

    @GetMapping("/getReviewByRecommend/{id}")
    @ResponseBody
    public List<ReviewDTO> getReviewByRecommend(@PathVariable Long id){
        return movieService.getReviewByRecommend(id);
    }


}
