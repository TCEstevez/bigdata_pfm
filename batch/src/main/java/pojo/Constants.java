package pojo;


import exception.BusinessError;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

public class Constants implements Serializable{

    private static final long serialVersionUID = -784521422578965415L;

    private final static Logger log = Logger.getLogger(Constants.class);

    public static String nameElasticSearchCluster="";
    public static Integer portElasticSearchCluster=0;
    public static String hostElasticSearchCluster="";
    public static String masterSparkCluster="";
    public static String inputFile="";
    public static String DAY_FILE = "2016-03-06 04:00:36.443";

    public static void loadConstants() throws BusinessError {
        log.debug("Cargando properties");
        Properties prop = new Properties();
        InputStream input = null;

        String filename = "config.properties";
        input = Constants.class.getClassLoader().getResourceAsStream(filename);
        try {
            prop.load(input);
        }catch (IOException e){
            throw new BusinessError("Error al cargar el fichero de propiedades");
        }

        nameElasticSearchCluster=prop.getProperty("cluster.elasticsearch.name");
        hostElasticSearchCluster=prop.getProperty("cluster.elasticsearch.host");
        try {
            portElasticSearchCluster = Integer.parseInt(prop.getProperty("cluster.elasticsearch.port"));
        }catch (NumberFormatException e){
            throw new BusinessError("Puerto de elasticsearch en propiedades inválido");
        }
        masterSparkCluster=prop.getProperty("cluster.spark.master");
        inputFile=prop.getProperty("inputFile");
        DAY_FILE=prop.getProperty("DAY_FILE");

        if (prop.size() != 6) {
            throw new BusinessError("El numero de argumentos no es válido");
        }
        log.debug("Cargando properties finalizado");

    }
}
