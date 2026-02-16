package com.moon.labs.model;

// Выгрузил не все характеристики из таблицы
public class Character {
    private int id;
    private String name;
    private String status;
    private String species;
    private String gender;

    public Character(int id, String name, String status, String species, String gender) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.gender = gender;
    }

    public String toCSV() {
        return id + "," + name + "," + status + "," + species + "," + gender;
    }

    public static Character fromCSV(String line) {
        String[] parts = line.split(",");
        return new Character(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                parts[3],
                parts[4]
        );
    }
}

