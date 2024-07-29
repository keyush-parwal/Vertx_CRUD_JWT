package com.Keyush.CRUD_And_JWT;

import io.ebean.Model;
import io.ebean.annotation.Identity;

import javax.persistence.*;

@Entity
@Table(name = "cruddata")
public class Student extends Model {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String branch;
  private double percentage=Double.NaN;


  // Getters and Setters
  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getBranch() { return branch; }
  public void setBranch(String branch) { this.branch = branch; }

  public double getPercentage() { return percentage; }
  public void setPercentage(double percentage) { this.percentage = percentage; }
}
