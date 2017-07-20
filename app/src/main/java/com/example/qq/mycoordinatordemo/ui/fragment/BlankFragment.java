package com.example.qq.mycoordinatordemo.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.qq.mycoordinatordemo.R;

/**
 * Created by Administrator on 2017/3/17 0017.
 */
public class BlankFragment extends Fragment{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_POS = "pos";

    private String mParam1;
    private int mPos;

    public BlankFragment() {
    }

    public static BlankFragment newInstance(String param1,int param2){
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1,param1);
        args.putInt(ARG_POS,param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mParam1 = getArguments().getString(ARG_PARAM1);
            mPos = getArguments().getInt(ARG_POS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank,container,false);
        ImageView imageView = (ImageView)view.findViewById(R.id.iv_fg);
        switch (mPos){
            case 0:
                imageView.setImageResource(R.drawable.pos0);
                break;
            case 1:
                imageView.setImageResource(R.drawable.pos1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.pos2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.pos3);
                break;
        }
        return view;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
