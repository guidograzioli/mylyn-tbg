package com.undebugged.mylyn.tbg.core;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.eclipse.mylyn.tasks.core.TaskRepository;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.undebugged.mylyn.tbg.core.model.TBGIssues;
import com.undebugged.mylyn.tbg.core.model.TBGObject;
import com.undebugged.mylyn.tbg.core.model.TBGProjects;

/**
 * Service object to get/search/update issue in The Bug Genie.
 * @since 1.0.0
 * @author Guido Grazioli
 */
public class TBGService {
    
    public static TBGService get(TaskRepository repository) {
        return new TBGService(TBGRepository.createFromUrl(repository.getUrl()),
                TBGCredentials.create(repository));
    }
    
    public static TBGService get(String url, String username, String password, String key) {
        return new TBGService(TBGRepository.createFromUrl(url), new TBGCredentials(username, password, key));
    }
    
    protected final TBGRepository repository;
    protected final TBGCredentials credentials;

    /**
     * Constructor, create the client and JSON/Java interface object.
     */
    public TBGService(TBGRepository repository, TBGCredentials credentials) {
        this.repository = repository;
        this.credentials = credentials;
    }

    public boolean verifyCredentials() throws TBGServiceException {
        doGet(new TBGProjects());
        return true;
    }
    
    public TBGIssues searchIssues(TBGQuery query) throws TBGServiceException {
        String uri;
        try {
            uri = repository.getUrl() +  query.toQueryString(0);
            System.err.println(uri);
        } catch (UnsupportedEncodingException e) {
           throw new TBGServiceException(e);
        }
        TBGIssues issues = doGetIssuesQuery(uri);
        if (issues == null) return null;

        return issues;
    }
    
    private static Gson gson() {
        return new GsonBuilder()
        		.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTimestampDeserializer())
                .create();
    }
//    
//    public <T> T doDelete(BBModelI model)
//            throws TBGServiceException {
//        String uri = model.buildUrl(repository) + model.getKey() + "/";
//        System.err.println("Calling uri: "  + uri);
//        DeleteMethod method = new DeleteMethod(uri); 
//        return execute(method, credentials, model.getClass());
//    }
//    
//    /** TODO Anyone know a better way to do this? I would want to infer type from argument without having to cast the response */
//    public <T> List<T> doGetList(T model) 
//            throws TBGServiceException {
//        String uri =  ((BBModelI) model).buildUrl(repository);
//        GetMethod method = new GetMethod(uri);
//        //Type listType = new TypeToken<List<T>>(){}.getType();
//        return execute(method, credentials, ((BBModelI) model).getListType());
//    }
//    
    public <T> T doGet(TBGObject model) throws TBGServiceException {
        String uri = model.buildUrl(repository) + model.getObjectKey();
        System.err.println("Calling uri: "  + uri);
        GetMethod method = new GetMethod(uri); 
        T returned = execute(method, credentials, model.getClass());
        return returned;
    }
    
    public TBGIssues doGetIssuesQuery(String uri) throws TBGServiceException {
        GetMethod method = new GetMethod(uri); 
        TBGIssues issues = execute(method, credentials, TBGIssues.class);
        return issues;
    }

    
//
//    public <T extends BBModelI> T doPost(T model) throws TBGServiceException {
//        String uri = model.buildUrl(repository);
//        System.err.println("Calling uri: "  + uri);
//        PostMethod method = new PostMethod(uri);
//        if (!model.getParams().isEmpty()) {
//            for (Entry<String, String> entry : model.getParams().entrySet()) {
//                System.err.println(entry.getKey() + ":" +  entry.getValue());
//                method.addParameter(new NameValuePair(entry.getKey(), entry.getValue()));
//            }
//        }
//        return execute(method, credentials, model.getClass());
//    }
//
//    public <T extends BBModelI> T doPut(T model) throws TBGServiceException {
//        
//        String uri = model.buildUrl(repository) + model.getKey() + "/";
//        System.err.println("Calling uri: "  + uri);
//        PutMethod method = new PutMethod(uri);
//        if (!model.getParams().isEmpty()) {
//            try {
//                StringBuilder text = new StringBuilder();
//                for (Entry<String, String> entry : model.getParams().entrySet()) {
//                    text.append(entry.getKey()).append("=").ap0pend(entry.getValue()).append("&");
//                }
//                method.setRequestEntity(new StringRequestEntity(text.toString(), "text/plain", "utf-8"));
//            } catch (UnsupportedEncodingException e) {
//
//            }
//        }
//        return execute(method, credentials, model.getClass());
//    }
    
    private <T> T execute(HttpMethodBase method, TBGCredentials credentials, Type type) throws TBGServiceException {
        
        HttpClient client = new HttpClient();
        client.getParams().setParameter("http.useragent", "Mylyn Connector");
        /*for (IProxyData data : ProxySelector.getProxyData(ProxySelector.getDefaultProvider())) {
            if ((data.getHost() != null) && (data.getType().equalsIgnoreCase("https"))) {
                client.getHostConfiguration().setProxy(data.getHost(), data.getPort());                
            }            
        }   */

        try {
	        // Authentication
	        if (credentials != null) {
	        	String encryptedPassword = URLEncoder
	        			.encode(BCrypt
	        					.hashpw(credentials.getPassword(), 
	        							"$2a$07$"+credentials.getSecurityKey()+"$"), "UTF-8");
				String token = "tbg3_password="+encryptedPassword+"; tbg3_username="+credentials.getUsername()+";";
	        	method.addRequestHeader("Cookie", token);
	        }
                
            int statusCode = client.executeMethod(method);
            if (statusCode == HttpStatus.SC_UNAUTHORIZED || statusCode == HttpStatus.SC_FORBIDDEN) {
            	throw new TBGServiceException("Authentication failed, check username and security key.");
            }
            if (statusCode != HttpStatus.SC_OK && (statusCode!= HttpStatus.SC_NO_CONTENT && method instanceof DeleteMethod)) {
                throw new TBGServiceException(method.getURI() + " : " + method.getStatusText());
            }
            if (statusCode == HttpStatus.SC_NOT_FOUND) {
                throw new TBGServiceException("Could not find item, it is possible that the repositories are not in sync, please try and synchronize you repository.");
            }
            if (statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                throw new TBGServiceException("Server error while performing query.");
            }
            String json = method.getResponseBodyAsString();
            return gson().fromJson(json, type);
        } catch (HttpException e) {
            throw new TBGServiceException(e);
        } catch (IOException e) {
            throw new TBGServiceException(e);
        } finally {
            method.releaseConnection();            
        }
    }
    
    static class DateTimestampDeserializer implements JsonDeserializer<Date> {
		@Override
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			long time = Long.parseLong(json.getAsString());
			return new Date(time*1000);
		}
    }
}