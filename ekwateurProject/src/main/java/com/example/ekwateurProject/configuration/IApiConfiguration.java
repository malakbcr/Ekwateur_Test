package com.example.ekwateurProject.configuration;

import com.example.ekwateurProject.model.Urls;

import java.io.IOException;

public interface IApiConfiguration {
    Urls GetUrls() throws IOException;
}
