package com.sxt.fragment;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sxt.activity.R;
import com.sxt.base.BaseFragment;
import com.sxt.model.ActionModel;

/**
 * 分类fragment
 * */
public class CategoryFragment extends BaseFragment implements android.view.View.OnClickListener{

	private View view;
	private ImageView mTabImg;
	private TextView mCenterTv,mLeftTv,mRightTv;
	private TextView mCLineTv,mLLineTv,mRLineTv;
	private ClassFragment classFragment;
	private BrandFragment brandFragment;
	private FollowFragment followFragment;
	private FragmentManager fm;

	@Override
	public View initView() {
		view = View.inflate(getActivity(), R.layout.fragment_category, null);
		view.setLayoutParams(
				new RelativeLayout.LayoutParams
				(RelativeLayout.LayoutParams.MATCH_PARENT,
						RelativeLayout.LayoutParams.MATCH_PARENT));
		fm = getFragmentManager();
		mTabImg = (ImageView) view.findViewById(R.id.CategoryFragment_TabMenuImg);
		mCenterTv = (TextView) view.findViewById(R.id.CategoryFragment_BrandTv);
		mLeftTv = (TextView) view.findViewById(R.id.CategoryFragment_ClassTv);
		mRightTv = (TextView) view.findViewById(R.id.CategoryFragment_AboutTv);
		mCLineTv = (TextView) view.findViewById(R.id.CategoryFragment_centerLine);
		mLLineTv = (TextView) view.findViewById(R.id.CategoryFragment_leftLine);
		mRLineTv = (TextView) view.findViewById(R.id.CategoryFragment_rightLine);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		selectFragment(R.id.CategoryFragment_ClassTv);
	}

	@Override
	protected void initList() {
		// TODO Auto-generated method stub
		super.initList();
	}

	@Override
	protected void setClick() {
		super.setClick();
		mTabImg.setOnClickListener(this);
		mCenterTv.setOnClickListener(this);
		mLeftTv.setOnClickListener(this);
		mRightTv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int ID = v.getId();
		switch(ID){
		case R.id.CategoryFragment_TabMenuImg:
			//向FrameActivity发送广播->更新slidingui状态
			Intent intent = new Intent();
			intent.setAction(ActionModel.FrameUiAction);
			getActivity().sendBroadcast(intent);
			break;
		case R.id.CategoryFragment_BrandTv:
			goneLine();
			goneFragment();
			selectFragment(ID);
			mCLineTv.setVisibility(View.VISIBLE);
			break;
		case R.id.CategoryFragment_ClassTv:
			goneLine();
			goneFragment();
			selectFragment(ID);
			mLLineTv.setVisibility(View.VISIBLE);
			break;
		case R.id.CategoryFragment_AboutTv:
			goneLine();
			goneFragment();
			selectFragment(ID);
			mRLineTv.setVisibility(View.VISIBLE);
			break;
		}
	}

	//隐藏所有文字底部线显示状态方法
	private void goneLine(){
		mCLineTv.setVisibility(View.INVISIBLE);
		mLLineTv.setVisibility(View.INVISIBLE);
		mRLineTv.setVisibility(View.INVISIBLE);
	}

	//隐藏所有子fragment方法
	private void goneFragment(){
		FragmentTransaction ft = fm.beginTransaction();
		if(classFragment!=null){
			ft.hide(classFragment);
		}
		if(brandFragment != null){
			ft.hide(brandFragment);
		}
		if(followFragment != null){
			ft.hide(followFragment);
		}
		ft.commit();
	}

	//切换子fragment方法
	private void selectFragment(int ID){
		FragmentTransaction ft = fm.beginTransaction();
		switch(ID){
		case R.id.CategoryFragment_ClassTv:
			if(classFragment == null){
				classFragment = new ClassFragment();
				ft.add(R.id.CategoryFragment_FragmentGroup, classFragment);
			}else{
				ft.show(classFragment);
			}
			break;
		case R.id.CategoryFragment_BrandTv:
			if(brandFragment == null){
				brandFragment = new BrandFragment();
				ft.add(R.id.CategoryFragment_FragmentGroup, brandFragment);
			}else{
				ft.show(brandFragment);
			}
			break;
		case R.id.CategoryFragment_AboutTv:
			if(followFragment == null){
				followFragment = new FollowFragment();
				ft.add(R.id.CategoryFragment_FragmentGroup, followFragment);
			}else{
				ft.show(followFragment);
			}
			break;
		}
		ft.commit();
	}
}
