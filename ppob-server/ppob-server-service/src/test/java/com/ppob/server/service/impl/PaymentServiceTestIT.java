/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppob.server.service.impl;

import com.ppob.server.domain.Payment;
import static org.junit.Assert.*;
import com.ppob.server.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author opaw
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:com/ppob/server/**/applicationContext.xml")
public class PaymentServiceTestIT {
    
    @Autowired private TransactionService paymentService;
    
    @Test
    public void testFindAll(){
        Page<Payment> result = paymentService.findAllPayment(new PageRequest(0, 10));
        assertEquals(new Integer(1), new Integer(result.getNumberOfElements()));
    }
    
    @Test
    public void testFindById(){
        Payment p = paymentService.findPaymentById("1");
        assertNotNull(p);
        assertEquals("2013-02-23", p.getTime());
        assertEquals("1a", p.getLocket());
        assertEquals("1", p.getBill().getId());
    
        assertNull(paymentService.findPaymentById(null));
        assertNull(paymentService.findPaymentById(""));
    }
    
}
