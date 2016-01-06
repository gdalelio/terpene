
package terpene.user;

import java.io.Serializable;
import terpene.user.contact.ContactInfo;

/**
 *
 */
public interface Person extends Serializable {

    ContactInfo getContactInfo();
}
