/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package order;

import customer.Customer;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import product.Product;

/**
 *
 * @author rajes
 */
@ManagedBean
@RequestScoped
//this is controller class for order which is reponsible for interacting with EJB class and JSF
public class OrderController {

    /**
     * Creates a new instance of OrderController
     */
    @EJB
    private OrderEJB orderEJB;

    private Customer customer = new Customer();//create an instance of customer
    private Product product = new Product();//create an instance of product
    private Orders order = new Orders();//create an instace of order
    private List<Orders> orderList = new ArrayList<>();//create orderlist
    private List<Orders> orderSearchList = new ArrayList<>();//create ordersearchlist

    public OrderController() {
    }

    //create order
    public String doCreateOrder() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            order = new Orders(new Date(), customer, product, order.getQuantity());
            //check product stock
            int stock = orderEJB.getStock(order);//get stock
            if(stock<order.getQuantity()){//check stock 
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Stock is not sufficient",""));
                return null;
            }
            order = orderEJB.createOrder(order);//create order
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully created", " the order for: " + order.getCustomer().getName()));
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Order hasn't been created", e.getMessage()));
        }
        return "listOrders.faces";
    }

    //search an order
    public String searchOrder() {
        setOrderSearchList(orderEJB.searchOrder(order));
        return "searchOrderResult.faces";
    }

    //delete order
    public String deleteOrder(Orders order) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            order = orderEJB.deleteOrder(order);
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully deleted", " the order for: " + order.getCustomer().getName()));
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Order hasn't been deleted", e.getMessage()));
        }
        return "listOrders.faces";
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @return the order
     */
    public Orders getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(Orders order) {
        this.order = order;
    }

    /**
     * @return the orderList
     */
    public List<Orders> getOrderList() {
        orderList = orderEJB.findOrders();
        return orderList;
    }

    /**
     * @param orderList the orderList to set
     */
    public void setOrderList(List<Orders> orderList) {
        this.orderList = orderList;
    }

    
    /**
     * @param orderSearchList the orderSearchList to set
     */
    public void setOrderSearchList(List<Orders> orderSearchList) {
        this.orderSearchList = orderSearchList;
    }

    /**
     * @return the orderSearchList
     */
    public List<Orders> getOrderSearchList() {
        return orderSearchList;
    }
}
