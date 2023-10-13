package com.find.myipapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codetask.mainapp.ui.theme.MainAppTheme
import com.find.myip.R
import com.find.myipapp.api_content.RetrofitClient
import com.find.myipapp.model.MyIpResponse
import com.find.myipapp.repo.DataSource
import com.find.myipapp.repo.MyRepository
import com.find.myipapp.repo.Resource
import com.find.myipapp.viewmodel.MyIpViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val myIPViewModel: MyIpViewModel by viewModels {
        ViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainAppTheme {
                FindMyIpScreen(myIPViewModel)
            }
        }
    }
}

@Composable
fun FindMyIpScreen(myIPViewModel: MyIpViewModel) {
    val scope = rememberCoroutineScope()

    val ipDetails by myIPViewModel.myIpResponse.observeAsState(
        initial = Resource.Empty(),
    )

    Screen(ipDetails) {
        scope.launch {
            myIPViewModel.findMyIp()
        }
    }
}

@Composable
fun Screen(myIpResponse: Resource<MyIpResponse>, onCheckAPICall: () -> Unit) {
    when (myIpResponse) {
        is Resource.Failure -> {
            myIpResponse.message?.let { FailureMessage(it) }
        }

        is Resource.Loading -> {
            ShowProgress()
        }

        is Resource.Success -> {
            myIpResponse.data?.let { FetchMyIp(it.ip!!, it.city!!) }
        }

        is Resource.Empty -> {
            ResourceEmpty(fetchApiCall = onCheckAPICall)
        }

        else -> {
//            Toast.makeText(context, "", Toast.LENGTH_LONG).show()
        }
    }
}

@Composable
fun FailureMessage(string: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) { Text(text = string) }
}

@Composable
fun ShowProgress() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(size = 50.dp),
            color = Color.Cyan,
        )
        Spacer(modifier = Modifier.width(width = 15.dp))
        Text(text = stringResource(id = R.string.loading))
    }
}

@Composable
fun FetchMyIp(
    ip: String,
    city: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            shape = RoundedCornerShape(
                topStart = 15.dp,
                topEnd = 15.dp,
                bottomStart = 15.dp,
                bottomEnd = 15.dp,
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(18.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {

                Text(
                    text = "IP Address: $ip",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.Black,
                )

                Spacer(Modifier.height(25.dp))

                Text(
                    text = "City : $city",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.Black,
                )
            }
        }
    }
}

@Composable
fun ResourceEmpty(modifier: Modifier = Modifier, fetchApiCall: () -> Unit) {
    Column(
        modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(onClick = fetchApiCall) {
            Text(
                text = stringResource(id = R.string.btn_find_my_ip_label),
                fontSize = 20.sp,
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 15.dp,
                    bottom = 15.dp,
                ),
            )
        }
    }
}

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyIpViewModel::class.java)) {
            return MyIpViewModel(myRepository = MyRepository(dataSource = DataSource(api = RetrofitClient.myIpClient))) as T
        }
        throw IllegalArgumentException("ViewModel not found!!!")
    }
}
