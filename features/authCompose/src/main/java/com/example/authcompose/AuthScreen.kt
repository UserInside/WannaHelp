package com.example.authcompose

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common.compose.Colors
import com.example.common.R as commonR

@Composable
internal fun AuthScreen(
    onBackClicked: () -> Unit,
    onNavigate: () -> Unit,
) {
    val viewModel = viewModel<AuthViewModel>()
    AuthView(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        onBackClicked = onBackClicked,
        onNavigate = onNavigate
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AuthView(
    state: AuthScreenState,
    onEvent: (AuthScreenEvent) -> Unit = {},
    onBackClicked: () -> Unit,
    onNavigate: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(commonR.string.authorization),
                        textAlign = TextAlign.Center,
                        fontSize = 21.sp,
                        color = Colors.white,
                        fontWeight = FontWeight.ExtraBold,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onBackClicked() }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Colors.leaf,
                    titleContentColor = Colors.white,
                    navigationIconContentColor = Colors.white,
                )
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Colors.white)
                    .padding(20.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 76.dp),
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
                            tint = Color.Unspecified,
                            contentDescription = null
                        )
                        Icon(
                            modifier = Modifier
                                .padding(horizontal = 40.dp)
                                .size(40.dp),
//                        .padding(40.dp),
                            painter = painterResource(R.drawable.icon_ok),
                            tint = Color.Unspecified,
                            contentDescription = null
                        )
                        Icon(
                            modifier = Modifier.size(40.dp),
                            painter = painterResource(R.drawable.icon_fb),
                            tint = Color.Unspecified,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.size(40.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 14.sp,
                        color = Colors.black_70,
                        text = stringResource(commonR.string.auth_title2),
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    Text(
                        color = Colors.black_38,
                        text = stringResource(commonR.string.email)
                    )

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = state.email,
                        placeholder = {
                            Text(
                                text = stringResource(commonR.string.input_email),
                                color = Colors.black_38
                            )
                        },
                        onValueChange = {
                            onEvent(AuthScreenEvent.SetEmailEvent(it))
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Colors.white,
                            focusedContainerColor = Colors.white,
                            focusedTextColor = Colors.black_70,
                            focusedIndicatorColor = Colors.black_70,
                        ),
                    )
                    Spacer(modifier = Modifier.size(20.dp))

                    Text(
                        color = Colors.black_38,
                        text = stringResource(commonR.string.tv_password),
                    )

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = state.password,
                        placeholder = {
                            Text(
                                text = stringResource(commonR.string.input_password),
                                color = Colors.black_38
                            )
                        },
                        onValueChange = {
                            onEvent(AuthScreenEvent.SetPasswordEvent(it))
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Colors.white,
                            focusedContainerColor = Colors.white,
                            focusedTextColor = Colors.black_70,
                            focusedIndicatorColor = Colors.black_70,
                        ),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = state.isButtonActive,
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Colors.leaf,
                            disabledContainerColor = Colors.warm_grey,
                        ),
                        onClick = {
                            onNavigate()
                        },
                    ) {
                        Text(
                            fontSize = 16.sp,
                            color = Colors.white,
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
                            textDecoration = TextDecoration.Underline,
                        )
                        Text(
                            text = stringResource(commonR.string.registration),
                            color = Colors.leaf,
                            textDecoration = TextDecoration.Underline,

                            )
                    }
                }
            }
        }
    )
}
//@Preview
//@Composable
//fun authViewPreview() {
//    AuthView { }
//}