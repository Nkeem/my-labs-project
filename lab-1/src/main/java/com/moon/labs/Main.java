package com.moon.labs;

import com.moon.labs.service.CharacterService;

public class Main {
    public static void main(String[] args) {

        CharacterService service = new CharacterService();

        service.loadFromFile("characters.csv");
        //System.out.println(System.getProperty("user.dir"));
        service.saveReversedToFile("reversed_characters.csv");
    }
}
