package com.example.buivutan_fitcms_blog.thiGK.ntu64132105.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.buivutan_fitcms_blog.thiGK.ntu64132105.model.Post;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    private List<Post> posts = new ArrayList<>();
    private Long nextPostId = 4L;

    public PostController() {
        posts.add(new Post(1L, "First Post", "Content of First Post", 1L));
        posts.add(new Post(2L, "Second Post", "Content of Second Post", 2L));
        posts.add(new Post(3L, "Third Post", "Content of Third Post", 1L));
    }

    @GetMapping("/post/all")
    public String postList(Model model) {
        model.addAttribute("posts", posts);
        return "post/post-list";
    }

    @GetMapping("/post/new")
    public String postAddnew(Model model) {
        return "post/post-addnew";
    }

    @PostMapping("/post/new")
    public String postSave(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("categoryId") Long categoryId
    ) {
        Post newPost = new Post(nextPostId++, title, content, categoryId);
        posts.add(newPost);
        return "redirect:/post/all";
    }

    @GetMapping("/post/view/{id}")
    public String postView(@PathVariable("id") Long id, Model model) {
        Post post = posts.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("post", post);
        return "post/post-view";
    }

    @GetMapping("/post/delete/{id}")
    public String postDelete(@PathVariable("id") Long id) {
        posts.removeIf(p -> p.getId().equals(id));
        return "redirect:/post/all";
    }

    @GetMapping("/post/edit/{id}")
    public String postEdit(@PathVariable("id") Long id, Model model) {
        Post post = posts.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("post", post);
        return "post/post-edit";
    }

    @PostMapping("/post/edit/{id}")
    public String postUpdate(
            @PathVariable("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("categoryId") Long categoryId
    ) {
        Post post = posts.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        if (post != null) {
            post.setTitle(title);
            post.setContent(content);
            post.setCategoryId(categoryId);
        }
        return "redirect:/post/all";
    }
}