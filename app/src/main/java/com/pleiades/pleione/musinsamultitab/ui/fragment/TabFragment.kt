package com.pleiades.pleione.musinsamultitab.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pleiades.pleione.musinsamultitab.Config.Companion.DEFAULT_URL
import com.pleiades.pleione.musinsamultitab.Config.Companion.KEY_CURRENT_TAB_TIME
import com.pleiades.pleione.musinsamultitab.Config.Companion.KEY_CURRENT_TAB_URL_STRING
import com.pleiades.pleione.musinsamultitab.Config.Companion.PREFS
import com.pleiades.pleione.musinsamultitab.R
import com.pleiades.pleione.musinsamultitab.data.UrlString
import com.pleiades.pleione.musinsamultitab.ui.viewmodel.MainViewModel
import com.pleiades.pleione.note.ui.theme.MainTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TabFragment : Fragment() {
    companion object {
        lateinit var supportFragmentManager: FragmentManager

        fun newInstance(): TabFragment {
            return TabFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        supportFragmentManager = requireActivity().supportFragmentManager

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
private fun ComposeScaffold(viewModel: MainViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Surface(modifier = Modifier.fillMaxWidth(), color = Color.White) {
                Row {
                    IconButton(onClick = { TabFragment.supportFragmentManager.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = Color.Black
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            viewModel.insert(UrlString(System.currentTimeMillis(), DEFAULT_URL))
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(R.string.add),
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    ) {
        ComposeUI(viewModel)
    }
}

@Composable
private fun ComposeUI(viewModel: MainViewModel) {
    val prefs = LocalContext.current.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    val currentUrlString = prefs.getString(KEY_CURRENT_TAB_URL_STRING, DEFAULT_URL)!!

    Column {
        Surface(modifier = Modifier.fillMaxWidth(), color = Color.Black) {
            Row {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(all = 16.dp),
                    text = currentUrlString,
                    color = Color.White,
                    maxLines = 1
                )
            }
        }
        val urlStrings by viewModel.urlStrings.collectAsState(initial = emptyList())
        ComposeUrlStrings(urlStrings, viewModel)
    }
}

@Composable
private fun ComposeUrlStrings(urlStrings: List<UrlString>, viewModel: MainViewModel) {
    val coroutineScope = rememberCoroutineScope()

    val prefs = LocalContext.current.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    val editor = prefs.edit()

    val currentUrlString = prefs.getString(KEY_CURRENT_TAB_URL_STRING, DEFAULT_URL)!!
    val currentTime = prefs.getLong(KEY_CURRENT_TAB_TIME, System.currentTimeMillis())

    Surface(
        color = Color.White
    ) {
        LazyColumn {
            itemsIndexed(items = urlStrings) { _, urlString ->
                ComposeUrlStringItem(urlString,
                    onClicked = { item ->
                        coroutineScope.launch(Dispatchers.IO) {
                            viewModel.insert(UrlString(currentTime, currentUrlString))
                            editor.putString(KEY_CURRENT_TAB_URL_STRING, item.urlString)
                            editor.putLong(KEY_CURRENT_TAB_TIME, item.time)
                            editor.apply()
                            viewModel.delete(item)
                            TabFragment.supportFragmentManager.popBackStack()
                        }
                    },
                    onDeleted = { item ->
                        coroutineScope.launch(Dispatchers.IO) {
                            viewModel.delete(item)
                        }
                    })
            }
        }
    }
}

@Composable
private fun ComposeUrlStringItem(urlString: UrlString, onClicked: (UrlString) -> Unit, onDeleted: (UrlString) -> Unit) {
    Row(Modifier.clickable(onClick = { onClicked(urlString) })) {
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(all = 16.dp),
            text = urlString.urlString,
            color = Color.Black,
            maxLines = 1
        )
        IconButton(onClick = { onDeleted(urlString) })
        {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = stringResource(R.string.remove),
                tint = Color.Black
            )
        }
    }
}