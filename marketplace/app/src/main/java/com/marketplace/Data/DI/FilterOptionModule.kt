package com.marketplace.Data.DI

import com.google.android.material.slider.LabelFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.text.NumberFormat
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FilterOptionModule {

    @Provides
    @Singleton
    fun provideLabelFormatter(): LabelFormatter {
        return LabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("USD")
            format.format(value.toDouble())
        }
    }
}