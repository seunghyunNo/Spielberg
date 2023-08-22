package com.project.MovieMania.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz){
        boolean result = User.class.isAssignableFrom(clazz);
        return result;

    }

    @Override
    public void validate(Object target, Errors errors){
        User user = (User)target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username", "아이디 입력을 해주세요");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password", "비밀번호 입력을 해주세요");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email", "이메일 입력을 해주세요");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"phoneNum", "휴대폰 번호를 입력 해주세요");

        if(!user.getPassword().equals(user.getRe_password())){
            errors.rejectValue("re_password", "비밀번호를 다시 확인해주세요");
        }

        String emailWrite = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailWrite);

        if(!pattern.matcher(user.getEmail()).matches()){
            errors.rejectValue("email","이메일을 다시 확인해주세요");
        }
    }
}
