package com.example.personalstatement.service;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class OpenAIService {

    private final OpenAiService openAiService;

    // OpenAI API 키를 주입받음
    public OpenAIService(@Value("${spring.ai.openai.api-key}") String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("OpenAI API key must be set.");
        }
        this.openAiService = new OpenAiService(apiKey);
    }

    // 자기소개서 생성 메서드 (한글로 생성되도록 수정)
    public String generatePersonalStatement(String intro, String qualifications, String company, String experience, String tone) {
        String prompt = String.format(
                "자기소개서를 만들어주세요. " +
                        "소개: %s, " +
                        "자격증: %s, " +
                        "지원하는 회사: %s, " +
                        "경력: %s, " +
                        "원하는 어조: %s. " +
                        "응답은 한국어로 작성해 주세요. 1000자까지 적어주세요.",
                intro, qualifications, company, experience, tone
        );

        // ChatGPT API 호출을 위한 ChatMessage 객체 생성
        ChatMessage message = new ChatMessage("user", prompt);

        // ChatCompletionRequest 객체 생성
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")  // 사용할 GPT 모델 설정
                .messages(Collections.singletonList(message))  // 메시지 추가
                .maxTokens(1000)  // 응답으로 생성할 최대 토큰 수
                .build();

        // OpenAI API 호출
        ChatCompletionResult result = openAiService.createChatCompletion(chatCompletionRequest);

        // 결과에서 첫 번째 선택지의 메시지 가져오기
        ChatCompletionChoice choice = result.getChoices().get(0);
        return choice.getMessage().getContent().trim();
    }
}
