package ru.skogmark.aggregator.image;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

// todo add interface and replace package
@Service
public class ImageDownloadService {
    public Optional<Image> downloadAndSave(URI imageUri) {
//        FileUtils.copyURLToFile();
        return Optional.empty(); // todo write code
    }
}
