/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package order;

import customer.Customer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;
import product.Product;

/**
 *
 * @author rajes
 */
//this is EJB class for order
@Stateless
public class OrderEJB {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "ebusinessPU")
    private EntityManager em;

    //list customers           
    public List<Orders> findOrders() {
        Query query = em.createNamedQuery("findAllOrders");
        System.out.println(query.getResultList());
        return query.getResultList();
    }

    //get product stock number
    public int getStock(Orders order) {
        Query q = em.createNamedQuery("Product.findById").setParameter("id", order.getItem().getId());
        Product prod = new Product();//create instance of product
        prod = (Product) q.getSingleResult();//casting query result to product type
        int stockNumber = prod.getStockNumber();//get stock of the product
        return stockNumber;//return stock number
    }

    //create order and update stock
    @Transactional()//make automic transaction
    public Orders createOrder(Orders order) {
        em.persist(order);//persist data into database
        int orderQty = order.getQuantity();//get order quanity
        Query q = em.createNamedQuery("Product.findById").setParameter("id", order.getItem().getId());
        Product prod = new Product();//create instance of product
        prod = (Product) q.getSingleResult();//casting query result to product type

        int stockNumber = prod.getStockNumber();//get stock of the product
        int stockLeft = stockNumber - orderQty;//deduct the order quanity from the stock
        Query query = em.createNamedQuery("Product.updateStock");
        query.setParameter("stockNumber", stockLeft);
        query.setParameter("id", order.getItem().getId());
        query.executeUpdate();//update the stock into database

        Customer customer = new Customer();//create instace of customer
        Query custQuery = em.createNamedQuery("Customer.findById").setParameter("id", order.getCustomer().getId());
        customer = (Customer) custQuery.getSingleResult();
        order.getCustomer().setName(customer.getName());
        return order;//returns the order
    }

    //delete order
    @Transactional()//make automic transaction
    public Orders deleteOrder(Orders order) {
        Query query = em.createNamedQuery("Orders.deleteOrder").setParameter("id", order.getId());
        query.executeUpdate();//execute query

        //update stock after delete the order
        int stockNumber = order.getItem().getStockNumber() + order.getQuantity();
        Query queryUpdate = em.createNamedQuery("Product.updateStock");
        queryUpdate.setParameter("stockNumber", stockNumber);
        queryUpdate.setParameter("id", order.getItem().getId());
        queryUpdate.executeUpdate();
        return order;
    }

    //search an order
    public List<Orders> searchOrder(Orders order) {
        Query query = em.createNamedQuery("Orders.findById").setParameter("id", order.getId());
        return query.getResultList();
    }
}
