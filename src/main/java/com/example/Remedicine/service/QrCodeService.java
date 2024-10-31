package com.example.Remedicine.service;

import com.example.Remedicine.Entity.Medicine;
import com.example.Remedicine.Entity.QrCode;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface QrCodeService {
    byte[] generateMicroQRCode(String url, int id) throws IOException, WriterException;

    QrCode getQRCodeById(int qrcodeid);

    List<QrCode> getAllQRCodes();

    void deleteQRCode(int qrcodeid);

    QrCode updateQrCodeByTabletId(int qrid, Medicine medicine);

    QrCode saveQrCode(int id);

    QrCode updateQrCode(int id, byte[] byteStream);

    Medicine getDetailsOfQrCode(MultipartFile multipartFile) throws IOException, NotFoundException;
}
