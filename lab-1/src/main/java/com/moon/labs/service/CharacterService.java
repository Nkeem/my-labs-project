package com.moon.labs.service;

import com.moon.labs.model.Character;

import java.io.*;
import java.util.Stack;

public class CharacterService {

    private Stack<Character> stack = new Stack<>();

    public void loadFromFile(String fileName) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String line;
            br.readLine(); // пропустить header

            while ((line = br.readLine()) != null) {
                Character character = Character.fromCSV(line);
                stack.push(character);
            }

            System.out.println("Characters loaded: " + stack.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveReversedToFile(String outputPath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {

            bw.write("id,name,status,species,gender\n");

            while (!stack.isEmpty()) {
                Character character = stack.pop();
                bw.write(character.toCSV());
                bw.newLine();
            }

            System.out.println("Reversed file created.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
