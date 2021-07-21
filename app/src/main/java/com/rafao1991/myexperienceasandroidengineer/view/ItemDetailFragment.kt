package com.rafao1991.myexperienceasandroidengineer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.rafao1991.myexperienceasandroidengineer.databinding.FragmentItemDetailBinding
import com.rafao1991.myexperienceasandroidengineer.model.Experience

class ItemDetailFragment : Fragment() {

    private var item: Experience? = null

    private lateinit var itemDetailTextView: TextView

    private var _binding: FragmentItemDetailBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                item = it.getParcelable(ARG_ITEM_ID)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        item?.let {
            binding.toolbarLayout.title = it.year.toString()

            itemDetailTextView = binding.itemDetail
            itemDetailTextView.text = it.description
        }

        return rootView
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}