package com.project.MovieMania.controller;

import com.project.MovieMania.config.PrincipalDetails;
import com.project.MovieMania.domain.User;
import com.project.MovieMania.domain.UserValidator;
import com.project.MovieMania.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public void login(){}

    // 로그인 실패 시  request 받은 attribute 를 사용하기 위함
    @PostMapping("/loginError")
    public String loginError(){return "user/login";}


    // 접근 권한
    @RequestMapping("/rejectAuth")
    public String rejectAuth(){return "user/rejectAuth";}


    // 회원가입
    @PreAuthorize("isAnonymous()")
    @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String registerOk(@Valid User user
//                             ,@RequestParam("emailCodeInput") String emailCodeInput
//                             ,@RequestParam("emailCode") String emailCode
//                             ,@RequestParam("pnCodeInput") String pnCodeInput
//                             ,@RequestParam("pnCode") String pnCode
//                             ,@RequestParam("email") String email
//                             ,@RequestParam("phoneNum") String phoneNum
                             , BindingResult result      // 유효성 검사결과 담김
                             , Model model
                             , RedirectAttributes redirectAttributes
                             ){
        // 이미 등록된 중복 아이디 검증
        if (!result.hasFieldErrors("username") && userService.isExistUsername(user.getUsername())){
            result.rejectValue("username","이미 존재하는 아이디입니다");
        }else if(user.getUsername().length() < 6){
            result.rejectValue("username","아이디는 6글자 이상으로 기입해주세요");
        }

        // 이미 등록된 이메일 검증
        if(!result.hasFieldErrors("email") && userService.isExistEmail(user.getEmail())){
            result.rejectValue("email","이미 존재하는 이메일입니다");
        }

        // 이미 등록된 핸드폰 검증
        if(!result.hasFieldErrors("phoneNum") && userService.isExistPhoneNum(user.getPhoneNum())){
            result.rejectValue("phoneNum","이미 존재하는 핸드폰 번호입니다");
        }

        // 검증에러가 있을 때
        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("username",user.getUsername());
            redirectAttributes.addFlashAttribute("name",user.getName());
            redirectAttributes.addFlashAttribute("birth",user.getBirthday());
            redirectAttributes.addFlashAttribute("phoneNum",user.getPhoneNum());
            redirectAttributes.addFlashAttribute("email",user.getEmail());
//            redirectAttributes.addFlashAttribute("emailCodeInput",emailCodeInput);
//            redirectAttributes.addFlashAttribute("emailCode",emailCode);
//            redirectAttributes.addFlashAttribute("pnCodeInput",pnCodeInput);
//            redirectAttributes.addFlashAttribute("pnCode",pnCode);

            List<FieldError> errorList = result.getFieldErrors();
            for (FieldError err : errorList){
                redirectAttributes.addFlashAttribute("error",err.getCode());
                break;
            }
            return "redirect:/user/register";
        }

        // 에러 없을 시 가입
        String page = "user/registerOk";
        int cnt = userService.register(user);
        model.addAttribute("result",cnt);
        return page;
    }

//    @PostMapping("/register/usernameCheck")
//    public @ResponseBody int usernameCheck(@RequestParam("username")String username){
//        System.out.println(username);
//        return userService.usernameCheck(username);
//    }
//
//    @PostMapping("/register/mailCheck")
//    public @ResponseBody int mailCheck(@RequestParam("email") String email){
//        return userService.mailCheck(email);
//    }
//
    @PreAuthorize("isAnonymous()")
    @GetMapping("findUsernameId")
    public void findUsernameId(){}
//
//
//    // 아이디 찾기
    @PostMapping("/findUsernameId")
    public String findUsernameIdOk(@RequestParam("email")String email,
                                               @RequestParam("name") String name,
                                               @RequestParam("birthday") LocalDate birthday,
                                                Model model

        ){
       User user = userService.findUsernameId(name,email,birthday);
       String username = user.getUsername();
       model.addAttribute("username",username);
//       model.addAttribute("username",userService.findUsernameId(email, name, birthday).getUsername());
        return "/user/findUsernameIdOk";
    }
//
    // 비밀번호 찾기
    @PreAuthorize("isAnonymous()")
    @GetMapping("/findPw")
    public void findPw(){}

    // 비밀번호 찾기
    @PostMapping("/findPw")
    public String findPwOk(@RequestParam("username") String username,
                                      @RequestParam("name") String name,
                                      @RequestParam("email") String email,
                                        Model model
    ){
       User user = userService.findPw(username,name,email);

       if(user != null){
           model.addAttribute("user",user);
           return "/user/changePw";
       }
       return "/user/findPw";
    }

    //비밀번호 찾기에서 비밀번호 변경
    @PostMapping("/changePw")
    public String changePw(@RequestParam("userId") Long userId,
                           @RequestParam("password") String password,
                           @RequestParam("re_password")String re_password,
                           Model model){

        if(password.equals(re_password)){

            model.addAttribute("result",1);
            User user = userService.changePw(userId,password);
            return "/user/changePwOk";
        }else {

            User user = userService.findByUserId(userId);
            model.addAttribute("user",user);
            return "/user/changePw";
        }


    }


    @GetMapping("/profileUpdate")
    public void profileUpdate(Model model){
        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user =userDetails.getUser();
        System.out.println(user.getAuthorities());
        model.addAttribute("authority",user.getAuthorities());
        model.addAttribute("userId",user.getId());
        model.addAttribute("name",user.getName());
        model.addAttribute("username",user.getUsername());
        model.addAttribute("email",user.getEmail());
        model.addAttribute("phoneNum",user.getPhoneNum());
        model.addAttribute("password",user.getPassword());
    }

    // 프로필업데이트(비밀번호 변경)
    @PostMapping("/profileUpdate")
    public String profileUpdate(@RequestParam("userId")Long userId,
                                @RequestParam("username") String username,
                                @RequestParam("name") String name,
                                @RequestParam("email") String email,
                                @RequestParam("phoneNum") String phoneNum,
                                @RequestParam("password") String password,
                                @RequestParam("re_password") String re_password,
                                Model model
                                ){
        if(password.equals(re_password)){
            model.addAttribute("result",1);
            User user = userService.profileUpdate(userId, name, username, email, phoneNum, password);
            return "/user/profileUpdateOk";
        }else{
            User user = userService.findByUserId(userId);
            model.addAttribute("user",user);
            return "/user/profileUpdate";
        }
    }


    @GetMapping("/delete")
    public void delete(Model model){
        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        model.addAttribute("userId",user.getId());

    }

    @PostMapping("/delete")
    public String deleteOk(@RequestParam("userId") Long userId,
                           @RequestParam("password") String password,
                           Model model,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes){
        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user1 = userDetails.getUser();
        Long id = user1.getId();


        boolean isPasswordCorrect = userService.pwCheck(userId,password);

        if(isPasswordCorrect){
            model.addAttribute("result",1);
            // 하기전에 시큐리티 로그아웃
            new SecurityContextLogoutHandler().logout(request,response,null);
            userService.delete(userId);
            return "/user/deleteOk";
        }else {
            model.addAttribute("result", 0);
            return "redirect:/user/delete";
        }
    }



    @GetMapping("/myPage")
    public void myPage(Model model){
        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user =userDetails.getUser();
        System.out.println(user.getAuthorities());

        model.addAttribute("id",user.getId());
        model.addAttribute("username",user.getUsername());
        model.addAttribute("authority",user.getAuthorities());
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){binder.setValidator(new UserValidator());}
}
