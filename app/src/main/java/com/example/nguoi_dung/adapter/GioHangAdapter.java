package com.example.nguoi_dung.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nguoi_dung.MainActivity;
import com.example.nguoi_dung.R;
import com.example.nguoi_dung.molder.Laptop;
import com.example.nguoi_dung.utils.AppUtils;

import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {
    private List<Laptop> list;
    private Context context;

    CheckBoxItem checkBoxItem;
    ItemChangedListener listener;

    public void setCheckBoxItem(CheckBoxItem checkBoxItem) {
        this.checkBoxItem = checkBoxItem;
    }

    public GioHangAdapter(List<Laptop> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Laptop laptop = list.get(position);

        //chuyển đổi chuỗi Base64 thành hình ảnh Bitmap
        String hinhanh = laptop.getHinhanh();
        byte[] anh = Base64.decode(hinhanh, Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(anh,0,anh.length);

        holder.checkBox.setChecked(MainActivity.itemOrders.contains(laptop));
        holder.imgHinhanh.setImageBitmap(bitmap);
        holder.txtTen.setText(laptop.getTensp());
        String txtPrice = AppUtils.formatNumber((long) laptop.getGiasp()) + " " + AppUtils.getCurrencySymbol();
        holder.txtGia.setText(txtPrice);
        holder.txtCount.setText(""+laptop.getSoLuong());

        //xóa sản phẩn trong giỏ hàng
        holder.imgXoaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogDeleteCard(laptop);
                MainActivity.numInCart--;
                MainActivity.updateBadge();

            }
        });
        //nút giảm số lượng
        holder.giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl = laptop.getSoLuong() - 1;
                laptop.setSoLuong(sl);
                if (sl <= 1){
                    laptop.setSoLuong(1);
                }

                holder.txtCount.setText(String.valueOf(laptop.getSoLuong()));
                int abc = (int) (laptop.getGiasp()*laptop.getSoLuong());

                Log.d("TAG", "onClick: "+abc);
                String txtPrice = AppUtils.formatNumber((long) abc) + " " + AppUtils.getCurrencySymbol();

                holder.txtGia.setText(txtPrice);
                listener.onChanged();
                //Reload List
            }
        });
        //nút tăng số lượng
        holder.tang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl = laptop.getSoLuong() + 1;
                laptop.setSoLuong(sl);
                holder.txtCount.setText(String.valueOf(laptop.getSoLuong()));

                int abc = (int) (laptop.getGiasp()*laptop.getSoLuong());

                Log.d("TAG", "onClick: "+abc);
                String txtPrice = AppUtils.formatNumber((long) abc) + " " + AppUtils.getCurrencySymbol();

                holder.txtGia.setText(txtPrice);
                listener.onChanged();


            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.itemOrders.contains(laptop)){
                    MainActivity.itemOrders.remove(laptop);
                    holder.checkBox.setChecked(false);
                }else{
                    MainActivity.itemOrders.add(laptop);
                    holder.checkBox.setChecked(true);
                }
                listener.onChanged();
            }
        });




    }


    private void showDiaLogDeleteCard(Laptop sanPham){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo!!").setMessage("Bạn có chắc chắn muốn xóa sản phẩm này không");
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Update badge
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                list.remove(sanPham);
                MainActivity.itemOrders.remove(sanPham);
                listener.onChanged();
                MainActivity.spCart.remove(sanPham);
                MainActivity.updateBadge();
                notifyDataSetChanged();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    public void setListener(ItemChangedListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ImageView imgHinhanh,imgXoaCard;
        TextView txtTen, txtGia, txtCount;
        AppCompatButton giam,tang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.ckbCard);
            imgHinhanh = itemView.findViewById(R.id.card_Anh);
            imgXoaCard = itemView.findViewById(R.id.card_xoa);
            txtTen = itemView.findViewById(R.id.card_Tensp);
            txtGia = itemView.findViewById(R.id.card_Giasp);
            txtCount = itemView.findViewById(R.id.txt_count);
            giam = itemView.findViewById(R.id.btn_giam);
            tang = itemView.findViewById(R.id.btn_tang);
        }
    }

    public interface ItemChangedListener{
        void onChanged();
    }
}
