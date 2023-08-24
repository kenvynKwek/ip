import java.util.InputMismatchException;
import java.util.Scanner;

public class DogeBot {
    private static Task[] tasks = new Task[100];
    private static int tasksCounter = 0;
    public static void main(String[] args) {
        String logo = "    ___\n"
                + " __/_  `.  .-\"\"\"-."         + "           |                      |             |   \n"
                + " \\_,` | \\-'  /   )`-')"      + "    _` |   _ \\    _` |   _ \\  __ \\    _ \\   __| \n"
                + "  \"\") `\"`    \\  ((`\"`"    + "    (   |  (   |  (   |   __/  |   |  (   |  |   \n"
                + " ___Y  ,    .'7 /|"            + "      \\__,_| \\___/  \\__, | \\___| _.__/  \\___/  \\__| \n"
                + "(_,___/...-` (_/_/"            + "                    |___/";

        System.out.println(logo + "\nHi ! I'm DogeBot \nHow may I help you today ?\n");
        Scanner sc = new Scanner(System.in);
        boolean loop = true;

        while (loop) {
            switch (sc.next().toLowerCase()) {
                case "bye":
                    loop = false;
                    break;
                case "list":
                    try {
                        list();
                    } catch (DogeBotException e) { // empty list
                        System.out.println(e.getMessage());
                    }
                    break;
                case "mark":
                    try {
                        mark(sc.nextInt() - 1);
                    } catch (InputMismatchException e) { // not 'int' datatype
                        sc.nextLine(); // absorb remaining words so 'default' block doesn't act up
                        System.out.println("Oops ! Integers only please :(");
                    } catch (DogeBotException e) { // no tasks added yet
                        System.out.println(e.getMessage());
                    }
                    break;
                case "unmark":
                    try {
                        unmark(sc.nextInt() - 1);
                    } catch (InputMismatchException e) { // not 'int' datatype
                        sc.nextLine(); // absorb remaining words so 'default' block doesn't act up
                        System.out.println("Oops ! Integers only please :(");
                    } catch (DogeBotException e) { // no tasks added yet
                        System.out.println(e.getMessage());
                    }
                    break;
                case "todo":
                    try {
                        todo(sc.nextLine()); // sc.nextLine() to get the remaining words
                    } catch (DogeBotException e) { // description blank
                        System.out.println(e.getMessage());
                    }
                    break;
                case "deadline":
                    try {
                        deadline(sc.nextLine());
                    } catch (DogeBotException e) { // description blank
                        System.out.println(e.getMessage());
                    }
                    break;
                case "event":
                    try {
                        event(sc.nextLine());
                    } catch (DogeBotException e) { // description blank
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Wuff, I'm not sure what that means :(");
            }
        }

        System.out.println("Bye~ See you again");
        sc.close();
    }

    public static void list() throws DogeBotException {
        if (tasksCounter == 0) throw new DogeBotException("Oops ! Your list is empty ! Try adding some tasks first c:");

        System.out.println("Stuff to do:");
        int i = 1;
        for (Task task : tasks) {
            if (task == null) break;
            System.out.println(i++ + "." + task.toString());
        }
    }

    public static void mark(int index) throws DogeBotException {
        if (tasksCounter == 0) throw new DogeBotException("Oops ! Try adding some tasks first c:");

        tasks[index].markTask(true);
        System.out.println("Good job on completing a task ! You deserve a cookie C:");
        System.out.println("\t" + tasks[index].toString());
    }

    public static void unmark (int index) throws DogeBotException {
        if (tasksCounter == 0) throw new DogeBotException("Oops ! Try adding some tasks first c:");

        tasks[index].markTask(false);
        System.out.println("Oh nyo, did someone make a mistake ?");
        System.out.println("\t" + tasks[index].toString());
    }

    public static void updateTasksCounter() {
        tasksCounter++;
        System.out.println("You now have " + tasksCounter + " task(s) in your list");
    }

    public static void todo(String words) throws DogeBotException {
        if (words.isBlank()) throw new DogeBotException("Oops ! The description of a todo cannot be empty :(");

        tasks[tasksCounter] = new ToDos(words);
        System.out.println("\t" + tasks[tasksCounter].toString());
        updateTasksCounter();
    }

    public static void deadline(String words) throws DogeBotException {
        if (words.isBlank()) throw new DogeBotException("Oops ! The description of a deadline cannot be empty :(");

        int split = words.indexOf("/");
        // substring w/o the spaces
        String taskDescription = words.substring(0, split - 1);
        String taskDeadline = words.substring(split + 4, words.length());

        tasks[tasksCounter] = new Deadline(taskDescription, taskDeadline);
        System.out.println("\t" + tasks[tasksCounter].toString());
        updateTasksCounter();
    }

    public static void event(String words) throws DogeBotException {
        if (words.isBlank()) throw new DogeBotException("Oops ! The description of an event cannot be empty :(");

        // substring w/o the spaces
        int startSplit = words.indexOf("/");
        String taskDesription = words.substring(0, startSplit - 1);
        int endSplit = words.indexOf("/", startSplit + 1); // find "/" after startSplit index
        String start = words.substring(startSplit + 6, endSplit - 1);
        String end = words.substring(endSplit + 4, words.length());

        tasks[tasksCounter] = new Event(taskDesription, start, end);
        System.out.println("\t" + tasks[tasksCounter].toString());
        updateTasksCounter();
    }
}