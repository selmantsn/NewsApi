package com.newsapi.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.newsapi.R
import com.newsapi.databinding.ActivityMainBinding
import com.newsapi.paging.ItemLoadStateAdapter
import com.newsapi.utils.getTenDaysAgo
import com.newsapi.utils.showDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var newsPagingAdapter: NewsPagingAdapter

    private val viewModel: NewsViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViews()
        collectUiState()
    }

    private fun initViews() {
        selectedDate = getTenDaysAgo()
        binding.tvDate.text = selectedDate

        binding.materialToolbar.setNavigationOnClickListener {
            showDatePicker(selectedDate) {
                selectedDate = it
                binding.tvDate.text = selectedDate
                updateUiState()
            }
        }
        binding.rvNews.adapter = newsPagingAdapter.withLoadStateHeaderAndFooter(
            header = ItemLoadStateAdapter(), footer = ItemLoadStateAdapter()
        )

        newsPagingAdapter.setListener(object : NewsPagingAdapter.Listener {
            override fun itemClicked(url: String?) {
                val viewIntent = Intent("android.intent.action.VIEW", Uri.parse(url))
                startActivity(viewIntent)
            }
        })

        newsPagingAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Error) {
                Toast.makeText(
                    this@MainActivity,
                    (loadState.refresh as LoadState.Error).error.message,
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }

    private fun collectUiState() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getNews(selectedDate, selectedDate).collectLatest {
                newsPagingAdapter.submitData(it)
            }
        }
    }


    private fun updateUiState() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.updateDateAndFetchData(selectedDate, selectedDate).collectLatest {
                newsPagingAdapter.submitData(it)
                binding.rvNews.scrollToPosition(0)
            }
        }
    }


}