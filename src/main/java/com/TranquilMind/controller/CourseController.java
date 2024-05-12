package com.TranquilMind.controller;

import com.TranquilMind.dto.CourseDto;
import com.TranquilMind.model.Course;
import com.TranquilMind.model.Task;
import com.TranquilMind.service.CourseService;
import com.TranquilMind.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/course")
@Tag(name = "Course", description = "API regarding course functionalities")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TaskService taskService;

    /**
     * Add a new course.
     *
     * @param courseDto The details of the new course.
     * @return The newly added course.
     */
    @Operation(summary = "Add a new course", description = "Add a new course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course added successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/admin/add-course")
    public ResponseEntity<?> addCourse(@RequestBody CourseDto courseDto){
        return new ResponseEntity<>(courseService.addCourse(courseDto), HttpStatus.CREATED);
    }

    /**
     * Update an existing course.
     *
     * @param courseDto The updated details of the course.
     * @return The updated course information.
     */
    @Operation(summary = "Update an existing course", description = "Update an existing course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course updated successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    @PutMapping("/admin/update-course")
    public ResponseEntity<?> updateCourse(@RequestBody CourseDto courseDto){
        return new ResponseEntity<>(courseService.updateCourse(courseDto),HttpStatus.OK);
    }

    /**
     * Add a new task.
     *
     * @param task The details of the new task.
     * @return The newly added task.
     */
    @Operation(summary = "Add a new task", description = "Add a new task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task added successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/admin/add-task")
    public ResponseEntity<?> addTask(@RequestBody Task task){
        return new ResponseEntity<>(taskService.addTask(task),HttpStatus.OK);
    }

    /**
     * Edit an existing task.
     *
     * @param task The updated details of the task.
     * @return The updated task information.
     */
    @Operation(summary = "Edit an existing task", description = "Edit an existing task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @PutMapping("/admin/update-task")
    public ResponseEntity<?> editTask(@RequestBody Task task){
        return new ResponseEntity<>(taskService.editTask(task),HttpStatus.OK);
    }

    /**
     * Retrieve all courses.
     *
     * @return A list of all courses.
     */
    @Operation(summary = "Get all courses", description = "Retrieve all courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/get-courses")
    public ResponseEntity<?> getCourses(){
        return new ResponseEntity<>(courseService.getAllCourses(),HttpStatus.OK);
    }

    /**
     * Get details of a course by ID.
     *
     * @param id The ID of the course.
     * @return The details of the course.
     */
    @Operation(summary = "Get course by ID", description = "Get details of a course by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    @GetMapping("/get-course/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id){
        CourseDto courseDto = courseService.getCourseById(id);

        if(courseDto != null){
            return new ResponseEntity<>(courseDto,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
