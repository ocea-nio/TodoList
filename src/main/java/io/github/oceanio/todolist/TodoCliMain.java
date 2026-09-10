package io.github.oceanio.todolist;

import java.util.*;

public class TodoCliMain {
    public void todomain(String appName){
        //必須instance
        Map<String, TaskData> taskMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);


        System.out.println(appName + "へようこそ!");

        /*複数回ループするシステムにすることで，
         *複数回入力コマンドを実行可能にする
         * shatDown時にwhile breakで終了
         */

        mainLoop: while (true){
            System.out.println("使い方:");
            System.out.println("タスク追加=> task add, タスク検索=> task search, タスク完了=> task complete {taskName}");
            String command = scanner.next();
            String commandDefault = "task";
            if (!Objects.equals(command, commandDefault)){
                System.out.println("違う");
                return;
            }
            String action = scanner.next();
            switch (action) {
                case "add" -> {
                    System.out.println("追加するタスク名を入力(cancelで中止):");
                    scanner.nextLine();
                    String taskName = scanner.nextLine();
                    if(Objects.equals(taskName,"cancel")){
                        continue;
                    }
                    System.out.println("タスク内容を入力");
                    TaskData taskData = new TaskData(UUID.randomUUID(),scanner.nextLine());
                    taskMap.put(taskName, taskData);
                    System.out.println("完了しました");
                }
                case "search" -> {
                    System.out.println("検索するタスク名を入力:");
                    scanner.nextLine(); //改行が残ってそれが反応するため
                    String taskName = scanner.nextLine();
                    if(!(taskMap.containsKey(taskName))){
                        System.out.println("一致するものが見つかりませんでした");
                        System.out.println("類似するもの:");
                        for (String key: taskMap.keySet()){
                            //searchResultを使用予定
                        }
                    }

                }
                case "shatDown"-> {
                    System.out.println("終了します");
                    break mainLoop;
                }
            }
        }
    }
    private void searchResult(){
        //正規表現を使うのがmust?
    }
}
