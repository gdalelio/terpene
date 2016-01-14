
package org.geoint.terpene.user;

import java.io.Serializable;
import org.geoint.terpene.user.contact.ContactInfo;

/**
 *
 */
public interface Person extends Serializable {

    ContactInfo getContactInfo();
}
