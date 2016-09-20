package utils;

import com.google.common.base.Optional;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import pojo.Constants;
import pojo.Question;
import scala.Tuple2;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SparkUtil implements Serializable {

    private static final long serialVersionUID = -569856422536701496L;

    private final static Logger log = Logger.getLogger(SparkUtil.class);

    public JavaRDD processData(JavaSparkContext sc, String inputFile) {
        log.debug("Procesando datos.....");
        JavaRDD<String> lines = sc.textFile(inputFile);

        /*JavaRDD<String> linesSinPosts = lines.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String s) throws Exception {
                return (s.contains("row"));
            }
        });*/
        JavaRDD<String> linesSinPosts = lines.filter(createFunctionFilter());
        log.debug("Filtradas lineas no row");

        JavaPairRDD<String, Question> rowWithkey = linesSinPosts.mapToPair(
                new PairFunction<String, String, Question>() {
                    public Tuple2<String, Question> call(String s) {
                        Question q = JsonUtil.convertStringtoObject(s);
                        return new Tuple2<>(q.getIdString(), q);
                    }
                }).filter(new Function<Tuple2<String, Question>, Boolean>() {
            @Override
            public Boolean call(Tuple2<String, Question> s) throws Exception {
                return (s._2.getPostTypeId() == 1 || s._2.getPostTypeId() == 2);
            }
        });
        log.debug("Filtrados posts distintos de 1 y 2");


        JavaPairRDD<String, String> respCorrectasFechas = linesSinPosts.mapToPair(
                new PairFunction<String, String, String>() {
                    public Tuple2<String, String> call(String s) {
                        Question q = JsonUtil.convertStringtoObject(s);
                        if (q.getPostTypeId() == 1) {
                            if (q.getAcceptedAnswerId() != 0) {
                                return new Tuple2<>(q.getIdString() + "-" + q.getAcceptedAnswerIdString(), " ");
                            } else {
                                return new Tuple2<String, String>("", "");
                            }
                        } else
                            return new Tuple2<>(q.getParentIdString() + "-" + q.getIdString(), q.getCreationDateString());
                    }
                }).reduceByKey(
                new Function2<String, String, String>() {
                    public String call(String value0, String value1) {
                        if (value1.equalsIgnoreCase(" "))
                            return value0 + "--1";
                        else
                            return value1 + "--1";
                    }

                    ;
                }
        ).filter(new Function<Tuple2<String, String>, Boolean>() {
                     public Boolean call(Tuple2<String, String> value1) throws Exception {
                         return !(value1._1.equalsIgnoreCase("") && value1._2.equalsIgnoreCase(""));
                     }
                 }
        ).filter(new Function<Tuple2<String, String>, Boolean>() {
                     public Boolean call(Tuple2<String, String> value1) throws Exception {
                         return value1._2.indexOf("--1") > 0;
                     }
                 }
        ).mapToPair(
                new PairFunction<Tuple2<String, String>, String, String>() {
                    public Tuple2<String, String> call(Tuple2<String, String> t) {
                        log.debug("respCorrectasFechas:" + t._1.substring(0, t._1.indexOf("-")) + "-" + t._2.substring(0, t._2.lastIndexOf("-")-1));
                        return new Tuple2<>(t._1.substring(0, t._1.indexOf("-")), t._2.substring(0, t._2.lastIndexOf("-")-1));
                    }
                });

        log.debug("---------");


        JavaPairRDD<String, Tuple2<Question, Optional<String>>> rowWithkeyAndDate = rowWithkey.leftOuterJoin(respCorrectasFechas);
        log.debug("join post type 1 with fecha");

        JavaRDD pregsAnswers = rowWithkeyAndDate
                .filter(new Function<Tuple2<String, Tuple2<Question, Optional<String>>>, Boolean>() {
                            public Boolean call(Tuple2<String, Tuple2<Question, Optional<String>>> tuple) throws Exception {
                                return !(tuple._2._1.getPostTypeId() == 1 && tuple._2._1.getAcceptedAnswerId() == 0);
                            }
                        }
                )

                .map(new Function<Tuple2<String, Tuple2<Question, Optional<String>>>, String>() {
                    @Override
                    public String call(Tuple2<String, Tuple2<Question, Optional<String>>> tuple) throws Exception {
                        if (tuple._2._1.getPostTypeId() == 1) {
                            if (tuple._2._2.isPresent()) {
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                                log.debug("IdPosts:" + tuple._2._1.getIdString());
                                long diasPunlicadoHastaRespuesta = getDifferenceDays(tuple._2._1.getCreationDate(), formatter.parse(tuple._2._2.get())) + 1;
                                long diasPublicado = getDifferenceDays(tuple._2._1.getCreationDate(), formatter.parse(Constants.DAY_FILE));
                                double numeroVecesVisto = (double) tuple._2._1.getViewCount();
                                double factorPonderacionNumeroVecesVisto = ((double) diasPunlicadoHastaRespuesta * numeroVecesVisto) / (double) diasPublicado;
                                tuple._2._1.setGradoDificultad1(String.format("%.2f", (double) diasPunlicadoHastaRespuesta / factorPonderacionNumeroVecesVisto));
                                log.debug("GradoDificultad1:" + tuple._2._1.getGradoDificultad1());
                            } else {
                                log.debug("Esta pregunta no tiene respuesta correcta " + tuple._2._1.getId());
                            }
                        }
                         log.debug("IdPosts:" + tuple._2._1.getIdString());
                        return tuple._2._1.toJson();
                    }
                });
        log.debug("Finalizado process");
        return pregsAnswers;

    }


    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static Function<String, Boolean> createFunctionFilter(){
        return new Function<String, Boolean>() {
            @Override
            public Boolean call(String s) throws Exception {
                return (s.contains("row"));
            }
        };

    }

}
