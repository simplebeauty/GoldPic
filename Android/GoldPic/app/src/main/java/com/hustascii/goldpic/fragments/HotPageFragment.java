package com.hustascii.goldpic.fragments;

import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hustascii.goldpic.R;
import com.hustascii.goldpic.adapter.HomeAdapter;
import com.hustascii.goldpic.beans.Picture;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;


public class HotPageFragment extends Fragment {


    private ListView mListView;
    private HomeAdapter homeAdapter;
    private ArrayList<Picture> mList;
    private ImageLoader mImageLoader;



    public HotPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<Picture>();
    }

    private void getData(){
        for(int i=0; i<10 ; i++){
            Picture pic = new Picture();
            mList.add(pic);
        }
        homeAdapter.notifyDataSetChanged();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_hot_page, null, false);
        mListView = (ListView)view.findViewById(R.id.piclist);
        mImageLoader = ImageLoader.getInstance();
        mListView.setOnScrollListener(new PauseOnScrollListener(mImageLoader,false, false));

        homeAdapter = new HomeAdapter(getActivity(),mList);

        mListView.setAdapter(homeAdapter);
        getData();
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
    }

}
