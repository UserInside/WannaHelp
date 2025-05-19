package com.example.news.di;

import android.app.Activity;
import android.content.Context;
import com.example.data.db.AppDatabase;
import com.example.data.di.DataModule;
import com.example.data.di.DataModule_ProvideContextFactory;
import com.example.data.di.DataModule_ProvideNewsInteractorFactory;
import com.example.data.di.DataModule_ProvideNewsRepositoryFactory;
import com.example.data.di.DataModule_ProvideRoomDatabaseFactory;
import com.example.domain.di.DomainModule;
import com.example.domain.interactors.NewsInteractor;
import com.example.domain.repository.NewsRepository;
import com.example.news.NewsScreenFragment;
import com.example.news.NewsScreenFragment_MembersInjector;
import com.example.news.NewsViewModel;
import com.example.news.NewsViewModel_MembersInjector;
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
public final class DaggerNewsComponent {
  private DaggerNewsComponent() {
  }

  public static NewsComponent.Builder builder() {
    return new Builder();
  }

  private static final class Builder implements NewsComponent.Builder {
    private DataModule dataModule;

    private NewsDeps newsDeps;

    @Override
    public Builder dataModule(DataModule module) {
      this.dataModule = Preconditions.checkNotNull(module);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Override
    @Deprecated
    public Builder domainModule(DomainModule module) {
      Preconditions.checkNotNull(module);
      return this;
    }

    @Override
    public Builder deps(NewsDeps newsDeps) {
      this.newsDeps = Preconditions.checkNotNull(newsDeps);
      return this;
    }

    @Override
    public NewsComponent build() {
      Preconditions.checkBuilderRequirement(dataModule, DataModule.class);
      Preconditions.checkBuilderRequirement(newsDeps, NewsDeps.class);
      return new NewsComponentImpl(dataModule, newsDeps);
    }
  }

  private static final class NewsComponentImpl implements NewsComponent {
    private final DataModule dataModule;

    private final NewsDeps newsDeps;

    private final NewsComponentImpl newsComponentImpl = this;

    Provider<Context> provideContextProvider;

    Provider<AppDatabase> provideRoomDatabaseProvider;

    NewsComponentImpl(DataModule dataModuleParam, NewsDeps newsDepsParam) {
      this.dataModule = dataModuleParam;
      this.newsDeps = newsDepsParam;
      initialize(dataModuleParam, newsDepsParam);

    }

    NewsRepository newsRepository() {
      return DataModule_ProvideNewsRepositoryFactory.provideNewsRepository(dataModule, provideRoomDatabaseProvider.get());
    }

    NewsInteractor newsInteractor() {
      return DataModule_ProvideNewsInteractorFactory.provideNewsInteractor(dataModule, newsRepository());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final DataModule dataModuleParam, final NewsDeps newsDepsParam) {
      this.provideContextProvider = DataModule_ProvideContextFactory.create(dataModuleParam);
      this.provideRoomDatabaseProvider = DoubleCheck.provider(DataModule_ProvideRoomDatabaseFactory.create(dataModuleParam, provideContextProvider));
    }

    @Override
    public void inject(NewsViewModel vm) {
      injectNewsViewModel(vm);
    }

    @Override
    public void inject(NewsScreenFragment fragment) {
      injectNewsScreenFragment(fragment);
    }

    @Override
    public void inject(Activity activity) {
    }

    private NewsViewModel injectNewsViewModel(NewsViewModel instance) {
      NewsViewModel_MembersInjector.injectInteractor(instance, newsInteractor());
      return instance;
    }

    private NewsScreenFragment injectNewsScreenFragment(NewsScreenFragment instance2) {
      NewsScreenFragment_MembersInjector.injectNavigator(instance2, Preconditions.checkNotNullFromComponent(newsDeps.getNewsNavigator()));
      NewsScreenFragment_MembersInjector.injectNewsComponent(instance2, this);
      return instance2;
    }
  }
}
