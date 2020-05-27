package com.matrimony.demo.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.SolrResponseBase;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.ContentStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.solr.core.SolrCallback;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@EnableAutoConfiguration
@EnableScheduling
@Service
public class DataImportScheduler {

    @Autowired
    private SolrTemplate solrTemplate;

    @Scheduled(fixedDelayString = "35000" )
    public void importData() {
        //logger.info("Solr Full Data Import Started");

    	SolrResponse response = solrTemplate.execute(new SolrCallback<SolrResponse>() {

			@Override
			public SolrResponse doInSolr(SolrClient solrClient) throws SolrServerException, IOException {
				// TODO Auto-generated method stub
				SolrRequest request = new SolrRequest(METHOD.GET, "/dataimport?command=full-import") {
					
					@Override
					public SolrParams getParams() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					protected SolrResponse createResponse(SolrClient client) {
						// TODO Auto-generated method stub
						return null;
					}
					
				};
				 class QESXMLResponseParser extends XMLResponseParser {
				    public QESXMLResponseParser() {
				        super();
				    }

				    @Override
				    public String getContentType() {
				        return "text/xml; charset=UTF-8";
				    }
				}
				
				 String fullimport = "http://localhost:8983/solr/UserProfile/dataimport?command=full-import";
				 URL url = new URL(fullimport);
				 HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				 connection.setRequestMethod("GET");
				 connection.connect();

				 InputStream stream = connection.getInputStream();
				request.setResponseParser(new QESXMLResponseParser());
			    return request.process(solrClient,"UserProfile");
    	};    	  
        //logger.info("Solr Full Data Import Completed");
    });
}
}
