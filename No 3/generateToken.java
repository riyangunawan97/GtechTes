@Entity
public class PasswordResetToken {
 
    private static final int EXPIRATION = 60 * 24;
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    private String token;
 
    @OneToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "customerId")
    private Customer customer;
 
    private Date expiryDate;
}

public GenericResponse resetPassword(HttpServletRequest request, 
  @RequestParam("email") String customerEmail) {
    Customer customer = customerService.findCustomerByEmail(customerEmail);
    if (customer == null) {
        throw new CustomerNotFoundException();
    }
    String token = UUID.randomUUID().toString();
    customerService.createPasswordResetTokenForCustomer(customer, token);
    mailSender.send(constructResetTokenEmail(getAppUrl(request), 
      request.getLocale(), token, customer));
    return new GenericResponse(
      messages.getMessage("message.resetPasswordEmail", null, 
      request.getLocale()));
}

public void createPasswordResetTokenForCustomer(Customer customer, String token) {
    PasswordResetToken myToken = new PasswordResetToken(token, customer);
    passwordTokenRepository.save(myToken);
}

private SimpleMailMessage constructResetTokenEmail(
  String contextPath, Locale locale, String token, Customer customer) {
    String url = contextPath + "/customer/changePassword?token=" + token;
    String message = messages.getMessage("message.resetPassword", 
      null, locale);
    return constructEmail("Reset Password", message + " \r\n" + url, customer);
}

private SimpleMailMessage constructEmail(String subject, String body, 
  Customer customer) {
    SimpleMailMessage email = new SimpleMailMessage();
    email.setSubject(subject);
    email.setText(body);
    email.setTo(customer.getEmail());
    email.setFrom(env.getProperty("support.email"));
    return email;
}

public class GenericResponse {
    private String message;
    private String error;
 
    public GenericResponse(String message) {
        super();
        this.message = message;
    }
 
    public GenericResponse(String message, String error) {
        super();
        this.message = message;
        this.error = error;
    }
}