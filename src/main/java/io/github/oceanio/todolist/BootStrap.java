package io.github.oceanio.todolist;

public class BootStrap {
    public static void main(String[] args){
        String appName = "TodoList";
        TodoCliMain todoCliMain = new TodoCliMain();
        todoCliMain.todomain(appName);
    }
}
