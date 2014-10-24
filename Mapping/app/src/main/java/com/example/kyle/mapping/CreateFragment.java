//Kyle Kauck

package com.example.kyle.mapping;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateFragment extends Fragment {

    private static final int REQUEST_TAKE_PICTURE = 0x010101;

    Uri mImageUri;
    ImageView mCreateImage;
    double mCreateLat;
    double mCreateLong;
    private eventDetails mEventDetail;

    TextView mName;
    TextView mTime;

    public interface eventDetails{

        public void details(String _name, String _time, String _image, double _lat, double _long);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.create_fragment, container, false);

    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

        if (activity instanceof eventDetails){

            mEventDetail = (eventDetails) activity;

        } else {

            throw new IllegalArgumentException("Must Implement This!");

        }

    }

    public void setLocation(){



    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        final View view = getView();
        assert view != null;

        mCreateImage = (ImageView) view.findViewById(R.id.createImage);
        mName = (TextView) view.findViewById(R.id.createName);
        mTime = (TextView) view.findViewById(R.id.createDate);

        Button takePicture = (Button) view.findViewById(R.id.createPicture);
        takePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mImageUri = getOutUri();

                if (mImageUri != null){

                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);

                }

                startActivityForResult(cameraIntent, REQUEST_TAKE_PICTURE);

            }

        });

        Button saveContact = (Button) view.findViewById(R.id.createSave);
        saveContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String name = mName.getText().toString();
                String time = mTime.getText().toString();
                String image = mImageUri.toString();
                double latitiude = DisplayMap.mLatitude;
                double longitude = DisplayMap.mLongitude;

                mEventDetail.details(name, time, image, latitiude, longitude);

                mName.setText("");
                mTime.setText("");
                mCreateImage.setImageBitmap(null);

            }

        });

    }

    private Uri getOutUri(){

        String imageName = new SimpleDateFormat("MMddyyyy_HHmmss").format(new Date(System.currentTimeMillis()));
        File imageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File eventDirectory = new File(imageDirectory, "Event Photos");
        eventDirectory.mkdirs();

        File image = new File(eventDirectory, imageName + ".jpg");

        try{

            image.createNewFile();

        } catch (Exception e){

            e.printStackTrace();
            return null;

        }

        return Uri.fromFile(image);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PICTURE && resultCode != Activity.RESULT_CANCELED){

            mCreateImage.setImageBitmap(BitmapFactory.decodeFile(mImageUri.getPath()));
            addImage(mImageUri);

        } else {

            mCreateImage.setImageBitmap((Bitmap)data.getParcelableExtra("data"));

        }

    }

    private void addImage(Uri imageUri){

        Intent picIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        picIntent.setData(imageUri);
        getActivity().sendBroadcast(picIntent);

    }

}
