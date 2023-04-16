package `in`.co.androidapp.ecozoom

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import `in`.co.androidapp.ecozoom.databinding.FragmentReportBinding
import java.net.URLEncoder


class ReportFragment : Fragment() {

    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val query = ReportFragmentArgs.fromBundle(requireArguments()).query
        getData(query)
    }

    fun fillInData(response: Response) {
        binding.consUploading.visibility = View.GONE
        binding.txtCarbonScore.text = response.cf_wf[0].replace("Response 2: ", "")
        binding.txtWaterScore.text = response.cf_wf[1].replace("Response 2: ", "")
        binding.txtDisposalGuide.text = response.disposal
    }

    fun getData(query: String) {
        val imgUrl = URLEncoder.encode(query, "utf-8")
        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://coldbrew-ai.onrender.com/final_report?p=${imgUrl}"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val data = Gson().fromJson(response, Response::class.java)
                // Display the first 500 characters of the response string.
                fillInData(data)
            },
            { exception ->
                Log.e("Errr", "That didnt work")
                exception.printStackTrace()
            })
        stringRequest.retryPolicy = object : RetryPolicy {
            override fun getCurrentTimeout(): Int {
                return 100000
            }

            override fun getCurrentRetryCount(): Int {
                return 100000
            }

            @Throws(VolleyError::class)
            override fun retry(error: VolleyError) {
                error.printStackTrace()
            }
        }

        queue.add(stringRequest)

    }

    data class Response(val cf_wf: List<String>, val disposal: String)
}