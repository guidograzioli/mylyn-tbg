package com.undebugged.mylyn.tbg.core;
import org.apache.commons.httpclient.HttpStatus;

import org.eclipse.mylyn.tasks.core.TaskRepository;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.util.EncodingUtil;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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
    
    public static TBGService get(String url, String username, String password) {
        return new TBGService(TBGRepository.createFromUrl(url), new TBGCredentials(username, password));
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
        doGet(new BBIssues());
        return true;
    }
    
    public BBIssues searchIssues(BitbucketQuery query) throws TBGServiceException {
        String uri;
        try {
            uri = TBGRepository.API_BITBUCKET + TBGRepository.REPO_PART + repository.getUsername() + "/" + repository.getRepoSlug() +  "/issues/" +  query.toQueryString(0);
            //System.err.println(uri);
        } catch (UnsupportedEncodingException e) {
           throw new TBGServiceException(e);
        }
        BBIssues issues = doGetIssuesQuery(uri);
       
        while(issues.getCount() > issues.getIssues().size()) {
            try {
                uri = TBGRepository.API_BITBUCKET + TBGRepository.REPO_PART + repository.getUsername() + "/" + repository.getRepoSlug() +  "/issues/" + query.toQueryString(issues.getIssues().size());
                //System.err.println(uri);
            } catch (UnsupportedEncodingException e) {
               throw new TBGServiceException(e);
            }
            issues.addMoreIssues(doGetIssuesQuery(uri).getIssues());
        }
        return issues;
    }
    
    private static Gson gson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }
    
    public <T> T doDelete(BBModelI model)
            throws TBGServiceException {
        String uri = model.buildUrl(repository) + model.getKey() + "/";
        System.err.println("Calling uri: "  + uri);
        DeleteMethod method = new DeleteMethod(uri); 
        return execute(method, credentials, model.getClass());
    }
    
    /** TODO Anyone know a better way to do this? I would want to infer type from argument without having to cast the response */
    public <T> List<T> doGetList(T model) 
            throws TBGServiceException {
        String uri =  ((BBModelI) model).buildUrl(repository);
        GetMethod method = new GetMethod(uri);
        //Type listType = new TypeToken<List<T>>(){}.getType();
        return execute(method, credentials, ((BBModelI) model).getListType());
    }
    
    public <T> T doGet(BBModelI model)
            throws TBGServiceException {
        String uri = model.buildUrl(repository) + model.getKey();
        System.err.println("Calling uri: "  + uri);
        GetMethod method = new GetMethod(uri); 
        T returned = execute(method, credentials, model.getClass());
        return returned;
    }
    
    public BBIssues doGetIssuesQuery(String uri)
            throws TBGServiceException {
        GetMethod method = new GetMethod(uri); 
        BBIssues issues = execute(method, credentials, BBIssues.class);
        return issues;
    }
    

    public <T extends BBModelI> T doPost(T model) throws TBGServiceException {
        String uri = model.buildUrl(repository);
        System.err.println("Calling uri: "  + uri);
        PostMethod method = new PostMethod(uri);
        if (!model.getParams().isEmpty()) {
            for (Entry<String, String> entry : model.getParams().entrySet()) {
                System.err.println(entry.getKey() + ":" +  entry.getValue());
                method.addParameter(new NameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        return execute(method, credentials, model.getClass());
    }

    public <T extends BBModelI> T doPut(T model) throws TBGServiceException {
        
        String uri = model.buildUrl(repository) + model.getKey() + "/";
        System.err.println("Calling uri: "  + uri);
        PutMethod method = new PutMethod(uri);
        if (!model.getParams().isEmpty()) {
            try {
                StringBuilder text = new StringBuilder();
                for (Entry<String, String> entry : model.getParams().entrySet()) {
                    text.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                method.setRequestEntity(new StringRequestEntity(text.toString(), "text/plain", "utf-8"));
            } catch (UnsupportedEncodingException e) {

            }
        }
        return execute(method, credentials, model.getClass());
    }
    
    private <T> T execute(HttpMethodBase method, TBGCredentials credentials, Type type) throws TBGServiceException {
        
        HttpClient client = new HttpClient();
        
        /*for (IProxyData data : ProxySelector.getProxyData(ProxySelector.getDefaultProvider())) {
            if ((data.getHost() != null) && (data.getType().equalsIgnoreCase("https"))) {
                client.getHostConfiguration().setProxy(data.getHost(), data.getPort());                
            }            
        }   */

        // Authentication
        if (credentials != null) {
            client.getState().setCredentials(new AuthScope("api.bitbucket.org", 443),
                    new UsernamePasswordCredentials(credentials.getUsername(), credentials.getPassword()));
            method.setDoAuthentication(true);
        }
                
        try {
            int statusCode = client.executeMethod(method);
            // Retry Basic Authorization (Bug)
            if (statusCode == HttpStatus.SC_UNAUTHORIZED || statusCode == HttpStatus.SC_FORBIDDEN) {
                String token = credentials.getUsername() + ":" + credentials.getPassword();
                method.setRequestHeader("Authorization",
                        "Basic " + EncodingUtil.getAsciiString(Base64.encodeBase64(token.getBytes())));
                statusCode = client.executeMethod(method);
            }
            if (statusCode != HttpStatus.SC_OK && (statusCode!= HttpStatus.SC_NO_CONTENT && method instanceof DeleteMethod)) {
                throw new TBGServiceException(method.getURI() + " : " + method.getStatusText());
            }
            if (statusCode == HttpStatus.SC_NOT_FOUND) {
                throw new TBGServiceException("Could not find item, it is possible that the repositories are not in sync, please try and synchronize you repository");
            }
            return gson().fromJson(method.getResponseBodyAsString(), type);
        } catch (HttpException e) {
            throw new TBGServiceException(e);
        } catch (IOException e) {
            throw new TBGServiceException(e);
        } finally {
            method.releaseConnection();            
        }
    }
    
}