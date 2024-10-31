package com.example.Remedicine.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "User_Qrcode")
public class UserQRCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "qrcodeId", nullable = false)
    private QrCode qrCode;
}
