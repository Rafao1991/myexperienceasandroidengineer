package com.rafao1991.myexperienceasandroidengineer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rafao1991.myexperienceasandroidengineer.R
import com.rafao1991.myexperienceasandroidengineer.databinding.FragmentItemListBinding
import com.rafao1991.myexperienceasandroidengineer.databinding.ItemListContentBinding
import com.rafao1991.myexperienceasandroidengineer.model.Experience
import com.rafao1991.myexperienceasandroidengineer.viewmodel.ItemListViewModel

class ItemListFragment : Fragment() {

    private var _binding: FragmentItemListBinding? = null

    private val binding get() = _binding!!

    private val viewModel: ItemListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.itemList

        val onClickListener = View.OnClickListener { itemView ->
            val item = itemView.tag as Experience
            val bundle = Bundle()
            bundle.putParcelable(
                ItemDetailFragment.ARG_ITEM_ID,
                item
            )
            itemView.findNavController().navigate(R.id.show_item_detail, bundle)
        }

        viewModel.viewExperience.observe(viewLifecycleOwner, { experiences ->
            if (!experiences.isNullOrEmpty()) {
                setupRecyclerView(recyclerView, experiences, onClickListener)
            } else {
                viewModel.loadData()
            }
        })
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        experiences: List<Experience>,
        onClickListener: View.OnClickListener
    ) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(
            experiences,
            onClickListener
        )
    }

    class SimpleItemRecyclerViewAdapter(
        private val experiences: List<Experience>,
        private val onClickListener: View.OnClickListener
    ) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = experiences[position]
            holder.idView.text = item.year.toString()

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = experiences.size

        inner class ViewHolder(binding: ItemListContentBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val idView: TextView = binding.idText
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}