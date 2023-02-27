package android.maxim.rxjavapractice

import android.maxim.rxjavapractice.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dispose: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener { doAction() }
    }

    private fun getObservable(): Observable<String> {
        return Observable.just(binding.editText.text.toString())
            .subscribeOn(Schedulers.newThread())
            .map { it.uppercase() }
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun doAction() {
        dispose = getObservable().subscribe {
            binding.textView.text = it
        }
    }

    override fun onStop() {
        super.onStop()
        dispose.dispose()
    }

}


