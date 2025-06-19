package com.example.boot.controller;

import com.example.boot.svc.UserSvc;
import com.example.boot.vo.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ExController {
    @Autowired
    UserSvc svc;

    @GetMapping({"/" , "/index"}) //클라이언트가 url요청한 주소
    public String home(){
        /* 스프링 부트의 viewResolver가 가지고 있는 것
         *  prefix :
         * "프로젝트명(애플리케이션명)/src/main/resources/templates/"
         * suffix : ".html"
         * => "boot/src/main/resources/templates/index.html"
         * */
        return "index"; // 클라이언트에게 보내줄 페이지 주소
    }

    //회원가입 폼 페이지 열기
    @GetMapping("/joinUs")
    public String joinform(){
        System.out.println("ㅎㅇㅎㅇ 여긴 겟");
        return "joinform";
    }

    //회원가입 데이터베이스에 넣기
    @PostMapping("/joinform")
    public String joinform(User user){
        System.out.println(user);
        System.out.println("ㅎㅇㅎㅇ 여긴 포스트");
        svc.joinUs(user);
        return "joinresult";
    }

    //회원 전체 목록 보기
    @GetMapping("/userList")
    public String userList(Model model){
        List<User> list = svc.userList();
        //thymeleaf문
        for( User user : list ){
            System.out.println(user);
        }
        //model 객체는 request 객체이다.
        //형식: model.addAttribute("속성명(키명)", 속성값);
        model.addAttribute("userList", list);
        return "userlist";
    }

    //로그인 창으로 이동
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String login(){
        return "loginform";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String login(User user){
        System.out.println("로그인의 user: " + user);
        User vo =  svc.userLogin(user);
        if(vo != null){ //만약 로그인 정보가 있으면 index 페이지로
            System.out.println("로그인 성공");
            return "index";
        } else {
            System.out.println("로그인 실패");
            //만약 로그인 정보가 없으면 login 페이지로
            return "loginform";
        }
    }
}
