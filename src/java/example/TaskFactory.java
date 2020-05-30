package example;

public class TaskFactory {

    public Task task() {
        return new TaskImpl();
    }
}
