package com.example.batchprocessing;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


public class KontoAbrechnungRowMapper implements RowMapper<KontoAbrechnung> {

	@Override
	public KontoAbrechnung mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		KontoAbrechnung kontoAbrechnung = new KontoAbrechnung();
		kontoAbrechnung.setIdAbrechnung(rs.getInt("idabrechnung"));
		kontoAbrechnung.setIdKonto(rs.getInt("idkonto"));
		kontoAbrechnung.setLakey(rs.getInt("lakey"));
		kontoAbrechnung.setName(rs.getString("name"));
		kontoAbrechnung.setBetrag(rs.getBigDecimal("betrag"));
		return kontoAbrechnung;
	}

}