import java.util.*;

public class Customer {
    
    private int custId;
    private String name;
    private String phone;
    private String password;
    private String email;
    private Date createdAt;
    private Date loginTime;

    public Customer(int custId, String name, String phone, String password, String email, Date createdAt, Date loginTime) {
        this.custId = custId;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
        this.loginTime = loginTime;
    }



    public int getCustId() {
        return this.custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
    
    
}