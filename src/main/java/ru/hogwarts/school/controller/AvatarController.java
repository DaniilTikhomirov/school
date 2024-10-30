package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.models.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> getAvatar(@PathVariable Long id, @RequestParam MultipartFile file) throws IOException {
        if (file.getSize() > 1024 * 300){
            return ResponseEntity.badRequest().body("File is big");
        }
        avatarService.uploadAvatar(id, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long id) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);
        if (avatar == null || avatar.getMediaType() == null) {
            return ResponseEntity.badRequest().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping("file/{id}")
    public ResponseEntity<byte[]> getAvatarFromFile(@PathVariable Long id) throws IOException {
        File avatar = avatarService.getAvatarFromDirectory(id);
        if (avatar == null) {
            return ResponseEntity.badRequest().build();
        }
        byte[] avatar_img = Files.readAllBytes(avatar.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(Files.probeContentType(avatar.toPath())));
        headers.setContentLength(avatar_img.length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar_img);
    }
}
