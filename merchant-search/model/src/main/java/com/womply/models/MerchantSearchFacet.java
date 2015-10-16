package com.womply.models;

import lombok.Data;

import java.util.Map;

/**
 * TODO doc
 */
@Data
public class MerchantSearchFacet {
    String name;
    Map<String, String> values;
}
