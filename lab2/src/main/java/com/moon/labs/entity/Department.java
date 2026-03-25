package com.moon.labs.entity;

public class Department {

    private final int id;
    private String name;
    private double budget;

    public Department(int id, String name, double budget) {
        this.id = id;
        this.name = name;
        this.budget = budget;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBudget() {
        return budget;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBudget(double budget) {
        this.budget = budget;
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
