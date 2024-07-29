package com.Keyush.CRUD_And_JWT;

import io.ebean.DB;
import java.util.List;

public class StudentService {

  public void createStudent(Student student) {
    student.save();
  }

  public Student getStudent(int id) {
    return DB.find(Student.class, id);
  }

  public List<Student> getAllStudents() {
    return DB.find(Student.class).findList();
  }

  public void updateStudent(Student student) {
    student.update();
  }

  public void deleteStudent(int id) {
    Student student = getStudent(id);
    if (student != null) {
      student.delete();
    }
  }
}

