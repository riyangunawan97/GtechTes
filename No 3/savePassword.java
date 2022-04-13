package No 3;

public String showChangePasswordPage(Locale locale, Model model, 
  @RequestParam("token") String token) {
    String result = securityService.validatePasswordResetToken(token);
    if(result != null) {
        String message = messages.getMessage("auth.message." + result, null, locale);
        return "redirect:/login.html?lang=" 
            + locale.getLanguage() + "&message=" + message;
    } else {
        model.addAttribute("token", token);
        return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
    }
}

public String validatePasswordResetToken(String token) {
    final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);

    return !isTokenFound(passToken) ? "invalidToken"
            : isTokenExpired(passToken) ? "expired"
            : null;
}

private boolean isTokenFound(PasswordResetToken passToken) {
    return passToken != null;
}

private boolean isTokenExpired(PasswordResetToken passToken) {
    final Calendar cal = Calendar.getInstance();
    return passToken.getExpiryDate().before(cal.getTime());
}

public GenericResponse savePassword(final Locale locale, @Valid PasswordDto passwordDto) {

    String result = securityCustomerService.validatePasswordResetToken(passwordDto.getToken());

    if(result != null) {
        return new GenericResponse(messages.getMessage(
            "auth.message." + result, null, locale));
    }

    Optional customer = customerService.getCustomerByPasswordResetToken(passwordDto.getToken());
    if(customer.isPresent()) {
        customerService.changeCustomerPassword(customer.get(), passwordDto.getNewPassword());
        return new GenericResponse(messages.getMessage(
            "message.resetPasswordSuc", null, locale));
    } else {
        return new GenericResponse(messages.getMessage(
            "auth.message.invalid", null, locale));
    }
}

public GenericResponse savePassword(final Locale locale, @Valid PasswordDto passwordDto) {

    String result = securityCustomerService.validatePasswordResetToken(passwordDto.getToken());

    if(result != null) {
        return new GenericResponse(messages.getMessage(
            "auth.message." + result, null, locale));
    }

    Optional customer = customerService.getCustomerByPasswordResetToken(passwordDto.getToken());
    if(customer.isPresent()) {
        customerService.changeCustomerPassword(customer.get(), passwordDto.getNewPassword());
        return new GenericResponse(messages.getMessage(
            "message.resetPasswordSuc", null, locale));
    } else {
        return new GenericResponse(messages.getMessage(
            "auth.message.invalid", null, locale));
    }
}

public class PasswordDto {

    private String oldPassword;

    private  String token;

    @ValidPassword
    private String newPassword;
}