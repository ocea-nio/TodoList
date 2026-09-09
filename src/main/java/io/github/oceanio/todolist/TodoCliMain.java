package io.github.oceanio.todolist;

import java.util.*;

public class TodoCliMain {
    public void todomain(String appName){
        Scanner scanner = new Scanner(System.in);
        System.out.println(appName + "へようこそ!");
        System.out.println("使い方:");
        System.out.println("タスク追加=> task add, タスク検索=> task search, タスク完了=> task complete {taskName}");
        String task = scanner.next();
        String commandDefault = "task";
        if (!Objects.equals(task, commandDefault)){
            System.out.println("違う");
            return;
        }
        String action = scanner.next();
        switch (action) {
            case "add" -> {
                Map<String,String> taskMap = new HashMap<>();
                System.out.println("追加するタスク名を入力:");
                String taskName = scanner.next();
                System.out.println("タスク内容を入力");
                taskMap.put(taskName, scanner.next());
            }
            case "search" ->{
                System.out.println("検索するタスク名を入力:");
            }
        }
    }
}
