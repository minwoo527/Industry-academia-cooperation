package com.example.personalstatement.controller;

import com.example.personalstatement.service.PersonalStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.personalstatement.dto.PersonalStatementRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PersonalStatementController {

    private final PersonalStatementService personalStatementService;

    @Autowired
    public PersonalStatementController(PersonalStatementService personalStatementService) {
        this.personalStatementService = personalStatementService;
    }

    @GetMapping("/generate-personal-statement")
    public String generatePersonalStatementPage(HttpSession session, Model model) {
        // 세션에서 사용자 이름과 생년월일을 가져옴
        String realname = (String) session.getAttribute("realname");
        String birthdate = (String) session.getAttribute("birthdate");

        // 세션에 사용자 정보가 없으면 로그인 페이지로 리디렉션
        if (realname == null || birthdate == null) {
            return "redirect:/login";
        }

        // 모델에 사용자 정보 추가하여 템플릿으로 전달
        model.addAttribute("realname", realname);
        model.addAttribute("birthdate", birthdate);

        return "generate-personal-statement";
    }

    @PostMapping("/generate-personal-statement")
    public String generatePersonalStatement(PersonalStatementRequest request, Model model) {
        // OpenAI GPT로 생성된 자기소개서를 받아서 처리
        String generatedStatement = personalStatementService.createAndSavePersonalStatement(
                request.getUsername(), request.getIntro(), request.getQualifications(),
                request.getCompany(), request.getExperience(), request.getTone());

        model.addAttribute("generatedStatement", generatedStatement);
        return "generate-personal-statement";
    }
}
