package com.dummy.myerp.business.impl.manager;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;


public class ComptabiliteManagerImplTest extends AbstractBusinessManager {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
    private ClassPathXmlApplicationContext appContext;
    private static ComptabiliteDaoImpl dao;
    
    @Before
    public void setUp() throws Exception{
    appContext= new ClassPathXmlApplicationContext("classpath:testContext.xml");
    dao = new ComptabiliteDaoImpl();

    }
    
    @After
    public void tearDown() throws Exception{
    	appContext=null;
    	dao=null;
    }
    @Test
    public void checkEcritureComptableUnit() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2019/00001");

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                                                                                 null, null,
                                                                                 new BigDecimal(123)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                                                                                 null, null,
                                                                                 new BigDecimal(1234)));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    @Test
    public void addReference() throws NotFoundException, ParseException{
    	EcritureComptable pEcritureComptable=new EcritureComptable();
    	
    	Integer vEcritureComptableAnnee = 2016;
    	pEcritureComptable.setJournal(new JournalComptable("OD", "Test"));
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    	Date d = sdf.parse("2016/12/31");


    	pEcritureComptable.setDate(d);
    	pEcritureComptable.setLibelle("Test");
    	pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(606),
                null, new BigDecimal(123),
                null));
    	pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, null,
                new BigDecimal(1234)));
    	
    	SequenceEcritureComptable vSequenceRecherche = new SequenceEcritureComptable();
        vSequenceRecherche.setJournalCode("OD");
        vSequenceRecherche.setAnnee(vEcritureComptableAnnee);
        SequenceEcritureComptable vSequenceTrouvee = dao.getSequenceParCodeEtAnnee(vSequenceRecherche);
        Integer vSequenceValeur;
        if (vSequenceTrouvee == null) vSequenceValeur = 1;
        else vSequenceValeur = vSequenceTrouvee.getDerniereValeur() + 1;
        
        String vReferenceEcriture = pEcritureComptable.getJournal().getCode() +
                "-" + vEcritureComptableAnnee +
                "/" + String.format("%05d", vSequenceValeur);
         pEcritureComptable.setReference(vReferenceEcriture);
         pEcritureComptable.setId(-1);
			manager.updateEcritureComptable(pEcritureComptable);
			
		
	        SequenceEcritureComptable vNouvelleSequence= new SequenceEcritureComptable();
	        vNouvelleSequence.setAnnee(vEcritureComptableAnnee);
	        vNouvelleSequence.setJournalCode(pEcritureComptable.getJournal().getCode());
	        vNouvelleSequence.setDerniereValeur(vSequenceValeur);
	        manager.upsertSequenceEcritureComptable(vNouvelleSequence);
			
		assertEquals(manager.getEcritureComptable(pEcritureComptable.getId()).getReference(),vReferenceEcriture);
    }
    
}
