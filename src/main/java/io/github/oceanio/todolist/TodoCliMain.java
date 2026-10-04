package io.github.oceanio.todolist;

import java.util.*;

public class TodoCliMain {
    private final String appName;
    private final TaskLoader loader;
    public TodoCliMain(String appName,TaskLoader loader){
        this.appName = appName;
        this.loader = loader;
    }
    public void todoMain(){
        //必須instance
        List<TaskData> taskList = loader.readTable(appName);
        Scanner scanner = new Scanner(System.in);


        System.out.println(appName + "へようこそ!");

        /*複数回ループするシステムにすることで，
         *複数回入力コマンドを実行可能にする
         * shatDown時にwhile breakで終了
         */

        mainLoop: while (true){
            System.out.println("使い方:");
            System.out.println(
                    "タスク追加=> task add, タスク検索=> task search, タスク完了=> task complete {taskName}, 終了=> task shatDown,\n" +
                    "一覧検索=> task list"
            );
            String command = scanner.next();
            if (!command.equals("task")){return;}
            String action = scanner.next();
            switch (action) {
                case "add" -> {
                    System.out.println("追加するタスク名を入力(cancelで中止):");
                    scanner.nextLine();
                    String taskName = scanner.nextLine();
                    if (contains(taskList,taskName)){
                        System.out.println("同名タスクが存在しています。\n違う名前にしてください。");
                        continue;
                    }
                    if(Objects.equals(taskName,"cancel")){continue;}
                    System.out.println("タスク内容を入力:");
                    TaskData taskData = new TaskData(UUID.randomUUID(),taskName,scanner.nextLine());
                    taskList.add(taskData);
                    System.out.println("完了しました");
                }
                case "search" -> {
                    System.out.println("検索するタスク名を入力:");
                    scanner.nextLine(); //改行が残ってそれが反応するため
                    String taskName = scanner.nextLine();
                    TaskData matchContents;
                    if(!(contains(taskList,taskName))){
                        System.out.println("一致するものが見つかりませんでした");
                        List<String> results = searchSimilar(taskName, taskList);

                        if (results.isEmpty()){continue;}
                        System.out.println("類似するもの:");
                        for (String result: results){
                            System.out.println("││");
                            System.out.println("│├" + result);
                        }

                        System.out.println("指定するものを入力してください：(cancelで中止)");
                        String secondSearch = scanner.nextLine();

                        if (secondSearch.equals("cancel") || !(contains(taskList,secondSearch))){
                            System.out.println("指定されたタスクが見つからなかった，またはキャンセルされました");
                            continue ;
                        }
                        matchContents = findTask(taskList,secondSearch);
                    }else matchContents =  findTask(taskList,taskName);
                    if (matchContents == null){continue ;}
                    matchContentView(matchContents);
                }
                case "complete"-> {
                    System.out.println("完了したタスク名を入力");
                    scanner.nextLine();
                    String complete = scanner.nextLine();
                    if (!contains(taskList,complete)){
                        System.out.println("タスクが見つかりませんでした");
                        continue ;
                    }
                    try {
                        findTask(taskList,complete).setCondition(TaskData.TaskCondition.COMPLETE);
                        System.out.println(complete + "を完了しました");
                    }catch (NullPointerException e){
                        System.out.println(e);
                    }
                }
                case "shutDown"-> {
                    System.out.println("終了します");
                    break mainLoop;
                }
                case "list"-> {
                    System.out.println("リスト対象を選択(all,complete,progress,stop)");
                    String listMod = scanner.next();
                    listAction(listMod, taskList);
                }
            }
        }
    }
    private List<String> searchSimilar(String searchTask, List<TaskData> taskData){
        List<String> result = new ArrayList<>();
        List<String> keyList = taskData.stream().map(TaskData::getTaskName).toList();
        for (String key: keyList){
            if (searchTask.isEmpty() || key.isEmpty()) { continue; }
            if (searchTask.contains(key) || key.contains(searchTask)){
                System.out.println("add search");
                result.add(key);
            }
        }
        return result;
    }

    private void matchContentView(TaskData matchContents){
        System.out.println("一致するものが見つかりました");
        System.out.println("タスク内容: " + matchContents.getContents() + " ,進捗状況: " + matchContents.getCondition().toString());
    }

    private boolean contains(List<TaskData> taskDataList,String key){
        for (TaskData task : taskDataList) {
            if (task.hasTaskName(key)) {
                return true;
            }
        }
        return false;
    }

    private TaskData findTask(List<TaskData> taskDataList,String searchKey){
        for (TaskData task : taskDataList) {
            if (task.hasTaskName(searchKey)) {
                return task;
            }
        }
        return null;
    }

    private void listAction(String modifier, List<TaskData> taskDataList){
        switch(modifier) {
            case "all"->{
                for (String key : taskDataList.stream().map(TaskData::getTaskName).toList()){
                    System.out.println("││");
                    System.out.println("│├" + key);
                }
            }
            case "progress"->
                    taskDataList.stream().filter(progress -> progress.getCondition() == TaskData.TaskCondition.PROGRESS).forEach(taskContent -> System.out.println(
                            "││\n" + "│├" + taskContent
                            ));
            case "complete"-> taskDataList.stream().filter(progress -> progress.getCondition() == TaskData.TaskCondition.COMPLETE).forEach(taskContent -> System.out.println(
                    "││\n" + "│├" + taskContent
            ));
            case "stop"-> taskDataList.stream().filter(progress -> progress.getCondition() == TaskData.TaskCondition.STOP).forEach(taskContent -> System.out.println(
                    "││\n" + "│├" + taskContent
            ));

        }
    }
}
