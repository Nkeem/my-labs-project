package com.moon.labs.database.model;

import java.util.Objects;

public class Department {

    private Integer id;
    private String name;
    private double budget;

    public Department() {
    }

    public Department(String name, double budget) {
        this.name = name;
        this.budget = budget;
    }

    public Department(Integer id, String name, double budget) {
        this.id = id;
        this.name = name;
        this.budget = budget;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department that)) return false;
        return Double.compare(that.budget, budget) == 0
                && Objects.equals(id, that.id)
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, budget);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", budget=" + budget +
                '}';
    }
}