package com.example.buivutan_fitcms_blog.thiGK.ntu64132105.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.buivutan_fitcms_blog.thiGK.ntu64132105.model.Page;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {

    private List<Page> pages = new ArrayList<>();
    private Long nextPageId = 4L;

    public PageController() {
        pages.add(new Page(1L, "Home", "Home Page", "Content of Home Page", null, ""));
        pages.add(new Page(2L, "About Us", "About Us Page", "Content of About Us", null, ""));
        pages.add(new Page(3L, "Contact", "Contact Page", "Content of Contact", null, ""));
    }

    @GetMapping("/page/all")
    public String pageList(Model model) {
        model.addAttribute("pages", pages);
        return "page/page-list";
    }

    @GetMapping("/page/new")
    public String pageAddnew(Model model) {
        return "page/page-addnew";
    }

    @PostMapping("/page/new")
    public String pageSave(
            @RequestParam("pageName") String pageName,
            @RequestParam("keyword") String keyword,
            @RequestParam("content") String content,
            @RequestParam(value = "parentPageId", required = false) Long parentPageId,
            @RequestParam("thumbnailImage") String thumbnailImage
    ) {
        Page newPage = new Page(nextPageId++, pageName, keyword, content, parentPageId, thumbnailImage);
        pages.add(newPage);
        return "redirect:/page/all";
    }


    @GetMapping("/page/view/{id}")
    public String pageView(@PathVariable("id") Long id, Model model) {
        Page page = pages.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("page", page);
        return "page/page-view";
    }

    @GetMapping("/page/delete/{id}")
    public String pageDelete(@PathVariable("id") Long id) {
        pages.removeIf(p -> p.getId().equals(id));
        return "redirect:/page/all";
    }

    @GetMapping("/page/edit/{id}")
    public String pageEdit(@PathVariable("id") Long id, Model model) {
        Page page = pages.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("page", page);
        return "page/page-edit";
    }

    @PostMapping("/page/edit/{id}")
    public String pageUpdate(
            @PathVariable("id") Long id,
            @RequestParam("pageName") String pageName,
            @RequestParam("keyword") String keyword,
            @RequestParam("content") String content,
            @RequestParam(value = "parentPageId", required = false) Long parentPageId,
            @RequestParam("thumbnailImage") String thumbnailImage
    ) {
        Page page = pages.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        if (page != null) {
            page.setPageName(pageName);
            page.setKeyword(keyword);
            page.setContent(content);
            page.setParentPageId(parentPageId);
            page.setThumbnailImage(thumbnailImage);
        }
        return "redirect:/page/all";
    }
}