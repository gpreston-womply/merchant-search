package com.womply.models;

import lombok.Data;
import org.apache.solr.common.SolrDocumentList;

import java.util.List;

/**
 * TODO doc
 */
@Data
public class MerchantSearchResult {
SolrDocumentList results;
    List<MerchantSearchFacet> facets;
}
