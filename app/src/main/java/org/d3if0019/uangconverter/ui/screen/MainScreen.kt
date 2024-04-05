package org.d3if0019.uangconverter.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0019.uangconverter.R
import org.d3if0019.uangconverter.model.Uang
import org.d3if0019.uangconverter.model.mataUang
import org.d3if0019.uangconverter.navigation.Screen
import org.d3if0019.uangconverter.ui.theme.UangConverterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {navController.navigate(Screen.About.route)}) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(id = R.string.about_app),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding))
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ScreenContent(modifier: Modifier) {
    var jumlah by rememberSaveable { mutableStateOf("") }
    var jumlahError by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }

    var asal by remember { mutableStateOf(mataUang[0]) }
    var asalError by remember { mutableStateOf(false) }
    var asalInput by rememberSaveable { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    var tujuan by remember { mutableStateOf(mataUang[1]) }
    var tujuanError by remember { mutableStateOf(false) }
    var tujuanInput by rememberSaveable { mutableStateOf("") }
    var hasil by rememberSaveable { mutableDoubleStateOf(0.0) }
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = jumlah,
            onValueChange = { jumlah = it },
            label = {Text(text = stringResource(R.string.amount))},
            isError = jumlahError,
            singleLine = true,
            trailingIcon = { IconPicker(jumlahError, "")},
            supportingText = { ErrorHint(jumlahError) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = stringResource(id = R.string.from))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange ={ newValue ->
                expanded = newValue },
            ) {
                TextField(
                    value = asalInput,
                    onValueChange = {asalInput = it },
                    readOnly = true,
                    singleLine = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    placeholder = { Text(text = stringResource(id = R.string.from_input)) },
                    isError = asalError,
                    supportingText = { ErrorHint(asalError)},
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    mataUang.forEach{ uang ->
                        DropdownMenuItem(
                            leadingIcon = {
                                Image(painter = painterResource(id = uang.imageResId),
                                    contentDescription = stringResource(R.string.pictures, uang.nama),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                )
                            },
                            text = { Text(text = uang.kode) },
                            onClick = {
                                keyboardController?.hide()
                                asalInput = uang.nama
                                asal = uang
                                expanded = false
                            },
                        )
                    }
                }
            }
        }
        Text(text = stringResource(id = R.string.to))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded2,
                onExpandedChange ={ newValue ->
                    expanded2 = newValue },
            ) {
                TextField(
                    value = tujuanInput,
                    onValueChange = {tujuanInput = it },
                    readOnly = true,
                    singleLine = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded2) },
                    placeholder = { Text(text = stringResource(id = R.string.to_input)) },
                    isError = tujuanError,
                    supportingText = { ErrorHint(tujuanError)},
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                ExposedDropdownMenu(
                    expanded = expanded2,
                    onDismissRequest = { expanded2 = false },
                ) {
                    mataUang.forEach{ uang ->
                        DropdownMenuItem(
                            leadingIcon = {
                                Image(painter = painterResource(id = uang.imageResId),
                                    contentDescription = stringResource(R.string.pictures, uang.nama),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                )
                            },
                            text = { Text(text = uang.kode) },
                            onClick = {
                                keyboardController?.hide()
                                tujuanInput = uang.nama
                                tujuan = uang
                                expanded2 = false
                            },
                        )
                    }
                }
            }
        }
        Button(onClick = {
            jumlahError = (jumlah == "" || jumlah == "0")
            asalError = (asalInput == "")
            tujuanError = (tujuanInput == "")
            if (jumlahError || asalError || tujuanError) return@Button
            hasil = convertUang(jumlah.toDouble(),asal, tujuan)
        },
            modifier = Modifier.padding(top = 16.dp),
            contentPadding = PaddingValues(horizontal=32.dp, vertical=16.dp)
        ) {
            Text(text = stringResource(R.string.convert))
        }
        if (hasil != 0.0) {
        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp
        )
        Text(
            text = stringResource(id = R.string.result, hasil),
            style = MaterialTheme.typography.titleLarge
        )
            Button(
                onClick = {
                          shareData(
                              context = context,
                              message = context.getString(R.string.share_template,
                                  jumlah,asalInput, tujuanInput, hasil
                              )
                          )
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.share))
            }
        }
    }
    
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}
@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}

fun convertUang(jumlah: Double, asal: Uang, tujuan: Uang): Double {
    val nilaiAsal = asal.nilai
    val nilaiTujuan = tujuan.nilai

    return jumlah / nilaiAsal * nilaiTujuan
}

private  fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ScreenPreview() {
    UangConverterTheme {
        MainScreen(rememberNavController())
    }
}