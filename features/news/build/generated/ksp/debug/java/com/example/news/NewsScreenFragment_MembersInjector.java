package com.example.news;

import com.example.news.di.NewsComponent;
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

  private final Provider<NewsComponent> newsComponentProvider;

  public NewsScreenFragment_MembersInjector(Provider<NewsNavigator> navigatorProvider,
      Provider<NewsComponent> newsComponentProvider) {
    this.navigatorProvider = navigatorProvider;
    this.newsComponentProvider = newsComponentProvider;
  }

  public static MembersInjector<NewsScreenFragment> create(
      Provider<NewsNavigator> navigatorProvider, Provider<NewsComponent> newsComponentProvider) {
    return new NewsScreenFragment_MembersInjector(navigatorProvider, newsComponentProvider);
  }

  @Override
  public void injectMembers(NewsScreenFragment instance) {
    injectNavigator(instance, navigatorProvider.get());
    injectNewsComponent(instance, newsComponentProvider.get());
  }

  @InjectedFieldSignature("com.example.news.NewsScreenFragment.navigator")
  public static void injectNavigator(NewsScreenFragment instance, NewsNavigator navigator) {
    instance.navigator = navigator;
  }

  @InjectedFieldSignature("com.example.news.NewsScreenFragment.newsComponent")
  public static void injectNewsComponent(NewsScreenFragment instance, NewsComponent newsComponent) {
    instance.newsComponent = newsComponent;
  }
}
