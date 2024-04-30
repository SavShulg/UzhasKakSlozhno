package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Student {

    @Entity
    public class Student {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private int age;

        @JoinColumn(name = "faculty_id")
        @ManyToOne
        @JsonIgnore
        private Faculty faculty;



        public Student(Long id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public Student(long l, String name1, int i, Student name2) {

        }

        public Long getId() {
            return id;
        }
        @@ -28,4 +53,25 @@ public int getAge() {
            public void setAge(int age) {
                this.age = age;
            }

            public Faculty getFaculty() {
                return faculty;
            }

            public void setFaculty(Faculty faculty) {
                this.faculty = faculty;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Student student = (Student) o;
                return age == student.age && Objects.equals(id, student.id) && Objects.equals(name, student.name);
            }

            @Override
            public int hashCode() {
                return Objects.hash(id, name, age);
            }
}
