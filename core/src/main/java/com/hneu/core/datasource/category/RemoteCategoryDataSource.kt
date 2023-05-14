package com.hneu.core.datasource.category

import kotlinx.coroutines.flow.Flow
import com.hneu.core.domain.request.Result

interface RemoteCategoryDataSource {
    fun getCategories(): Flow<Result>
}