package com.example.SPPTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/save")
     public Task saveTask(@RequestBody Task task){
        taskRepository.save(task);
        if(task.getMainTaskID() != null){
            Task taskToUpdate = taskRepository.findById(task.getMainTaskID()).get();
            taskToUpdate.addSubTask(task);
            taskRepository.save(taskToUpdate);
        }
        return task;
    }

    @GetMapping("/get/{id}")
    public Task getTask(@PathVariable Long id){
        return taskRepository.findById(id).get();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable Long id){
        taskRepository.delete(taskRepository.findById(id).get());
    }

    @PutMapping("/edit/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task){
        Task taskToBeUpdated = taskRepository.findById(id).get();
        taskToBeUpdated.setAssigne(task.getAssigne());
        taskToBeUpdated.setName(task.getName());
        taskToBeUpdated.setTeam(task.getTeam());

        try {
            int counter = 0;
            if (task.isFinished()) {
                if (taskToBeUpdated.getSubTasks().size() > 0) {
                    for (int i = 0; i < taskToBeUpdated.getSubTasks().size(); i++) {
                        if (taskToBeUpdated.getSubTasks().get(i).isFinished()) {
                            counter++;
                        }
                    }
                }
            }
            if (counter == taskToBeUpdated.getSubTasks().size()) {
                taskToBeUpdated.setFinished(task.isFinished());
                taskToBeUpdated.calculateTimeTaken();
            }
        } catch (NullPointerException e){
            taskToBeUpdated.setFinished(task.isFinished());
            taskToBeUpdated.calculateTimeTaken();
        }

        return taskRepository.save(taskToBeUpdated);
    }
}
