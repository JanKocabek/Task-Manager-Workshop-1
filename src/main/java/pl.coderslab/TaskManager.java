package pl.coderslab;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {
        System.out.println("Welcome to Task Manager");
        System.out.println("System will now read all the tasks from the task.csv file");
        final var tasks = loadTasks();
        Scanner scan = new Scanner(System.in);
        boolean isEnd = false;
        menu();
        while (!isEnd) {
            System.out.print("Please enter your choice:");
            switch (scan.nextLine()) {
                case "add":
                    addTask(tasks);
                    break;
                case "remove":
                    deleteTask(tasks);
                    break;
                case "list":
                    showTasks(tasks);
                    break;
                case "exit":
                    isEnd = true;
                    break;
                case "?":
                    menu();
                    break;
                default:
                    System.out.println("Invalid option\nType valid option or \"?\" for menu");
                    break;
            }
            saveTask(tasks);
        }
    }

    private static void deleteTask(String[][] tasks) {

    }

    private static void addTask(String[][] tasks) {

    }

    private static void menu() {
        System.out.println();
        System.out.println("Welcome to Task Manager");
        System.out.println("1. Add a new task - add");
        System.out.println("2. Delete a task - remove");
        System.out.println("3. Show all tasks - list");
        System.out.println("4. Save and Exit - exit");
        System.out.println("#######################");
    }

    private static String[][] loadTasks() {
        Path pathFile = Paths.get("tasks.csv");
        String[][] tasks = new String[0][];
        try {
            if (Files.exists(pathFile)) {
                tasks = listTo2DArr(Files.readAllLines(pathFile, StandardCharsets.UTF_8));
                System.out.println("Task were successfully loaded from tasks.csv");
            } else {
                System.out.println("File tasks.csv not found!");
                System.out.println("Program will try create new file");
                Files.createFile(pathFile);
                System.out.println("File tasks.csv successfully created");
            }
            return tasks;
        } catch (IOException e) {
            System.err.println("Error while reading tasks.csv file");
            e.printStackTrace(System.err);
            System.err.println("Program will try create new file");
            try {
                Files.createFile(pathFile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("File tasks.csv successfully created");
        }
        return tasks;
    }

    private static void saveTask(String[][] tasks) {
        Path pathFile = Paths.get("tasks.csv");
        if (Files.exists(pathFile)) {
            ArrayList<String> tasksList = new ArrayList<>(Arrays.asList(convertToStrings(tasks)));
            try {
                Files.write(pathFile, tasksList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void showTasks(String[][] tasks) {
        for (String[] task : tasks) {
            System.out.println(String.join(",", task));
        }
    }

    private static String[][] listTo2DArr(List<String> list) {
        String[][] arr = new String[list.size()][3];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i).split(",");
        }
        return arr;
    }

    private static String[] convertToStrings(String[][] tasks) {
        String[] tasksInStr = new String[tasks.length];
        int i = 0;
        for (String[] task : tasks) {
            tasksInStr[i] = String.join(",", task);
            i++;
        }
        return tasksInStr;
    }

}