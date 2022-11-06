package com.example.batchprocessing;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class KontoAbrechnungDAOImpl implements KontoAbrechnungDAO {
    
	@Autowired
	DataSource dataSource;
	
	JdbcTemplate jdbcTemplate;
	
	private final String SQL1 = "select K.*, A.* from db2_Konto K join db2_abrechnung A on A.idkonto = K.idkonto order by A.Idkonto, A.Idabrechnung";
	private final String SQL2 = "insert into db2_gevo (idgevo,idkonto,idabrechnung,pem,lakey,gevoart,stornoart,betrag,tilgbezug) values(?,?,?,?,?,?,?,?,?)";
	
	@Autowired
	public KontoAbrechnungDAOImpl(DataSource dataSource) {
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		
	}
	
	@Override
	public List<KontoAbrechnung> alleKontoAbrechnung() {
		// TODO Auto-generated method stub
		
		
		return jdbcTemplate.query(SQL1, new KontoAbrechnungRowMapper());
	}

	@Override
	public int insertGevo(Gevo gevo) {
		// TODO Auto-generated method stub
		return
		jdbcTemplate.update(SQL2, gevo.getIdgevo(),gevo.getIdKonto(),gevo.getIdAbrechnung(),gevo.getPem(),gevo.getLakey(),gevo.getGevoart(),gevo.getStornoart(),gevo.getBetrag()
				,gevo.getTilgbezug());
		
		
	}

	@Override
	public int deleteGevo() {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("delete from db2_gevo");
	}
    
}
