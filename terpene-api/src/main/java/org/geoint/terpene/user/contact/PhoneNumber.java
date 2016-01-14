package org.geoint.terpene.user.contact;

import java.io.Serializable;

/**
 *
 */
public class PhoneNumber implements Serializable {

    private final static long serialVersionUID = 1L;
    private String network = "COMMERCIAL";
    private String number;

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PhoneNumber other = (PhoneNumber) obj;
        if ((this.network == null) ? (other.network != null) : !this.network.equals(other.network)) {
            return false;
        }
        if ((this.number == null) ? (other.number != null) : !this.number.equals(other.number)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.network != null ? this.network.hashCode() : 0);
        hash = 37 * hash + (this.number != null ? this.number.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return network + ": " + number;
    }
}
