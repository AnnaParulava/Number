package com.example.number.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.number.R
import com.example.number.databinding.FragmentGameBinding
import com.example.number.domain.entity.Level

class GameFragment : Fragment() {

    private lateinit var level: Level

    private val viewModel: GameFragmentViewModel by lazy {
        ViewModelProvider(
            this,
            GameViewModelFactory(
                level,
                requireActivity().application
            )
        )[GameFragmentViewModel::class.java]
    }

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setClickListenersToOptions() {
        for (tvOption in tvOptions) {
            tvOption.setOnClickListener {
                viewModel.chooseAnswer(tvOption.text.toString().toInt())
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        viewModel.startGame(level)
        setClickListenersToOptions()
    }

    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            with(binding) {
                tvSum.text = it.sum.toString()
                tvLeftNumber.text = it.visibleNumber.toString()

                for (i in 0 until tvOptions.size) {
                    tvOptions[i].text = it.options[i].toString()
                }
            }
            viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
                binding.progressBar.setProgress(it, true)
            }
            viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
                binding.tvAnswersProgress.setTextColor(getColorByState(it))
            }
            viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
                val color = getColorByState(it)
                binding.progressBar.progressTintList = ColorStateList.valueOf(color)
            }

            viewModel.formattedTime.observe(viewLifecycleOwner) {
                binding.tvTimer.text = it
            }

            viewModel.minPercent.observe(viewLifecycleOwner) {
                binding.progressBar.secondaryProgress = it
            }

            viewModel.gameResult.observe(viewLifecycleOwner) {
                //gamefinishedfragment
            }

            viewModel.progressAnswers.observe(viewLifecycleOwner) {
                binding.tvAnswersProgress.text = it
            }
        }
    }

    fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        const val NAME = "GameFragment"
        private const val KEY_LEVEL = "level"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}