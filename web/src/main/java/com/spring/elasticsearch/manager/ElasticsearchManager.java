package com.spring.elasticsearch.manager;

import com.spring.elasticsearch.model.entities.Posts;
import com.spring.elasticsearch.model.domain.ExamResponse;
import com.spring.elasticsearch.repository.PostsRepository;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.elasticsearch.client.node.NodeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;


@Service
@EnableElasticsearchRepositories(basePackages = "com.spring.elasticsearch.repository")
public class ElasticsearchManager {
	@Autowired
	private PostsRepository repository;

	@Autowired
	private ElasticsearchTemplate template;

	private static String POSTSTYPEID="1";
	private static int NUMBER_ANSWERS=3;
	
	
	@Bean
	public ElasticsearchTemplate elasticsearchTemplate() {
		return new ElasticsearchTemplate(getNodeClient());
	}

	private static NodeClient getNodeClient() {
		return (NodeClient) nodeBuilder().clusterName(UUID.randomUUID().toString()).local(true).node()
				.client();
	}


	public boolean findByDificultyBetween(double dificulty,double dificultyto, int number){
		List<Posts> posts =repository.findByPostTypeIdAndGradoDificultad1Between(POSTSTYPEID,dificulty,dificultyto);
		return posts.size()>=number;
	}

	public List<ExamResponse> findAndReturnByDificultyBetween(double dificulty, double dificultyto,int number){
		List<Posts> posts =repository.findByPostTypeIdAndGradoDificultad1Between(POSTSTYPEID,dificulty,dificultyto);
		return postsToExamenResponse(posts,number);
	}


	private List<ExamResponse> postsToExamenResponse(List<Posts> posts, int number){
		List<ExamResponse> result=new ArrayList<ExamResponse>();
		int i=0;
		for(Posts p:posts){
			ExamResponse er=new ExamResponse();
			er.setQuestion(p.getBody());
			List<String> listAnswers=findPostsByParentId(String.valueOf(p.getId()),p.getAcceptedAnswerId());
			listAnswers.add(repository.findPostsById(p.getAcceptedAnswerId()).getBody());
			er.setAnswers(listAnswers);
			result.add(er);
			i++;
			if(i==number)
				break;
		}
		return result;
	}

	private List<String> findPostsByParentId(String parentId,Long acceptedAnswerId) {
		List<String> result=new ArrayList<String>();
		List<Posts> postsList = repository.findPostsByParentId(parentId);
		int i=0;
		for (Posts post : postsList) {
			if(!post.getId().equals(acceptedAnswerId)) {
				result.add(post.getBody());
				i++;
				if (i == NUMBER_ANSWERS)
					break;
			}
		}
		return result;
	}

	public boolean findByTagsInAndGradoDificultad1Between(String [] tags,double dificulty, double dificultyto,int number){
		List<Posts> posts =repository.findByPostTypeIdAndTagsInAndGradoDificultad1Between(POSTSTYPEID,tags,dificulty,dificultyto);
		return posts.size()>=number;
	}

	public List<ExamResponse> findAndReturnByTagsInAndGradoDificultad1Between(String [] tags,double dificulty,double dificultyto, int number){
		List<Posts> posts =repository.findByPostTypeIdAndTagsInAndGradoDificultad1Between(POSTSTYPEID,tags,dificulty,dificultyto);
		return postsToExamenResponse(posts,number);
	}

	public Object[] countByTags(List<String> tags){
		List<Long> countTags=new ArrayList<Long>();
		for(String tag:tags){
			countTags.add(repository.countByTags(tag));
		}
		return countTags.toArray();
	}

	public String countByGradoDificultad1()throws JSONException{
		JSONArray list = new JSONArray();
		for(int i=0;i<9;i++){
			JSONObject obj = new JSONObject();
			obj.put("label", i+"-"+(i+1));
			obj.put("value", repository.countByGradoDificultad1Between(i,i+1));
			list.put(obj);
		}

		return list.toString();
	}









	

}
