package com.example.Remedicine.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "QrCode")
@Entity
public class QrCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int qrcodeId;

    @Column(name = "Qr_code_created_date")
    private Date QrCreatedDate;

    @Column(name = "Qr_code_Tablet_entry_date")
    private Date QrTableEntryDate;

    @Column(name = "Qr_code_image_name")
    private String QrCodeName;

    @Column(name = "Qr_code_image_type")
    private String QrCodeType;

    @Lob
    @Column(name = "Qr_code")
    private byte[] qrcode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tablet_id", nullable = true)
    private Medicine medicine;

}
