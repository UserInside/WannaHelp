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

  public static ProfileComponent.Builder builder() {
    return new Builder();
  }

  private static final class Builder implements ProfileComponent.Builder {
    private ProfileDeps profileDeps;

    private ProfileModule profileModule;

    private DataModule dataModule;

    @Override
    public Builder deps(ProfileDeps deps) {
      this.profileDeps = Preconditions.checkNotNull(deps);
      return this;
    }

    @Override
    public Builder profileModule(ProfileModule module) {
      this.profileModule = Preconditions.checkNotNull(module);
      return this;
    }

    @Override
    public Builder dataModule(DataModule module) {
      this.dataModule = Preconditions.checkNotNull(module);
      return this;
    }

    @Override
    public ProfileComponent build() {
      Preconditions.checkBuilderRequirement(profileDeps, ProfileDeps.class);
      if (profileModule == null) {
        this.profileModule = new ProfileModule();
      }
      Preconditions.checkBuilderRequirement(dataModule, DataModule.class);
      return new ProfileComponentImpl(profileModule, dataModule, profileDeps);
    }
  }

  private static final class ProfileComponentImpl implements ProfileComponent {
    private final ProfileDeps profileDeps;

    private final ProfileComponentImpl profileComponentImpl = this;

    Provider<OkHttpClient> provideOkHttpProvider;

    Provider<Json> provideJsonProvider;

    Provider<ApiService> provideApiServiceProvider;

    Provider<ProfileViewModelFactory> provideProfileViewModelFactoryProvider;

    ProfileComponentImpl(ProfileModule profileModuleParam, DataModule dataModuleParam,
        ProfileDeps profileDepsParam) {
      this.profileDeps = profileDepsParam;
      initialize(profileModuleParam, dataModuleParam, profileDepsParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ProfileModule profileModuleParam,
        final DataModule dataModuleParam, final ProfileDeps profileDepsParam) {
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
      ProfileScreenFragment_MembersInjector.injectNavigator(instance, Preconditions.checkNotNullFromComponent(profileDeps.getProfileNavigator()));
      return instance;
    }
  }
}
