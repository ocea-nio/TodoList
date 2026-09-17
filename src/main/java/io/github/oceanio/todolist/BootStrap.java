package io.github.oceanio.todolist;

public class BootStrap {
    public static void main(String[] args){
        String appName = "TodoList";
        TaskLoader loader = new TaskLoader();
        TodoCliMain todoCliMain = new TodoCliMain(appName,loader);
        todoCliMain.todoMain();
        loader.closeConnection();
    }
}
