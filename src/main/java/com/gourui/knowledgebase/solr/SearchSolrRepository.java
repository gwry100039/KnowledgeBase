package com.gourui.knowledgebase.solr;

import com.gourui.knowledgebase.solr.model.DataCaliber;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface SearchSolrRepository extends SolrCrudRepository<DataCaliber, String> {
    @Query("?0")
//    List<DataCaliber> findAll(String key, Pageable pageable);
    List<DataCaliber> findAll(String key);

    @Query("?0")
    @Highlight(prefix = "1E432EE5B2CB1BA6D95CFAAA628999D3", postfix = "2879AEAB891E193C96A1E0D94A0C781B") //gourui的32位MD5码 和 苟瑞的32位MD5码
    HighlightPage<DataCaliber> findAllHighlight(String key, Pageable pageable);
}
