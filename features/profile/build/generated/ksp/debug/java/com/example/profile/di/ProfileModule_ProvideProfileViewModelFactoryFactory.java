package com.example.profile.di;

import com.example.data.network.ApiService;
import com.example.profile.ProfileViewModelFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class ProfileModule_ProvideProfileViewModelFactoryFactory implements Factory<ProfileViewModelFactory> {
  private final ProfileModule module;

  private final Provider<ApiService> apiServiceProvider;

  public ProfileModule_ProvideProfileViewModelFactoryFactory(ProfileModule module,
      Provider<ApiService> apiServiceProvider) {
    this.module = module;
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public ProfileViewModelFactory get() {
    return provideProfileViewModelFactory(module, apiServiceProvider.get());
  }

  public static ProfileModule_ProvideProfileViewModelFactoryFactory create(ProfileModule module,
      Provider<ApiService> apiServiceProvider) {
    return new ProfileModule_ProvideProfileViewModelFactoryFactory(module, apiServiceProvider);
  }

  public static ProfileViewModelFactory provideProfileViewModelFactory(ProfileModule instance,
      ApiService apiService) {
    return Preconditions.checkNotNullFromProvides(instance.provideProfileViewModelFactory(apiService));
  }
}
