package maurotuzzolino.u6_w1_d4_compito.controllers;


import jakarta.validation.Valid;
import maurotuzzolino.u6_w1_d4_compito.entities.Post;
import maurotuzzolino.u6_w1_d4_compito.payloads.NewPostPayload;
import maurotuzzolino.u6_w1_d4_compito.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // 1. GET /posts
    @GetMapping("/page")
    public Page<Post> getPostsPaginated(Pageable pageable) {
        return postService.getPostsPaginated(pageable);
    }

    // 2. POST /posts
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody @Valid NewPostPayload body) {
        return postService.save(body);
    }

    // 3. GET /posts/{id}
    @GetMapping("/{id}")
    public Post getPostById(@PathVariable long id) {
        return postService.findById(id);
    }

    // 4. PUT /posts/{id}
    @PutMapping("/{id}")
    public Post updatePost(@PathVariable long id, @RequestBody @Valid NewPostPayload body) {
        return postService.updateById(id, body);
    }

    // 5. DELETE /posts/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable long id) {
        postService.deleteById(id);
    }
}