package com.spring.elasticsearch.controller;

import com.spring.elasticsearch.manager.ElasticsearchManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class JsonController {

    @Autowired
    ElasticsearchManager es;


    @RequestMapping(value = "/string.json", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String bar() {
        //String data="[{\"label\":\"Master Course\",\"value\":2807},{\"label\":\"Affiliates\", \"value\":1072},{\"label\":\"Ebook\", \"value\": 972}]";
        try {
            return es.countByGradoDificultad1();
        }catch (Exception e){
            return "404";
        }
    }


    @RequestMapping(value = "/dificulty.htm",method = RequestMethod.GET)
    public String initForm(ModelMap model){
        return "dificulty";
    }
}
