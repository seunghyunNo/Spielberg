package com.project.MovieMania.service;

import com.project.MovieMania.domain.*;
import com.project.MovieMania.domain.DTO.ReportDTO;
import com.project.MovieMania.domain.DTO.ReviewDTO;
import com.project.MovieMania.domain.DTO.UserDTO;
import com.project.MovieMania.domain.type.Gender;
import com.project.MovieMania.domain.type.ReportType;
import com.project.MovieMania.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

    private ShowInfoRepository showInfoRepository;

    private TicketInfoRepository ticketInfoRepository;

    private ReviewRepository reviewRepository;

    private UserRepository userRepository;

    private RecommendRepository recommendRepository;

    private ReportRepository reportRepository;

    @Autowired
    public void setMovieRepository(MovieRepository movieRepository){ this.movieRepository = movieRepository;}

    @Autowired
    public void setShowInfoRepository(ShowInfoRepository showInfoRepository){this.showInfoRepository = showInfoRepository;}

    @Autowired
    public void setTicketInfoRepository(TicketInfoRepository ticketInfoRepository) {this.ticketInfoRepository = ticketInfoRepository;}

    @Autowired
    public void setReviewRepository(ReviewRepository reviewRepository) {this.reviewRepository = reviewRepository;}

    @Autowired
    public void setUserRepository(UserRepository userRepository) {this.userRepository = userRepository;}

    @Autowired
    public void setRecommendRepository(RecommendRepository recommendRepository) {this.recommendRepository = recommendRepository;}

    @Autowired
    public void setReportRepository(ReportRepository reportRepository) {this.reportRepository = reportRepository;}
    @Override
    public Movie findById(Long id) {

        // 영화 조회
        Movie movie = movieRepository.findById(id).orElseThrow(null);

        return movie;
    }

    @Override
    public String reserveRate(Long id) {

        // 영화 조회
        Movie movie = findById(id);

        // 실시간 예매율 산출기준 = A(예매관객수) / B(전체 예매관객수) * 100
        // 예매관객수(A) : 조회시점 특정영화의 상영 2시간 이후의 발권데이터
        // 전체 예매관객수(B) : 조회시점 모든 영화의 상영 2시간 이후의 발권데이터

        // 전체 예매관객수 설정(B)
        // 현재시간에서 2시간 이후 설정
        LocalDateTime nowPlusTwo = LocalDateTime.now().plusHours(2);

        // showInfo 에서 현재시간에서 2시간 이후의 모든 영화의 상영정보 조회
        List<ShowInfo> upcomingShow = showInfoRepository.findByshowDateTimeAfter(nowPlusTwo);

        // 모든 상영정보로 모든 발권 티켓 조회
        List<TicketInfo> allTickets = ticketInfoRepository.findByShowInfoIn(upcomingShow);

        // 조회 한 티켓의 모든 좌석 수 조회
        Long allTicketSeats = 0L;
        for(TicketInfo ticketInfo : allTickets){
            allTicketSeats += ticketInfo.getSeats().size();
        }

        // 예매관객수 설정(A)
        // showInfo 에서 현재시간에서 2시간 이후의 특정 영화의 상영정보 조회
        List<ShowInfo> upcomingMovie = showInfoRepository.findByshowDateTimeAfterAndMovie(nowPlusTwo, movie);

        // 가져온 상영정보로 특정영화의 발권 티켓 조회
        List<TicketInfo> tickets = ticketInfoRepository.findByShowInfoIn(upcomingMovie);

        // 조회 한 티켓의 좌석 수 조회
        Long ticketSeats = 0L;
        for(TicketInfo ticketInfo : tickets){
            ticketSeats += ticketInfo.getSeats().size();
        }

        // 예매율 계산(A / B * 100 : 소숫점 둘째까지만 표현)
        // 분모가 0인 상황 예외처리
        String reserveRate;
        if(allTicketSeats != 0) {
            reserveRate = new DecimalFormat("0.00").format((double) ticketSeats / allTicketSeats * 100);
        } else{
            reserveRate = "0";
        }

        return reserveRate;
    }

    @Override
    public Long audiNum(Long id) {

        // 영화 조회
        Movie movie = findById(id);

        // 누적관객수
        // 현재 시간기준 상영시간이 지난 예매표들의 갯수
        // 현재 시간설정
        LocalDateTime now = LocalDateTime.now();

        // 현재시간 기준 상영이 끝난 특정 영화의 상영정보 조회
        List<ShowInfo> endShow = showInfoRepository.findByshowDateTimeBeforeAndMovie(now, movie);

        // 가져온 상영정보로 발권 완료된 티켓 조회
        List<TicketInfo> endTickets = ticketInfoRepository.findByShowInfoIn(endShow);

        // 조회 한 티켓의 좌석 수 = 누적관객 수
        Long audiNum = 0L;
        for(TicketInfo ticketInfo : endTickets){
            audiNum += ticketInfo.getSeats().size();
        }

        return audiNum;
    }

    @Override
    public String avgScore(Long id) {

        // 영화 조회
        Movie movie = findById(id);

        // 평점
        // 특정영화의 review 조회
        List<Review> movieReview =  reviewRepository.findByMovie(movie);

        // review 에서 평점들 합산
        int score = 0;
        int reviewCnt = 0;
        for(Review review : movieReview){
            score += review.getScore();
            reviewCnt++;
        }

        // 평점 평균 구하기(소숫점 둘째짜리까지)
        // 분모가 0인 상황 예외처리
        String avgScore;
        if(reviewCnt != 0) {
            avgScore = new DecimalFormat("0.00").format((double) score / reviewCnt);
        } else{
            avgScore = "0";
        }

        return avgScore;
    }

    @Override
    public List<Integer> audiPerDay(Long id) {

        // 영화 조회
        Movie movie = findById(id);

        // 최근 5일간 일별 관객수를 담을 List
        List<Integer> audiPerDay = new ArrayList<>();

        for(int i = 1; i < 6; i++){
            // i일 전의 날짜 생성
            LocalDate date = LocalDate.now().minusDays(i);

            // 해당 날짜에 상영된 해당 영화의 모든 상영 정보 가져오기
            List<ShowInfo> todayShowInfo = showInfoRepository.findByshowDateTimeAfterAndShowDateTimeBeforeAndMovie(date.atStartOfDay(), date.plusDays(1).atStartOfDay(), movie);

            // 해당 showInfo 의 Ticket 정보들 가져오기
            List<TicketInfo> todayTickets = ticketInfoRepository.findByShowInfoIn(todayShowInfo);

            // Ticket에서 좌석 수를 모두 더해 일별 관객 수 구하기
            int audinum = 0;
            for(TicketInfo ticketInfo : todayTickets){
                audinum += ticketInfo.getSeats().size();
            }
            // 변수 audiPerDay List 에 추가
            audiPerDay.add(audinum);
        }

        return audiPerDay;
    }

    @Override
    public List<Integer> reserveRateForGender(Long id) {

        // 영화 조회
        Movie movie = findById(id);

        // 시간 설정
        LocalDateTime nowPlusTwo = LocalDateTime.now().plusHours(2);

        // showInfo 에서 현재시간에서 2시간 이후의 특정 영화의 상영정보 조회
        List<ShowInfo> upcomingMovie = showInfoRepository.findByshowDateTimeAfterAndMovie(nowPlusTwo, movie);

        // 성별 설정
        Gender male = Gender.MALE;
        Gender female = Gender.FEMALE;

        // 가져온 상영정보로 특정설별에 특정영화의 발권 티켓 조회
        List<TicketInfo> maleTickets = ticketInfoRepository.findByShowInfoInAndUser_Gender(upcomingMovie, male);
        List<TicketInfo> femaleTickets = ticketInfoRepository.findByShowInfoInAndUser_Gender(upcomingMovie, female);

        // 성별별 예매관객수 담을 Map 준비
        List<Integer> reserveRateForAge = new ArrayList<>();

        Integer maleSeats = 0;
        Integer femaleSeats = 0;
        // ticket의 좌석수로 남성 예매관객수 구하기
        for(TicketInfo ticketInfo : maleTickets){
            maleSeats += ticketInfo.getSeats().size();
        }

        // ticket의 좌석수로 여성 예매관객수 구하기
        for(TicketInfo ticketInfo : femaleTickets){
            femaleSeats += ticketInfo.getSeats().size();
        }

        // List 에 male, female 순으로 결과 저장
        reserveRateForAge.add(maleSeats);
        reserveRateForAge.add(femaleSeats);

        return reserveRateForAge;
    }

    @Override
    public List<Integer> reserveRateForAge(Long id) {

        // 영화 조회
        Movie movie = findById(id);

        // 시간 설정
        LocalDateTime nowPlusTwo = LocalDateTime.now().plusHours(2);

        // showInfo 에서 현재시간에서 2시간 이후의 특정 영화의 상영정보 조회
        List<ShowInfo> upcomingMovie = showInfoRepository.findByshowDateTimeAfterAndMovie(nowPlusTwo, movie);

        // 연령대 구분을 위한 변수
        int[] ageRanges = {10, 20, 30, 40, 50, 60};

        List<Integer> reserveRateForAge = new ArrayList<>();

        for (int i = 0; i < ageRanges.length - 1; i++) {
            int lowerAge = ageRanges[i];
            int upperAge = ageRanges[i + 1];

            // 해당 연령대의 시작과 끝 날짜 계산
            LocalDateTime lowerDate = LocalDateTime.now().minusYears(upperAge);
            LocalDateTime upperDate = LocalDateTime.now().minusYears(lowerAge);

            // 해당 연령대에 속하는 티켓 정보 조회
            List<TicketInfo> tickets = ticketInfoRepository.findByShowInfoInAndUser_BirthdayBetween(upcomingMovie, lowerDate.toLocalDate(), upperDate.toLocalDate());

            // 해당 연령대의 티켓 수 계산
            int ticketCount = 0;
            for (TicketInfo ticketInfo : tickets) {
                ticketCount += ticketInfo.getSeats().size();
            }

            // 10대 ~ 50대 까지 순서로 결과 저장
            reserveRateForAge.add(ticketCount);
        }

        return reserveRateForAge;
    }

    @Override
    public List<ReviewDTO> getReview(Long id) {

        // 영화 조회
        Movie movie = findById(id);

        // review 들 가져오기
        List<Review> reviews = reviewRepository.findByMovieOrderByCreatedAtDesc(movie);
        return reviews.stream().map(this::convertReviewDTO).collect(Collectors.toList());
    }

    private ReviewDTO convertReviewDTO(Review review){

        // 리뷰 가져오기
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setContent(review.getContent());
        reviewDTO.setScore(review.getScore());
        reviewDTO.setCreatedAt(review.getCreatedAt());

        // 유저가져오기
        UserDTO userDTO = new UserDTO();
        userDTO.setId(review.getUser().getId());
        userDTO.setUsername(review.getUser().getUsername());
        userDTO.setEmail(review.getUser().getEmail());
        userDTO.setName(review.getUser().getName());

        // 리뷰 User 에 담기
        reviewDTO.setUser(userDTO);


        // Recommend 가져오기
        reviewDTO.setRecommendCount(review.getRecommends().size());
        reviewDTO.setRecommends(recommendRepository.findByReview(review));

        return reviewDTO;
    }

    @Override
    public int reviewWrite(Long movieId, int score, String content, Long userId) {

        Movie movie = movieRepository.findById(movieId).orElseThrow(null);
        User user = userRepository.findById(userId).orElseThrow(null);
        Review review = new Review();
        review.setMovie(movie);;
        review.setUser(user);
        review.setContent(content);
        review.setScore(score);

        reviewRepository.saveAndFlush(review);

        return 1;
    }

    @Override
    public long findRecommend(Long userId, Long reviewId) {
        User user = userRepository.findById(userId).orElseThrow(null);
        Review review = reviewRepository.findById(reviewId).orElseThrow(null);
        List<Recommend> recommends = review.getRecommends();
        for(Recommend recommend : recommends){
            long recommendReview = recommend.getReview().getId();
            long recommendUser = recommend.getUser().getId();
            if(recommendReview == review.getId() && recommendUser == user.getId()){
                return 1;
            }
        }
        return 0;
    }

    @Override
    @Transactional
    public long deleteRecommend(Long userId, Long reviewId) {
        User user = userRepository.findById(userId).orElseThrow(null);
        Review review = reviewRepository.findById(reviewId).orElseThrow(null);
        recommendRepository.deleteByUserAndReview(user, review);
        return 1;
    }

    @Override
    @Transactional
    public long addRecommend(Long userId, Long reviewId) {
        User user = userRepository.findById(userId).orElseThrow(null);
        Review review = reviewRepository.findById(reviewId).orElseThrow(null);

        RecommendPK recommendPK = new RecommendPK();
        recommendPK.setUser_id(user.getId());
        recommendPK.setReview_id(review.getId());

        Recommend recommend = new Recommend();
        recommend.setRecommendPK(recommendPK);
        recommend.setUser(user);
        recommend.setReview(review);

        recommendRepository.saveAndFlush(recommend);

        return 1;
    }

    @Override
    public long findReport(Long userId, Long reviewId) {
        User user = userRepository.findById(userId).orElseThrow(null);
        Review review = reviewRepository.findById(reviewId).orElseThrow(null);
        if (user != null && review != null) {
            return reportRepository.countByUserAndReview(user, review);
        } else {
            // 유저나 리뷰가 존재하지 않는 경우에 대한 처리
            return 0L;
        }
    }

    @Override
    @Transactional
    public ReportDTO addReport(Long userId, Long reviewId, ReportType reportType) {
        User user = userRepository.findById(userId).orElseThrow(null);
        Review review = reviewRepository.findById(reviewId).orElseThrow(null);
        Report report = Report.builder()
                .user(user)
                .review(review)
                .type(reportType)
                .build();
        reportRepository.saveAndFlush(report);


        return convertReportDTO(report);
    }

    private ReportDTO convertReportDTO(Report report){

        // 리뷰 가져오기
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(report.getId());
        reportDTO.setReportType(report.getType());

        return reportDTO;
    }

    @Override
    public long deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(null);

        // recommend 지우기
        List<Recommend> recommends = review.getRecommends();
        for (Recommend recommend : recommends) {
            recommendRepository.delete(recommend);
        }

        reviewRepository.delete(review);

        return 0;
    }

    @Override
    public long countRecommend(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(null);
        List<Recommend> recommends = review.getRecommends();
        return recommends.size();
    }


}
