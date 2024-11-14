package org.example.springhateoas;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springhateoas.entity.Post;
import org.example.springhateoas.repository.PostRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;

import java.net.URL;
import java.util.List;

@SpringBootApplication
public class SpringHateoasApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringHateoasApplication.class, args);
    }


    @Bean
    ApplicationRunner applicationRunner(PostRepository postRepository, ObjectMapper objectMapper) {
        return args -> {
            URL url = new URL("https://jsonplaceholder.typicode.com/posts");
            List<Post> posts = objectMapper.readValue(url, new TypeReference<>() {});
             postRepository.saveAll(posts);
        };
    }


    @Bean(name = "postPagedResourcesAssembler")
   public PagedResourcesAssembler<Post> postPagedResourcesAssembler() {
        return new PagedResourcesAssembler<>(new HateoasPageableHandlerMethodArgumentResolver(), null);
    }

}
