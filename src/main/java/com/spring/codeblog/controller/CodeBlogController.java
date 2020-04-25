package com.spring.codeblog.controller;

import com.spring.codeblog.model.Post;
import com.spring.codeblog.service.CodeBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CodeBlogController {

    @Autowired
    CodeBlogService codeBlogService;

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public ModelAndView getPosts(){

        ModelAndView mv = new ModelAndView("posts");
        List<Post> posts = codeBlogService.findAll();
        mv.addObject("posts", posts);
        return mv;
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public ModelAndView getPostsDetail(@PathVariable("id") long id){

        ModelAndView mv = new ModelAndView("postsDetail");
        Post post = codeBlogService.findById(id);
        mv.addObject("post", post);
        return mv;
    }

    @RequestMapping(value = "/newpost", method = RequestMethod.GET)
    public String getPostForm(){

        return "postForm";
    }

    @RequestMapping(value = "/newpost", method = RequestMethod.POST)
    public String savePost(@Valid Post post, BindingResult result, RedirectAttributes attributes){

        if(result.hasErrors()){
            /*
            *  Retorna mensagem de erro caso alguma propriedade da model esteja inválida conforme definição.
            *  A variável "mensagem" será passada como parâmetro para o formulário "validacaoMensagem.html"
            * */
            attributes.addFlashAttribute("mensagem", "Verifique se os campos obrigatórios foram preenchidos!");
            return "redirect:/newpost";
        }

        post.setData(LocalDate.now());
        codeBlogService.save(post);
        return "redirect:/posts";
    }
}
