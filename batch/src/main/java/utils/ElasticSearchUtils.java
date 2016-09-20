package utils;

import exception.BusinessError;
import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/*Clase singleton*/
public class ElasticSearchUtils implements Serializable{

    private Client client;

    private final static Logger log = Logger.getLogger(ElasticSearchUtils.class);
    private static String index = "posts";
    private static String type = "row";

    /*private static ElasticSearchUtils singletonElasticSearchUtils;



    public static synchronized ElasticSearchUtils getInstance() {
        log.debug("Obteniendo instancia elasticsearch");
        if (singletonElasticSearchUtils == null) {
            singletonElasticSearchUtils = new ElasticSearchUtils();
            log.debug("Creando instancia elasticsearch");
        }

        return singletonElasticSearchUtils;
    }*/


    public ElasticSearchUtils() {
    }

    public void openConexionBBDD(String clusterName, String server, int port) throws BusinessError{
        log.debug("Abriendo conexion a BBDD");
        try {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", clusterName)
                .put("client.transport.ignore_cluster_name", false)
                .put("client.transport.sniff", true)
                .build();


        client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(server, port));
        log.debug("Finalizado Abriendo conexion a BBDD");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessError("UnknownHostException");
        }
    }

    public void closeConexionBBDD() {
        log.debug("Cerrando conexion a BBDD");
        client.close();
        log.debug("finalizado cerrando conexion a BBDD");
    }

    public void createIndex() throws BusinessError {
        log.debug("Creando indice");
        try {
            deleteIndexIfExist();

            // Create Index and set settings and mappings
            CreateIndexRequestBuilder createIndexRequestBuilder = client.admin()
                    .indices().prepareCreate(index);
            XContentBuilder mappingBuilder = createMapping(type);
            createIndexRequestBuilder.addMapping(type, mappingBuilder);
            createIndexRequestBuilder.execute().actionGet();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessError("No se ha podido crear el indice");
        }
        log.debug("finalizado creando indice");
    }

    public XContentBuilder createMapping(String documentType) throws IOException {
        log.debug("creando mapeo");
        XContentBuilder mappingBuilder = jsonBuilder().startObject().startObject(documentType)
                .startObject("_id")
                .field("path", "id")
                .endObject()
                .startObject("properties")
                .startObject("id")
                .field("type", "long")
                .field("index", "not_analyzed")
                .endObject()
                .startObject("postTypeId")
                .field("type", "long")
                .field("index", "not_analyzed")
                .endObject()
                .startObject("parentId")
                .field("type", "long")
                .field("index", "not_analyzed")
                .endObject()
                .startObject("creationDate")
                .field("type", "date")
                .field("format", "yyy-MM-dd HH:mm:ss.SSS")
                .field("index", "not_analyzed")
                .endObject()
                .startObject("score")
                .field("type", "long")
                .field("index", "not_analyzed")
                .endObject()
                .startObject("ownerUserId")
                .field("type", "long")
                .field("index", "not_analyzed")
                .endObject()
                .startObject("lastActivityDate")
                .field("type", "date")
                .field("format", "yyy-MM-dd HH:mm:ss.SSS")
                .field("index", "not_analyzed")
                .endObject()
                .startObject("viewCount")
                .field("type", "long")
                .field("index", "not_analyzed")
                .endObject()
                .startObject("body")
                .field("type", "string")
                .field("index", "no")
                .endObject()
                .startObject("favoriteCount")
                .field("type", "long")
                .field("index", "not_analyzed")
                .endObject()
                .startObject("answerCount")
                .field("type", "long")
                .field("index", "not_analyzed")
                .endObject()
                .startObject("commentCount")
                .field("type", "long")
                .field("index", "not_analyzed")
                .endObject()
                .startObject("acceptedAnswerId")
                .field("type", "long")
                .field("index", "not_analyzed")
                .endObject()
                .startObject("gradoDificultad1")
                .field("type", "double")
                .field("index", "not_analyzed")
                .endObject()
                .startObject("tags")
                .field("type", "string")
                .field("store", "yes")
                .field("index", "not_analyzed")
                .endObject()
                .endObject();


        log.debug(mappingBuilder.string());
        log.debug("finalizado mapeo");
        return mappingBuilder;

    }

    public void deleteIndexIfExist() {
        log.debug("Borrando indice");
        final IndicesExistsResponse res = client.admin().indices().prepareExists(index).execute().actionGet();
        if (res.isExists()) {
            final DeleteIndexRequestBuilder delIdx = client.admin().indices().prepareDelete(index);
            delIdx.execute().actionGet();
        }
        log.debug("finalizado borrando indice");
    }


    public void addDocument(String content) {
        log.debug("añadiendo documento");
        addDocument(index, type, content);
        log.debug("finalizado añadiendo documento");
    }

    public void addDocument(String indexName, String type, String content) {
        IndexResponse response = null;

        IndexRequestBuilder requestBuilder = client.prepareIndex(indexName, type)
                .setSource(content);

        response = requestBuilder.execute().actionGet();

    }
}
