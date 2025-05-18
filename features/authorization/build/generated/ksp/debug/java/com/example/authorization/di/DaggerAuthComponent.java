package com.example.authorization.di;

import com.example.authorization.AuthFragment;
import com.example.authorization.AuthFragment_MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;

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
public final class DaggerAuthComponent {
  private DaggerAuthComponent() {
  }

  public static AuthComponent.Builder builder() {
    return new Builder();
  }

  private static final class Builder implements AuthComponent.Builder {
    private AuthDeps authDeps;

    @Override
    public Builder deps(AuthDeps authDeps) {
      this.authDeps = Preconditions.checkNotNull(authDeps);
      return this;
    }

    @Override
    public AuthComponent build() {
      Preconditions.checkBuilderRequirement(authDeps, AuthDeps.class);
      return new AuthComponentImpl(authDeps);
    }
  }

  private static final class AuthComponentImpl implements AuthComponent {
    private final AuthDeps authDeps;

    private final AuthComponentImpl authComponentImpl = this;

    AuthComponentImpl(AuthDeps authDepsParam) {
      this.authDeps = authDepsParam;

    }

    @Override
    public void inject(AuthFragment fragment) {
      injectAuthFragment(fragment);
    }

    private AuthFragment injectAuthFragment(AuthFragment instance) {
      AuthFragment_MembersInjector.injectNavigator(instance, Preconditions.checkNotNullFromComponent(authDeps.getAuthNavigator()));
      return instance;
    }
  }
}
