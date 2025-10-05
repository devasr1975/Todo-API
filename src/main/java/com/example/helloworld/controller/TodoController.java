package com.example.helloworld.controller;

import com.example.helloworld.models.Todo;
import com.example.helloworld.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;


    @PostMapping("/create")
    ResponseEntity<Todo> createTodo(@RequestBody Todo todo){
        return new ResponseEntity<>(todoService.createTodo(todo), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<Todo> getTodoById(@PathVariable Integer id){
        try{
            Todo todofound =todoService.getTodoByID(id);
            return  new ResponseEntity<>(todofound,HttpStatus.OK    );
        }
        catch (RuntimeException e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    ResponseEntity<List<Todo>> getAllTodo(){
        return new ResponseEntity<>(todoService.getAllTodo(),HttpStatus.OK);
    }

    @PutMapping("/update")
    ResponseEntity<Todo> updateTodo(@RequestBody Todo todo){
        return new ResponseEntity<>(todoService.updateTodo(todo),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void deleteTodoById(@PathVariable Integer id){
        todoService.deleteTodoById(id);
    }
}

