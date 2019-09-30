package com.dummy.myerp.model.bean.comptabilite;



import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class JournalComptableTest {

    private static JournalComptable vJournal;
    private static List<JournalComptable> vList;

    @Before
    public void init() {
        vJournal = new JournalComptable();
        vJournal.setCode("OD");
        vJournal.setLibelle("Opérations Diverses");
    	
        vList = new ArrayList<>(0);
        vList.add(vJournal);
        vList.add(new JournalComptable("VE", "Vente"));
    	


    }

    @After
    public void tearDownAll() {
        vJournal = null;
        vList=null;
    }

    @Test
    public void getByCode() {
        assertEquals(JournalComptable.getByCode(vList, "OD"), vJournal);
    }
}