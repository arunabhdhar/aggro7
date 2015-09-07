package com.app.appfragement.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.Utility.CustomCategoryDialog;
import com.app.adapter.AggroRecyclerGridViewAdapter;
import com.app.adapter.AggroRecyclerViewAdapter;
import com.app.aggro.R;
import com.app.aggro.Registration;
import com.app.getterAndSetter.MyToolBar;
import com.app.gridcategory.ImageItem;
import com.app.holder.ChildItem;
import com.app.holder.GroupItem;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewGridFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    public static final String CUSTOM_CATEGORY_PREFIX = "C-";

    private static final int ITEM_COUNT = 100;

    private GroupItem groupItem = new GroupItem();
//    ArrayList<ImageItem> mItems;

    public static RecyclerViewGridFragment newInstance() {
        return new RecyclerViewGridFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        init(view);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new AggroRecyclerGridViewAdapter(Registration.mItems,getActivity()));

        mRecyclerView.setAdapter(mAdapter);



        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }


    private void init(View root){
//         mItems = new ArrayList<>();
        FloatingActionButton pinkButton = (FloatingActionButton)root.findViewById(R.id.multiple_actions);
        pinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createCustomCategory();
            }
        });
    }

    private void createCustomCategory(){

        CustomCategoryDialog customCategoryDialog = CustomCategoryDialog.getInstance();
        customCategoryDialog.createDialog(getActivity(), new CustomCategoryDialog.OnSubmit() {
            @Override
            public void onCreateButtonClick(String nameOfCategory) {

                String name = nameOfCategory;
                Registration.mItems.add(new ImageItem(CUSTOM_CATEGORY_PREFIX + nameOfCategory, R.mipmap.games));
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Prepare some dummy data for gridview
     */
    /**
    private ArrayList<ImageItem> getData() {
//        final ArrayList<ImageItem> mItems = new ArrayList<>();
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_bussiness),R.mipmap.games));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_lifestyle),R.mipmap.games));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_news),   R.mipmap.news));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_education),R.mipmap.teaching));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_transporation),R.mipmap.games));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_productiity),R.mipmap.games));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_games),R.mipmap.games));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_travel),R.mipmap.games));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_sports), R.mipmap.sports));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_health), R.mipmap.health));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_entertainment), R.mipmap.entertainment));

        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_comics), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_communication), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_finance), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_media_video), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_medical), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_personilazation), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_photography), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_shopping), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_social), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_tool), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_wheather), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_lib_demo), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_arcade), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_puzzle), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_card), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_casual), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_racing), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_sport), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_action), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_adventure), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_board), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_casino), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_educational), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_family), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_music), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_role_playing), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_simulation), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_strategy), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_trivia), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_word), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_app_wallpaper), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_app_widget), R.mipmap.entertainment));
        return mItems;
    }
     */
}
