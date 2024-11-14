package org.example.springhateoas.repository;

import org.example.springhateoas.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}