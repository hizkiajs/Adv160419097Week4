package id.web.rpgfantasy.adv160419097week4.view

import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat.Token.fromBundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.Person.fromBundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.media.AudioAttributesCompat.fromBundle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import id.web.rpgfantasy.adv160419097week4.R
import id.web.rpgfantasy.adv160419097week4.databinding.FragmentStudentDetailBinding
import id.web.rpgfantasy.adv160419097week4.databinding.StudentListItemBinding
import id.web.rpgfantasy.adv160419097week4.model.Student
import id.web.rpgfantasy.adv160419097week4.util.loadImage
import id.web.rpgfantasy.adv160419097week4.viewmodel.DetailViewModel
import id.web.rpgfantasy.adv160419097week4.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_student_detail.*
import kotlinx.android.synthetic.main.fragment_student_list.*
import kotlinx.android.synthetic.main.student_list_item.*
import java.util.concurrent.TimeUnit
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * A simple [Fragment] subclass.
 * Use the [StudentDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentDetailFragment : Fragment(), StudentUpdateCLickListener, StudentCreateNotificationChannelListener {
    private lateinit var viewModel : DetailViewModel
    private lateinit var dataBinding: FragmentStudentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_student_detail, container, false)
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var id = ""
        arguments?.let {
            id = StudentDetailFragmentArgs.fromBundle(requireArguments()).id
        }

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetch(id)

        observeViewModel()

        dataBinding.updateListener = this
        dataBinding.createNotificationChannelListener = this
    }

    private fun observeViewModel() {
        viewModel.studentLiveData.observe(viewLifecycleOwner){
            dataBinding.student = it

            /*editID.setText(it.id)
            editName.setText(it.name)
            editDate.setText(it.dob)
            imgStudentDetailPhoto.loadImage(it.photoUrl, progressLoadingDetailPhoto)
            editPhone.setText(it.phone)*/
        }
    }

    override fun onStudentUpdateClick(view: View) {
        Toast.makeText(view.context,"Student updated",Toast.LENGTH_SHORT).show()
        Navigation.findNavController(view).popBackStack()
    }

    override fun onStudentCreateNotificationChannel(view: View) {
        Observable
            .timer(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("mynotif", "Notification delayed after 5 seconds")
                MainActivity.showNotification(view.tag.toString(), "Notification created",
                    R.drawable.ic_baseline_error_24)
            }
    }
}