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

  public ProfileScreenFragment_MembersInjector(
      Provider<ProfileViewModelFactory> vmFactoryProvider) {
    this.vmFactoryProvider = vmFactoryProvider;
  }

  public static MembersInjector<ProfileScreenFragment> create(
      Provider<ProfileViewModelFactory> vmFactoryProvider) {
    return new ProfileScreenFragment_MembersInjector(vmFactoryProvider);
  }

  @Override
  public void injectMembers(ProfileScreenFragment instance) {
    injectVmFactory(instance, vmFactoryProvider.get());
  }

  @InjectedFieldSignature("com.example.profile.ProfileScreenFragment.vmFactory")
  public static void injectVmFactory(ProfileScreenFragment instance,
      ProfileViewModelFactory vmFactory) {
    instance.vmFactory = vmFactory;
  }
}
