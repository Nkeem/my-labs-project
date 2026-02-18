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

    public void saveToFile(String outputPath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {

            bw.write("id,name,status,species,gender\n");

            for (Character c : stack) {
                bw.write(c.toCSV());
                bw.newLine();
            }

            System.out.println("File saved.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Create
    public void create(Character character) {
        stack.push(character);
        System.out.println("Character added.");
    }

    // Read
    public void readAll() {
        for (Character c : stack) {
            System.out.println(c.toCSV());
        }
    }

    // Read by ID
    public Character readById(int id) {
        for (Character c : stack) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }


    //Update
    public void update(int id, Character updatedCharacter) {

        Stack<Character> tempStack = new Stack<>();
        boolean found = false;

        while (!stack.isEmpty()) {
            Character current = stack.pop();

            if (current.getId() == id) {

                tempStack.push(updatedCharacter);
                found = true;
            } else {
                tempStack.push(current);
            }
        }

        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }

        if (found) {
            System.out.println("Character updated.");
        } else {
            System.out.println("Character not found.");
        }
    }

    // Delete
    public void delete(int id) {

        Stack<Character> tempStack = new Stack<>();
        boolean found = false;

        while (!stack.isEmpty()) {
            Character current = stack.pop();

            if (current.getId() == id) {
                found = true;
            } else {
                tempStack.push(current);
            }
        }

        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }

        if (found) {
            System.out.println("Character deleted.");
        } else {
            System.out.println("Character not found.");
        }
    }
}
