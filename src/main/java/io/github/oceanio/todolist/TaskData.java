package io.github.oceanio.todolist;

import java.util.Objects;
import java.util.UUID;

public class TaskData {
    private final UUID taskUUID;
    private final String taskName;
    private final String taskContents;
    private TaskCondition condition;
    public enum TaskCondition{
        COMPLETE,
        PROGRESS,
        STOP
    }

    public TaskData(UUID taskUUID,String taskName, String taskContents, TaskCondition condition) {
        this.taskName = taskName;
        this.taskUUID = taskUUID;
        this.taskContents = taskContents;
        this.condition = condition;

    }
    public TaskData(UUID taskUUID, String taskName, String taskContents) {
        this.taskUUID = taskUUID;
        this.taskName = taskName;
        this.taskContents = taskContents;
        this.condition = TaskCondition.PROGRESS;
    }

    public UUID getUUID(){
        return taskUUID;
    }
    public String getTaskName(){
        return taskName;
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
    public boolean hasTaskName(String name){
        return Objects.equals(name, taskName);
    }
}
