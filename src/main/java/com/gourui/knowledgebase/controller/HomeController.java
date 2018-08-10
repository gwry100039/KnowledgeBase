package com.gourui.knowledgebase.controller;

import com.gourui.knowledgebase.mapper.DataCaliberMapper;
import com.gourui.knowledgebase.mapper.OrgMapper;
import com.gourui.knowledgebase.mapper.WorkerMapper;
import com.gourui.knowledgebase.utils.sqlparser.OracleSqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {

    @Autowired
    private DataCaliberMapper dataCaliberMapper;

    @Autowired
    private OrgMapper orgMapper;

    @Autowired
    private WorkerMapper workerMapper;

    @RequestMapping("/requirements")
    public String requirementInput(Model model) {
        model.addAttribute("orgList", orgMapper.selectList());
        model.addAttribute("workerList", workerMapper.selectList());
        model.addAttribute("dataCaliberList", dataCaliberMapper.selectList());
        return "/RequirementInput";
    }

    @RequestMapping("/getSqlAnalyzeResult")
    public String getSqlAnalyzeResult(@RequestParam("sql") String sql, Model model) {
        OracleSqlParser osp = new OracleSqlParser(sql);
        osp.init();
        model.addAttribute("stMapList", osp.getStMapList());
        return "ajax/SqlAnalyzeResult";
    }

    @RequestMapping("/saveDataCaliber")
    public String saveDataCaliber(
            @RequestParam("requirement_id") String requirement_id,
            @RequestParam("requirement_name") String requirement_name,
            @RequestParam("worker_name") String worker_name,
            @RequestParam("extractor_name") String extractor_name,
            @RequestParam("department_name") String department_name,
            @RequestParam("requirement_desc") String requirement_desc,
            @RequestParam("comments") String comments,
            @RequestParam("sql") String sql) {
        /*System.out.println(requirement_id);
        System.out.println(requirement_name);
        System.out.println(worker_name);
        System.out.println(extractor_name);
        System.out.println(department_name);
        System.out.println(comments);
        System.out.println(sql);*/

        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String strDate2 = dtf2.format(LocalDateTime.now());

        dataCaliberMapper.add(strDate2, requirement_id, requirement_name, worker_name, extractor_name, department_name, requirement_desc, comments, sql);
        return "redirect:/requirements";
    }
}
