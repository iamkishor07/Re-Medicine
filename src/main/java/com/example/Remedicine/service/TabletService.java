package com.example.Remedicine.service;

import com.example.Remedicine.Entity.Medicine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TabletService {

    Medicine createTablet(Medicine medicine);

    Medicine getTabletById(int tabletid);

    List<Medicine> getAllTablets();

    void deleteTablet(int tabletid);
}
