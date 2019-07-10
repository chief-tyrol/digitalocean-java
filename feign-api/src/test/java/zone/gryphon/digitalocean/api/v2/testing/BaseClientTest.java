/*
 * Copyright 2019-2019 Gryphon Zone
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zone.gryphon.digitalocean.api.v2.testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.optionals.OptionalDecoder;
import feign.slf4j.Slf4jLogger;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import zone.gryphon.digitalocean.api.v2.DigitalOceanAuthorizer;

import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public abstract class BaseClientTest<T> {

    private static final String PROPERTY = "do.auth.token";

    private static String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {

                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("JDK does not support sha256 hashing", e);
        }
    }

    @Getter
    protected final T client;

    public BaseClientTest() {
        this("https://api.digitalocean.com");
    }

    public BaseClientTest(String host) {

        ObjectMapper mapper = new ObjectMapper()
                .disable(FAIL_ON_UNKNOWN_PROPERTIES);

        // noinspection unchecked
        Class<T> clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        String authToken = Objects.requireNonNull(System.getProperty(PROPERTY), String.format("Must set system property named \"%s\" which contains the Digital Ocean auth token to use for the tests", PROPERTY));

        log.info("Using the following token for test {} (value is hashed): {}", getClass().getSimpleName(), hash(authToken));

        this.client = Feign.builder()
                .decoder(new OptionalDecoder(new JacksonDecoder(mapper)))
                .encoder(new JacksonEncoder(mapper))
                .requestInterceptor(new DigitalOceanAuthorizer(authToken))
                .logLevel(Logger.Level.BASIC)
                .decode404()
                .logger(new Slf4jLogger())
                .target(clazz, host);

        assertThat(client).isNotNull();
    }
}
