package com.TranquilMind.dto;

import com.TranquilMind.model.Course;
import com.TranquilMind.model.Task;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDto {

    Long taskId;

    Integer weekNo;

    Integer taskNo;

    String title;

    String description;

    String link;

    public static Task toTask(TaskDto taskDto, Course  course) {
        Task newTask = new Task();
        newTask.setWeekNo(taskDto.getWeekNo());
        newTask.setTaskNo(taskDto.getTaskNo());
        newTask.setTitle(taskDto.getTitle());
        newTask.setDescription(taskDto.getDescription());
        newTask.setLink(taskDto.getLink());
        newTask.setCourse(course);
        return newTask;
    }
}
