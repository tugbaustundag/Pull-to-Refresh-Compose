package com.smality.pulltorefreshcompose

import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RefreshDataViewModel: ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing.asStateFlow()
    //1 saniyelik sürede sayfanın yenilenmesi
    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            delay(1000)
            _isRefreshing.emit(false)
        }
    }
    //Array'de varolan verileri karıştırılmış halini döndüren fonksiyon
    fun shuffleItems(): List<Data> {
        return dataArray.shuffled()
    }
}
data class Data(val title: String, val category: String, val backColor: String)

//Makale başlık, kategori gibi içeriklerin array'e eklenmesi
val dataArray = listOf(
    Data("Haritada Pin Drag & Drop ile Konum Gösterme", "API & Library","#F39027"),
    Data("Compose ile Expandable List Oluşturma", "UI/UX","#34A8F8"),
    Data("Compose ile BottomSheet Kullanımı", "Animasyon","#F989E3"),
    Data("Play Integrity API ile Güvenlik Önlemleri", "Güvenlik,API & Library","#B4E764"),
    Data("Android Compose’da Lazy Layout Kullanımı", "Compose","#E8B664"),
    Data("Apk Uygulama İçi Güncelleme [In-app Update]", "API & Library","#81AFCD"),
    Data("'Insecure Data Storage' Güvenlik Zafiyetleri", "Güvenlik","#FFF909"),
    Data("Android View ile Compose Birlikte Kullanımı", "UI/UX","#A381CD")
)