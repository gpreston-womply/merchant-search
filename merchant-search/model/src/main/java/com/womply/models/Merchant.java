package com.womply.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Random;

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
    Integer userCount;
    String city;
    String State;
    String address1;
    String address2;
    String zip;

    /**
     * testing
     */
    public static void main(String[] args) throws Exception {
        List<Merchant> merchants = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            Thread.sleep(2);
            merchants.add(randomizedMerchant());
        }
        ObjectMapper om  = new ObjectMapper();
        String merchantsJson =  om.writeValueAsString(merchants);
        System.out.println(merchantsJson);
    }

    /**
     * create a {@link Merchant} with randomized values for testing
     */
    private static Merchant randomizedMerchant() {
        Merchant merchant = new Merchant();
        merchant.setMerchantLocationId(System.currentTimeMillis());
        merchant.setMerchantType(RandomStringUtils.randomAlphabetic(1));
        merchant.setMerchantLocationName(RandomStringUtils.randomAlphabetic(1));
        merchant.setPhoneNumber(RandomStringUtils.randomNumeric(10));
        merchant.setIsFake(Math.random() < 0.5);
        merchant.setIsClaimed(Math.random() < 0.5);
        merchant.setLegalName(RandomStringUtils.randomAlphabetic(1));
        merchant.setWebsiteUrl(RandomStringUtils.randomAlphabetic(1));
        merchant.setSignerEmail(RandomStringUtils.randomAlphabetic(1));
        merchant.setSignerFullName(RandomStringUtils.randomAlphabetic(1));
        merchant.setMerchantLocationSlug(RandomStringUtils.randomAlphabetic(1));
        merchant.setMerchantName(RandomStringUtils.randomAlphabetic(1));
        merchant.setMid(System.currentTimeMillis());
        merchant.setProcessorName(RandomStringUtils.randomAlphabetic(1));
        merchant.setCompanyName(RandomStringUtils.randomAlphabetic(1));
        merchant.setPartnerId(System.currentTimeMillis());
        merchant.setPartnerName(RandomStringUtils.randomAlphabetic(1));
        merchant.setRevenueLastMonth(250 + (10000000 - 250) * new Random().nextDouble());
        merchant.setRevenueLastMonth(0.01 * Math.floor(merchant.getRevenueLastMonth() * 100.0));

        merchant.setRevenueThisMonth(250 + (10000000 - 250) * new Random().nextDouble());
        merchant.setRevenueThisMonth(0.01 * Math.floor(merchant.getRevenueThisMonth() * 100.0));
        merchant.setUserCount(new Random().nextInt((100 - 1) + 1) + 1);
        merchant.setCategory(RandomStringUtils.randomAlphabetic(1));

        return merchant;
    }
}


