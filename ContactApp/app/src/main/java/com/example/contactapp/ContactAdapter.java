package com.example.contactapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private ArrayList<Contact> contacts;
    private Context context;
    private OnItemClickListener listener;

    public ContactAdapter(ArrayList<Contact> dataSet, Context context) {
        this.context = context;
        this.contacts = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        String s = contact.getFirstName().substring(0, 1).toUpperCase();

        holder.tvName.setText(contact.getLastName() + " " + contact.getFirstName());
        if (contact.getAvatar() != null) {
            holder.tvAvatar.setVisibility(View.GONE);
            holder.ivAvatar.setVisibility(View.VISIBLE);
            holder.ivAvatar.setImageBitmap(BitmapHelper.byteArrayToBitmap(contact.getAvatar()));
        } else {
            holder.tvAvatar.setVisibility(View.VISIBLE);
            holder.ivAvatar.setVisibility(View.GONE);
            holder.tvAvatar.setText(s);
        }
        holder.tvSort.setText(s);
        if (position != 0 && s.equals(contacts.get(position - 1).getFirstName().substring(0, 1).toUpperCase())) {
            holder.tvSort.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ImageView ivAvatar;
        private TextView tvSort;
        private TextView tvAvatar;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_fullname);
            ivAvatar = view.findViewById(R.id.iv_avatar);
            tvSort = view.findViewById(R.id.tv_sort);
            tvAvatar = view.findViewById(R.id.tv_avatar);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    if (listener != null && index != RecyclerView.NO_POSITION) {
                        listener.onItemClick(contacts.get(index));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setContacts(List<Contact> listContacts) {
        this.contacts.clear();
        this.contacts.addAll(listContacts);
        notifyDataSetChanged();
    }

    public void findByName(List<Contact> listContacts, String name) {
        this.contacts.clear();
        if (name.isEmpty()) {
            this.contacts.addAll(listContacts);
        } else {
            name = name.toLowerCase();
            for (Contact contact : listContacts) {
                String fullName = contact.getLastName() + " " + contact.getFirstName();
                if (fullName.toLowerCase().contains(name)) {
                    this.contacts.add(contact);
                }
            }
        }
        notifyDataSetChanged();
    }
}
