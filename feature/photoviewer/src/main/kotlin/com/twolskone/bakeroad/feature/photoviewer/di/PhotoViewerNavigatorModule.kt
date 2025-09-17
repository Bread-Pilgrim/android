package com.twolskone.bakeroad.feature.photoviewer.di

import com.twolskone.bakeroad.core.navigator.PhotoViewerNavigator
import com.twolskone.bakeroad.feature.photoviewer.navigator.PhotoViewerNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PhotoViewerNavigatorModule {

    @Binds
    @Singleton
    abstract fun bindsPhotoViewerNavigator(photoViewerNavigator: PhotoViewerNavigatorImpl): PhotoViewerNavigator
}