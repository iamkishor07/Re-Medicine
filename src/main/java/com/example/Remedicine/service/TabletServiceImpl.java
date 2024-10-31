package com.example.Remedicine.service;

import com.example.Remedicine.Entity.Medicine;
import com.example.Remedicine.repositry.TabletRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TabletServiceImpl implements TabletService {

    @Autowired
    TabletRepositry tabletRepositry;

    @Override
    public Medicine createTablet(Medicine medicine) {
        if (medicine != null) {
            return tabletRepositry.save(medicine);
        }
        return null;
    }

    @Override
    public Medicine getTabletById(int tabletid) {
        return tabletRepositry.findBytabletId(tabletid);
    }

    @Override
    public List<Medicine> getAllTablets() {
        return tabletRepositry.findAll();
    }

    @Override
    public void deleteTablet(int tabletid) {

    }
}
