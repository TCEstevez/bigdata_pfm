package com.spring.elasticsearch.manager;


import com.spring.elasticsearch.model.domain.Exam;
import com.spring.elasticsearch.model.domain.ExamResponse;
import com.spring.elasticsearch.model.domain.Question;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class FindManager {
    @Autowired
    private ElasticsearchManager esManager;

    @Autowired
    private GeneratePdf generatePdf;


    public boolean searchExistFind(Question q, int numberfind) {
        int numberOfQuestion = Integer.parseInt(q.getNumber());
        double dificultyOfquestion = Double.parseDouble(q.getDificulty());
        double dificultyOfquestionto = Double.parseDouble(q.getDificultyto());
        String[] tags = null;
        if (!q.getTags().trim().equalsIgnoreCase("")) {
            tags = q.getTags().split(",");
        }
        boolean encontrado = false;
        if (tags == null || tags.length == 0) {
            encontrado = esManager.findByDificultyBetween(dificultyOfquestion, dificultyOfquestionto,numberOfQuestion);

        } else {
            encontrado = esManager.findByTagsInAndGradoDificultad1Between(tags, dificultyOfquestion, dificultyOfquestionto,numberOfQuestion);
        }
        return encontrado;


    }

    public boolean generatePDF(Exam ex,HttpServletResponse response) {
            int i=0;
            for (Question q : ex.getQuestions()) {
                 if(!searchExistFind( q,  i))
                     return false;
                 i++;
            }
            List<ExamResponse> list = new ArrayList<ExamResponse>();
            for (Question q : ex.getQuestions()) {
                List<ExamResponse> list1 = new ArrayList<ExamResponse>();
                int numberOfQuestion = Integer.parseInt(q.getNumber());
                double dificultyOfquestion = Double.parseDouble(q.getDificulty());
                double dificultyOfquestionto = Double.parseDouble(q.getDificultyto());
                String[] tags = null;
                if (!q.getTags().trim().equalsIgnoreCase("")) {
                    tags = q.getTags().split(",");
                }
                if (tags == null || tags.length == 0) {
                    list1 = esManager.findAndReturnByDificultyBetween(dificultyOfquestion,dificultyOfquestionto, numberOfQuestion);

                } else {
                    list1 = esManager.findAndReturnByTagsInAndGradoDificultad1Between(tags, dificultyOfquestion, dificultyOfquestionto,numberOfQuestion);
                }
                list = ListUtils.union(list1, list);
            }
            generatePdf.doPdf(list,response);
            return true;

        }
    }
