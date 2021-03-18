package com.osynt.keep.keepnote.controllers;

import com.osynt.keep.keepnote.entities.Note;
import com.osynt.keep.keepnote.services.DIR;
import com.osynt.keep.keepnote.services.KeepDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class HomeController {

   @Autowired
   KeepDao dao;

   @Autowired
   DIR DIR;

   // saving image
   @PostMapping("/image")
   public ResponseEntity<?> image(@PathVariable("image") MultipartFile image) {

      try {
         System.out.println(image.getOriginalFilename());
         if (image.getContentType().equals("image/*")
                 || image.getContentType().equals("image/jpeg") && !image.isEmpty()) {

            // logic to save image in static directory later i'll change it to dynamic one

            Files.copy(image.getInputStream(),
                    Paths.get(DIR.getPath().getAbsolutePath() + File.separator + image.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok().build();

         } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
      } catch (Exception e) {
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
   }

   // hit to get a note
   @GetMapping("/note/{id}")
   public ResponseEntity<Note> getNote(@PathVariable("id") int id) {
      try {
         Optional<Note> opt = dao.findById(id);
         return ResponseEntity.ok(opt.get());
      } catch (NoSuchElementException e) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
   }

   // getting image
   @GetMapping("/image/{path}")
   public ResponseEntity<byte[]> getImage(@PathVariable("path") String path) {

      try {
         File fi = new File(DIR.getPath().getAbsolutePath() + File.separator + path);
         byte[] fileContent = Files.readAllBytes(fi.toPath());
         return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(fileContent);
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
   }

   // hit to get all notes
   @GetMapping("/notes")
   public ResponseEntity<List<Note>> getNotes() {
      try {
         List<Note> notes = dao.getNotess();
         return ResponseEntity.ok().body(notes);
      } catch (Exception e) {
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
   }

   // delete note
   @DeleteMapping("/note/{id}")
   public ResponseEntity<Void> deleteNote(@PathVariable("id") int id) {

      try {
         Optional<Note> opt = dao.findById(id);
         // deleting notes
         if (opt.get().getImage() != null) {
            Files.delete(Paths.get(DIR.getPath().getAbsolutePath() + File.separator, opt.get().getImage()));
         }
         dao.delete(opt.get());

         return ResponseEntity.ok().build();
      } catch (NoSuchElementException e) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
   }

   // update here
   @PutMapping("/note-update")
   public ResponseEntity<Void> updateNote(@RequestPart("image") MultipartFile image, @RequestPart("note") Note note,
                                          @RequestPart("prev") String prev) {

      try {
         Files.delete(Paths.get(DIR.getPath().getAbsolutePath() + File.separator + prev));

         Files.copy(image.getInputStream(),
                 Paths.get(DIR.getPath().getAbsolutePath() + File.separator + image.getOriginalFilename()),
                 StandardCopyOption.REPLACE_EXISTING);
         note.setImage(image.getOriginalFilename());

         dao.save(note);

         dao.save(note);
         return ResponseEntity.ok().build();
      } catch (Exception e) {
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
   }

   @PutMapping("/update-without-image")
   public ResponseEntity<?> updateWithoutImage(@RequestBody Note note) {

      try {
         if (note != null) {
            dao.save(note);
            return ResponseEntity.ok().build();
         } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

         }
      } catch (Exception e) {
         e.printStackTrace();

         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }

   }

   // for saving image and notes together
   @PostMapping(value = "/image-and-note", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<?> testing(@RequestPart("image") MultipartFile image, @RequestPart("note") Note note) {
      try {

         if (!image.isEmpty() && note != null && image.getContentType().contains("image/")) {

            Files.copy(image.getInputStream(),
                    Paths.get(DIR.getPath().getAbsolutePath() + File.separator + image.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            // saving note with its original name
            note.setImage(image.getOriginalFilename());
            dao.save(note);
            System.out.println(image.getContentType());
            return ResponseEntity.ok().build();
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
   }

   // saving the note witout image
   @PostMapping("/note")
   public ResponseEntity<?> saveNote(@RequestBody Note note) {
      try {
         dao.save(note);

         return ResponseEntity.ok().build();
      } catch (Exception e) {
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }

   }

}