import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nexters.hyomk.domain.repository.AnniversaryRepository
import nexters.hyomk.domain.repository.DeviceInfoRepository
import nexters.hyomk.domain.usecaseImpl.AddAnniversaryUseCaseImpl
import nexters.hyomk.domain.usecaseImpl.DeleteAnniversaryUseCaseImpl
import nexters.hyomk.domain.usecaseImpl.GetAnniversaryListUseCaseImpl
import nexters.hyomk.domain.usecaseImpl.GetDetailAnniversaryUseCaseImpl
import nexters.hyomk.domain.usecaseImpl.ModifyAnniversaryUseCaseImpl
import nexters.hyomk.domain.usecaseImpl.UpdateDeviceInfoUseCaseImpl
import javax.inject.Singleton

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
