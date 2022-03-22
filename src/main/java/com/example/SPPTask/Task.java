package com.example.SPPTask;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;


@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    final private LocalTime startTime = LocalTime.now();
    private String team;    //Team and assigne could be switched to objects.
    private String assigne;
    private LocalTime endTime;

    @OneToMany(targetEntity = Task.class, fetch = FetchType.EAGER)
    private List<Task> subTasks;
    private boolean finished;
    private Long mainTaskID;
    private Duration duration;

    public Task (){
    }

    public Task (String name, String team, String assigne){
        this.name = name;
        this.team = team;
        this.assigne = assigne;
    }

    public Task (String name, String team, String assigne, Long mainTaskID){
        this.name = name;
        this.team = team;
        this.assigne = assigne;
        this.mainTaskID = mainTaskID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getAssigne() {
        return assigne;
    }

    public void setAssigne(String assigne) {
        this.assigne = assigne;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
        if(this.isFinished()){
            setEndTime(LocalTime.now());
        }
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public List<Task> getSubTasks() {
        return subTasks;
    }

    public Long getMainTaskID() {
        return mainTaskID;
    }

    public void setMainTaskID(Long mainTaskID) {
        this.mainTaskID = mainTaskID;
    }

    public void addSubTask(Task task){
        subTasks.add(task);
    }

    public void deleteSubTask(Task task){
        subTasks.remove(task);
    }

    public void calculateTimeTaken(){
        setDuration(Duration.between(getStartTime(), getEndTime()));
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}