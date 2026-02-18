package com.moon.labs;

import com.moon.labs.model.Character;
import com.moon.labs.service.CharacterService;

public class Main {
    public static void main(String[] args) {

        CharacterService service = new CharacterService();

        service.loadFromFile("characters.csv");

        service.delete(12);

        service.create(new Character(21, "Cosmo Beth", "Alive", "Human", "Female"));

        System.out.println(service.readById(21));

        service.update(21, new Character(21, "Cosmo Beth С137", "Alive", "Human", "Female"));

        service.delete(12);

        service.saveToFile("updated_characters.csv");



    }
}
