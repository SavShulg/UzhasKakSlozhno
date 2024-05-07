package service;

import model.Avatar;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import repository.AvatarRepository;
import repository.StudentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Logger;


@Service
    public class AvatarService {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(AvatarService.class);
        private final AvatarRepository avatarRepository;
        private final Path avatarsDir;
        private final StudentRepository studentRepository;


        public AvatarService(AvatarRepository avatarRepository, @Value("${avatars.dir}") Path avatarsDir, StudentRepository studentRepository) {
            this.avatarRepository = avatarRepository;
            this.avatarsDir = avatarsDir;
            this.studentRepository = studentRepository;
        }

        public Avatar save(Long studentId, MultipartFile file) throws IOException {
            logger.info("Upload avatar was invoked!");

            Files.createDirectories(avatarsDir);
            var index = file.getOriginalFilename().lastIndexOf('.');
            var extension = file.getOriginalFilename().substring(index);
            Path filePath = avatarsDir.resolve(studentId + extension);
            try (var in = file.getInputStream()) {
                Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
                logger.info("Converting bytes...");
            }

            logger.info("File has been uploaded!");
            Avatar avatar = avatarRepository.findAllByStudentId(studentId).orElse(new Avatar());
            avatar.setFileSize(file.getSize());
            avatar.setMediaType(file.getContentType());
            avatar.setData(file.getBytes());
            avatar.setStudent(studentRepository.getReferenceById(studentId));
            avatar.setFilePath(filePath.toString());
            return avatarRepository.save(avatar);
            logger.info("Avatar has been saved! id = {}, path = {}" , avatarsDir.getId(), filePath);

        }

        public Avatar getById(Long id) {
            return avatarRepository.findById(id).orElse(null);

        }

        public List<Avatar> getPage(int pageNumder, int pageSize) {
            return avatarRepository.findAll(PageRequest.of(pageNumder, pageSize)).toList();
        }
    }
