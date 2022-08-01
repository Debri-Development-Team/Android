import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.debri_lize.R

class SpinnerAdapter(context: Context, datas: ArrayList<String>?) : BaseAdapter() {


    var datas: ArrayList<String>? = datas
    var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return if(datas!=null) {
            datas!!.size
        } else {
            0
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View { //처음에 클릭전에 보여지는 레이아웃
        var view = inflater.inflate(R.layout.spinner_custom, parent, false)

        if (datas != null) {
            val text = datas!![position]
            (view.findViewById<View>(R.id.spinnerText) as TextView).text = text
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View { //클릭 후 보여지는 레이아웃
        var view = inflater.inflate(R.layout.spinner_getview, parent, false)
        val text = datas!![position]
        (view.findViewById<View>(R.id.spinnerText) as TextView).text = text
        return view
    }

    override fun getItem(position: Int): Any {
        return datas!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}