package com.example.helloworld.service;

import com.example.helloworld.Repository.TodoRepository;
import com.example.helloworld.models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public Todo createTodo(Todo todo){
        return todoRepository.save(todo);
    }

    public Todo getTodoByID(Integer id){
        return todoRepository.findById(id).orElseThrow(()-> new RuntimeException("Todo not found"));
    }

    public List<Todo> getAllTodo() {
        return todoRepository.findAll();
    }

    public Todo updateTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public void deleteTodoById(Integer id) {
        todoRepository.deleteById(id);
    }
}
