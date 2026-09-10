package io.github.oceanio.todolist;

import java.util.UUID;

public class TaskData {
    private final UUID taskUUID;
    private final String taskContents;
    private TaskCondition condition;
    public enum TaskCondition{
        COMPLETE,
        PROGRESS,
        STOP
    }

    public TaskData(UUID taskUUID, String taskContents, TaskCondition condition) {
        this.taskUUID = taskUUID;
        this.taskContents = taskContents;
        this.condition = condition;

    }
    public TaskData(UUID taskUUID, String taskContents) {
        this.taskUUID = taskUUID;
        this.taskContents = taskContents;
        this.condition = TaskCondition.PROGRESS;
    }

    public UUID getUUID(){
        return taskUUID;
    }
    public String getContents(){
        return  taskContents;
    }
    public TaskCondition getCondition(){
        return condition;
    }
    public void setCondition(TaskCondition c){
        condition = c;
    }
}
