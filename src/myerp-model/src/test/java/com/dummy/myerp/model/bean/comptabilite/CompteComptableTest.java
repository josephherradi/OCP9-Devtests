package com.dummy.myerp.model.bean.comptabilite;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CompteComptableTest {

	   private static CompteComptable vCompte;
	   private static List<CompteComptable> vList;

	    @Before
	    public void init() {
	        vCompte = new CompteComptable();
	        vCompte.setNumero(512);
	        vCompte.setLibelle("Banque");
	        
	        vList = new ArrayList<>(0);
	        vList.add(vCompte);
	        vList.add(new CompteComptable(401, "Fournisseurs"));
	    	


	    }

	    @After
	    public void tearDownAll() {
	        vCompte = null;
	        vList=null;
	    }

	    @Test
	    public void getByNumero() {
	        assertEquals(CompteComptable.getByNumero(vList, 512), vCompte);
	    }
	}


