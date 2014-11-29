/*
 *  BugsControl
 *  Copyright (C) 2014  Jon Ander Peñalba
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package jp.bugscontrol.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import jp.bugscontrol.R;
import jp.bugscontrol.general.Product;
import jp.bugscontrol.general.Server;


public class BugListFragment extends ListFragment {
    private OnBugSelectedListener listener;
    private AdapterBug adapter;

    public interface OnBugSelectedListener {
        public void onBugSelected(final int bugId);
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        listener = (OnBugSelectedListener) activity;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.product_list_fragment, container, false);
        final ActionBarActivity activity = (ActionBarActivity) getActivity();

        activity.setSupportProgressBarIndeterminateVisibility(true);

        final Bundle arguments = getArguments();
        final int serverPos;
        final int productId;
        if (arguments != null) {
            serverPos = arguments.getInt("server_position", -1);
            productId = arguments.getInt("product_id", -1);
        } else {
            serverPos = -1;
            productId = -1;
        }

        if (serverPos == -1 || productId == -1) {
            Toast.makeText(activity, R.string.invalid_product, Toast.LENGTH_SHORT).show();
            activity.onBackPressed();
            return view;
        }

        final Product product = Server.servers.get(serverPos).getProductFromId(productId);
        //activity.setTitle(product.getName());

        adapter = new AdapterBug(activity, product.getBugs());
        setListAdapter(adapter);
        product.setAdapterBug(adapter, activity);

        return view;
    }

    @Override
    public void onListItemClick(final ListView l, final View v, final int position, final long id) {
        listener.onBugSelected(adapter.getBugIdFromPosition(position));
    }
}
