package com.smality.pulltorefreshcompose
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smality.pulltorefreshcompose.ui.theme.PullToRefreshComposeTheme
import com.google.accompanist.swiperefresh.*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PullToRefreshComposeTheme {
                RefreshData()
            }
        }
    }
    //Card View oluşturma
    @Composable
    fun CardComponent(data: Data) {
        //Card view köşelerinin yuvarlıklığını, arka plan rengi gibi özelliklerini tanımlıyoruz
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = 2.dp,
            backgroundColor = Color(android.graphics.Color.parseColor(data.backColor))
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                //Card view içine başlık ve kategori Text'lerini tanımlıyoruz
                Text(text = data.title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = data.category, fontSize = 14.sp)
            }
        }
    }
    @Composable
    private fun RefreshData() {
        //Verileri array'e eklediğimiz ve karıştırılmış veriyi sunan ViewModel sınıfı tanımlıyoruz
        val viewModel: RefreshDataViewModel = viewModel()
        val isRefreshing by viewModel.isRefreshing.collectAsState()
        val items = remember { mutableStateOf(dataArray) }

        SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = isRefreshing), onRefresh = {
            //RefreshDataViewModel'de 1 saniyede yenileme yapan metodu
            viewModel.refresh()
            //RefreshDataViewModel'de karıştırılmış içeriğimizi kullanıyoruz
            items.value = viewModel.shuffleItems()
        }) {
            //Card elementlerini alt alta listeleme için LazyColumn tanımlıyoruz
            LazyColumn(
                contentPadding = PaddingValues(15.dp), verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items.value) { item ->
                    CardComponent(data = item)
                }
            }
        }
    }
}