package lol.hostov.tracemoe.presentation.ui.screens.result

import androidx.lifecycle.ViewModel
import lol.hostov.tracemoe.repository.ApiRepository
import javax.inject.Inject

class ResultViewModel @Inject constructor(
    private val api: ApiRepository,
) : ViewModel() {

}