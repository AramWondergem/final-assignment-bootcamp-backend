package nl.wondergem.wondercooks.service;

import nl.wondergem.wondercooks.model.EmailDetails;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);

}
