package com.example.batchprocessing;

import org.springframework.batch.item.ItemProcessor;

public class AbrechnungGevoItemProcessor implements ItemProcessor<KontoAbrechnung,Gevo> {
    int gevoid = 90;
	@Override
	public Gevo process(KontoAbrechnung item) throws Exception {
		// TODO Auto-generated method stub
		gevoid++;
		Gevo gevo = new Gevo();
		   gevo.setIdgevo(gevoid); 
		   gevo.setIdKonto(item.getIdKonto());
		   gevo.setIdAbrechnung(item.getIdAbrechnung());
		   gevo.setLakey(gevo.getLakey());
		   gevo.setPem(9);
		   gevo.setGevoart("U");
		   gevo.setStornoart("U");
		   gevo.setBetrag(item.getBetrag());
		   gevo.setTilgbezug(gevoid); 
		return gevo;
	}
	

}
