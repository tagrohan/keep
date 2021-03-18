package com.osynt.keep.keepnote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class KeepNoteApplication {
   public static void main(String[] args) {
      SpringApplication.run(KeepNoteApplication.class, args);
      System.out.println("its started at : http://localhost:8080");

//      try {
//         new ClassPathResource("static/image").toString();
//      } catch (Exception e) {
//         Files.createDirectory(Paths.get(new ClassPathResource("static/image").toString()));
//      }


      try {
         String dir = new ClassPathResource("com").getFile().getAbsolutePath();
         System.out.println(dir);
         if (!Files.exists(Paths.get(dir + File.separator + "image"))) {
            Files.createDirectory(Paths.get(dir + File.separator + "image"));
         }
      } catch (Exception e) {
         e.printStackTrace();
      }


   }
}
