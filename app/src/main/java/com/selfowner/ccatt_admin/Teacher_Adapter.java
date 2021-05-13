package com.selfowner.ccatt_admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Teacher_Adapter extends RecyclerView.Adapter<Teacher_Adapter.ImageViewHolder> {
    private Context mcontext;
    private List<Teacher_Helper_Class> mupload;
    public Teacher_Adapter(Context context,List<Teacher_Helper_Class> upload){
        mcontext=context;
        mupload=upload;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mcontext).inflate(R.layout.teacher_card_list,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Teacher_Helper_Class teacherAddHelperClass=mupload.get(position);
        holder.textname.setText(teacherAddHelperClass.getTeacherName());
        holder.textdept.setText(teacherAddHelperClass.getTeacherDept());
        holder.textemail.setText(teacherAddHelperClass.getTeacherEmail());
        holder.textcollege.setText(teacherAddHelperClass.getTeacherCollege());


    }
    @Override
    public int getItemCount() {
        return mupload.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView textname,textdept,textemail,textcollege;
        public ImageViewHolder(View itemView){
            super(itemView);
            textname=itemView.findViewById(R.id.tName);
            textdept=itemView.findViewById(R.id.tDept);
            textemail=itemView.findViewById(R.id.tEmail);
            textcollege=itemView.findViewById(R.id.tCollege);


        }
    }
}
