package nexters.hyomk.dontforget.di.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nexters.hyomk.data.repositoryImpl.AnniversaryRepositoryImpl
import nexters.hyomk.data.repositoryImpl.DeviceInfoRepositoryImpl
import nexters.hyomk.domain.repository.AnniversaryRepository
import nexters.hyomk.domain.repository.DeviceInfoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun providesAnniversaryRepository(repository: AnniversaryRepositoryImpl): AnniversaryRepository {
        return repository
    }

    @Provides
    @Singleton
    fun providesDeviceInfoRepository(repository: DeviceInfoRepositoryImpl): DeviceInfoRepository {
        return repository
    }
}
