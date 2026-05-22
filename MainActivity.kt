package com.example.vidyavahini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VidyaVahiniApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VidyaVahiniApp() {

    val database = FirebaseDatabase.getInstance().reference

    var selectedRoute by remember {
        mutableStateOf("College Route 101")
    }

    var busStatus by remember {
        mutableStateOf("Waiting for Bus")
    }

    var eta by remember {
        mutableStateOf("12 mins")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    val routes = listOf(
        "College Route 101",
        "School Route 202",
        "Town Route 303"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Vidya-Vahini",
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {

            TextField(
                value = selectedRoute,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Route") },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {

                routes.forEach {

                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            selectedRoute = it
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Bus Status",
                    fontSize = 22.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = busStatus,
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "ETA",
                    fontSize = 22.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = eta,
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {

                busStatus = "Bus crossed the bridge"
                eta = "5 mins"

                database.child("route")
                    .setValue(selectedRoute)

                database.child("status")
                    .setValue(busStatus)

                database.child("eta")
                    .setValue(eta)
            }
        ) {

            Text("I Saw the Bus")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                database.child("safeReach")
                    .setValue("Student Reached College")
            }
        ) {

            Text("Safe Reach")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                database.child("breakdown")
                    .setValue("Bus Breakdown Reported")
            }
        ) {

            Text("Report Breakdown")
        }
    }
}