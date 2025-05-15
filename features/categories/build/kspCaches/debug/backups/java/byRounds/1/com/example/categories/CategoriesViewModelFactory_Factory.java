package com.example.categories;

import com.example.categories.di.CategoriesComponent;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class CategoriesViewModelFactory_Factory implements Factory<CategoriesViewModelFactory> {
  private final Provider<CategoriesComponent> componentProvider;

  public CategoriesViewModelFactory_Factory(Provider<CategoriesComponent> componentProvider) {
    this.componentProvider = componentProvider;
  }

  @Override
  public CategoriesViewModelFactory get() {
    return newInstance(componentProvider.get());
  }

  public static CategoriesViewModelFactory_Factory create(
      Provider<CategoriesComponent> componentProvider) {
    return new CategoriesViewModelFactory_Factory(componentProvider);
  }

  public static CategoriesViewModelFactory newInstance(CategoriesComponent component) {
    return new CategoriesViewModelFactory(component);
  }
}
