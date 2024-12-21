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

import org.apache.commons.lang3.ArrayUtils;

public class TaskManager {
    public static void main(String[] args) {
        System.out.println("Welcome to Task Manager");
        System.out.println("System will now read all the tasks from the task.csv file");
        final var tasks = loadTasks();
        showTasks(tasks);
        //Scanner scan = new Scanner(System.in);
        //saveTask(tasks);
    }

    private static String[][] loadTasks() {
        Path pathFile = Paths.get("tasks.csv");
        String[][] tasks = new String[0][];
        try {
            if (Files.exists(pathFile)) {
                tasks = listTo2dArr(Files.readAllLines(pathFile, StandardCharsets.UTF_8));
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

    private static void saveTask(ArrayList<String> list) {
        Path pathFile = Paths.get("tasks.csv");
        if (Files.exists(pathFile)) {

        }
    }

    private static String[][] listTo2dArr(List<String> list) {
        String[][] arr = new String[list.size()][3];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i).split(",");
        }
        return arr;
    }
    private static void showTasks(String[][] tasks)
    {
        for(String[] task : tasks)
        {
            System.out.println(String.join(",", task));
        }
    }
}