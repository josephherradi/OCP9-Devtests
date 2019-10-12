package com.dummy.myerp.consumer.impl.db.dao;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;

public class ComptabiliteDaoImplTest extends AbstractDbConsumer{
	
	
	
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
public void getListCompteComptable() {
    List<CompteComptable> vList = dao.getListCompteComptable();
    assertEquals(7, vList.size());
}
@Test
public void getListJournalComptable() {
    List<JournalComptable> vListJournal = dao.getListJournalComptable();
    assertEquals(5, vListJournal.size());
}

@Test
public void getListEcritureComptable() {
    List<EcritureComptable> vListEcriture = dao.getListEcritureComptable();
    assertEquals(5, vListEcriture.size());
}

@Test
public void getEcritureComptable() throws NotFoundException {
    EcritureComptable vEcritureComptable = dao.getEcritureComptable(-2);
    assertEquals("VE-2016/00002", vEcritureComptable.getReference());
}

@Test
public void getEcritureComptableByRef() throws NotFoundException {
    EcritureComptable vEcritureComptable = dao.getEcritureComptableByRef("VE-2016/00004");
    assertEquals("VE", vEcritureComptable.getJournal().getCode());
    String vEcritureAnnee = new SimpleDateFormat("yyyy").format(vEcritureComptable.getDate());
    assertEquals("2016", vEcritureAnnee);
    assertEquals(-4, vEcritureComptable.getId().intValue());

}

@Test
public void loadListLigneEcriture() {
    EcritureComptable vEcritureComptable = new EcritureComptable();
    vEcritureComptable.setId(-2);
    dao.loadListLigneEcriture(vEcritureComptable);
    assertEquals(3, vEcritureComptable.getListLigneEcriture().size());
}

@Test
public void insertEcritureComptable() {
    EcritureComptable vEcritureComptable = new EcritureComptable();
    vEcritureComptable.setJournal(new JournalComptable("BQ", "Banque"));
    vEcritureComptable.setReference("BQ-" + "2019" + "/00003");
    vEcritureComptable.setDate(new Date());
    vEcritureComptable.setLibelle("test");

    vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(606),
            "test", new BigDecimal(8),
            null));
    vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(4456),
            "TVA 20%", new BigDecimal(4),
            null));
    vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
            "Facture test", null,
            new BigDecimal(12)));

    dao.insertEcritureComptable(vEcritureComptable);
    dao.deleteEcritureComptable(vEcritureComptable.getId());
}



}
