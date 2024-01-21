/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package customer;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import order.Orders;

/**
 *
 * @author sagar
 */
@ManagedBean
@RequestScoped
//this is managed backing bean which interact with  presentation tier (JSF pages) and business tier (EJB)
public class CustomerController {

    /**
     * Creates a new instance of CustomerController
     */
    @EJB
    private CustomerEJB customerEJB;//create an instace of CustomerEJB

    private Customer customer = new Customer();//create an instace of customer
    private List<Customer> customerList = new ArrayList<Customer>();//create customer list
    private List<Customer> customerSearchList = new ArrayList<Customer>();//create search customer list
    private List<Orders> orderList = new ArrayList<>();//create order list

    //create default constructor
    public CustomerController() {

    }

    //create customer
    public String doCreateCustomer() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            customer = customerEJB.createCustomer(customer);
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully created", " the customer: " + customer.getName()));
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Customer hasn't been created", e.getMessage()));
        }
        return "listCustomers.faces";
    }

    //search customer
    public String searchCustomer() {
        System.out.println("Searching customer");
        customerSearchList = customerEJB.searchCustomer(customer);
        return "searchCustomerResult.faces";
    }

    //return number of order of customer
    public int countOrder(Customer customer) {
        this.setOrderList(customerEJB.listOrderByCustomer(customer));
        int numberOfOrder = this.orderList.size();
        return numberOfOrder;
    }

    //view customer details
    public String viewDetails(Customer customer) {
        this.customer = customer;
        this.setOrderList(customerEJB.listOrderByCustomer(customer));
        return "customers.faces";
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
     * @return the customerList
     */
    public List<Customer> getCustomerList() {
        customerList = customerEJB.findCustomers();
        return customerList;
    }

    /**
     * @param customerList the customerList to set
     */
    public void setCustomerList(List<Customer> customerList) {
        customerList = customerEJB.findCustomers();
        this.customerList = customerList;
    }

    /**
     * @return the customerSearchList
     */
    public List<Customer> getCustomerSearchList() {
        return customerSearchList;
    }

    /**
     * @param customerSearchList the customerSearchList to set
     */
    public void setCustomerSearchList(List<Customer> customerSearchList) {
        this.customerSearchList = customerSearchList;
    }

    /**
     * @return the orderList
     */
    public List<Orders> getOrderList() {
        return orderList;
    }

    /**
     * @param orderList the orderList to set
     */
    public void setOrderList(List<Orders> orderList) {
        this.orderList = orderList;
    }

}
