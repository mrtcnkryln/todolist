package com.mertcan.todolist.Models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DependencyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "itemFromId")
    private TodoListItemModel itemFrom;

    @ManyToOne
    @JoinColumn(name = "itemToId")
    private TodoListItemModel itemTo;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createdAt;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TodoListItemModel getItemFrom() {
        return itemFrom;
    }

    public void setItemFrom(TodoListItemModel itemFrom) {
        this.itemFrom = itemFrom;
    }

    public TodoListItemModel getItemTo() {
        return itemTo;
    }

    public void setItemTo(TodoListItemModel itemTo) {
        this.itemTo = itemTo;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
