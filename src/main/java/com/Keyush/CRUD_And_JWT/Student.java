package com.Keyush.CRUD_And_JWT;

import io.ebean.Model;
import io.ebean.Finder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cruddata")
public class Student extends Model {

  @Id
  private int id;
  private String name;
  private Double percentage;
  private String branch;

  public static final Finder<Long, Student> find = new Finder<>(Student.class);

  // Getters and setters

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPercentage() {
    return percentage;
  }

  public void setPercentage(Double percentage) {
    this.percentage = percentage;
  }

  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }
}
