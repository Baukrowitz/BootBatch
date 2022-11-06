package com.example.batchprocessing;

import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AbrechnungGevoTasklet implements Tasklet, StepExecutionListener {
	@Autowired
	private KontoAbrechnungDAO kontoAbrechnungDAO;
	
	private List<KontoAbrechnung> ka; // = kontoAbrechnungDAO.alleKontoAbrechnung();
	private KontoAbrechnung kontoAbrechnungAlt;
	private void kontoAnfangExec() {
		System.out.println(kontoAbrechnungAlt.getIdKonto());
		// if (ka.get(idx).getIdKonto()==3)gevoid--;
	};
	private void kontoEndeExec() {
		// return RepeatStatus.CONTINUABLE;
	};
	private void abrechnungAnfangExec() {
		System.out.println(kontoAbrechnungAlt.getIdAbrechnung());
	};
	private void abrechnungEndeExec() {};
	private int idx= 0;
	private int gevoid = 1;
	private Gevo gevo = new Gevo();
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
	    System.out.println("start execute");
		// TODO Auto-generated method stub
		if (idx==0)
	     ka = kontoAbrechnungDAO.alleKontoAbrechnung();
	//	kontoAbrechnungAlt = (KontoAbrechnung) ka.get(0);
	   // int idx = 0;
     // for (int idx = 0; idx < ka.size(); idx++) {
	    while (idx < ka.size()){
			kontoAbrechnungAlt = (KontoAbrechnung) ka.get(idx);
			this.kontoAnfangExec();
			while ( idx < ka.size() &&
					ka.get(idx).getIdKonto()==kontoAbrechnungAlt.getIdKonto()) {
				kontoAbrechnungAlt = (KontoAbrechnung) ka.get(idx);
				this.abrechnungAnfangExec();
				while ( idx < ka.size() &&
						ka.get(idx).getIdKonto()==kontoAbrechnungAlt.getIdKonto() &&
						ka.get(idx).getIdAbrechnung()==kontoAbrechnungAlt.getIdAbrechnung()) {
				   // Detailverarbeitung
				   System.out.println(ka.get(idx).getBetrag());
				   this.gevo.setIdgevo(gevoid); 
				   this.gevo.setIdKonto(ka.get(idx).getIdKonto());
				   this.gevo.setIdAbrechnung(ka.get(idx).getIdAbrechnung());
				   this.gevo.setLakey(ka.get(idx).getLakey());
				   this.gevo.setPem(9);
				   this.gevo.setGevoart("U");
				   this.gevo.setStornoart("U");
				   this.gevo.setBetrag(ka.get(idx).getBetrag());
				   this.gevo.setTilgbezug(gevoid); 
				   System.out.println(gevo.getIdKonto());
				   int i = kontoAbrechnungDAO.insertGevo(gevo);
				   
				   gevoid++;
				   this.gevo.setIdgevo(gevoid); 
				   this.gevo.setPem(40);
				   this.gevo.setBetrag(this.gevo.getBetrag().negate());
				   i = kontoAbrechnungDAO.insertGevo(gevo);
				   gevoid++;
				   //nachlesen
				   idx++;
				} 
				this.abrechnungEndeExec();
			}
			this.kontoEndeExec();
			return RepeatStatus.CONTINUABLE;
		};
		
		return RepeatStatus.FINISHED;
	}
	@Override
	public void beforeStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		kontoAbrechnungDAO.deleteGevo();
		
	}
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		return null;
	}

}

	
