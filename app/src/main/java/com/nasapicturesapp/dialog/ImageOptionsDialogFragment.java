package com.nasapicturesapp.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.nasapicturesapp.R;

public class ImageOptionsDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View couponView = inflater.inflate(R.layout.image_options_menu, null);
        builder.setView(couponView).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getContext(), "options menu shown", Toast.LENGTH_SHORT).show();
//                    CustomToast.toast("Coupon Code: "+couponCode.getText().toString(), getActivity());
//                couponDialogListener.onDialogPositiveClick(DialogFragClass.this, couponCode.getText().toString());
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ImageOptionsDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
