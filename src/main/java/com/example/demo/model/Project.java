package com.example.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Created by Divineit-Iftekher on 1/16/2018.
 */
@Entity
public class Project {


  private Integer id;

  private String name;

  private List<Task> tasks;

  private String createdBy;

  private String updatedBy;


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @NotEmpty(message = "Name can not be empty")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @OneToMany()
  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }


  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }
}
