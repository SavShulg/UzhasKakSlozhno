package controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletResponse;
import model.Avatar;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.AvatarService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class AvatarController {

    @RequestMapping("/avatar")
    @RestController
    public class AvatarController {
        private final AvatarService service;
        private final AvatarService avatarService;

        public AvatarController(AvatarService service, AvatarService avatarService) {
            this.service = service;
            this.avatarService = avatarService;
        }

        @PostMapping
        public ResponseEntity<Avatar> save(@RequestParam Long studentId, @RequestBody MultipartFile file) throws IOException {
            return ResponseEntity.ofNullable(service.save(studentId, file));
        }

        @GetMapping("/disk/{id}")
        public void loadFromDisk(@PathVariable Long id, HttpServletResponse response) throws IOException {
            var avatar = service.getById(id);
            if (avatar != null) {
                response.setContentLength((int) avatar.getFileSize());
                response.setContentType(avatar.getMediaType());
                try (var fis = new FileInputStream(avatar.getFilePath())) {
                    fis.transferTo(response.getOutputStream());

                }
            }
        }

        @GetMapping("/db/{id}")
        public ResponseEntity<byte[]> loadFromDisk(@PathVariable Long id) {
            var avatar = service.getById(id);
            if (avatar == null) {
                return ResponseEntity.ofNullable(null);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(avatar.getFileSize());
            headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
            return ResponseEntity.status(200).headers(headers).body(avatar.getData());
        }

        @GetMapping()
        public List<Avatar> getByPage(@RequestParam("page") int page,
                                      @RequestParam("size") int size) {
            return avatarService.getPage(page, size);

        }
    }
