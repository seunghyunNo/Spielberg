package com.project.MovieMania.service;

import com.project.MovieMania.domain.*;
import com.project.MovieMania.domain.DTO.ReviewDTO;
import com.project.MovieMania.domain.DTO.UserDTO;
import com.project.MovieMania.domain.type.UserStatus;
import com.project.MovieMania.repository.*;
import com.project.MovieMania.util.U;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Value("${app.pagination.write_pages}")
    private int WRITE_PAGES;

    @Value("${app.pagination.page_rows}")
    private int PAGE_ROWS;

    private UserRepository userRepository;


    private AuthorityRepository authorityRepository;


    private PasswordEncoder passwordEncoder;

    private ReviewRepository reviewRepository;

    private TicketInfoRepository ticketInfoRepository;

    private QuestionRepository questionRepository;

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    public void setTicketInfoRepository(TicketInfoRepository ticketInfoRepository) {
        this.ticketInfoRepository = ticketInfoRepository;
    }

    @Autowired
    public void setReviewRepository(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthorityRepository(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }


    @Autowired
    public  void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean isExistUsername(String username) {
       User user =userRepository.findByUsername(username);
       if(user != null){
           return true;
       }
       return false;
    }

    @Override
    public boolean isExistEmail(String email) {
        User user= userRepository.findByEmail(email);
        if(user !=null){
            return true;
        }
        return false;
    }

    @Override
    public boolean isExistPhoneNum(String phoneNum) {
        User user = userRepository.findByPhoneNum(phoneNum);
        if(user != null){
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public int register(User user) {


        user.setStatus(UserStatus.ACTIVE);


        user.setPassword(passwordEncoder.encode(user.getPassword()));   // 비밀번호를 가져와서 암호화

        User user1 = userRepository.save(user);

        Authority authority = authorityRepository.findByName("ROLE_MEMBER");

        user1.addAuthorities(authority);

        user1 = userRepository.save(user1); // update


        User user2 = userRepository.findById(user1.getId()).orElseThrow();

        if(user2 != null){
            return 1;
        }
        return 0;
    }

    // 유저 entity 넘겨주는
    @Override
//    @Transactional
    public User findUsernameId(String name, String email, LocalDate birthday){
        User user =  userRepository.findByNameAndEmailAndBirthday(name, email, birthday);
        return user;
    }

    @Override
    public User findPw(String username, String name, String email) {

        User user = userRepository.findByNameAndUsernameAndEmail(name,username,email);
        return user;
    }

    @Override
    public List<Authority> findAuthorityById(Long id) {

        User user = userRepository.findById(id).orElseThrow();

//        List<Authority> authorityList = user.getAuthorities();

        return user.getAuthorities();
    }

    @Override
    public User changePw(Long userId, String password){

        User user = userRepository.findById(userId).orElseThrow();

        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);


    }

    @Override
    public User findByUserId(Long userId) {

        User user= userRepository.findById(userId).orElseThrow();

        return user;
    }

    @Override
    public User profileUpdate(Long userId, String name, String username, String email, String phoneNum, String password) {

        // 여기에 유저정보가 다 담겨있는건가????

        User user = userRepository.findById(userId).orElseThrow();


        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }



    // 비밀번호를 확인 해서 유저 회원탈퇴
    @Override
    public boolean pwCheck(Long userId, String password) {

        User user = userRepository.findById(userId).orElseThrow();

        String getPassword = user.getPassword();

        // 비밀번호가 일치하면 아이디 삭제
        if(passwordEncoder.matches(password,getPassword)){
//            userRepository.delete(user);    // 타입이 안맞아서 void != boolean
            return true;
        }else {
            return false;
        }

    }

    @Override
    public int delete(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        userRepository.delete(user);
        return 1;
    }

    @Override
    public List<ReviewDTO> findReview(Long id) {

        User user = userRepository.findById(id).orElseThrow();

        List<Review> reviews = reviewRepository.findByUser(user);

        return reviews.stream().map(this::convertReviewDTO).collect(Collectors.toList());
    }

    @Override
    public List<TicketInfo> findTicketList(Long id) {

        User user = userRepository.findById(id).orElseThrow();

        List<TicketInfo> ticketInfos = ticketInfoRepository.findByUser(user);

        return ticketInfos;
    }

    @Override
    public List<Question> findQuestionList(Long id) {
        User user = userRepository.findById(id).orElseThrow();

        List<Question> questionList = questionRepository.findByUser(user);

        return questionList;
    }


    private ReviewDTO convertReviewDTO(Review review){
        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setId(review.getId());
        reviewDTO.setContent(review.getContent());
        reviewDTO.setScore(review.getScore());
        reviewDTO.setCreatedAt(review.getCreatedAt());



        return reviewDTO;
    }


}
