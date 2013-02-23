/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppob.server.ui.controller;

import com.ppob.server.domain.Product;
import com.ppob.server.service.PaymentService;
import java.net.URI;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriTemplate;

/**
 *
 * @author opaw
 */

@Controller
public class ProductController {
    
    @Autowired private PaymentService paymentService;
    
    @RequestMapping(value="/master/product", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid Product product, HttpServletRequest request, HttpServletResponse response){
        paymentService.save(product);
        String requestUrl = request.getRequestURL().toString();
        URI uri = new UriTemplate("{requestUrl}/{id}").expand(requestUrl, product.getId());
        response.setHeader("Location", uri.toASCIIString());
    }
    
    @RequestMapping(value="/master/product/{id}", method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id){
        Product productDb = paymentService.findProduckById(id);
        if(productDb == null){
            throw new IllegalStateException();
        }
        paymentService.delete(productDb);
    }
    
    @RequestMapping(value="/master/product/{id}", method= RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable String id, @RequestBody @Valid Product product){
        Product productDb = paymentService.findProduckById(id);
        if(productDb == null){
            throw new IllegalStateException();
        }
        product.setId(productDb.getId());
        paymentService.save(product);
    }
    
    @RequestMapping(value="/master/product", method= RequestMethod.GET)
    @ResponseBody
    public List<Product> findAll(Pageable pagination){
        return paymentService.findAllProduck(pagination).getContent();
        
    }
    
    @RequestMapping(value="/master/product/{id}", method= RequestMethod.GET)
    @ResponseBody
    public Product findById(@PathVariable String id){
        return paymentService.findProduckById(id);
    }
}
