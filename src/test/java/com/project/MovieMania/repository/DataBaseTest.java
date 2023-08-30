package com.project.MovieMania.repository;

import com.project.MovieMania.domain.*;
import com.project.MovieMania.domain.type.Gender;
import com.project.MovieMania.domain.type.ReportType;
import com.project.MovieMania.domain.type.ShowInfoStatus;
import com.project.MovieMania.domain.type.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class DataBaseTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthorityRepository authorityRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private CinemaRepository cinemaRepository;

	@Autowired
	private TheaterRepository theaterRepository;

	@Autowired
	private ShowInfoRepository showinfoRepository;

	@Autowired
	private TicketInfoRepository ticketInfoRepository;

	@Autowired
	private PriceInfoRepository priceInfoRepository;

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private RecommendRepository recommendRepository;

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void User(){
		System.out.println(">>>>>>> 유저 + 권한 정보");

		User user1 = User.builder()
				.username("user1")
				.password("1234")
				.name("유저1")
				.phoneNum("000-0000-0000")
				.email("user1@naver.com")
				.birthday(LocalDate.of(2023, 8, 20))
				.status(UserStatus.ACTIVE)
				.gender(Gender.OTHERS)
				.build();
		User user2 = User.builder()
				.username("user2")
				.password("1234")
				.name("유저2")
				.phoneNum("000-0000-0000")
				.email("user2@naver.com")
				.birthday(LocalDate.of(2023, 8, 20))
				.status(UserStatus.ACTIVE)
				.gender(Gender.OTHERS)
				.build();
		User user3 = User.builder()
				.username("user3")
				.password("1234")
				.name("유저3")
				.phoneNum("000-0000-0000")
				.email("user3@naver.com")
				.birthday(LocalDate.of(2023, 8, 20))
				.status(UserStatus.ACTIVE)
				.gender(Gender.OTHERS)
				.build();

		Authority auth_member = Authority.builder()
				.name("ROLE_MEMBER")
				.build();
		Authority auth_admin = Authority.builder()
				.name("ROLE_ADMIN")
				.build();

		authorityRepository.saveAllAndFlush(List.of(auth_member, auth_admin));

		authorityRepository.findAll().forEach(System.out::println);

		user1.addAuthorities(auth_member);
		user3.addAuthorities(auth_member, auth_admin);

		userRepository.saveAllAndFlush(List.of(user1,user2,user3));
		userRepository.findAll().forEach(System.out::println);

		System.out.println(">>>>>>> 영화 정보");

		Movie movie = Movie.builder()
				.title("영화1")
				.directors("감독")
				.actors("배우")
				.showTime(90)
				.openDate(LocalDate.of(2023,8,20))
				.rateGrade("제한연령")
				.genre("장르")
				.build();

		movieRepository.saveAndFlush(movie);
		movieRepository.findAll().forEach(System.out::println);

		System.out.println(">>>>>>> 영화관 정보");

		Cinema cinema1 = Cinema.builder()
				.name("영화관1")
				.address("주소1")
				.build();

		Cinema cinema2 = Cinema.builder()
				.name("영화관2")
				.address("주소2")
				.build();

		cinemaRepository.saveAllAndFlush(List.of(cinema1, cinema2));
		cinemaRepository.findAll().forEach(System.out::println);

		System.out.println(">>>>>>> 상영관 정보");

		Theater theater = Theater.builder()
				.theaterNum(1)
				.maxSeatColumn(10)
				.maxSeatRow(10)
				.cinema(cinema1)
				.build();

		theaterRepository.saveAndFlush(theater);
		theaterRepository.findAll().forEach(System.out::println);

		System.out.println(">>>>>>> 상영 정보");

		ShowInfo showInfo = ShowInfo.builder()
				.movie(movie)
				.theater(theater)
				.status(ShowInfoStatus.NOW)
				.showDateTime(LocalDateTime.of(2023, 8, 20, 11, 00))
				.build();

		showinfoRepository.saveAndFlush(showInfo);
		showinfoRepository.findAll().forEach(System.out::println);

		System.out.println(">>>>>>> 가격 정보");

		PriceInfo adultPrice = PriceInfo.builder()
				.name("성인")
				.price(10000)
				.build();

		PriceInfo juniorPrice = PriceInfo.builder()
				.name("아동")
				.price(5000)
				.build();

		priceInfoRepository.saveAllAndFlush(List.of(adultPrice, juniorPrice));
		priceInfoRepository.findAll().forEach(System.out::println);


		System.out.println(">>>>>>> 티켓 정보");

		TicketInfo ticketInfo = TicketInfo.builder()
				.user(user1)
				.showInfo(showInfo)
				.ticketCode("티켓코드")
				.priceInfo(adultPrice)
				.build();

		ticketInfoRepository.saveAndFlush(ticketInfo);
		ticketInfoRepository.findAll().forEach(System.out::println);

		System.out.println(">>>>>>> 좌석 정보");

		Seat seat1 = Seat.builder()
				.seatRow(1)
				.seatColumn(10)
				.ticketInfo(ticketInfo)
				.build();

		Seat seat2 = Seat.builder()
				.seatRow(1)
				.seatColumn(9)
				.ticketInfo(ticketInfo)
				.build();

		Seat seat3 = Seat.builder()
				.seatRow(1)
				.seatColumn(8)
				.ticketInfo(ticketInfo)
				.build();

		seatRepository.saveAllAndFlush(List.of(seat1, seat2, seat3));
		seatRepository.findAll().forEach(System.out::println);

		System.out.println(">>>>>>> 티켓 + 좌석 정보");

		ticketInfo.addSeats(seat1, seat2, seat3);

		ticketInfoRepository.findAll().forEach(System.out::println);

		System.out.println(">>>>>> 리뷰 ");

		Review review = Review.builder()
				.content("리뷰입니다")
				.score(5)
				.user(user1)
				.movie(movie)
				.build();

		reviewRepository.save(review);
		reviewRepository.findAll().forEach(System.out::println);

		System.out.println(">>>>>> 추천 ");

		RecommendPK recommendPk1 = new RecommendPK();
		recommendPk1.setUser_id(user2.getId());
		recommendPk1.setReview_id(review.getId());
		RecommendPK recommendPk2 = new RecommendPK();
		recommendPk2.setUser_id(user3.getId());
		recommendPk2.setReview_id(review.getId());

		Recommend recommend1 = Recommend.builder()
				.recommendPK(recommendPk1)
				.build();
		Recommend recommend2 = Recommend.builder()
				.recommendPK(recommendPk2)
				.build();

		recommendRepository.saveAllAndFlush(List.of(recommend1, recommend2));
		recommendRepository.findAll().forEach(System.out::println);

		System.out.println(">>>>>> 신고 ");

		Report report = Report.builder()
				.user(user2)
				.review(review)
				.type(ReportType.SPOILER)
				.build();

		reportRepository.saveAndFlush(report);
		reportRepository.findAll().forEach(System.out::println);

		System.out.println(">>>>>> 문의사항 ");

		Question question1 = Question.builder()
				.user(user1)
				.title("제목")
				.content("질문1")
				.answer("답변1")
				.build();
		Question question2 = Question.builder()
				.user(user2)
				.title("제목")
				.content("질문2")
				.answer("답변2")
				.build();

		questionRepository.saveAllAndFlush(List.of(question1,question2));
		questionRepository.findAll().forEach(System.out::println);


	}


}