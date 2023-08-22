package com.project.MovieMania.controller;

import com.project.MovieMania.domain.User;
import com.project.MovieMania.domain.UserValidator;
import com.project.MovieMania.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public void login(){}

    @PostMapping("/loginError")
    public String loginError(){return "user/login";}

    @RequestMapping("/rejectAuth")
    public String rejectAuth(){return "common/rejectAuth";}

    @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String registerOk(@Valid User user
                             , BindingResult result      // 유효성 검사결과 담김
                             , Model model
                             , RedirectAttributes redirectAttributes
                             ){
        // 이미 등록된 중복 아이디 검증
        if (!result.hasFieldErrors("username") && userService.isExistUsername(user.getUsername())){
            result.rejectValue("username","이미 존재하는 아이디입니다");
        }

        // 검증에러가 있을 때
        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("username",user.getUsername());
            redirectAttributes.addFlashAttribute("name",user.getName());

            List<FieldError> errorList = result.getFieldErrors();
            for (FieldError err : errorList){
                redirectAttributes.addFlashAttribute("error",err.getCode());
                break;
            }
            return "redirect:/user/register";
        }
        String page = "/user/registerOk";
        int cnt = userService.register(user);
        model.addAttribute("result",cnt);
        return page;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){binder.setValidator(new UserValidator());}
}
