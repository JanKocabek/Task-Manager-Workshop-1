package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static pl.coderslab.ConsoleColors.*;

public class TaskManager {
    static Scanner scan;
    private static String[][] tasks;

    public static void main(String[] args) {
        System.out.println("Starting Task Manager 1.0");
        tasks = loadTasks();
        scan = new Scanner(System.in);
        boolean isEnd = false;
        displayMenu();
        do {
            System.out.printf( "\n%sPlease enter your choice:%s",BLUE_BOLD_BRIGHT, RESET);
            switch (scan.nextLine()) {
                case "add" -> addTask();
                case "remove" -> removeTask();
                case "show" -> showTasks();
                case "exit" -> isEnd = true;
                case "?" -> displayMenu();
                default -> System.out.println("Invalid option!");
            }
        } while (!isEnd);
        saveTask();
        scan.close();
    }

    private static String[][] loadTasks() {
        System.out.println("System will now read all the tasks from the task.csv file");
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

    private static void displayMenu() {
        System.out.printf("\n%s#  Welcome to the Task Manager  #\n", GREEN_BOLD_BRIGHT);
        System.out.print("#                               #\n");
        System.out.printf("#   %s1. Add a new task:  %sadd     %s#\n", BLUE_BOLD_BRIGHT, YELLOW_BOLD_BRIGHT, GREEN_BOLD_BRIGHT);
        System.out.printf("#   %s2. Remove a task:   %sremove  %s#\n", BLUE_BOLD_BRIGHT, YELLOW_BOLD_BRIGHT, GREEN_BOLD_BRIGHT);
        System.out.printf("#   %s3. Show all tasks:  %sshow    %s#\n", BLUE_BOLD_BRIGHT, YELLOW_BOLD_BRIGHT, GREEN_BOLD_BRIGHT);
        System.out.printf("#   %s4. Save and Exit:   %sexit    %s#\n", BLUE_BOLD_BRIGHT, YELLOW_BOLD_BRIGHT, GREEN_BOLD_BRIGHT);
        System.out.printf("#################################%s\n", RESET);
    }

    private static void showTasks() {
        int i = 0;
        System.out.println("\nTask List:");
        for (String[] task : tasks) {
            System.out.printf("%d. %s\n", i, String.join(",", task));
            i++;
        }
    }

    private static void addTask() {
        final var taskStrSize = 3;
        int taskNum = tasks.length + 1;
        String[] task = new String[taskStrSize];
        System.out.printf("\nAdding new task N.%d:\n", taskNum);
        System.out.print("Write the description of the task: ");
        task[0] = scan.nextLine();
        System.out.print("Write the deadline of the task -in the format YYYY-MM-DD: ");
        task[1] = " %s".formatted(scan.nextLine());
        System.out.print("Write \"true\" or \"false\" if task is important or not: ");
        while (!scan.hasNext("true") && !scan.hasNext("false")) {
            scan.nextLine();
            System.out.print("Invalid option - put only true or false: ");
        }
        task[2] = " %s".formatted(scan.nextLine());
        tasks = ArrayUtils.add(tasks, task);
        System.out.printf("Task  Number %d added\n", taskNum);
    }

    private static void removeTask() {
        System.out.println("\nWrite the number of tasks you want to remove from the list");
        System.out.print("if you don't know the number of tasks write \"?\" or \"exit\" to return to the main menu:");
        do {
            String input = scan.nextLine();
            if (NumberUtils.isDigits(input)) {
                int num = Integer.parseInt(input);
                if (num >= 0 && num < tasks.length) {
                    tasks = ArrayUtils.remove(tasks, num);
                    System.out.printf("Task N.%d deleted successfully", num);
                    break;
                }
                System.out.printf("In the list isn't task number %d, please try again:", num);
                continue;
            }
            if (input.equals("?")) {
                showTasks();
                System.out.print("Write the number of tasks you want to remove from the list: ");
                continue;
            }
            if (input.equals("exit")) break;
            System.out.println("Invalid input!");
            System.out.print("if you don't know the number of tasks write \"?\" or \"exit\" to return to the main menu: :");
        } while (true);
    }

    private static void saveTask() {
        Path pathFile = Paths.get("tasks.csv");
        if (Files.exists(pathFile)) {
            //ArrayList<String> tasksList = new ArrayList<>(Arrays.asList(convertToStrings()));

            try {
                Files.write(pathFile, tasksList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static String[][] listTo2DArr(List<String> list) {
        String[][] arr = new String[list.size()][3];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i).split(",");
        }
        return arr;
    }

    private static String[] convertToStrings() {
        String[] tasksInStr = new String[tasks.length];
        int i = 0;
        for (String[] task : tasks) {
            tasksInStr[i] = String.join(",", task);
            i++;
        }
        return tasksInStr;
    }

}