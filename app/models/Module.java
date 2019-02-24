package models;


import com.google.inject.AbstractModule;

import util.JwtControllerHelper;
import util.JwtControllerHelperImpl;
import util.JwtValidator;
import util.JwtValidatorImpl;

public class Module extends AbstractModule {
	
	@Override
    protected void configure() {
        bind(JwtValidator.class).to(JwtValidatorImpl.class).asEagerSingleton();
        bind(JwtControllerHelper.class).to(JwtControllerHelperImpl.class).asEagerSingleton();
    }

}
