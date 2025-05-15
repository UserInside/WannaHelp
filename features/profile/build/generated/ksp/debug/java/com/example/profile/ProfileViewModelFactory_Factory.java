package com.example.profile;

import com.example.data.network.ApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
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
public final class ProfileViewModelFactory_Factory implements Factory<ProfileViewModelFactory> {
  private final Provider<ApiService> apiServiceProvider;

  public ProfileViewModelFactory_Factory(Provider<ApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public ProfileViewModelFactory get() {
    return newInstance(apiServiceProvider.get());
  }

  public static ProfileViewModelFactory_Factory create(Provider<ApiService> apiServiceProvider) {
    return new ProfileViewModelFactory_Factory(apiServiceProvider);
  }

  public static ProfileViewModelFactory newInstance(ApiService apiService) {
    return new ProfileViewModelFactory(apiService);
  }
}
