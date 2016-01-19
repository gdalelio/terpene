
package org.geoint.terpene.security.user;

import java.io.Serializable;
import org.geoint.terpene.security.user.contact.ContactInfo;

/**
 *
 */
public interface Person extends Serializable {

    ContactInfo getContactInfo();
}
