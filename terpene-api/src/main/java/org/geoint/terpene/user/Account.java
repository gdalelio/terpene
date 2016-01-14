
package org.geoint.terpene.user;

import java.io.Serializable;

/**
 * An account allows a user to access the system using their stored credentials.
 * 
 * In other words, without an account, a Person or Agent can not authenticate.
 * 
 */
public interface Account extends Serializable{

    AccountId getId();
    
}
