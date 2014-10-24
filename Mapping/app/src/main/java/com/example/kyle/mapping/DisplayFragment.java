//Kyle Kauck

package com.example.kyle.mapping;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayFragment extends Fragment {

    DataHelper mDataHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.display_fragment, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        final View view = getView();
        assert view != null;

        mDataHelper = ((DisplayActivity) getActivity()).setValue();

        ImageView image = (ImageView) view.findViewById(R.id.displayImage);
        TextView title = (TextView) view.findViewById(R.id.displayTitle);
        TextView date = (TextView) view.findViewById(R.id.displayTime);

        title.setText(mDataHelper.getName());
        date.setText(mDataHelper.getTime());

        Uri imageUri = Uri.parse(mDataHelper.getImage());
        //mCreateImage.setImageBitmap(BitmapFactory.decodeFile(mImageUri.getPath()));
        image.setImageBitmap(BitmapFactory.decodeFile(imageUri.getPath()));

    }
}
