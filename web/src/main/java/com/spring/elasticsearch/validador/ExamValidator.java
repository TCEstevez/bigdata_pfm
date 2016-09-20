package com.spring.elasticsearch.validador;

import com.spring.elasticsearch.model.domain.Exam;
import com.spring.elasticsearch.model.domain.Question;
import org.apache.commons.lang.math.NumberUtils;
import org.omg.CORBA.Object;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ExamValidator implements Validator {
    public boolean supports(Class<?> aClass) {
        return Exam.class.isAssignableFrom(aClass);
    }

    public void validate(java.lang.Object o, Errors errors) {
        boolean noDificulty=true;
        boolean noNumber=true;
        boolean noTags=true;
        int i=0;
        Exam ex = (Exam) o;
        for (Question question : ex.getQuestions()) {
            if (noDificulty) {
                noDificulty = dificultValidate(question.getDificulty(),question.getDificultyto(),errors,i);
            }
            if (noNumber){
                noNumber = numberValidate(question.getNumber(), errors, i);
             }
            if(noTags) {
               noTags=tagsValidate(question.getTags(), errors, i);

            }
            i++;

        }
    }

    private boolean dificultValidate(String dificulty,String dificultyto,Errors errors,int i){
        if (dificulty==null  || (!(isFloat(dificulty) && Double.parseDouble(dificulty) >= 0.0 && Double.parseDouble(dificulty) < 10))
                || (!(isFloat(dificultyto) && Double.parseDouble(dificultyto) >= 0.0 && Double.parseDouble(dificultyto) < 10))
                || Double.parseDouble(dificulty)>=Double.parseDouble(dificultyto)) {
            errors.rejectValue("questions[" + i + "].dificulty", "valoresnopermitidos.dificultad", "El campo dificultad debe de ser un rango entre 0.00 a 9.99");
            return false;
        }
        return true;
    }

    private boolean numberValidate(String number,Errors errors,int i){
        if (number==null || !NumberUtils.isNumber(number) || (Integer.parseInt(number)) <= 0){
            errors.rejectValue("questions[" + i + "].number", "valoresnopermitidos.numero", "El campo numero de preguntas debe ser mayor que cero");
            return false;
        }
        return true;
    }

    public boolean tagsValidate(String tags,Errors errors,int i){
       if (!tags.trim().equalsIgnoreCase("")) {
                String patron = "[^A-Za-z0-9\\.\\,\\-]+";
                Pattern p = Pattern.compile(patron);
                Matcher matcher = p.matcher(tags);
                if (matcher.find()) {
                    errors.rejectValue("questions[" + i + "].tags", "valoresnopermitidos.tags", "El campo tags contiene caracteres no permitidos");
                    return false;
                }

       }
        return true;

    }

    public void validateQuestion(java.lang.Object o, Errors errors, int i) {
        Question question = (Question) o;
        dificultValidate(question.getDificulty(),question.getDificultyto(),errors,i);
        numberValidate(question.getNumber(), errors, i);
        tagsValidate(question.getTags(), errors, i);
    }


    private static final Pattern DOUBLE_PATTERN = Pattern.compile(
            "[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)" +
                    "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|" +
                    "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))" +
                    "[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");

    public static boolean isFloat(String s)
    {
        return DOUBLE_PATTERN.matcher(s).matches();
    }
}
