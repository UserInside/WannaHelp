package com.example.news;

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
public final class NewsScreenFragment_MembersInjector implements MembersInjector<NewsScreenFragment> {
  private final Provider<NewsNavigator> navigatorProvider;

  public NewsScreenFragment_MembersInjector(Provider<NewsNavigator> navigatorProvider) {
    this.navigatorProvider = navigatorProvider;
  }

  public static MembersInjector<NewsScreenFragment> create(
      Provider<NewsNavigator> navigatorProvider) {
    return new NewsScreenFragment_MembersInjector(navigatorProvider);
  }

  @Override
  public void injectMembers(NewsScreenFragment instance) {
    injectNavigator(instance, navigatorProvider.get());
  }

  @InjectedFieldSignature("com.example.news.NewsScreenFragment.navigator")
  public static void injectNavigator(NewsScreenFragment instance, NewsNavigator navigator) {
    instance.navigator = navigator;
  }
}
