package com.example.androidworkshop.activities

import android.app.Application
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.example.androidworkshop.BeerAdapter
import com.example.androidworkshop.MyApplication
import com.example.androidworkshop.R
import com.example.androidworkshop.database.BeerViewModel
import com.example.androidworkshop.database.MyRoomDatabase
import com.example.androidworkshop.networking.Beer
import com.example.androidworkshop.networking.BeerApiController
import com.example.securesharedpref.database.BeerEntity
import kotlinx.android.synthetic.main.activity_list.*
import org.jetbrains.anko.doAsync
import timber.log.Timber

class ListActivity : AppCompatActivity() {


    lateinit var adapter: BeerAdapter

    var list = mutableListOf<Beer>()

    lateinit var viewModel: BeerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        /**
         * The RecyclerView uses a layout manager to position the individual items on the screen
         * and determine when to reuse item views that are no longer visible to the user.
         * To reuse (or recycle) a view, a layout manager may ask the adapter to replace the contents
         * of the view with a different element from the dataset. Recycling views in this manner
         * improves performance by avoiding the creation of unnecessary views or performing expensive findViewById() lookups.
         */
        val layoutManager = LinearLayoutManager(this)
        // Optionally customize the position you want to default scroll to
        layoutManager.scrollToPosition(0)
        recycleView.layoutManager = layoutManager
        recycleView.setHasFixedSize(true)

        //We can decorate the items using various decorators attached to the recyclerview such as the DividerItemDecoration:
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recycleView.addItemDecoration(itemDecoration)

        adapter = BeerAdapter(list)
        recycleView.adapter = adapter


        viewModel = BeerViewModel(application)
        viewModel.getBeerList().observe(this, Observer { newlist ->
            Timber.d("@@ view model observer @@")
            if (newlist != null) {
                val newList: List<Beer> = newlist.map { Beer(it.id, it.name, it.desc) }
                list.addAll(newList)

                Timber.d("list size=${list.size}")
                adapter.updateData(list)
            }
        })
    }


    override fun onResume() {
        super.onResume()

        val db = MyRoomDatabase.getInstance(this)

        doAsync {

            // call to retrofit to get list of beers
            val beerController = BeerApiController()
            beerController.start()

            val listOfBeers = beerController.listOfBeers

            db.beerDao().deleteAll()
            for (i in listOfBeers.indices) {
                val b = listOfBeers[i]
                val entity = BeerEntity(b.id, b.name, b.description, "")
                Timber.d("insert item to db")
                db.beerDao().insert(entity)
            }


//            runOnUiThread {
//                list.clear()
//                list.addAll(listOfBeers)
//
//                adapter.updateData(listOfBeers)
//            }
        }


        // update listview
    }
}
