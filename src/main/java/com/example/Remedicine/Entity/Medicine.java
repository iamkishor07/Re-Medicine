package com.example.Remedicine.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Tablet")
@Entity
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private int tabletId;

    @Column(name = "tablet_name")
    private String tabletName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "DD-MM-YYYY")
    @Column(name = "tablet_Exipy_Date")
    private Date ExpiryDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "DD-MM-YYYY")
    @Column(name = "tablet_purchase_Date")
    private Date PurchaseDate;

}
