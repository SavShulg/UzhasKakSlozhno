package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import repository.StudentRepository;
import service.AvatarService;
import service.FacultyService;
import service.StudentService;

import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class StudentControllerTestWebMvc {
    @Autowired
    MockMvc mvc;

    @MockBean
    StudentRepository studentRepository;
    @SpyBean
    FacultyService facultyService;
    @MockBean
    AvatarService avatarService;
    @MockBean
    StudentService studentService;
    @InjectMocks
    StudentController controller;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testGet() throws Exception {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student(1L, "test_student_mvc", 10)));
        mvc.perform(MockMvcRequestBuilders.get("/student=?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test_student_mvc"))
                .andExpect(jsonPath("$.age").value(10));
    }

    @Test
    void testUpdate() throws Exception {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student(1L, "test_faculty_mvc", 10)));
        Student student = new Student(1L, "updated_name", 20);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(MockMvcRequestBuilders.put("faculty=?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("updated_name"))
                .andExpect(jsonPath("$.age").value(10));
    }

    @Test
    void testDelete() throws Exception {
        when(studentRepository.findById(2L)).thenReturn(Optional.of(new Student(1L, "test_faculty_mvc", 10)));
        mvc.perform(MockMvcRequestBuilders.delete("student=?id=2"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        when(studentRepository.findById(333L)).thenReturn(Optional.empty());
        mvc.perform(MockMvcRequestBuilders.delete("student=?id=333"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

    }

    @Test
    void testAdd() throws Exception {
        when(studentRepository.save(any(Student.class))).then(invocationOnMock -> {
            Student input = invocationOnMock.getArgument(0, Student.class);
            Student f = new Student(1L, "name1", 10, new Student(2L, "name2", 20));
            f.setId(100L);
            f.setAge(input.getAge());
            f.setName(input.getName());
            return f;
        });
        Student student = new Student(null, "foo", 10);

        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(MockMvcRequestBuilders.post("student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.name").value("foo"))
                .andExpect(jsonPath("$.age").value(10));
    }

    @Test
    void testByColorAndName() throws Exception {

        when(studentRepository.findAllByAgeBetween(10,20)
                .iterator(
                        new Student(1L, "name1", 10,
                                new Student(2L, "name2", 20));


        mvc.perform(MockMvcRequestBuilders.get("/student/byAgeAndName?name=name1&age=age"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("name1"))
                .andExpect(jsonPath("$[0].age").value(10))
                .andExpect(jsonPath("$[1].name").value("name2"))
                .andExpect(jsonPath("$[1].age").value(20));
    }

    @Test
    void testGetStudent() throws Exception {
        Student f = new Student(1L, "f1", 10);
        f.setId((new Student(1L, "s1", 10)).getId());


        when(studentRepository.findById(1L)).thenReturn(Optional.of(f));

        mvc.perform(MockMvcRequestBuilders.get("/student/faculty?studentId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("s1"))
                .andExpect(jsonPath("$[0].age").value(10));

        mvc.perform(MockMvcRequestBuilders.get("/student/faculty?studentId="))
                .andExpect(status().is(400));
    }

}