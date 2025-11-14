package com.example.employeemanagementapp.Entities;

import java.sql.Date;

public class Departments {
    private int department_id;
    private String department_name;
    private int manager_id;
    private Date created_at;
    private Date updated_at;
    
    private Departments(Builder builder) {
        department_id = builder.department_id;
        department_name = builder.department_name;
        manager_id = builder.manager_id;
        created_at = builder.created_at;
        updated_at = builder.updated_at;
    }

    public static class Builder {
      private int department_id;
      private String department_name;
      private int manager_id;
      private Date created_at;
      private Date updated_at;

      public Builder Department_id(int department_id) {
          this.department_id = department_id;
          return this;
      }

      public Builder Department_name(String department_name) {
          this.department_name = department_name;
          return this;
      }

      public Builder Manager_id(int manager_id) {
          this.manager_id = manager_id;
          return this;
      }

      public Builder Created_at(Date created_at) {
          this.created_at = created_at;
          return this;
      }

      public Builder Updated_at(Date updated_at) {
          this.updated_at = updated_at;
          return this;
      }

      public Departments build() {
          return new Departments(this);
      }
    }
    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return department_name;
    }
}
