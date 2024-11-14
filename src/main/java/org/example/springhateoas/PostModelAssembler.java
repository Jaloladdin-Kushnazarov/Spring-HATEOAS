package org.example.springhateoas;

import org.example.springhateoas.controller.PostController;
import org.example.springhateoas.entity.Post;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class PostModelAssembler implements RepresentationModelAssembler<Post, EntityModel<Post>> {
    @Override
    public EntityModel<Post> toModel(Post post) {
        Link selfLink = linkTo(methodOn(PostController.class).getPostById(post.getId())).withSelfRel();
        Link postsLink = linkTo(methodOn(PostController.class).getPosts()).withRel("bu postlarni linki(all)");
         return EntityModel.of(post, selfLink, postsLink);
    }

    @Override
    public CollectionModel<EntityModel<Post>> toCollectionModel(Iterable<? extends Post> entities) {
        List<EntityModel<Post>> entityModels = new ArrayList<>();
        entities.forEach(post -> entityModels.add(toModel(post)));
        Link postsLink = linkTo(methodOn(PostController.class).getPosts()).withRel("bu postlarni linki(all)");
        return CollectionModel.of(entityModels, postsLink);
    }
}
