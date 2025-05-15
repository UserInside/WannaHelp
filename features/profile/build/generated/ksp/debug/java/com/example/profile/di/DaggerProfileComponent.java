package com.example.profile.di;

import com.example.data.di.DataModule;
import com.example.data.di.DataModule_ProvideApiServiceFactory;
import com.example.data.di.DataModule_ProvideJsonFactory;
import com.example.data.di.DataModule_ProvideOkHttpFactory;
import com.example.data.network.ApiService;
import com.example.profile.ProfileScreenFragment;
import com.example.profile.ProfileScreenFragment_MembersInjector;
import com.example.profile.ProfileViewModelFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import javax.annotation.processing.Generated;
import kotlinx.serialization.json.Json;
import okhttp3.OkHttpClient;

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
public final class DaggerProfileComponent {
  private DaggerProfileComponent() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ProfileModule profileModule;

    private DataModule dataModule;

    private Builder() {
    }

    public Builder profileModule(ProfileModule profileModule) {
      this.profileModule = Preconditions.checkNotNull(profileModule);
      return this;
    }

    public Builder dataModule(DataModule dataModule) {
      this.dataModule = Preconditions.checkNotNull(dataModule);
      return this;
    }

    public ProfileComponent build() {
      if (profileModule == null) {
        this.profileModule = new ProfileModule();
      }
      Preconditions.checkBuilderRequirement(dataModule, DataModule.class);
      return new ProfileComponentImpl(profileModule, dataModule);
    }
  }

  private static final class ProfileComponentImpl implements ProfileComponent {
    private final ProfileComponentImpl profileComponentImpl = this;

    Provider<OkHttpClient> provideOkHttpProvider;

    Provider<Json> provideJsonProvider;

    Provider<ApiService> provideApiServiceProvider;

    Provider<ProfileViewModelFactory> provideProfileViewModelFactoryProvider;

    ProfileComponentImpl(ProfileModule profileModuleParam, DataModule dataModuleParam) {

      initialize(profileModuleParam, dataModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ProfileModule profileModuleParam,
        final DataModule dataModuleParam) {
      this.provideOkHttpProvider = DoubleCheck.provider(DataModule_ProvideOkHttpFactory.create(dataModuleParam));
      this.provideJsonProvider = DoubleCheck.provider(DataModule_ProvideJsonFactory.create(dataModuleParam));
      this.provideApiServiceProvider = DoubleCheck.provider(DataModule_ProvideApiServiceFactory.create(dataModuleParam, provideOkHttpProvider, provideJsonProvider));
      this.provideProfileViewModelFactoryProvider = DoubleCheck.provider(ProfileModule_ProvideProfileViewModelFactoryFactory.create(profileModuleParam, provideApiServiceProvider));
    }

    @Override
    public void inject(ProfileScreenFragment fragment) {
      injectProfileScreenFragment(fragment);
    }

    private ProfileScreenFragment injectProfileScreenFragment(ProfileScreenFragment instance) {
      ProfileScreenFragment_MembersInjector.injectVmFactory(instance, provideProfileViewModelFactoryProvider.get());
      return instance;
    }
  }
}
