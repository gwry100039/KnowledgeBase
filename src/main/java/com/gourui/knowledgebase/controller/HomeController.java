package com.gourui.knowledgebase.controller;

import com.gourui.knowledgebase.utils.OracleSqlParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/getSqlAnalyzeResult")
    public String getSqlAnalyzeResult(@RequestParam("sql") String sql, Model model) {
        OracleSqlParser osp = new OracleSqlParser(sql);
        return "SqlAnalyzeResult";
    }
}
