package com.felh;

import com.felh.openai.OpenAiService;
import com.felh.openai.model.Model;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("start");

        String sk = "";

        OpenAiService openAiService = new OpenAiService(sk);
        List<Model> model = openAiService.listModels();
        System.out.println(model);

    }

}
