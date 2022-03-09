package id.web.rpgfantasy.adv160419097week4.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.web.rpgfantasy.adv160419097week4.model.Student

class DetailViewModel: ViewModel() {
    val studentLiveData = MutableLiveData<Student>()

    fun fetch() {
        studentLiveData.value = Student("16055","Nonie","1998/03/28","5718444778","http://dummyimage.com/75x100.jpg/cc0000/ffffff")
    }
}