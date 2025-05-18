package com.example.authorization;

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
public final class AuthFragment_MembersInjector implements MembersInjector<AuthFragment> {
  private final Provider<AuthorizationNavigator> navigatorProvider;

  public AuthFragment_MembersInjector(Provider<AuthorizationNavigator> navigatorProvider) {
    this.navigatorProvider = navigatorProvider;
  }

  public static MembersInjector<AuthFragment> create(
      Provider<AuthorizationNavigator> navigatorProvider) {
    return new AuthFragment_MembersInjector(navigatorProvider);
  }

  @Override
  public void injectMembers(AuthFragment instance) {
    injectNavigator(instance, navigatorProvider.get());
  }

  @InjectedFieldSignature("com.example.authorization.AuthFragment.navigator")
  public static void injectNavigator(AuthFragment instance, AuthorizationNavigator navigator) {
    instance.navigator = navigator;
  }
}
