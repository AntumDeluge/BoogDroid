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
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import jp.bugscontrol.R;
import jp.bugscontrol.general.Server;


public class LeftMenuFragment extends ListFragment {
    private OnServerSelectedListener listener;
    private ServerTypeAdapter adapter;

    public interface OnServerSelectedListener {
        public void onServerSelected(final int position);
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        listener = (OnServerSelectedListener) activity;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.product_list_fragment, container, false);

        adapter = new ServerTypeAdapter(getActivity());
        setListAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(final ListView l, final View v, final int position, final long id) {
        listener.onServerSelected(position);
    }

    private class ServerTypeAdapter extends ArrayAdapter<Server> {
        public ServerTypeAdapter(final Context context) {
            super(context, R.layout.adapter_server, R.id.name, Server.servers);
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.adapter_server, parent, false);
                convertView.findViewById(R.id.delete_button).setVisibility(View.GONE);
            }

            final Server s = Server.servers.get(position);

            ((TextView) convertView.findViewById(R.id.name)).setText(s.getName());

            final ImageView iconImage = (ImageView) convertView.findViewById(R.id.icon);
            iconImage.setImageResource(Server.typeIcon.get(Server.typeName.indexOf(s.getType())));

            return convertView;
        }
    }
}
