public class Customer {
    private String name;
    private Account account;
    private String identificationNumber;
    private String phone;
    private String email;
    private Address address;

    Customer(String name, Account account) {
        this.name = name;
        this.account = account;
    }
    public String getName() {
        return name;
    }
    public Account getAccount() {
        return this.account;
    }
    
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() { 
        return address;
    }

    public void setAddress(Address address) { 
        this.address = address;
    }
}