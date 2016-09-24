package com.codefororlando.streettrees.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.codefororlando.streettrees.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a utility class that will iterate through a list of fragments,
 * replacing the specified container view with a fragment as it iterates.
 */
public class FragmentListPager {

    /**
     * We hold on to this to switch fragments in the container view
     */
    private FragmentManager mFragmentManager;

    /**
     * This is a list of the entry to create fragments from
     */
    private List<Entry> mEntryList;

    private List<PageFragment> mFragmentList;

    /**
     * This is the container view that will display our fragments
     */
    private int mContainerViewId;

    /**
     * This is the current position in the fragment list that we're displaying
     */
    private int mCurrentItem;

    /**
     * The default constructor.
     * @param fragmentManager used to switch between fragments in the container view.
     * @param containerViewId specifies the container that will hold our fragments.
     * @param fragmentList the list of fragments to switch between.
     */

    public FragmentListPager(FragmentManager fragmentManager, int containerViewId, List<Entry> fragmentList, Bundle args) {
        mFragmentManager = fragmentManager;
        mContainerViewId = containerViewId;
        mEntryList = fragmentList;
        mFragmentList = new ArrayList<>(mEntryList.size());
        setCurrentItem(0, args);
    }


    /**
     * This will switch to the next fragment in the list.
     * @return false if there is no next fragment.
     */
    public synchronized boolean nextFragment(Bundle args) {
        int nextItem = getCurrentItem() + 1;
        return setCurrentItem(nextItem, args);
    }

    /**
     * This will switch to the previous fragment in the list.
     * @return false if there is no previous fragment.
     */
    public synchronized boolean previousFragment() {
        int previousItem = getCurrentItem() - 1;
        return setCurrentItem(previousItem, null);
    }

    public synchronized PageFragment getCurrentFragment() {
        return mFragmentList.get(getCurrentItem());
    }
    /**
     * This is a helper function that will return the current position in
     * the list of fragments being displayed.
     * @return the position.
     */
    public synchronized int getCurrentItem() {
        return mCurrentItem;
    }

    /**
     * This will set the current fragment to display by selecting the
     * fragment at the specified position.
     * @param position the position of the fragment to display.
     * @return true if the position is valid.
     */
    public synchronized boolean setCurrentItem(int position, Bundle args) {
        if (0 <= position && position < mEntryList.size()) {
            //Log.h("Navigating to page " + position + "/" + (mEntryList.size() - 1));

            PageFragment fragment = null; // mFragmentList.get(position);
            if(position >= mFragmentList.size()) {
                Entry entry = mEntryList.get(position);
                Class<? extends PageFragment> fragmentClass = entry.getClassType();
                try {
                    fragment = fragmentClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                    return false;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return false;
                }
                fragment.setArguments(args);
                mFragmentList.add(position, fragment);
            } else {
                fragment = mFragmentList.get(position);
            }

            if (position < mCurrentItem) {
                mFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left,
                                R.anim.slide_out_right)
                        .replace(mContainerViewId, fragment)
                        .commitAllowingStateLoss();
            } else {
                mFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right,
                                R.anim.slide_out_left)
                        .replace(mContainerViewId, fragment)
                        .commitAllowingStateLoss();
            }

            mCurrentItem = position;
            return true;
        } else {
            return false;
        }
    }

    public static class Entry {
        private Class<? extends PageFragment> mClassType;

        public Entry(Class<? extends PageFragment> fragmentClass) {
            mClassType = fragmentClass;
        }

        public Class<? extends PageFragment> getClassType() {
            return mClassType;
        }
    }
}

