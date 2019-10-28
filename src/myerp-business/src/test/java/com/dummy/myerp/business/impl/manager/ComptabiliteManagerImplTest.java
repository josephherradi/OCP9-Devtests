package com.dummy.myerp.business.impl.manager;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.testbusiness.business.BusinessTestCase;



public class ComptabiliteManagerImplTest extends BusinessTestCase {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
    private static ComptabiliteDaoImpl dao;
    
    @Before
    public void setUp() throws Exception{
    dao = new ComptabiliteDaoImpl();

    }
    
    @After
    public void tearDown() throws Exception{
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
    
    //test updateSequenceEcritureComptable
    @Test
    public void addReference1() throws Exception {
    	EcritureComptable pEcritureComptable=new EcritureComptable();
    	pEcritureComptable.setId(-1);
    	pEcritureComptable.setJournal(new JournalComptable("AC", "Test"));
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    	Date d = sdf.parse("2016/12/31");


    	pEcritureComptable.setDate(d);
    	pEcritureComptable.setLibelle("Cartouches d’imprimante");
    	pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(606),
                null, new BigDecimal(1234),
                null));
    	pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, null,
                new BigDecimal(1234)));
    	
    	manager.addReference(pEcritureComptable);
    	assertEquals(pEcritureComptable.getReference(),dao.getEcritureComptable(-1).getReference());
    	
    	//reinitialisation de l etat initial de la db avant le test
    	
    	SequenceEcritureComptable pSequence= new SequenceEcritureComptable();
    	pSequence.setAnnee(2016);
    	pSequence.setJournalCode("AC");
    	pSequence.setDerniereValeur(40);
    	
    	dao.updateSequenceEcritureComptable(pSequence);
    	
    	pEcritureComptable.setReference("AC-2016/00001");
    	dao.updateEcritureComptable(pEcritureComptable);
    	
    	
    }
    
    //test insertSequenceEcritureComptable
    @Test
    public void addReference2() throws Exception {
    	EcritureComptable pEcritureComptable=new EcritureComptable();
    	pEcritureComptable.setId(-1);
    	pEcritureComptable.setJournal(new JournalComptable("AC", "Test"));
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    	Date d = sdf.parse("2019/12/31");


    	pEcritureComptable.setDate(d);
    	pEcritureComptable.setLibelle("Test");
    	pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(606),
                null, new BigDecimal(1234),
                null));
    	pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, null,
                new BigDecimal(1234)));
    	
    	manager.addReference(pEcritureComptable);
    	assertEquals(pEcritureComptable.getReference(),dao.getEcritureComptable(-1).getReference());
    	
        SequenceEcritureComptable vSequenceRecherche = new SequenceEcritureComptable();
        vSequenceRecherche.setJournalCode(pEcritureComptable.getJournal().getCode());
        Integer vEcritureComptableAnnee = Integer.parseInt(new SimpleDateFormat("yyyy").format(pEcritureComptable.getDate()));
        vSequenceRecherche.setAnnee(vEcritureComptableAnnee);
    	
        assertEquals(Integer.valueOf(2019),dao.getSequenceParCodeEtAnnee(vSequenceRecherche).getAnnee());
        assertEquals("AC",dao.getSequenceParCodeEtAnnee(vSequenceRecherche).getJournalCode());


    	//reinitialisation de l etat initial de la db avant le test

    	dao.deleteSequenceParCodeEtAnnee(vSequenceRecherche);
    	pEcritureComptable.setDate(sdf.parse("2016/12/31"));
    	pEcritureComptable.setLibelle("Cartouches d’imprimante");
    	pEcritureComptable.setReference("AC-2016/00001");
    	dao.updateEcritureComptable(pEcritureComptable);
    	
    	
    	
    }
    
    @Test(expected= FunctionalException.class)
    public void checkEcritureComptableUnitRG5a() throws ParseException, FunctionalException {
    	EcritureComptable pEcritureComptable=new EcritureComptable();
    	pEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    	Date d = sdf.parse("2019/12/31");
    	pEcritureComptable.setDate(d);
    	pEcritureComptable.setLibelle("Libelle");
    	pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
    	pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        pEcritureComptable.setReference("AC-" + "2018" + "/00001");
        manager.checkEcritureComptableUnit(pEcritureComptable);
        
    }
    
    @Test(expected= FunctionalException.class)
    public void checkEcritureComptableUnitRG5b() throws ParseException, FunctionalException {
    	EcritureComptable pEcritureComptable=new EcritureComptable();
    	pEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    	Date d = sdf.parse("2019/12/31");
    	pEcritureComptable.setDate(d);
    	pEcritureComptable.setLibelle("Libelle");
    	pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
    	pEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        pEcritureComptable.setReference("OP-" + "2019" + "/00001");
        manager.checkEcritureComptableUnit(pEcritureComptable);
}
    @Test
    public void checkEcritureComptableContext() throws Exception {
    	EcritureComptable pEcritureComptable=new EcritureComptable();
    	pEcritureComptable.setReference("BQ-2016/00001");
        manager.checkEcritureComptableContext(pEcritureComptable);
    }
    
    @Test(expected= FunctionalException.class)
    public void checkEcritureComptableRG6a() throws Exception {
    	EcritureComptable pEcritureComptable=new EcritureComptable();
    	pEcritureComptable.setReference("BQ-2016/00003");
        manager.checkEcritureComptableContext(pEcritureComptable);
    }
    //
    @Test(expected= FunctionalException.class)
    public void checkEcritureComptableRG6b() throws Exception {
    	EcritureComptable pEcritureComptable=new EcritureComptable();
    	pEcritureComptable.setReference("BQ-2016/00003");
    	pEcritureComptable.setId(0);
        manager.checkEcritureComptableContext(pEcritureComptable);
    }
    
    
}
