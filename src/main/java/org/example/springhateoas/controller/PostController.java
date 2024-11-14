package org.example.springhateoas.controller;

import lombok.RequiredArgsConstructor;
import org.example.springhateoas.PostModelAssembler;
import org.example.springhateoas.entity.Post;
import org.example.springhateoas.repository.PostRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostRepository postRepository;
    private final PostModelAssembler postModelAssembler;
    private final PagedResourcesAssembler<Post> pagedResourcesAssembler;

    public PostController(PostRepository postRepository, PostModelAssembler postModelAssembler,
                          @Qualifier("postPagedResourcesAssembler") PagedResourcesAssembler<Post> pagedResourcesAssembler) {
        this.postRepository = postRepository;
        this.postModelAssembler = postModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }


    //bu simple dget all + hateoas
    @GetMapping
    public CollectionModel<EntityModel<Post>> getPosts() {
        List<Post> posts = postRepository.findAll();
        return postModelAssembler.toCollectionModel(posts);
    }

    //bu page uchun mo'jallangan
    @GetMapping("/page")
    public PagedModel<EntityModel<Post>> getPostsPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(posts,postModelAssembler);

    }


    @GetMapping("/{id}")
    public EntityModel<Post> getPostById(@PathVariable Long id) {
        Post post = postRepository.findById(id).get();
        return postModelAssembler.toModel(post);
    }


    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

}
