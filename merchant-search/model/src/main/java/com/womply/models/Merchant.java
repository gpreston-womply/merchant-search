package com.womply.models;

import lombok.Data;

import java.sql.Timestamp;
import javax.persistence.Column;

/**
 * A Merchant
 */
@Data
public class Merchant {

    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
