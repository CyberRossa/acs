package org.example.services;

import org.example.dao.IcmalDAO;
import org.example.model.ACSPLUSInput;
import org.example.model.ACSPLUSOutput;
import org.example.model.IcmalRow;

import java.util.ArrayList;
import java.util.List;

public class IcmalService {

    private final IcmalDAO icmalDAO;

    public IcmalService(DatabaseService databaseService) {
        this.icmalDAO = new IcmalDAO(databaseService);
    }

    public boolean addIcmalRow(IcmalRow row) {
        return icmalDAO.insert(row);
    }

    public List<IcmalRow> getAllIcmalRows() {
        return icmalDAO.findAll();
    }

    public boolean clearIcmalRows() {
        return icmalDAO.deleteAll();
    }
    public List<IcmalRow> generateIcmalRows(ACSPLUSInput input, ACSPLUSOutput output) {
        List<IcmalRow> rows = new ArrayList<>();
        // Hesaplamalara göre IcmalRow nesnelerini oluştur
        // Örnek:
        IcmalRow row = new IcmalRow();
        row.setDevice("Cihaz 1");
       // row.setCapacity(input.capacity);
        row.setAirflow(input.totalAirflow);
        row.setPower(output.automationPower);
        row.setPrice(1000); // Örnek fiyat
        row.setTotal(row.getPrice() * 1); // Örnek toplam
        rows.add(row);

        // Diğer hesaplamalar ve satırlar...

        return rows;
    }

}
