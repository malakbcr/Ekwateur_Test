package com.example.ekwateurProject.configuration;

import java.io.IOException;

public interface IApiConnection {
    String OpenHttpConnection(String url) throws IOException;
}
