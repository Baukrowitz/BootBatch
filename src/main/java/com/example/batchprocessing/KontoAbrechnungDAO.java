package com.example.batchprocessing;
import java.util.List;
public interface KontoAbrechnungDAO {
    public List<KontoAbrechnung> alleKontoAbrechnung();
    public int insertGevo(Gevo gevo);
    public int deleteGevo();
}
