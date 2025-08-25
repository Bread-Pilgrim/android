package com.twolskone.bakeroad.core.data.di

import com.twolskone.bakeroad.core.data.repository.AuthRepositoryImpl
import com.twolskone.bakeroad.core.data.repository.BadgeRepositoryImpl
import com.twolskone.bakeroad.core.data.repository.BakeryRepositoryImpl
import com.twolskone.bakeroad.core.data.repository.HomeRepositoryImpl
import com.twolskone.bakeroad.core.data.repository.PreferRepositoryImpl
import com.twolskone.bakeroad.core.data.repository.ReviewRepositoryImpl
import com.twolskone.bakeroad.core.data.repository.SearchRepositoryImpl
import com.twolskone.bakeroad.core.data.repository.TourRepositoryImpl
import com.twolskone.bakeroad.core.data.repository.UserRepositoryImpl
import com.twolskone.bakeroad.core.domain.repository.AuthRepository
import com.twolskone.bakeroad.core.domain.repository.BadgeRepository
import com.twolskone.bakeroad.core.domain.repository.BakeryRepository
import com.twolskone.bakeroad.core.domain.repository.HomeRepository
import com.twolskone.bakeroad.core.domain.repository.PreferRepository
import com.twolskone.bakeroad.core.domain.repository.ReviewRepository
import com.twolskone.bakeroad.core.domain.repository.SearchRepository
import com.twolskone.bakeroad.core.domain.repository.TourRepository
import com.twolskone.bakeroad.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindsUserRepository(userRepository: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindsPreferRepository(preferRepository: PreferRepositoryImpl): PreferRepository

    @Binds
    @Singleton
    abstract fun bindsHomeRepository(homeRepository: HomeRepositoryImpl): HomeRepository

    @Binds
    @Singleton
    abstract fun bindsBakeryRepository(bakeryRepository: BakeryRepositoryImpl): BakeryRepository

    @Binds
    @Singleton
    abstract fun bindsTourRepository(tourRepository: TourRepositoryImpl): TourRepository

    @Binds
    @Singleton
    abstract fun bindsReviewRepository(reviewRepository: ReviewRepositoryImpl): ReviewRepository

    @Binds
    @Singleton
    abstract fun bindsSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    abstract fun bindsBadgeRepository(badgeRepository: BadgeRepositoryImpl): BadgeRepository
}