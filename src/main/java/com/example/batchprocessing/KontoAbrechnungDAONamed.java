package com.example.batchprocessing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@Primary
public class KontoAbrechnungDAONamed implements KontoAbrechnungDAO {
	

	
	    
		@Autowired
		DataSource dataSource;
		
		NamedParameterJdbcTemplate jdbcTemplate;
		
		private final String SQL1 = "select K.*, A.* from db2_Konto K join db2_abrechnung A on A.idkonto = K.idkonto order by A.Idkonto, A.Idabrechnung";
		private final String SQL2 = "insert into db2_gevo (idgevo,idkonto,idabrechnung,pem,lakey,gevoart,stornoart,betrag,tilgbezug)"
				+ "values(:idgevo,:idKonto,:idAbrechnung,:pem,:lakey,:gevoart,:stornoart,:betrag,:tilgbezug)";
		
		@Autowired
		public KontoAbrechnungDAONamed(DataSource dataSource) {
			
			this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			
		}
		
		@Override
		public List<KontoAbrechnung> alleKontoAbrechnung() {
			// TODO Auto-generated method stub
			
			
			return jdbcTemplate.query(SQL1, 
					(resultset,i) -> {  
						return toKontoAbrechnung(resultset);
					}
					);
		}

		@Override
		public int insertGevo(Gevo gevo) {;
			// TODO Auto-generated method stub
			return
			jdbcTemplate.update(SQL2, new BeanPropertySqlParameterSource(gevo));
			
			
		}

		@Override
		public int deleteGevo() {
			// TODO Auto-generated method stub
			return jdbcTemplate.update("delete from db2_gevo",(MapSqlParameterSource)null);
		}
		
		private KontoAbrechnung toKontoAbrechnung(ResultSet rs) throws SQLException {
		
			KontoAbrechnung kontoAbrechnung = new KontoAbrechnung();
			kontoAbrechnung.setIdAbrechnung(rs.getInt(3));
			kontoAbrechnung.setIdKonto(rs.getInt(1));
			kontoAbrechnung.setLakey(rs.getInt(5));
			kontoAbrechnung.setName(rs.getString(2));
			kontoAbrechnung.setBetrag(rs.getBigDecimal(6));
			return kontoAbrechnung;
			
		}
}
