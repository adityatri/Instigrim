package di

import mvi.viewmodel.HomeViewModel
import org.koin.dsl.module

fun appModule() = module {
    viewModelDefinition { HomeViewModel() }
}