package com.example.Remedicine.controller;


import com.example.Remedicine.Entity.QrCode;
import com.example.Remedicine.service.QrCodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/QrCode")
public class QrCodeController {

    @Autowired
    QrCodeServiceImpl qrCodeServiceImpl;

    @GetMapping("/{id}")
    public ResponseEntity<QrCode> getQrCode(@PathVariable int id) {
        return new ResponseEntity<>(qrCodeServiceImpl.getQRCodeById(id), HttpStatus.OK);
    }

    @GetMapping("/findAllQrCodes")
    public ResponseEntity<List<QrCode>> getQrCode() {
        return new ResponseEntity<>(qrCodeServiceImpl.getAllQRCodes(), HttpStatus.OK);
    }

}
