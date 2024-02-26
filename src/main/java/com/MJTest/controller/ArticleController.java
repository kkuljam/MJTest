package com.MJTest.controller;

import com.MJTest.dao.ArticleDao;
import com.MJTest.dto.ArticleForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j // 로깅을 위한 어노테이션
@Controller
public class ArticleController {
    @Autowired
    private ArticleDao articleDao;

    @GetMapping("/articles/new")
    public String newArticleController(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info(form.toString());
        //System.out.println(form.toString());
        ArticleForm saved=articleDao.createArticle(form);
        System.out.println(saved);
//        log.info(result);
        //System.out.println(result);
        return "redirect:/articles/"+saved.getBno();
    }

    @GetMapping("/articles/{bno}")
    public String show(@PathVariable Long bno, Model model){
        log.info("bno"+bno);
        ArticleForm form=articleDao.show(bno);
        model.addAttribute("article",form);
        return "articles/show";
    }
    @GetMapping("/articles")
    public String index(Model model){
        List<ArticleForm>formList=articleDao.index();
        model.addAttribute("articleList",formList);
        return "articles/index";
    }
    @GetMapping("/articles/{bno}/edit")
    public String edit(@PathVariable long bno, Model model){
        System.out.println(bno);
        ArticleForm form=articleDao.findById(bno);
        System.out.println(form);
        //2. 응답 결과 뷰 템플릿에게 보낼 준비 model
        model.addAttribute("article",form);
        return "articles/edit";
    }
    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        ArticleForm updated=articleDao.update(form);
        return "redirect:/articles/"+updated.getBno();
    }
    @GetMapping("/articles/{bno}/delete")
    public String delete(@PathVariable long bno){
        boolean result=articleDao.delete(bno);
        return "redirect:/articles";
    }





}
