package com.project.services.statistics.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.RSATokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.jose.jws.JWSHeader;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.KeysMetadataRepresentation.KeyMetadataRepresentation;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Component
public class KeycloakTokenVerifier {

    /**
     * Verifies a token against a keycloak instance
     *
     * @param tokenString the string representation of the jws token
     * @return a validated keycloak AccessToken
     * @throws VerificationException when the token is not valid
     */
    public AccessToken verifyToken(String tokenString) throws VerificationException {
        RSATokenVerifier verifier = RSATokenVerifier.create(tokenString);
        PublicKey publicKey = retrievePublicKeyFromCertsEndpoint(verifier.getHeader());
        return verifier.realmUrl(getRealmUrl()).publicKey(publicKey).verify().getToken();
    }

    @SuppressWarnings("unchecked")
    private PublicKey retrievePublicKeyFromCertsEndpoint(JWSHeader jwsHeader) {
        try {
            ObjectMapper om = new ObjectMapper();
            Map<String, Object> certInfos = om.readValue(new URL(getRealmCertsUrl()).openStream(), Map.class);
            List<Map<String, Object>> keys = (List<Map<String, Object>>) certInfos.get("keys");

            Map<String, Object> keyInfo = null;
            for (Map<String, Object> key : keys) {
                String kid = (String) key.get("kid");
                if (jwsHeader.getKeyId().equals(kid)) {
                    keyInfo = key;
                    break;
                }
            }

            if (keyInfo == null) {
                return null;
            }

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            String modulusBase64 = (String) keyInfo.get("n");
            String exponentBase64 = (String) keyInfo.get("e");
            Base64.Decoder urlDecoder = Base64.getUrlDecoder();
            BigInteger modulus = new BigInteger(1, urlDecoder.decode(modulusBase64));
            BigInteger publicExponent = new BigInteger(1, urlDecoder.decode(exponentBase64));

            return keyFactory.generatePublic(new RSAPublicKeySpec(modulus, publicExponent));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getRealmUrl() {
        return "http://localhost:8888/realms/super_realm";
    }

    public String getRealmCertsUrl() {
        return "http://localhost:8888/auth/realms/super_realm/protocol/openid-connect/certs";
    }
}
