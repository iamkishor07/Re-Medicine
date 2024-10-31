package com.example.Remedicine.controller;

import com.example.Remedicine.Entity.Medicine;
import com.example.Remedicine.Entity.QrCode;
import com.example.Remedicine.Entity.User;
import com.example.Remedicine.service.QrCodeServiceImpl;
import com.example.Remedicine.service.TabletServiceImpl;
import com.example.Remedicine.service.UserServiceImpl;
import com.google.zxing.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController()
@RequestMapping("")
public class UserController {

    //URL defined in the application.properties file
    @Value("${QrCodeGeneration.URL}")
    private static String URL;

    UserServiceImpl userServiceImpl;
    QrCodeServiceImpl qrCodeServiceImpl;
    TabletServiceImpl tabletServiceImpl;


    //Construction Injection
    public UserController(UserServiceImpl userServiceImpl, TabletServiceImpl tabletServiceImpl, QrCodeServiceImpl qrCodeServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.tabletServiceImpl = tabletServiceImpl;
        this.qrCodeServiceImpl = qrCodeServiceImpl;
    }


    //To add the Users
    @PostMapping("/signup")
    public ResponseEntity<User> SaveUser(@RequestBody User user) {
        return new ResponseEntity<>(userServiceImpl.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return new ResponseEntity<>(userServiceImpl.getUserById(id), HttpStatus.OK);
    }

    @RequestMapping(path = "users/{id}/QrCode", method = {RequestMethod.GET})
    public ResponseEntity<?> generateQrCode(@PathVariable int id) {
        //Code to generate Mini QrCode  => that includes the URL & Unique QrCodeId
        //While generating it need to create an entry in qrCode Table with unique id & user Id & generated image.
        String name = "micro_qr_code";
        try {
            //Now we need to create QrCode record in the table so that we can have the primary key of the qrcode.
            //That we need to put it in the url of the qrcode generation.

            QrCode qrCode = qrCodeServiceImpl.saveQrCode(id);
            //After the qrcode creation done , we generate a qrcode byte & update the qrcode table with the byte
            System.out.println(qrCode.getQrcodeId() + " Qrcode id");
            byte[] qrCodeImage = qrCodeServiceImpl.generateMicroQRCode(URL, qrCode.getQrcodeId());

            //After generating byte Stream of the image ,we update the same in the qrcode table
            qrCodeServiceImpl.updateQrCode(qrCode.getQrcodeId(), qrCodeImage);


            String downloadsPath = System.getProperty("user.home") + "\\Downloads\\micro_qr_code" + qrCode.getQrcodeId() + ".png";
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(qrCodeImage));
            ImageIO.write(image, "png", new File(downloadsPath));
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "image/png");


            return new ResponseEntity<>(qrCodeImage, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userServiceImpl.getAllUsers(), HttpStatus.OK);
    }


    //This method will scan the qr code & extracts the unique id
    @RequestMapping(path = "users/{id}/ScanQr", consumes = {"multipart/form-data"}, method = {RequestMethod.GET})
    public ResponseEntity<?> scanQrCode(@PathVariable int id, @RequestPart MultipartFile multipartFile) throws NotFoundException, IOException {

        if (userServiceImpl.getUserById(id) == null) {
            return new ResponseEntity<>("User Not Found with userid = " + id, HttpStatus.NOT_FOUND);
        }
        Medicine medicine = qrCodeServiceImpl.getDetailsOfQrCode(multipartFile);
        if (medicine != null) {
            return new ResponseEntity<>(medicine, HttpStatus.OK);
        }
        return new ResponseEntity<>("There are no details assosiated with it", HttpStatus.NOT_FOUND);

    }

    //This will create table details in the table with the qrcode unique id.
    @RequestMapping(path = "users/{id}/ScanQr/{qrId}/Tablet", method = {RequestMethod.POST})
    public ResponseEntity<?> qrCodeTabletDetails(@PathVariable int id, @PathVariable int qrId, @RequestBody Medicine medicine) {

        //The user is not assosicated with the Qr
        if (userServiceImpl.getUserById(id).getUserId() != id) {
            return new ResponseEntity<>("The Userid= " + id + "is not associate with the QrId =" + qrId, HttpStatus.BAD_REQUEST);
        }
        if (medicine != null) {
            Medicine medicine1 = tabletServiceImpl.createTablet(medicine);
            qrCodeServiceImpl.updateQrCodeByTabletId(qrId, medicine1);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Tablet body is null", HttpStatus.BAD_REQUEST);

    }

}
