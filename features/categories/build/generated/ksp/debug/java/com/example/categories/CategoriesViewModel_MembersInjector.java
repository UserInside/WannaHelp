package com.example.categories;

import com.example.domain.interactors.CategoriesInteractor;
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
public final class CategoriesViewModel_MembersInjector implements MembersInjector<CategoriesViewModel> {
  private final Provider<CategoriesInteractor> interactorProvider;

  public CategoriesViewModel_MembersInjector(Provider<CategoriesInteractor> interactorProvider) {
    this.interactorProvider = interactorProvider;
  }

  public static MembersInjector<CategoriesViewModel> create(
      Provider<CategoriesInteractor> interactorProvider) {
    return new CategoriesViewModel_MembersInjector(interactorProvider);
  }

  @Override
  public void injectMembers(CategoriesViewModel instance) {
    injectInteractor(instance, interactorProvider.get());
  }

  @InjectedFieldSignature("com.example.categories.CategoriesViewModel.interactor")
  public static void injectInteractor(CategoriesViewModel instance,
      CategoriesInteractor interactor) {
    instance.interactor = interactor;
  }
}
