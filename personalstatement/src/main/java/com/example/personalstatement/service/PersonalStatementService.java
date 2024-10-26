package com.example.personalstatement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalStatementService {

    private final OpenAIService openAIService;

    @Autowired
    public PersonalStatementService(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    public String createAndSavePersonalStatement(String username, String intro, String qualifications,
                                                 String company, String experience, String tone) {
        // OpenAI GPT로 자기소개서 생성
        String generatedStatement = openAIService.generatePersonalStatement(intro, qualifications, company, experience, tone);
        // 로직: 생성된 자기소개서를 저장하는 로직 추가
        return generatedStatement;
    }
}
