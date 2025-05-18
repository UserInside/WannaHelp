package com.example.news;

import com.example.domain.interactors.NewsInteractor;
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
public final class NewsViewModel_MembersInjector implements MembersInjector<NewsViewModel> {
  private final Provider<NewsInteractor> interactorProvider;

  public NewsViewModel_MembersInjector(Provider<NewsInteractor> interactorProvider) {
    this.interactorProvider = interactorProvider;
  }

  public static MembersInjector<NewsViewModel> create(Provider<NewsInteractor> interactorProvider) {
    return new NewsViewModel_MembersInjector(interactorProvider);
  }

  @Override
  public void injectMembers(NewsViewModel instance) {
    injectInteractor(instance, interactorProvider.get());
  }

  @InjectedFieldSignature("com.example.news.NewsViewModel.interactor")
  public static void injectInteractor(NewsViewModel instance, NewsInteractor interactor) {
    instance.interactor = interactor;
  }
}
