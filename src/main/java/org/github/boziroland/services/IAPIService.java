package org.github.boziroland.services;

import org.github.boziroland.entities.GeneralAPIData;

import java.io.IOException;
import java.util.Optional;

public interface IAPIService {

    void requestInformation(String accountId, GeneralAPIData location);
    Optional<String> readKeyFromFile(String file) throws IOException;

}
