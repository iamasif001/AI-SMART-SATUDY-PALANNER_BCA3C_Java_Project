
package com.smartstudy.app;

import com.smartstudy.app.ai.GeminiAIService;

public class GeminiMCQTest {

    public static void main(String[] args) {

        String sampleStudyMaterial = """
                Database Management System (DBMS) is software used to
                create, store, organize, and manage data in a database.
                A primary key uniquely identifies each record in a table.
                A foreign key creates a relationship between tables.
                SQL stands for Structured Query Language.
                """;

        try {
            GeminiAIService aiService = new GeminiAIService();

            System.out.println("Generating MCQs using Gemini AI...");
            System.out.println();

            String result = aiService.generateMCQs(
                    sampleStudyMaterial,
                    5
            );

            System.out.println("===== GENERATED MCQs =====");
            System.out.println(result);

        } catch (Exception e) {
            System.out.println("MCQ generation failed:");
            System.out.println(e.getMessage());
        }
    }
}
