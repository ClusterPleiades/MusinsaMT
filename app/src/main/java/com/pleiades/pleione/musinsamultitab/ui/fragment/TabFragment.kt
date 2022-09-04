package com.pleiades.pleione.musinsamultitab.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
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
                    ComposeUI()
                }
            }
        }
    }
}

@Composable
private fun ComposeUI(modifier: Modifier = Modifier, viewModel: MainViewModel = viewModel()) {
    Text(text = "Hello world.")
}