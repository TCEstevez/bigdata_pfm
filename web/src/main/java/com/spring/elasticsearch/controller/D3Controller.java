package com.spring.elasticsearch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.elasticsearch.manager.ElasticsearchManager;
import com.spring.elasticsearch.model.domain.Exam;
import com.spring.elasticsearch.model.domain.Question;
import com.spring.elasticsearch.validador.ExamValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class D3Controller {

    public static String BUSCAR="buscar";

    ExamValidator examValidator;

    @Autowired
    public D3Controller(ExamValidator examValidator) {
        this.examValidator = examValidator;
    }

    @Autowired
    ElasticsearchManager es;


    @RequestMapping(value = "/grade.htm",method = RequestMethod.GET)
    public String initForm(ModelMap model){
        return "grade";
    }


    @RequestMapping(value = "/grade.htm",method = RequestMethod.POST)
    public String processSubmit(
            @RequestParam(value = "accion") String accion, @ModelAttribute("examen") Exam ex,
            BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response,ModelMap model) {




        if (accion.equalsIgnoreCase(BUSCAR)) {
            examValidator.tagsValidate(ex.getQuestions().get(0).getTags(), result, 0);
            if(!result.hasErrors()) {
                status.setComplete();
                List<String> listTags=convertToList(ex.getQuestions().get(0).getTags());
                String tagsString=convertJson(listTags);

                model.addAttribute("tagsLong",convertJson(es.countByTags(listTags)));
                model.addAttribute("tagsString",tagsString);

            }
            return "grade";

        }
        return "grade";

    }

    @ModelAttribute("examen")
    public Exam obtenerExamen(){
        Exam ex = new Exam();
        ex.getQuestions().add(new Question());

        return ex;
    }


    public String convertJson(Object lista) {

        ObjectMapper mapper = new ObjectMapper();

        String json = "";
        try {
            json = mapper.writeValueAsString(lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
         return json;
    }


    public List<String> convertToList(String tag)

    {
        List<String> tags = new ArrayList<String>();
        tags.add("");
        if (!tag.trim().equalsIgnoreCase("")) {
           tags.addAll(Arrays.asList(tag.split(",")));
        }
        return tags;
    }





    }


