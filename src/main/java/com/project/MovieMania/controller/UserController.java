package com.project.MovieMania.controller;

import com.project.MovieMania.config.PrincipalDetails;
import com.project.MovieMania.domain.User;
import com.project.MovieMania.domain.UserValidator;
import com.project.MovieMania.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public String rejectAuth(){return "common/rejectAuth";}


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

    //비밀번호 변경
    @PostMapping("/changePw")
    public String changePw(@RequestParam("userId") Long userId,
                           @RequestParam("password") String password,
                           @RequestParam("re_password")String re_password,
                           Model model){
        System.out.println(userId);
        System.out.println(password);
        System.out.println(re_password);
        if(password.equals(re_password)){
            System.out.println("success");
            model.addAttribute("result",1);
            User user = userService.changePw(userId,password);
            return "/user/changePwOk";
        }else {
            System.out.println("fail");
            User user = userService.findByUserId(userId);
            model.addAttribute("user",user);
            return "/user/changePw";
        }


    }

//    @GetMapping("/delete")
//    public void delete(Model model){
//        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user =userDetails.getUser();
//
//        model.addAttribute("username",user.getUsername());
//        model.addAttribute("email",user.getUsername());
//    }
//
//    @PostMapping("/delete")
//    public String deleteOk(User user, Model model, HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes){
//        User user = userService (User)
//    }

    @PostMapping("/delete")
    public String deleteOk(@RequestParam String password, Model model, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean result = userService.pwCheck(Long.valueOf(userDetails.getUsername()),password);

        if (result){
            return "redirect:/user/deleteOk";
        } else {
            model.addAttribute("password", password);
            return "/user/delete";
        }
    }


//
//    // 비밀번호 변경
//    @PostMapping("/updatePw")
//    public String updatePw(@Valid User user
//                            ,BindingResult result
//                           ,Model model
//                           ,RedirectAttributes redirectAttributes){
//        if (result.hasErrors()){
//            List<FieldError> errorList = result.getFieldErrors();
//            for (FieldError error : errorList){
//                redirectAttributes.addFlashAttribute("error",error.getCode());
//                break;
//            }
//            return "redirect:/user/findPw";
//        }
//        String page = "/user/updatePw";
//        String cnt = userService.updatePw(user);
//        model.addAttribute("result",cnt);
//        return page;
//    }
//
//    @GetMapping("/rePw")
//    public String rePwOk(User user, HttpSession session, RedirectAttributes redirectAttributes){
//        if(userService.rePw(user)){
//            // 성공하면 rePwComplete 에 저장 해놓음
//            session.setAttribute("rePwComplete",true);
//            return "redirect:/user/update";
//        }else {
//            redirectAttributes.addFlashAttribute("error","비밀번호가 틀렸습니다 다시 확인해주세요");
//            return "redirect:/user/rePw";
//        }
//    }
//
//    @GetMapping("/update")
//    public String update(HttpSession session,Model model){
//        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userDetails.getUser();
//
//        model.addAttribute("username",user.getUsername());
//        model.addAttribute("email",user.getEmail());
//
//        Boolean rePwComplete = (Boolean) session.getAttribute("rePwComplete");
//        if(rePwComplete == null || !rePwComplete){
//            return "redirect:/user/rePw";
//        }
//        session.removeAttribute("rePwComplete");
//        return "/user/update";
//    }
//
//    @PostMapping("/update")
//    public String updateOk(@Valid User user,BindingResult result, Model model, RedirectAttributes redirectAttributes){
//        if (result.hasErrors()){
//            List<FieldError> errorList = result.getFieldErrors();
//            for(FieldError error : errorList){
//                redirectAttributes.addFlashAttribute("error",error.getCode());
//                break;
//            }
//            return "redirect:/user/update";
//        }
//        String page = "/user/updateOk";
//        String cnt = userService.updatePw(user);
//        model.addAttribute("result",cnt);
//        return page;
//    }
//
//    @GetMapping("/delete")
//    public void delete(Model model){
//        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userDetails.getUser();
//
//        model.addAttribute("username",user.getUsername());
//        model.addAttribute("email",user.getEmail());
//    }
//
//    @PostMapping("/delete")
//    public String deleteOk(User user, Model model, HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes){
//        if(userService.rePw(user)){
//            PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            User user1 = userDetails.getUser();
//            Long id = user1.getId();
//
//            if(id !=null){
//                int cnt = userService.delete(id);
//                model.addAttribute("result",cnt);
//            }
//
//            // 시큐리티의 로그아웃
//            new SecurityContextLogoutHandler().logout(request,response,null);
//            return "/user/deleteOk";
//        }else {
//            redirectAttributes.addFlashAttribute("error","비밀번호를 다시 확인해주세요");
//            return "redirect:/user/delete";
//        }
//    }
//
//
//    @GetMapping("/myPage")
//    public void myPage(Model model){
//
//        // 로그인한 사용자 정보를 페이지에 표시 함
//        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userDetails.getUser();
//        model.addAttribute("id",user.getId());
//        model.addAttribute("username",user.getUsername());
//    }
//
//
//
//
//
//
//





    @InitBinder
    public void initBinder(WebDataBinder binder){binder.setValidator(new UserValidator());}
}
