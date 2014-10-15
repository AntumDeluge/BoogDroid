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

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.widget.BaseAdapter;

import jp.bugscontrol.R;
import jp.bugscontrol.general.Server;

public class DialogDeleteServer extends DialogFragment {
    private Server server;
    private BaseAdapter adapter = null;

    public DialogDeleteServer setServer(final Server server) {
        this.server = server;
        return this;
    }

    public DialogDeleteServer setAdapter(final BaseAdapter adapter) {
        this.adapter = adapter;
        return this;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(Html.fromHtml(String.format(getString(R.string.delete_server_title), server.getName())));
        builder.setMessage(Html.fromHtml(String.format(getString(R.string.delete_server_description), server.getName())));
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {
                server.delete();
                Server.servers.remove(server);
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {
            }
        });

        return builder.create();
    }
}
