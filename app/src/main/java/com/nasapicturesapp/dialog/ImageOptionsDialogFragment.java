package com.nasapicturesapp.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.nasapicturesapp.Constants;
import com.nasapicturesapp.R;
import com.nasapicturesapp.model.NasaPicture;

public class ImageOptionsDialogFragment extends DialogFragment {

    private NasaPicture nasaPicture;
    private ImageOptionsDialogFragmentOptionSelectionListener imageOptionsDialogFragmentOptionSelectionListener;

    public ImageOptionsDialogFragment(ImageOptionsDialogFragmentOptionSelectionListener imageOptionsDialogFragmentOptionSelectionListener) {
        this.imageOptionsDialogFragmentOptionSelectionListener = imageOptionsDialogFragmentOptionSelectionListener;
    }

    public interface ImageOptionsDialogFragmentOptionSelectionListener {
        void optionSelected(int selectedOption, NasaPicture nasaPicture);
    }

    public void showDialogForItem(FragmentManager fragmentManager, NasaPicture nasaPicture) {
        this.nasaPicture = nasaPicture;
        show(fragmentManager, Constants.IMAGE_OPTIONS_DIALOG);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View menuOptions = inflater.inflate(R.layout.image_options_menu, null);

        TextView share = menuOptions.findViewById(R.id.share_image);
        TextView setAsWallPaper = menuOptions.findViewById(R.id.set_as_wallpaper);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (v.getTag().toString().equals("share"))
                    imageOptionsDialogFragmentOptionSelectionListener.optionSelected(0, nasaPicture);
                else if (v.getTag().toString().equals("wallpaper"))
                    imageOptionsDialogFragmentOptionSelectionListener.optionSelected(1, nasaPicture);

            }
        };

        share.setOnClickListener(onClickListener);
        setAsWallPaper.setOnClickListener(onClickListener);

        builder.setView(menuOptions).setTitle(getContext().getString(R.string.choose_option));
        builder.setView(menuOptions).setPositiveButton(getContext().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                ImageOptionsDialogFragment.this.dismiss();
            }
        });

        return builder.create();
    }

}
