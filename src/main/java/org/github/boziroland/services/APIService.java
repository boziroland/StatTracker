package org.github.boziroland.services;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public interface APIService {

    Map<String, String> requestInformation(String accountId);
    Optional<String> readKeyFromFile(String file) throws IOException;

}
