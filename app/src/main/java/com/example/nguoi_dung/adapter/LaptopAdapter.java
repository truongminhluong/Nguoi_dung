package com.example.nguoi_dung.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nguoi_dung.MainActivity;
import com.example.nguoi_dung.R;
import com.example.nguoi_dung.molder.Laptop;
import com.example.nguoi_dung.utils.AppUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class LaptopAdapter extends RecyclerView.Adapter<LaptopAdapter.ViewHolder> {

    private List<Laptop> list;
    private Context context;

    AddCard addCard;


    public void setAddCard(AddCard addCard) {
        this.addCard = addCard;
    }

    public LaptopAdapter(List<Laptop> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Laptop sanPham = list.get(position);

        if (sanPham == null){
            return;
        }

        //chuyển đổi chuỗi Base64 thành hình ảnh Bitmap
        String hinhanh = sanPham.getHinhanh();
        byte[] anh = Base64.decode(hinhanh, Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(anh,0,anh.length);

        holder.imgHinhAnh.setImageBitmap(bitmap);
        holder.txtTenSp.setText(sanPham.getTensp());
        String txtPrice = AppUtils.formatNumber((long) sanPham.getGiasp()) + " " + AppUtils.getCurrencySymbol();
        holder.txtGiaSp.setText(txtPrice);

        if(isAddedToCart(sanPham)){
            holder.imgDatHang.setBackgroundResource(R.drawable.backgroud_img_dathang);

        }else{
            //TODO
        }


        holder.imgDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addCard != null){
                    addCard.onItemClicked(sanPham);
                }

            }
        });


    }

    private void showDiaLogDeleteSanPham(Laptop sanPham){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("RealTime DataBase").setMessage("Bạn có chắc chắn muốn xóa sản phẩm này không");
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("SanPham");
                reference.child(sanPham.getId()).removeValue();

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSp, txtGiaSp;
        ImageView imgHinhAnh,imgDatHang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhAnh = (ImageView) itemView.findViewById(R.id.item_Anh1);
            txtTenSp = (TextView) itemView.findViewById(R.id.item_Tensp1);
            txtGiaSp = (TextView) itemView.findViewById(R.id.item_Giasp1);
            imgDatHang = (ImageView) itemView.findViewById(R.id.imgDatHang);

        }
    }
    /*check xem sản phẩm đó có trong giỏ hàng hay chưa*/
    private boolean isAddedToCart(Laptop sanPham){
        for (Laptop sp: MainActivity.spCart){
            if(sp.getId().equals(sanPham.getId()))
                return true;
        }
        return false;
    }
}
