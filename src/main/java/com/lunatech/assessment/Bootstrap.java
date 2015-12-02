package com.lunatech.assessment;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

import java.io.IOException;

/**
 * Created by Victor on 02/12/2015.
 */
public class Bootstrap {

    public static void main(String[] args) throws IOException {
        Injector injector = Guice.createInjector(Modules.EMPTY_MODULE);
        App app = injector.getInstance(App.class);
        app.begin();
    }

}
