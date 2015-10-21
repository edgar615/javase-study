package com.edgar.module.sys.repository.domain;

import javax.annotation.Generated;

/**
 * Task is a Querydsl bean type
 */
@Generated("com.mysema.query.codegen.BeanSerializer")
public class Task {

    private String assignee;

    private Integer assigneeId;

    private Long assigneeTime;

    private String comment;

    private Integer companyId;

    private Integer taskId;

    private String taskName;

    private String tracker;

    private Integer trackerId;

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Integer getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Integer assigneeId) {
        this.assigneeId = assigneeId;
    }

    public Long getAssigneeTime() {
        return assigneeTime;
    }

    public void setAssigneeTime(Long assigneeTime) {
        this.assigneeTime = assigneeTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTracker() {
        return tracker;
    }

    public void setTracker(String tracker) {
        this.tracker = tracker;
    }

    public Integer getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(Integer trackerId) {
        this.trackerId = trackerId;
    }

}

