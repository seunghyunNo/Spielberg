package com.project.MovieMania.service;

import com.project.MovieMania.domain.Question;
import com.project.MovieMania.domain.User;
import com.project.MovieMania.repository.QuestionRepository;
import com.project.MovieMania.repository.UserRepository;
import com.project.MovieMania.util.U;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Value("${app.pagination.write_pages}")
    private int WRITE_PAGES;

    @Value("${app.pagination.page_rows}")
    private int PAGE_ROWS;


    private QuestionRepository questionRepository;

    private UserRepository userRepository;

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Question> list() {
        return  questionRepository.findAll();
    }

    @Override
    public List<Question> list(Integer page, Model model) {
        if(page == null) page = 1;
        if(page < 1) page = 1;

        HttpSession session = U.getSession();

        Integer writePages = (Integer)session.getAttribute("writePages");
        if(writePages == null) writePages = WRITE_PAGES;

        Integer pageRows = (Integer)session.getAttribute("pageRows");
        if(pageRows == null) pageRows = PAGE_ROWS;

        session.setAttribute("page", page);

        Page<Question> pageWrites = questionRepository.findAll(PageRequest.of(page - 1, pageRows, Sort.by(Sort.Order.desc("id"))));

        long cnt = pageWrites.getTotalElements();
        int totalPage =  pageWrites.getTotalPages();

        if(page > totalPage) page = totalPage;

        int fromRow = (page - 1) * pageRows;

        int startPage = (((page - 1) / writePages) * writePages) + 1;
        int endPage = startPage + writePages - 1;
        if (endPage >= totalPage) endPage = totalPage;

        model.addAttribute("cnt", cnt);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("pageRows", pageRows);

        model.addAttribute("url", U.getRequest().getRequestURI());
        model.addAttribute("writePages", writePages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        List<Question> list = pageWrites.getContent();
        model.addAttribute("list", list);

        return list;
    }


    @Override
    public int write(Question question) {
        questionRepository.save(question);
        return 1;
    }

    @Override
    @Transactional
    public Question detail(long id) {
        Question question = questionRepository.findById(id).orElse(null);

        return question;
    }

    @Override
    public Question selectById(long id) {
        Question question = questionRepository.findById(id).orElse(null);
        return question;
    }

    @Override
    public int update(Question question) {
        int result = 0;
        Question q = questionRepository.findById(question.getId()).orElse(null);
        if (q != null){
            q.setTitle(question.getTitle());
            q.setContent(question.getContent());
            q = questionRepository.save(q);
            result = 1;
        }

        return result;
    }

    @Override
    public int answer(Question question) {
        int result = 0;
        Question q = questionRepository.findById(question.getId()).orElse(null);
        if (q != null){
            q.setTitle(question.getTitle());
            q.setContent(question.getContent());
            q.setAnswer(question.getAnswer());
            q = questionRepository.save(q);
            result = 1;
        }

        return result;
    }

    @Override
    public int delete(long id) {
        int result = 0;

        Question question = questionRepository.findById(id).orElse(null);

       if (question != null){
           questionRepository.delete(question);
           result = 1;
       }

        return result;
    }

    @Override
    public List<Question> findMyQuestion(Long id) {

        User user = userRepository.findById(id).orElseThrow();

        List<Question> questionList = questionRepository.findByUser_id(id);

        return questionList;
    }



    // 마이페이지 내 질문 리스트
    @Override
    public List<Question> findMyQuestionList(Model model, Integer myPage, long id) {
        if(myPage == null) myPage = 1;
        if(myPage < 1) myPage = 1;

        HttpSession session = U.getSession();

        Integer writePages = (Integer)session.getAttribute("writePages");
        if(writePages == null) writePages = WRITE_PAGES;

        Integer pageRows = (Integer)session.getAttribute("pageRows");
        if(pageRows == null) pageRows = PAGE_ROWS;

        session.setAttribute("myPage", myPage);

        Page<Question> pageWrites = questionRepository.findAll(PageRequest.of(myPage - 1, pageRows, Sort.by(Sort.Order.desc("id"))));

        long cnt = pageWrites.getTotalElements();
        int totalPage =  pageWrites.getTotalPages();

        if(myPage > totalPage) myPage = totalPage;

        int fromRow = (myPage - 1) * pageRows;

        int startPage = (((myPage - 1) / writePages) * writePages) + 1;
        int endPage = startPage + writePages - 1;
        if (endPage >= totalPage) endPage = totalPage;

        model.addAttribute("cnt", cnt);
        model.addAttribute("myPage", myPage);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("pageRows", pageRows);

        model.addAttribute("url", U.getRequest().getRequestURI());
        model.addAttribute("writePages", writePages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        List<Question> list = pageWrites.getContent();
        model.addAttribute("list", list);

        return list;
    }

}
