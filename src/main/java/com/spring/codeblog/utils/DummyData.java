package com.spring.codeblog.utils;

import com.spring.codeblog.model.Post;
import com.spring.codeblog.repository.CodeBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DummyData {

    @Autowired
    CodeBlogRepository codeBlogRepository;

    @PostConstruct
    public void savePosts() {
        List<Post> posts = new ArrayList<Post>();
        Post post1 = new Post();
        post1.setAutor("Carlos Fabiano");
        post1.setData(LocalDate.now());
        post1.setTitulo("Jesus");
        post1.setTexto("Jesus");

        Post post2 = new Post();
        post2.setAutor("Carlos Fabiano");
        post2.setData(LocalDate.now());
        post2.setTitulo("Jesus");
        post2.setTexto("Jesus");

        posts.add(post1);
        posts.add(post2);

        for (Post post : posts) {
            Post postSaved = codeBlogRepository.save(post);
            System.out.println(post.getId());
        }
    }
}
