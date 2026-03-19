package spring.crud.crud.controllers;

import spring.crud.crud.models.Todo;
import spring.crud.crud.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*") // Allows your Frontend to talk to this Backend
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    // 1. READ ALL
    @GetMapping
    public List<Todo> getAll() {
        return todoRepository.findAll();
    }

    // 2. READ ONE (Optional, useful for details)
    @GetMapping("/{id}")
    public Todo getById(@PathVariable Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
    }

    // 3. CREATE
    @PostMapping
    public Todo create(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    // 4. UPDATE
    @PutMapping("/{id}")
    public Todo update(@PathVariable Long id, @RequestBody Todo todoDetails) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        // Update the fields from the request body
        todo.setTask(todoDetails.getTask());
        todo.setCompleted(todoDetails.isCompleted());

        return todoRepository.save(todo);
    }

    // 5. DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!todoRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete: Todo not found with id: " + id);
        }
        todoRepository.deleteById(id);
    }
}