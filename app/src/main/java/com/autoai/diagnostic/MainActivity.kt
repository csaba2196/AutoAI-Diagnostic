
```kotlin
package com.autoai.diagnostic

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        BluetoothAdapter.getDefaultAdapter()
    }

    private val permissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestBluetoothPermissions()

        setContent {
            AutoAIDiagnosticApp(
                bluetoothAdapter = bluetoothAdapter
            )
        }
    }

    private fun requestBluetoothPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT
                )
            )
        }
    }
}

@Composable
fun AutoAIDiagnosticApp(
    bluetoothAdapter: BluetoothAdapter?
) {
    var connected by remember { mutableStateOf(false) }
    var selectedVehicle by remember { mutableStateOf("Noch kein Fahrzeug ausgewählt") }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("AutoAI Diagnostic")
                    }
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "Universal AI Fahrzeugdiagnose",
                    style = MaterialTheme.typography.headlineSmall
                )

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Fahrzeug")
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(selectedVehicle)

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(onClick = {
                                    selectedVehicle = "Porsche Cayenne 957 3.6"
                                }
                            ) {
                                Text("Porsche")
                            }

                            Button(
                                onClick = {
                                    selectedVehicle = "VW / Audi"
                                }
                            ) {
                                Text("VW/Audi")
                            }
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Diagnoseadapter")

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            if (connected)
                                "🟢 Adapter verbunden"
                            else
                                "🔴 Kein Adapter verbunden"
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                connected = bluetoothAdapter != null
                            }
                        ) {
                            Text("Bluetooth-Adapter suchen")
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "Diagnose",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Fehlercodes")
                        Text("Live-Daten")
                        Text("Steuergeräte")
                        Text("Service")
                        Text("Codierung")
                        Text("KI-Diagnose")
                    }
                }
            }
        }
    }
}
