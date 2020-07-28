package org.github.boziroland.services;

import org.github.boziroland.entities.GeneralAPIData;
import org.github.boziroland.entities.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public interface IAPIService {

	void requestInformation(User user, GeneralAPIData location);

	default Optional<String> readKeyFromFile(String file) throws IOException {
		List<String> lines;
		lines = Files.readAllLines(Paths.get(file));
		return Optional.of(lines.get(0));
	}
}
