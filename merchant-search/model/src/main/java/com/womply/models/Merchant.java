package com.womply.models;

import lombok.Data;

/**
 * A Merchant
 */
@Data
public class Merchant {

//    @Column(name = "id")
//    private long id;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "created_at")
//    private Timestamp createdAt;
//
//    @Column(name = "updated_at")
//    private Timestamp updatedAt;

    Long merchantLocationId;
    String merchantType;
    String merchantLocationName;
    String phoneNumber;
    String location;
    Boolean isClaimed;
    Boolean isFake;
    String websiteUrl;
    String legalName;
    String signerFullName;
    String signerEmail;
    String merchantLocationSlug;
    String merchantName;
    Long mid;
    String processorName;
    String companyName;
    Long partnerId;
    String partnerName;
    Double revenueThisMonth;
    Double revenueLastMonth;
    String category;
}
