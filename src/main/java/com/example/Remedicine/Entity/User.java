package com.example.Remedicine.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//I got this exception was that some tables had names which are reserved for PostgreSQL. For example, "Like" or "User". Changed name with:
@Table(name = "Users")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private int userId;

    @Column(name = "user_name")
    private String UserName;

    @Column(name = "user_mobile_no")
    private String MobileNo;

    //Joins the primary key of the qrcode
    @JsonIgnore
    @JoinColumn(name = "qrcodeId")
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<QrCode> qrCode;
}
