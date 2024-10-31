package com.example.Remedicine.service;

import com.example.Remedicine.Entity.Medicine;
import com.example.Remedicine.Entity.QrCode;
import com.example.Remedicine.repositry.QrCodeRepositry;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class QrCodeServiceImpl implements QrCodeService {

    @Autowired
    QrCodeRepositry qrCodeRepositry;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    TabletServiceImpl tabletServiceImpl;

    @Override
    public byte[] generateMicroQRCode(String url, int id) throws IOException, WriterException {
        int width = 90;  // Width of the image
        int height = 90; // Height of the image
        BitMatrix bitMatrix = new MultiFormatWriter().encode(url + id, BarcodeFormat.QR_CODE, width, height);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return outputStream.toByteArray();
    }


    @Override
    public QrCode getQRCodeById(int qrcodeid) {
        return qrCodeRepositry.findByqrcodeId(qrcodeid);
    }

    @Override
    public List<QrCode> getAllQRCodes() {
        return qrCodeRepositry.findAll();
    }

    @Override
    public void deleteQRCode(int qrcodeid) {

    }

    @Override
    public QrCode saveQrCode(int id) {
        QrCode qrcode = new QrCode();
//        qrcode.setQrcode(imagebytestream);
        qrcode.setQrCreatedDate(new Date());
        qrcode.setQrCodeType("png");
        qrcode.setQrCodeName("micro_qr_code");
        return qrCodeRepositry.save(qrcode);
    }

    @Override
    public QrCode updateQrCode(int id, byte[] byteStream) {
        QrCode qrCode = qrCodeRepositry.findByqrcodeId(id);
        if (qrCode != null) {
            qrCode.setQrcode(byteStream);
            qrCodeRepositry.save(qrCode);
            return qrCode;
        }
        return null;
    }

    @Override
    public QrCode updateQrCodeByTabletId(int Qrid, Medicine medicine) {
        QrCode qrCode = qrCodeRepositry.findByqrcodeId(Qrid);
        if (qrCode != null) {
            qrCode.setQrTableEntryDate(new Date());
            qrCode.setMedicine(medicine);
            qrCodeRepositry.save(qrCode);
            return qrCode;
        }
        return null;
    }

    @Override
    public Medicine getDetailsOfQrCode(MultipartFile multipartFile) throws IOException, NotFoundException {
        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Result result = new MultiFormatReader().decode(bitmap);
        String url = result.getText();
        int val = Integer.valueOf(url.substring(31));

        QrCode qrCode = qrCodeRepositry.findByqrcodeId(val);
        if (qrCode != null) {
            Medicine medicine = tabletServiceImpl.getTabletById(qrCode.getMedicine().getTabletId());
            //Check if it contains tablet id
            if (medicine != null) {
                return medicine;
            }
        }
        return new Medicine();
    }
}
