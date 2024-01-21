/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package customer;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import order.Orders;


/**
 *
 * @author sagar
 */
@Stateless
//this is an EJB class which is responsible for interacting with entity or database
public class CustomerEJB {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PersistenceContext(unitName = "ebusinessPU")
    private EntityManager em;

    //list customers           
    public List<Customer> findCustomers() {
        Query query = em.createNamedQuery("findAllCustomers");
        return query.getResultList();   
    }
    
  
    //create customer
    public Customer createCustomer(Customer customer) {
        em.persist(customer);
        return customer;
    }

    //search customer
    public List<Customer> searchCustomer(Customer customer) {
        Query query = em.createNamedQuery("Customer.findByName").setParameter("name", customer.getName());
        return query.getResultList();
    }
    
    //list customer order 
    public List<Orders> listOrderByCustomer(Customer customer){
        Query query = em.createNamedQuery("Orders.findByCustomer").setParameter("customer", customer);
        return query.getResultList();
    }

    
}
