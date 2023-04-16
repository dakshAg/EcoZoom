package `in`.co.androidapp.ecozoom

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import `in`.co.androidapp.ecozoom.databinding.FragmentCameraBinding
import `in`.co.androidapp.ecozoom.databinding.FragmentOptionsBinding
import java.net.URLEncoder

class OptionsFragment : Fragment() {
    private var _binding: FragmentOptionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageUrl = OptionsFragmentArgs.fromBundle(requireArguments()).imageUrl
        binding.imageView.load(imageUrl)
        getData(imageUrl)

        binding.button.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun fillRecyclerView(list: List<Option>) {
        val adapter = OptionAdapter(list, findNavController())

        // Setting the Adapter with the recyclerview
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter
    }

    fun getData(image_url: String) {
//        val rsp = listOf(
//            Option("https://m.media-amazon.com/images/M/MV5BMTk0Njk2OTY2N15BMl5BanBnXkFtZTcwNTAxNjAzMQ@@._V1_.jpg","Stainless Steel Specialty | Dulux","https://www.dulux.com.au/colours/details/Specialty_000001")
//        )


        // Display the first 500 characters of the response string.
        //fillRecyclerView(rsp)

        val imgUrl = URLEncoder.encode(image_url, "utf-8")
        val queue = Volley.newRequestQueue(requireContext())
        val url = "http://128.250.0.196:5000/image_search?url=${imgUrl}"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->

                val list = Gson().fromJson<List<Option>>(response, List::class.java)
                // Display the first 500 characters of the response string.
                fillRecyclerView(list)
            },
            { exception ->
                Log.e("Errr", "That didnt work")
                exception.printStackTrace()
            })
        queue.add(stringRequest)

    }
}