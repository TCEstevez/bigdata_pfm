package com.spring.elasticsearch.repository;


import com.spring.elasticsearch.model.entities.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
public interface PostsRepository extends ElasticsearchRepository<Posts, String> {


    @Query("{ \"terms\" : {\"tags\" : [ \"?0\", \"?1\" ],\"minimum_should_match\" : 2}}")
    Page<Posts> findByTagsUsingCustomQuery(String tags1, String tags2, Pageable pageable);


    Posts findPostsById(Long id);

    List<Posts> findPostsByViewCount(String viewCount);

    List<Posts> findPostsByParentId(String parentId);

    Page<Posts> findByPostTypeId(String postTypeId, Pageable page);

    List<Posts> findByViewCountOrderByIdAsc(String viewCount);

    Long countByViewCount(String viewCount);

    List<Posts> findTop10ByOrderByViewCountDesc();

    List<Posts> findByGradoDificultad1Between(double beginning, double end);

    List<Posts> findByPostTypeIdAndGradoDificultad1(String postTypeId,double dificulty);

    List<Posts> findByPostTypeIdAndGradoDificultad1Between(String name, double beginning, double end);

    List<Posts> findByPostTypeIdAndTagsInAndGradoDificultad1Between(String name, String[] tags, double beginning, double end);

    List<Posts> findByPostTypeIdAndTagsInAndGradoDificultad1(String name, String[] tags, double gradodificultad1);

    Page<Posts> findPostsByTagsIn(ArrayList<String> skills, Pageable page);

    Long countByTags(String tag);

    long countByGradoDificultad1Between(double beginning, double end);
}
