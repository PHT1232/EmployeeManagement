package com.example.employeemanagementapp.Entities;

import java.sql.Date;

public class Projects {
    private int project_id;
    private String project_name;
    private String description;
    private Date start_date;
    private Date end_date;
    private Date created_at;
    private Date updated_at;
    private double total_revenue;
    private double commission_rate;

    private Projects(Builder builder) {
        project_id = builder.project_id;
        project_name = builder.project_name;
        description = builder.description;
        start_date = builder.start_date;
        end_date = builder.end_date;
        created_at = builder.created_at;
        updated_at = builder.updated_at;
        total_revenue = builder.total_revenue;
        commission_rate = builder.commission_rate;
    }

    public static class Builder {
      private int project_id;
      private String project_name;
      private String description;
      private Date start_date;
      private Date end_date;
      private Date created_at;
      private Date updated_at;
      private double total_revenue;
      private double commission_rate;

      public Builder Project_id(int project_id) {
          this.project_id = project_id;
          return this;
      }

      public Builder Project_name(String project_name) {
          this.project_name = project_name;
          return this;
      }

      public Builder Description(String description) {
          this.description = description;
          return this;
      }

      public Builder Start_date(Date start_date) {
          this.start_date = start_date;
          return this;
      }

      public Builder End_date(Date end_date) {
          this.end_date = end_date;
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

      public Builder Total_revenue(double total_revenue) {
          this.total_revenue = total_revenue;
          return this;
      }

      public Builder Commission_rate(double commission_rate) {
          this.commission_rate = commission_rate;
          return this;
      }

      public Projects build() {
          return new Projects(this);
      }
    }

    public double getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(double total_revenue) {
        this.total_revenue = total_revenue;
    }

    public double getCommission_rate() {
        return commission_rate;
    }

    public void setCommission_rate(double commission_rate) {
        this.commission_rate = commission_rate;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
