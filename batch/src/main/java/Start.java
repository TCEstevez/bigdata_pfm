import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.log4j.Logger;
import pojo.Constants;
import exception.BusinessError;
import utils.ElasticSearchUtils;
import utils.SparkUtil;

import java.util.Iterator;


public class Start {

    public static ElasticSearchUtils elasticSearchService;
    public static SparkUtil sparkUtil;
    private final static Logger log = Logger.getLogger(Start.class);
    public static final Constants c = new Constants();



    public static void main(String[] args)  {

        try {
        log.debug("Iniciando....");


            c.loadConstants();


      /*Conexion elasticsearch mapeo y indice*/

            elasticSearchService = new ElasticSearchUtils();
            elasticSearchService.openConexionBBDD(c.nameElasticSearchCluster, c.hostElasticSearchCluster, c.portElasticSearchCluster);
            elasticSearchService.createIndex();
            elasticSearchService.closeConexionBBDD();


           JavaSparkContext sc = new JavaSparkContext(new SparkConf().setMaster(c.masterSparkCluster).setAppName("PFM"));

            sparkUtil=new SparkUtil();
            JavaRDD data = sparkUtil.processData(sc, c.inputFile);

            data.foreachPartition(new VoidFunction<Iterator<String>>() {
                public void call(Iterator<String> s) throws BusinessError{
                    ElasticSearchUtils es =new ElasticSearchUtils();
                    es.openConexionBBDD(c.nameElasticSearchCluster, c.hostElasticSearchCluster, c.portElasticSearchCluster);

                    while (s.hasNext()) {
                        es.addDocument(s.next());
                    }
                    es.closeConexionBBDD();

                }
            });




        }catch (Exception e){
            if(e instanceof BusinessError)
                log.error(e.getMessage());
            else
                e.printStackTrace();
            System.exit(1);
        }
    }


}
