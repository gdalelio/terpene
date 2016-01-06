package terpene.user.contact;

import java.io.Serializable;
import java.util.Set;

/**
 *
 */
public interface ContactInfo extends Serializable {

    Set<EmailAddress> getEmailAddresses();

    String getName();

    Set<PhoneNumber> getPhoneNumbers();
}
