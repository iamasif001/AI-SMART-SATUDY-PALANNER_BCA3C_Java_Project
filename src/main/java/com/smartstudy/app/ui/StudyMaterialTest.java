
package com.smartstudy.app.ui;

import org.apache.tika.Tika;
import java.io.File;

public class StudyMaterialTest {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
   new StudyMaterialsForm(null).setVisible(true);
});

        try {
            Tika tika = new Tika();

            System.out.println("Apache Tika loaded successfully!");
            System.out.println("Tika version: "
                    + Tika.class.getPackage().getImplementationVersion());

           File file = new File("sample.pptx.pptx");

            if (file.exists()) {
                String text = tika.parseToString(file);
                File outputFile = new File("extracted.txt");

try (java.io.FileWriter writer = new java.io.FileWriter(outputFile)) {
    writer.write(text);
}

System.out.println("Extracted text saved to: "
        + outputFile.getAbsolutePath());

                System.out.println("\nExtracted Text:");
               java.nio.file.Files.writeString(
        java.nio.file.Path.of("extracted.txt"),
        text
);

System.out.println("Text saved successfully to extracted.txt");
            } else {
                System.out.println(
                    "Test ready. Place sample.pptx.pptx "
+ "in the project root to test text extraction."
                );
            }

        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
