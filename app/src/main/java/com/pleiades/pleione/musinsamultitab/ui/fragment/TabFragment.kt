package com.pleiades.pleione.musinsamultitab.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pleiades.pleione.musinsamultitab.R
import com.pleiades.pleione.musinsamultitab.ui.viewmodel.MainViewModel
import com.pleiades.pleione.note.ui.theme.MainTheme

class TabFragment :Fragment(){
    companion object {
        fun newInstance(): TabFragment {
            return TabFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MainTheme {
                    ComposeScaffold()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ComposeScaffold(){
    Scaffold(
        topBar = {
            Surface(modifier = Modifier.fillMaxWidth(), color = Color.White) {
                Row {
                    IconButton(onClick = { /* TODO */}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    ) {
        ComposeUI()
    }
}

@Composable
private fun ComposeUI(modifier: Modifier = Modifier, viewModel: MainViewModel = viewModel()) {
//    val prefs = LocalContext.current.getSharedPreferences(Config.PREFS, Context.MODE_PRIVATE)
//    val currentUrlString = prefs.getString(Config.KEY_CURRENT_TAB_URL_STRING, "https://m.store.musinsa.com/")!!
//    Text(text = "Hello world.")
}