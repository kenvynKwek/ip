package tasks;

public class ToDos extends Task {
    public ToDos (String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    public String toString() {
        return "T | " + super.toString();
    }
}
