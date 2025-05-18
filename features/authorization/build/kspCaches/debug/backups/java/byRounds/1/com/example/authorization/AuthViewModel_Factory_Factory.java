package com.example.authorization;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class AuthViewModel_Factory_Factory implements Factory<AuthViewModel.Factory> {
  @Override
  public AuthViewModel.Factory get() {
    return newInstance();
  }

  public static AuthViewModel_Factory_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static AuthViewModel.Factory newInstance() {
    return new AuthViewModel.Factory();
  }

  private static final class InstanceHolder {
    static final AuthViewModel_Factory_Factory INSTANCE = new AuthViewModel_Factory_Factory();
  }
}
