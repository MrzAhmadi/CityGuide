package com.smrahmadi.cityguid.viewmodel.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smrahmadi.cityguid.data.repository.FoursquareRepository
import com.smrahmadi.cityguid.view.main.MainActivity

class FoursquareViewModelProvider
    (
    private var mainActivity: MainActivity,
    private var foursquareRepository: FoursquareRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(MainActivity::class.java, FoursquareRepository::class.java)
            .newInstance(mainActivity, foursquareRepository)
    }
}