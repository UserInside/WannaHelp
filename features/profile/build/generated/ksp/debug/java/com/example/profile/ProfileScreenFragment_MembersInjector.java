package com.example.profile;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;

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
public final class ProfileScreenFragment_MembersInjector implements MembersInjector<ProfileScreenFragment> {
  private final Provider<ProfileViewModelFactory> vmFactoryProvider;

  private final Provider<ProfileNavigator> navigatorProvider;

  public ProfileScreenFragment_MembersInjector(Provider<ProfileViewModelFactory> vmFactoryProvider,
      Provider<ProfileNavigator> navigatorProvider) {
    this.vmFactoryProvider = vmFactoryProvider;
    this.navigatorProvider = navigatorProvider;
  }

  public static MembersInjector<ProfileScreenFragment> create(
      Provider<ProfileViewModelFactory> vmFactoryProvider,
      Provider<ProfileNavigator> navigatorProvider) {
    return new ProfileScreenFragment_MembersInjector(vmFactoryProvider, navigatorProvider);
  }

  @Override
  public void injectMembers(ProfileScreenFragment instance) {
    injectVmFactory(instance, vmFactoryProvider.get());
    injectNavigator(instance, navigatorProvider.get());
  }

  @InjectedFieldSignature("com.example.profile.ProfileScreenFragment.vmFactory")
  public static void injectVmFactory(ProfileScreenFragment instance,
      ProfileViewModelFactory vmFactory) {
    instance.vmFactory = vmFactory;
  }

  @InjectedFieldSignature("com.example.profile.ProfileScreenFragment.navigator")
  public static void injectNavigator(ProfileScreenFragment instance, ProfileNavigator navigator) {
    instance.navigator = navigator;
  }
}
