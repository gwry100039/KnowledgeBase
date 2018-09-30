package com.gourui.knowledgebase.solr.service;

import com.gourui.knowledgebase.solr.SearchSolrRepository;
import com.gourui.knowledgebase.solr.model.DataCaliber;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SolrSearchService {
    private static final int DEFAULT_SEARCH_LIMIT = 50;
    private static final String SEARCH_TEMPLATE = "(requirement_desc:{key} OR comments:{key})";
    private static final String HIGHLIGHT_PRE = "1E432EE5B2CB1BA6D95CFAAA628999D3";
    private static final String HIGHLIGHT_POST = "2879AEAB891E193C96A1E0D94A0C781B";
    @Autowired
    private SearchSolrRepository searchSolrRepository;
    @Autowired
    private SolrClient solrClient;

//    @Autowired
//    public SearchSolrService(SearchSolrRepository searchSolrRepository, SolrClient solrClient) {
//        this.searchSolrRepository = searchSolrRepository;
//    }

    public HighlightPage<DataCaliber> listSolrHighlight(String key) {
        Pageable pageable = PageRequest.of(0, DEFAULT_SEARCH_LIMIT);
        return searchSolrRepository.findAllHighlight(buildSearchKeyword(key), pageable);
    }

    public List<DataCaliber> getHighlightedResults(String key) {
        List<DataCaliber> dcl = new ArrayList<>();
        HighlightPage<DataCaliber> hlist = listSolrHighlight(key);
        List<HighlightEntry<DataCaliber>> highlightEntries = hlist.getHighlighted();
        for (HighlightEntry<DataCaliber> dch : highlightEntries ) {
            DataCaliber dc = dch.getEntity();

            List<HighlightEntry.Highlight> highlightList = dch.getHighlights();
            String snipplets = StringEscapeUtils.escapeHtml4(highlightList.get(0).getSnipplets().get(0)).replaceAll(HIGHLIGHT_PRE,"<highlight>").replaceAll(HIGHLIGHT_POST,"</highlight>");

            dc.setSnipplets(snipplets);
            dcl.add(dc);
        }
        return dcl;
    }

    /**
     * build search keyword
     *
     * @param keyword
     * @return
     */
    private static String buildSearchKeyword(String keyword) {
        String[] tokens = keyword.split("\\s+");
        String searchKeyword = "";
        for (String item : tokens) {
            searchKeyword += "AND " + SEARCH_TEMPLATE.replace("{key}", item);
        }
        return searchKeyword.substring(4);
    }
}