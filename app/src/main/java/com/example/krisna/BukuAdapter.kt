import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.krisna.Buku
import com.example.krisna.R

class BukuAdapter(val mCtx: Context, val bukuList: List<Buku>) :
    RecyclerView.Adapter<BukuAdapter.BukuViewHolder>() {

    // Listener untuk menangani peristiwa klik pada tombol Edit
    private var onEditClickListener: ((Buku) -> Unit)? = null

    // Metode untuk menetapkan listener
    fun setOnEditClickListener(listener: (Buku) -> Unit) {
        onEditClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuViewHolder {
        val view: View = LayoutInflater.from(mCtx).inflate(R.layout.item_cardview, parent, false)
        return BukuViewHolder(view)
    }

    override fun onBindViewHolder(holder: BukuViewHolder, position: Int) {
        val buku = bukuList[position]

        holder.tvTitle.text = buku.nmBuku
        holder.tvSubtitle.text = buku.penerbit
        holder.tvKodeBuku.text = buku.idBuku
    }

    override fun getItemCount(): Int {
        return bukuList.size
    }

    class BukuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvKodeBuku: TextView = itemView.findViewById(R.id.etkodeBuku)
        val tvTitle: TextView = itemView.findViewById(R.id.etTitle)
        val tvSubtitle: TextView = itemView.findViewById(R.id.etSubtitle)
    }
}
