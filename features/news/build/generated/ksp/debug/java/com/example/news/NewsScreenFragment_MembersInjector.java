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
  private final Provider<NewsComponent> componentProvider;

  public NewsScreenFragment_MembersInjector(Provider<NewsComponent> componentProvider) {
    this.componentProvider = componentProvider;
  }

  public static MembersInjector<NewsScreenFragment> create(
      Provider<NewsComponent> componentProvider) {
    return new NewsScreenFragment_MembersInjector(componentProvider);
  }

  @Override
  public void injectMembers(NewsScreenFragment instance) {
    injectComponent(instance, componentProvider.get());
  }

  @InjectedFieldSignature("com.example.news.NewsScreenFragment.component")
  public static void injectComponent(NewsScreenFragment instance, NewsComponent component) {
    instance.component = component;
  }
}
