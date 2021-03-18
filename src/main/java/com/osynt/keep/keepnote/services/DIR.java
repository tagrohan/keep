package com.osynt.keep.keepnote.services;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class DIR {

    public File getPath() throws IOException {
        return new ClassPathResource("static/image").getFile();
    }
    
}
 