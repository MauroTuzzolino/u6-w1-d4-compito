package maurotuzzolino.u6_w1_d4_compito.controllers;

import jakarta.validation.Valid;
import maurotuzzolino.u6_w1_d4_compito.entities.Author;
import maurotuzzolino.u6_w1_d4_compito.payloads.NewAuthorPayload;
import maurotuzzolino.u6_w1_d4_compito.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    // 1. GET /authors
    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.findAll();
    }

    // 2. POST /authors
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author createAuthor(@RequestBody @Valid NewAuthorPayload payload) {
        return authorService.save(payload);
    }

    // 3. GET /authors/{id}
    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable long id) {
        return authorService.findById(id);
    }

    // 4. PUT /authors/{id}
    @PutMapping("/{id}")
    public Author updateAuthor(@PathVariable long id, @RequestBody @Valid NewAuthorPayload payload) {
        return authorService.updateById(id, payload);
    }

    // 5. DELETE /authors/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable long id) {
        authorService.deleteById(id);
    }

    @PostMapping("/upload-avatar/{id}")
    public Author uploadAvatar(@PathVariable long id, @RequestParam("file") MultipartFile file) {
        return authorService.uploadAvatar(id, file);
    }
}