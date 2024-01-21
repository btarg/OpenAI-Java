package io.github.btarg.javaOpenAI;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import io.github.btarg.javaOpenAI.memory.PersistentChatMemoryStore;
import io.github.btarg.javaOpenAI.tools.Calculator;

public class Main {
    public static void main(String[] args) {

        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(10)
                .chatMemoryStore(new PersistentChatMemoryStore())
                .build();

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(OpenAiChatModel.withApiKey(System.getenv("OPENAI_API_KEY")))
                .tools(new Calculator())
                .chatMemory(chatMemory)
                .build();

//        String question = "What is the square root of the sum of the numbers of letters in the words \"hello\" and \"world\"?";
        String question = "What was the last square root calculated?";
        String answer = assistant.chat(question);

        System.out.println(answer);
        // The square root of the sum of the number of letters in the words "hello" and "world" is approximately 3.162.
    }

    interface Assistant {

        String chat(String userMessage);
    }

}
