package com.project.MovieMania.controller;

import com.project.MovieMania.domain.Question;
import com.project.MovieMania.domain.User;
import com.project.MovieMania.domain.type.Gender;
import com.project.MovieMania.domain.type.UserStatus;
import com.project.MovieMania.service.QuestionService;
import com.project.MovieMania.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/list")
    public void list(Integer page, Model model) {

        User user = new User();
        user.setId(1l);
        user.setName("나");
        user.setUsername("나");
        user.setEmail("ppp@vmv.com");
        user.setPhoneNum("01011112222");
        user.setBirthday(LocalDate.now());
        user.setStatus(UserStatus.ACTIVE);
        user.setGender(Gender.MALE);

        model.addAttribute("username", user.getUsername());

        model.addAttribute("list", questionService.list(page, model));

    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        Page<Question> searchList = questionService.search(keyword);
        model.addAttribute("list", searchList);
        return "/question/list";
    }

    @GetMapping("/write")
    public void write(Model model){

        User user = new User();
        user.setId(1l);
        user.setName("나");
        user.setUsername("나");
        user.setEmail("ppp@vmv.com");
        user.setPhoneNum("01011112222");
        user.setBirthday(LocalDate.now());
        user.setStatus(UserStatus.ACTIVE);
        user.setGender(Gender.MALE);

        model.addAttribute("user_id", user.getId());
        model.addAttribute("username", user.getUsername());

    }


    @PostMapping("/write")
    public  String writeOk(Question question,
                           Model model){

        User user = new User();
        user.setId(1l);
        user.setName("나");
        user.setUsername("나");
        user.setEmail("ppp@vmv.com");
        user.setPhoneNum("01011112222");
        user.setBirthday(LocalDate.now());
        user.setStatus(UserStatus.ACTIVE);
        user.setGender(Gender.MALE);

        question.setUser(user);

        int write = questionService.write(question);
        model.addAttribute("result", write);

        return "question/writeOk";
    }


    @GetMapping("/detail/{id}")
    public String detail(@PathVariable long id, Model model){

        User user = new User();
        user.setId(1l);
        user.setName("나");
        user.setUsername("나");
        user.setEmail("ppp@vmv.com");
        user.setPhoneNum("01011112222");
        user.setBirthday(LocalDate.now());
        user.setStatus(UserStatus.ACTIVE);
        user.setGender(Gender.MALE);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("question", questionService.detail(id));

        return "question/detail";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable long id, Model model){

        User user = new User();
        user.setId(1l);
        user.setName("나");
        user.setUsername("나");
        user.setEmail("ppp@vmv.com");
        user.setPhoneNum("01011112222");
        user.setBirthday(LocalDate.now());
        user.setStatus(UserStatus.ACTIVE);
        user.setGender(Gender.MALE);

        model.addAttribute("user_id", user.getId());
        model.addAttribute("username", user.getUsername());

        model.addAttribute("question", questionService.selectById(id));
        return "question/update";

    }

    @PostMapping("/update")
    public String updateOk(
            Question question,
            Model model
    ){

        User user = new User();
        user.setId(1l);
        user.setName("나");
        user.setUsername("나");
        user.setEmail("ppp@vmv.com");
        user.setPhoneNum("01011112222");
        user.setBirthday(LocalDate.now());
        user.setStatus(UserStatus.ACTIVE);
        user.setGender(Gender.MALE);

        question.setUser(user);

        model.addAttribute("result", questionService.update(question));
        return "question/updateOk";
    }

    @PostMapping("/answer")
    public String answerOk(Question question,
                           Model model){
        User user = new User();
        user.setId(1l);
        user.setName("나");
        user.setUsername("나");
        user.setEmail("ppp@vmv.com");
        user.setPhoneNum("01011112222");
        user.setBirthday(LocalDate.now());
        user.setStatus(UserStatus.ACTIVE);
        user.setGender(Gender.MALE);

        question.setUser(user);

        model.addAttribute("result", questionService.answer(question));
        return "question/answerOk";
    }


    @PostMapping("/pageRows")
    public String pageRows(Integer page, Integer pageRows) {
        U.getSession().setAttribute("pageRows", pageRows);
        return "redirect:/question/list?=page="+ page;
    }


}
