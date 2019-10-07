package com.dummy.myerp.consumer.impl.db.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;

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
}