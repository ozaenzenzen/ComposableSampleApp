package com.example.composablesampleapp

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import com.example.appsample1.KonnekNative
import com.example.composablesampleapp.ui.theme.ComposableSampleAppTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        KonnekNative.initializeSDK(
            this,
            id = "b699182d-5ff0-4161-b649-239234ff9cb9",
            secret = "1dc8e065-2915-4b4e-8df2-45040e8314bd",
            flavorData = "staging",
        )


        setContent {
            val contextUpper = LocalContext.current

            // Initial position state
            val offsetX = remember { mutableStateOf(0f) }
            val offsetY = remember { mutableStateOf(0f) }

            ComposableSampleAppTheme {
                Box(
                    modifier = Modifier.fillMaxSize()
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(16.dp), // padding to keep button from touching edges
//                    contentAlignment = Alignment.BottomEnd
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    titleContentColor = MaterialTheme.colorScheme.primary,
                                ),
                                title = {
                                    Text("Sample App - Android Native")
                                }
                            )
                        },
                        modifier = Modifier.fillMaxSize(),
                    ) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                        ColumnData(
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
//                    AdvancedDraggableButton()
                    AndroidView(
                        factory = { context ->
                            object : FrameLayout(context) {
                                init {
                                    val floatingButton = KonnekNative.getFloatingButton(context)

                                    // Set layout parameters as if you were in XML
                                    val layoutParams = FrameLayout.LayoutParams(
                                        FrameLayout.LayoutParams.WRAP_CONTENT,
                                        FrameLayout.LayoutParams.WRAP_CONTENT
                                    ).apply {
                                        gravity = Gravity.BOTTOM or Gravity.END
                                        marginEnd = 10.toPx(contextUpper)
                                        bottomMargin = 10.toPx(contextUpper)
                                    }

                                    floatingButton.layoutParams = layoutParams

                                    // Return the view to AndroidView
                                     addView(floatingButton)
                                }

                                override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
                                    // Don't intercept touch events - let children handle them
                                    return false
                                }

                                override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
                                    // Ensure touch events reach the floating button
                                    return super.dispatchTouchEvent(ev)
                                }
                            }
                        },
                        modifier = Modifier
//                            .background(Color(0xff000000))
                            .fillMaxSize()
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .zIndex(4f) // make sure it's above other content
                    )
                }
            }
        }
    }
}

fun Int.toPx(context: Context): Int =
    (this * context.resources.displayMetrics.density).toInt()

private fun Dp.toPx(context: Context): Dp =
    (this * context.resources.displayMetrics.density)


//fun Int.toPx(context: Context): Int =
//    (this * context.resources.displayMetrics.density).toInt()

@Composable
fun AdvancedDraggableButton() {
    val context = LocalContext.current

    AndroidView(
        factory = { context ->
            object : FrameLayout(context) {
                init {
                    val floatingButton = KonnekNative.getFloatingButton(context)
                    addView(floatingButton)
                }

                override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
                    // Don't intercept touch events - let children handle them
                    return false
                }

                override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
                    // Ensure touch events reach the floating button
                    return super.dispatchTouchEvent(ev)
                }
            }
        },
        modifier = Modifier
            .fillMaxSize()
//        modifier = Modifier
//            .align(Alignment.BottomEnd)
//            .padding(16.dp)
//            .zIndex(1f) // make sure it's above other content
    )
}

@Composable
fun LegacyDraggableFloatingButton() {
    val context = LocalContext.current

    // Make sure you only create the view once, and preserve it across recompositions
    val floatingButtonView = remember {
        KonnekNative.getFloatingButton(context)
    }

    // Place the AndroidView into Compose
    AndroidView(
        factory = { floatingButtonView },
        modifier = Modifier
//            .fillMaxSize()  // <-- full touch area forwarding
            .zIndex(1f)
    )
}

@Composable
fun ComposeDraggableFab() {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX = offsetX.plus(dragAmount.x)
                    offsetY = offsetY.plus(dragAmount.y)
                }
            }
    ) {
        FloatingActionButton(
            onClick = { /* Do something */ },
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Draggable FAB")
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun ColumnData(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        content = {
//            AndroidView(
//                factory = { context ->
////                    val fab = KonnekNative.getFloatingButton(context)
////                    root.addView(fab)
//                    KonnekNative.getFloatingButton(context)
//                },
////                modifier = Modifier.wrapContentSize(),
////                modifier = modifier,
//                modifier = Modifier
//                    .align(
//                        alignment = Alignment.CenterHorizontally
//                    )  // Compose native positioning
//                    .padding(16.dp)
//            )
            Text(
                text = "String 1"
            )
            Text(
                text = "String 2"
            )
            Text(
                text = "String 3"
            )
            Button(onClick = {
                println("test")
            }) {
                Text("Filled")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposableSampleAppTheme {
        Greeting("Android")
    }
}