package nexters.hyomk.dontforget.di.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nexters.hyomk.domain.repository.AnniversaryRepository
import nexters.hyomk.domain.repository.DeviceInfoRepository
import nexters.hyomk.domain.usecase.AddAnniversaryUseCase
import nexters.hyomk.domain.usecase.DeleteAnniversaryUseCase
import nexters.hyomk.domain.usecase.GetAnniversaryListUseCase
import nexters.hyomk.domain.usecase.GetDetailAnniversaryUseCase
import nexters.hyomk.domain.usecase.GetDeviceInfoUseCase
import nexters.hyomk.domain.usecase.ModifyAnniversaryUserCase
import nexters.hyomk.domain.usecase.UpdateAnniversaryAlarmStateUseCase
import nexters.hyomk.domain.usecase.UpdateDeviceInfoUseCase
import nexters.hyomk.domain.usecaseImpl.AddAnniversaryUseCaseImpl
import nexters.hyomk.domain.usecaseImpl.DeleteAnniversaryUseCaseImpl
import nexters.hyomk.domain.usecaseImpl.GetAnniversaryListUseCaseImpl
import nexters.hyomk.domain.usecaseImpl.GetDetailAnniversaryUseCaseImpl
import nexters.hyomk.domain.usecaseImpl.GetDeviceInfoUseCaseImpl
import nexters.hyomk.domain.usecaseImpl.ModifyAnniversaryUseCaseImpl
import nexters.hyomk.domain.usecaseImpl.UpdateAnniversaryAlarmStateUseCaseImpl
import nexters.hyomk.domain.usecaseImpl.UpdateDeviceInfoUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideAddAnniversaryUseCase(useCaseImpl: AddAnniversaryUseCaseImpl): AddAnniversaryUseCase {
        return useCaseImpl
    }

    @Provides
    @Singleton
    fun provideGetDetailAnniversaryUseCase(useCaseImpl: GetDetailAnniversaryUseCaseImpl): GetDetailAnniversaryUseCase {
        return useCaseImpl
    }

    @Provides
    @Singleton
    fun provideGetAnniversaryListUseCase(useCaseImpl: GetAnniversaryListUseCaseImpl): GetAnniversaryListUseCase {
        return useCaseImpl
    }

    @Provides
    @Singleton
    fun provideDeleteAnniversaryUseCase(useCaseImpl: DeleteAnniversaryUseCaseImpl): DeleteAnniversaryUseCase {
        return useCaseImpl
    }

    @Provides
    @Singleton
    fun provideModifyAnniversaryUseCase(useCaseImpl: ModifyAnniversaryUseCaseImpl): ModifyAnniversaryUserCase {
        return useCaseImpl
    }

    @Provides
    @Singleton
    fun provideModifyAnniversaryAlarmStateUseCase(useCaseImpl: UpdateAnniversaryAlarmStateUseCaseImpl): UpdateAnniversaryAlarmStateUseCase {
        return useCaseImpl
    }

    @Provides
    @Singleton
    fun provideUpdateDeviceInfoUseCase(useCaseImpl: UpdateDeviceInfoUseCaseImpl): UpdateDeviceInfoUseCase {
        return useCaseImpl
    }

    @Provides
    @Singleton
    fun provideGetDeviceInfoUseCase(useCaseImpl: GetDeviceInfoUseCaseImpl): GetDeviceInfoUseCase {
        return useCaseImpl
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryUseCaseModule {
    @Provides
    @Singleton
    fun provideGetAnniversaryListUseCaseImpl(anniversaryRepository: AnniversaryRepository): GetAnniversaryListUseCaseImpl {
        return GetAnniversaryListUseCaseImpl(anniversaryRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateDeviceInfoCaseImpl(deviceInfoRepository: DeviceInfoRepository): UpdateDeviceInfoUseCaseImpl {
        return UpdateDeviceInfoUseCaseImpl(deviceInfoRepository)
    }

    @Provides
    @Singleton
    fun provideCreateAnniversaryCaseImpl(anniversaryRepository: AnniversaryRepository): AddAnniversaryUseCaseImpl {
        return AddAnniversaryUseCaseImpl(anniversaryRepository)
    }

    @Provides
    @Singleton
    fun provideModifyAnniversaryCaseImpl(anniversaryRepository: AnniversaryRepository): ModifyAnniversaryUseCaseImpl {
        return ModifyAnniversaryUseCaseImpl(anniversaryRepository)
    }

    @Provides
    @Singleton
    fun provideGetDetailAnniversaryCaseImpl(anniversaryRepository: AnniversaryRepository): GetDetailAnniversaryUseCaseImpl {
        return GetDetailAnniversaryUseCaseImpl(anniversaryRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteAnniversaryCaseImpl(anniversaryRepository: AnniversaryRepository): DeleteAnniversaryUseCaseImpl {
        return DeleteAnniversaryUseCaseImpl(anniversaryRepository)
    }
}
