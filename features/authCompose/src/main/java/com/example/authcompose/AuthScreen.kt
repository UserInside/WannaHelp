package com.example.authcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common.compose.Colors
import com.example.common.R as commonR

@Composable
internal fun AuthScreen() {
    val viewModel = viewModel<AuthViewModel>()
    AuthView(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
    )
}

@Composable
internal fun AuthView(
    state: AuthScreenState = AuthScreenState(),
    onEvent: (AuthScreenEvent) -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(top = 40.dp),
                fontSize = 14.sp,
                color = Colors.black_70,
                text = stringResource(commonR.string.auth_title),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(R.drawable.icon_vk),
                    tint = Colors.white,
                    contentDescription = null
                )
                Icon(
                    modifier = Modifier.size(40.dp).padding(40.dp),
                    painter = painterResource(R.drawable.icon_ok),
                    tint = Colors.white,
                    contentDescription = null
                )
                Icon(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(R.drawable.icon_fb),
                    tint = Colors.white,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.size(40.dp))
            Text(
                fontSize = 14.sp,
                color = Colors.black_70,
                text = stringResource(commonR.string.auth_title2),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                stringResource(commonR.string.email)
            )
            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp),
                color = Colors.warm_grey
            )
            TextField(
                value = state.email,
                onValueChange = {
                    onEvent(AuthScreenEvent.SetEmailEvent(it))
                },
            )
            Spacer(modifier = Modifier.size(20.dp))

            Text(
                text = stringResource(commonR.string.tv_password),

                )
            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp),
                color = Colors.warm_grey
            )
            TextField(
                value = state.password,
                onValueChange = {
                    onEvent(AuthScreenEvent.SetPasswordEvent(it))
                },
            )
            Spacer(modifier = Modifier.size(20.dp))
            Button(
                modifier = Modifier.fillMaxWidth().background(Colors.leaf),
                shape = RoundedCornerShape(2.dp),
                onClick = {}, //todo . сделать навигацию и изменение активности кнопки по вм.изФилд...
            ) {
                Text(
                    fontSize = 16.sp,
                    color = Colors.black_38,
                    textAlign = TextAlign.Center,
                    text = stringResource(commonR.string.enter)
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(commonR.string.forgotten_password),
                    color = Colors.leaf,
                )
                Text(
                    text = stringResource(commonR.string.registration),
                    color = Colors.leaf,
                )
            }
        }
    }
}

@Preview
@Composable
private fun AuthScreenPreview() {
    AuthView()
}