package com.dmitryshuba.sample.main.sectionlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dmitryshuba.sample.R
import com.dmitryshuba.sample.databinding.FragmentSectionListBinding
import com.dmitryshuba.sample.main.IMainActivity
import com.dmitryshuba.sample.main.sectionlist.adapter.OnSectionSelectedCallback
import com.dmitryshuba.sample.main.sectionlist.adapter.SectionListAdapter
import com.dmitryshuba.sample.main.sectionlist.util.ISectionListRouter
import com.dmitryshuba.sample.main.util.viewModelCreator
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SectionListFragment : Fragment(), KodeinAware, OnSectionSelectedCallback {

    override val kodein: Kodein by kodein()

    private val mAdapter = SectionListAdapter(this)
    private val mViewModel: SectionListViewModel by viewModelCreator()
    private lateinit var binding: FragmentSectionListBinding
    private val router: ISectionListRouter by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_section_list, container, false)
        setupObservers()
        updateToolbar()
        with(binding) {
            lifecycleOwner = this@SectionListFragment
            viewModel = mViewModel
            sectionRecyclerView.setup()
        }

        return binding.root
    }

    override fun onSelected(href: String) {
        router.navigateToSectionDetails(href)
    }

    private fun fetchData() {
        arguments?.let {
            val href = SectionListFragmentArgs.fromBundle(it).hrefArg
            if (SectionListFragmentArgs.fromBundle(it).backButtonAvailableArg) {
                (activity as IMainActivity).addBackButton()
            } else
                (activity as IMainActivity).removeBackButton()
            mViewModel.fetchSectionList(href)
        }
    }

    private fun updateToolbar() {
        arguments?.let {
            if (SectionListFragmentArgs.fromBundle(it).backButtonAvailableArg) {
                (activity as IMainActivity).addBackButton()
            } else
                (activity as IMainActivity).removeBackButton()
        }
    }

    private fun RecyclerView.setup() {
        layoutManager = LinearLayoutManager(activity)
        setHasFixedSize(true)
        adapter = mAdapter
    }

    private fun setupObservers() {
        with(mViewModel) {
            sectionDataPropsObservable.observe(this@SectionListFragment.viewLifecycleOwner, Observer {
                binding.props = it
                mAdapter.updateUI(it.sectionPropsList)
            })
            stateObservable.observe(this@SectionListFragment.viewLifecycleOwner, Observer { state ->
                when (state) {
                    SectionListViewModelState.ACTION_SHOW_NETWORK_IS_ON -> {
                        binding.internetIssueLayout.apply {
                            if (visibility == View.VISIBLE) {
                                visibility = View.GONE
                            }
                        }
                    }
                    SectionListViewModelState.ACTION_SHOW_NO_NETWORK_WARNING -> {
                        binding.internetIssueLayout.visibility = View.VISIBLE
                    }
                    else -> Unit
                }
            })
        }
    }
}
