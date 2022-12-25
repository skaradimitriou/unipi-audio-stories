package com.stathis.unipiaudiostories.ui.main.play

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseFragment
import com.stathis.unipiaudiostories.databinding.FragmentPlayStoryBinding
import com.stathis.unipiaudiostories.tts.TextToSpeechUtil
import com.stathis.unipiaudiostories.util.setScreenTitle

class PlayStoryFragment : BaseFragment<FragmentPlayStoryBinding>(R.layout.fragment_play_story) {

    private val viewModel: PlayStoryViewModel by viewModels()
    private val safeArgs: PlayStoryFragmentArgs by navArgs()

    private val tts = TextToSpeechUtil()

    override fun init() {
        setScreenTitle(getString(R.string.play_story_title))
        binding.model = safeArgs.story
        binding.viewModel = viewModel
        tts.initialize(requireContext())
    }

    override fun startOps() {
        viewModel.buttonState.observe(viewLifecycleOwner) { isPlaying ->
            if (isPlaying) {
                tts.speak(safeArgs.story.text)
                binding.playButton.setImageResource(R.drawable.ic_stop)
            } else {
                binding.playButton.setImageResource(R.drawable.ic_play)
                tts.stop()
            }
        }
    }

    override fun stopOps() {}

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }
}