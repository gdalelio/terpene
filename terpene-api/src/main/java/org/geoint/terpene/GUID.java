package org.geoint.terpene;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.geoint.terpene.domain.Value;
import org.geoint.terpene.domain.codec.BinaryCodec;
import org.geoint.terpene.domain.codec.CharCodec;
import org.geoint.terpene.domain.codec.InvalidCodecException;
import org.geoint.terpene.GUID.GuidBinaryCodec;
import org.geoint.terpene.GUID.GuidCharCodec;
import org.geoint.terpene.domain.Domain;

/**
 * Globally unique identifier.
 *
 * @author steve_siebert
 */
@Domain(domain = "terpene", version = "(,2.0)")
@Value(name = "guid",
        desc = "Globally Unique Identifier",
        binCodec = GuidBinaryCodec.class,
        charCodec = GuidCharCodec.class)
public final class GUID {

    private final String guid;

    /**
     *
     * @param guid UTF-8 GUID as String
     */
    public GUID(String guid) {
        this.guid = guid;
    }

    /**
     * GUID as a String.
     *
     * @return guid as a UTF-8 String
     */
    public String asString() {
        return guid;
    }

    /**
     * GUID as bytes.
     *
     * @return GUID as bytes
     */
    public byte[] asBytes() {
        return guid.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {
        return guid;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.guid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GUID other = (GUID) obj;
        return Objects.equals(this.guid, other.guid);
    }

    public static class GuidCharCodec implements CharCodec<GUID> {

        @Override
        public void write(GUID data, PrintWriter writer) throws IOException {
            writer.write(data.asString());
        }

        @Override
        public GUID read(Reader reader) throws IOException, InvalidCodecException {
            final char[] buffer = new char[36];
            int read;
            StringBuilder sb = new StringBuilder();
            while ((read = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, read);
            }
            return new GUID(sb.toString());
        }

    }

    public static class GuidBinaryCodec implements BinaryCodec<GUID> {

        @Override
        public void write(GUID data, OutputStream out) throws IOException {
            out.write(data.asBytes());
        }

        @Override
        public GUID read(InputStream in)
                throws IOException, InvalidCodecException {
            final byte[] buffer = new byte[36];
            int read;
            StringBuilder sb = new StringBuilder();
            while ((read = in.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, read, StandardCharsets.UTF_8));
            }
            return new GUID(sb.toString());
        }

    }

}
