package com.spring.elasticsearch.controller;

import com.spring.elasticsearch.manager.FindManager;
import com.spring.elasticsearch.model.domain.Exam;
import com.spring.elasticsearch.model.domain.Question;
import com.spring.elasticsearch.validador.ExamValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ExamenController {

    public static String ANADIR="anadir";
    public static String BUSCAR="buscar";
    public static String ELIMINAR="eliminar";
    public static String CONSULTAR="consultar";

    ExamValidator examValidator;

    @Autowired
    FindManager fm;

    @Autowired
    public ExamenController(ExamValidator examValidator) {
        this.examValidator = examValidator;
    }


    @RequestMapping(value = "/examen.htm",method = RequestMethod.GET)
    public String initForm(ModelMap model){
        return "examForm";
    }

    @ModelAttribute("examen")
    public Exam obtenerExamen(){
        Exam ex = new Exam();
        ex.getQuestions().add(new Question());

        return ex;
    }

    @RequestMapping(value = "/examen.htm",method = RequestMethod.POST)
    public String processSubmit(
            @RequestParam(value = "accion") String accion, @ModelAttribute("examen") Exam ex,
            BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response) {




            if (accion.equalsIgnoreCase(ANADIR)) {
                ex.getQuestions().add(new Question());
                return "examForm";
            }else if(accion.startsWith(CONSULTAR)){
               int numberFind = Integer.valueOf(accion.substring(accion.indexOf("_") + 1, accion.length()));
                examValidator.validateQuestion(ex.getQuestions().get(numberFind), result,numberFind);
                if(!result.hasErrors()) {
                    status.setComplete();
                    boolean encontrado=fm.searchExistFind(ex.getQuestions().get(numberFind), numberFind);
                    if (encontrado)
                        ex.setResult("Existen posts con los criterios buscados en la fila " + (numberFind + 1));
                    else
                        ex.setResult("No existen posts con los criterios buscados en la fila " + (numberFind + 1));
                }
                return "examForm";
            }else if(accion.startsWith(ELIMINAR)){
                if(ex.getQuestions().size()>1) {
                    int numeroeliminar = Integer.valueOf(accion.substring(accion.indexOf("_") + 1, accion.length()));
                    ex.getQuestions().remove(numeroeliminar);
                }else
                    result.addError(new ObjectError("","No se puede eliminar si solo hay un elemento"));
                return "examForm";
            }else{

                examValidator.validate(ex, result);
                if(!result.hasErrors()) {
                    if(!fm.generatePDF(ex,response)){
                        result.addError(new ObjectError("","Revise las preguntas pedidas porque para alguna de ellas no se encuentras datos"));
                        return "examForm";
                    }
                    return "examOk";
                }else {
                    return "examForm";
                }
            }
        }



    }


