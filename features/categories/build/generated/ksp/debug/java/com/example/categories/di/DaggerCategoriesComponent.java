package com.example.categories.di;

import android.content.Context;
import com.example.categories.CategoriesViewModel;
import com.example.categories.CategoriesViewModel_MembersInjector;
import com.example.data.db.AppDatabase;
import com.example.data.db.categories.CategoriesDao;
import com.example.data.di.DataModule;
import com.example.data.di.DataModule_ProvideCategoriesDaoFactory;
import com.example.data.di.DataModule_ProvideCategoriesRepositoryFactory;
import com.example.data.di.DataModule_ProvideContextFactory;
import com.example.data.di.DataModule_ProvideRoomDatabaseFactory;
import com.example.domain.di.DomainModule;
import com.example.domain.di.DomainModule_ProvideCategoryInteractorFactory;
import com.example.domain.interactors.CategoriesInteractor;
import com.example.domain.repository.CategoriesRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
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
public final class DaggerCategoriesComponent {
  private DaggerCategoriesComponent() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private DataModule dataModule;

    private DomainModule domainModule;

    private Builder() {
    }

    public Builder dataModule(DataModule dataModule) {
      this.dataModule = Preconditions.checkNotNull(dataModule);
      return this;
    }

    public Builder domainModule(DomainModule domainModule) {
      this.domainModule = Preconditions.checkNotNull(domainModule);
      return this;
    }

    public CategoriesComponent build() {
      Preconditions.checkBuilderRequirement(dataModule, DataModule.class);
      if (domainModule == null) {
        this.domainModule = new DomainModule();
      }
      return new CategoriesComponentImpl(dataModule, domainModule);
    }
  }

  private static final class CategoriesComponentImpl implements CategoriesComponent {
    private final DomainModule domainModule;

    private final DataModule dataModule;

    private final CategoriesComponentImpl categoriesComponentImpl = this;

    Provider<Context> provideContextProvider;

    Provider<AppDatabase> provideRoomDatabaseProvider;

    CategoriesComponentImpl(DataModule dataModuleParam, DomainModule domainModuleParam) {
      this.domainModule = domainModuleParam;
      this.dataModule = dataModuleParam;
      initialize(dataModuleParam, domainModuleParam);

    }

    CategoriesDao categoriesDao() {
      return DataModule_ProvideCategoriesDaoFactory.provideCategoriesDao(dataModule, provideRoomDatabaseProvider.get());
    }

    CategoriesRepository categoriesRepository() {
      return DataModule_ProvideCategoriesRepositoryFactory.provideCategoriesRepository(dataModule, DataModule_ProvideContextFactory.provideContext(dataModule), categoriesDao());
    }

    CategoriesInteractor categoriesInteractor() {
      return DomainModule_ProvideCategoryInteractorFactory.provideCategoryInteractor(domainModule, categoriesRepository());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final DataModule dataModuleParam,
        final DomainModule domainModuleParam) {
      this.provideContextProvider = DataModule_ProvideContextFactory.create(dataModuleParam);
      this.provideRoomDatabaseProvider = DoubleCheck.provider(DataModule_ProvideRoomDatabaseFactory.create(dataModuleParam, provideContextProvider));
    }

    @Override
    public void inject(CategoriesViewModel vm) {
      injectCategoriesViewModel(vm);
    }

    private CategoriesViewModel injectCategoriesViewModel(CategoriesViewModel instance) {
      CategoriesViewModel_MembersInjector.injectInteractor(instance, categoriesInteractor());
      return instance;
    }
  }
}
