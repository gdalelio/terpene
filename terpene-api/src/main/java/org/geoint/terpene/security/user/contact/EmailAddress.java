package org.geoint.terpene.security.user.contact;

import java.io.Serializable;

/**
 *
 */
public class EmailAddress implements Serializable {

    private final static long serialVersionUID = 1L;
    private String domain = "INTERNET";
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmailAddress other = (EmailAddress) obj;
        if ((this.domain == null) ? (other.domain != null) : !this.domain.equals(other.domain)) {
            return false;
        }
        if ((this.address == null) ? (other.address != null) : !this.address.equals(other.address)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.domain != null ? this.domain.hashCode() : 0);
        hash = 59 * hash + (this.address != null ? this.address.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return address;
    }
}
