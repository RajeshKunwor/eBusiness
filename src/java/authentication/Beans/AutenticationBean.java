package Authentication.Beans;

import java.io.Serializable;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.Resource;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.UserTransaction;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;

/**
 *
 * @author rajesh
 */
@ManagedBean (name="authBean") 
@SessionScoped
//Managed Bean class  which handles authentication functions
public class AutenticationBean implements Serializable {
    
    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    //The session username and password
    private String username;
    private String password;
    //The verfication password
    private String passwordv;
    //The user first name
    private String fname;
    //The user last name
    private String lname;
    //The user email address
    private String email;
    //The verfication code entered by the user
    private String verificationcode;
    //The verficvation code that is sent to the user's emnail
    private String verificationcode1;
    //Login state flag
    private boolean Logged=false;
    private boolean recovery=false;
    /*The user entity 
        1. consisting of first name, last name, username, password, email and since date
        2. providing JPQL queries based on the above attributes*/
    private Wuser ruser;//create instance of wuser
    
    //create default contructor
    public AutenticationBean() {
    }
    //check user is logged in or not
    public boolean isLogged() {
        return Logged;
    }
    
    //getter and setters
    public void setLogged(boolean Logged) {
        this.Logged = Logged;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPasswordv() {
        return passwordv;
    }
    public void setPasswordv(String passwordv) {
        this.passwordv = passwordv;
    }
    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public String getLname() {
        return lname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getVerificationcode() {
        return verificationcode;
    }
    public void setVerificationcode(String verificationcode) {
        this.verificationcode = verificationcode;
    }
    public String getVerificationcode1() {
        return verificationcode1;
    }
    public void setVerificationcode1(String verificationcode1) {
        this.verificationcode1 = verificationcode1;
    }
    public boolean isRecovery() {
        return recovery;
    }
    public void setRecovery(boolean recovery) {
        this.recovery = recovery;
    }
    public Wuser getRuser() {
        return ruser;
    }
    public void setRuser(Wuser ruser) {
        this.ruser = ruser;
    }
    //Generate the hash code of a password
    public String HashConvert(String oripassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(oripassword.getBytes());
            byte byteData[] = md.digest();
            //convert the byte to hex format
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
                throw new UnsupportedOperationException(e);
        }
    }
    /*Validate an existing user by checking
        1. if the user is a registered user
        2. if the username and password are correct*/
    public String validateUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        Wuser user = getUser();
        if (user != null) {
	    String p1=user.getPassword();
            String p2=HashConvert(password);
        if (!p1.equals(p2)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "Login Failed!", "The password specified is not correct.");
            context.addMessage(null, message);
                return null;
            }
            Logged=true;
            return "default.xhtml?faces-redirect=true";
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Login Failed!", "Username '"+ username+"' does not exist.");
            context.addMessage(null, message);
            return null;
        }
    }
    /*Recover a user by email address*/
    public void recoverUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        Wuser user = getUserbyEmail();
        if (user != null) {
            this.ruser=user;
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
               "Invalid Email!", "Email '"+ email+"' does not exist.");
            context.addMessage(null, message);
        }
    }
    //Retrieve a user by username
    private Wuser getUser() {
        try {
            Wuser user = (Wuser)
            em.createNamedQuery("Wuser.findByUsername").
                    setParameter("username", username).getSingleResult();
            return user;
        } catch (NoResultException nre) {
            return null;
        }
    }
    //Retrieve a user by email address
    private Wuser getUserbyEmail() {
        try {
            Wuser user = (Wuser)
            em.createNamedQuery("Wuser.findByEmail").
                    setParameter("email", email).getSingleResult();
            return user;
        } catch (NoResultException nre) {
            return null;
        }
    }
    //Generate a recovery string of 20 character length 
    public String createRecoveryCode(){
        recovery=true;
        String urpage=createRandomCode();
        return urpage;
    }
    //Generate a verification ccode for a new user who is registering
    public String createVerificationCode(){
        recovery=false;
        Wuser usr=this.getUserbyEmail();
        if (usr==null) {
            String urpage=createRandomCode();
            return urpage;
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "Email '" + email + "' already registered!  Please choose a different email.",
                                        "Please choose a different email.");
            context.addMessage(null, message);
            this.email=null;
            return null;
        }
    }
    //Generate a random verification code and send it to the user's email address
    public String createRandomCode() {
        //The sending of email uses a fake SMTP server
        Properties props = new Properties();
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.port", 2525);
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("CENTRE@glassfish.com"));
            InternetAddress[] address = {new InternetAddress(email)};
            message.setRecipients(Message.RecipientType.TO, address);
            if (!recovery) {
                message.setSubject("The Verification Code");
            } else {
                message.setSubject("The Recovery Code");
            }
            message.setSentDate(new Date());
            if (!recovery) {
                message.setText("The Verification Code: "+genVerificationCode());
            } else {
                message.setText("The Recovery Code: "+genVerificationCode());
            }
            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        if (recovery) {
            recovery=false;
            ruser=getUserbyEmail();
            return "userRecovery.xhtml";
        } else return "register.xhtml";
        
    }
    //Generate a random string of length of 20 characters
    public String genVerificationCode() {
        //The character pool
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "1234567890"
                + "abcdefghijklmnopqrstuvwxyz"
                + "!@#$%&*-+=?";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 20) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        this.verificationcode1=saltStr;
        Logger.getLogger(AutenticationBean.class.getName()).log(Level.INFO, "Recovery Code: "+saltStr);
        return saltStr;
    }
    //Register a user into database
    public String createUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        Wuser wuser = getUser();
        Wuser euser=this.getUserbyEmail();
        if ((wuser == null) && (euser==null)) {
            if (!password.equals(passwordv)) {
                FacesMessage message = new FacesMessage("The specified passwords do not match,  please try again!");
                context.addMessage(null, message);
                return null;
            }
            if (!verificationcode.equals(verificationcode1)) {
                FacesMessage message = new FacesMessage("Wrong verification code, please try again!");
                context.addMessage(null, message);
                return null;
            }
            wuser = new Wuser();
            wuser.setFirstname(fname);
            wuser.setLastname(lname);
            wuser.setPassword(HashConvert(password));
            wuser.setUsername(username);
            wuser.setSince(new Date());
            wuser.setEmail(email);
            try {
                utx.begin();
                em.persist(wuser);
                utx.commit();
                //Reset the user
                wuser=null;
                username=null;
                password=null;
                passwordv=null;
                fname=null;
                lname=null;
                verificationcode=null;
                verificationcode1=null;
                email=null;
                return "index.xhtml?faces-redirect=true";
            } catch (Exception e) {
                FacesMessage message = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "Error creating user!",
                        "Unexpected error when creating your account.  Please contact the system Administrator");
                context.addMessage(null, message);
                Logger.getAnonymousLogger().log(Level.SEVERE, "Unable to create new user",e);
                return null;
            }
        } else {
            if (!(wuser==null)) {
                FacesMessage message = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "Username '" + username
                        + "' already registered!  Please choose a different username.",
                        "Please choose a different username.");
                context.addMessage(null, message);
                return null; 
            } else {
                FacesMessage message = new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "Email '"+ email
                        + "' already registered!  Please choose a different email.",
                        "Please choose a different email.");
                context.addMessage(null, message);
                this.verificationcode=null;
                return "emailVerification.xhtml";
            }
        }
    }
    //Reset a user from the recovery procedure
    public String resetUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!password.equals(passwordv)) {
            FacesMessage message = new FacesMessage("The specified passwords do not match,  please try again!");
            context.addMessage(null, message);
            return null;
        }
        if (!verificationcode.equals(verificationcode1)) {
            FacesMessage message = new FacesMessage("Wrong recovery code, please try again!");
            context.addMessage(null, message);
            return null;
        }
        ruser.setPassword(HashConvert(password));
        try {
            utx.begin();
            em.merge(ruser);
            utx.commit();
            //Rest the user
            ruser=null;
            username=null;
            password=null;
            passwordv=null;
            fname=null;
            lname=null;
            verificationcode=null;
            verificationcode1=null;
            email=null;
            return "index.xhtml?faces-redirect=true";
        } catch (Exception e) {
          FacesMessage message = new FacesMessage(
                  FacesMessage.SEVERITY_ERROR, "Error recovering user!",
                  "Unexpected error when recovering your account.  Please contact the system Administrator");
          context.addMessage(null, message);
          Logger.getAnonymousLogger().log(Level.SEVERE, "Unable to recover user", e);
          return null;
        }
    }   
}
