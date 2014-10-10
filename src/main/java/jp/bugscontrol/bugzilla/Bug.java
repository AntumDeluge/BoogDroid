/*
 *  BugsControl
 *  Copyright (C) 2013  Jon Ander Peñalba
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

package jp.bugscontrol.bugzilla;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.util.Util;
import jp.util.Util.TaskListener;


public class Bug extends jp.bugscontrol.general.Bug {
    public Bug(final jp.bugscontrol.general.Product product, final JSONObject json) {
        super(product);
        createFromJSON(json);
    }

    @Override
    protected void loadComments() {
        final Bug b = this;
        final List<jp.bugscontrol.general.Comment> newList = new ArrayList<jp.bugscontrol.general.Comment>();
        final BugzillaTask task = new BugzillaTask(product.getServer(), "Bug.comments", "'ids':[" + b.id + "]", new TaskListener() {
            @Override
            public void doInBackground(final String s) {
                try {
                    final JSONObject object = new JSONObject(s);
                    final JSONArray comments = object.getJSONObject("result").getJSONObject("bugs").getJSONObject(Integer.toString(b.id)).getJSONArray("comments");
                    for (int i = 0; i < comments.length(); ++i) {
                        if (i == 0) {
                            description = comments.getJSONObject(i).getString("text");
                        } else {
                            newList.add(new Comment(b, comments.getJSONObject(i)));
                        }
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPostExecute(final String s) {
                comments.clear();
                comments.addAll(newList);
                commentsListUpdated();
            }
        });
        task.execute();
    }

    private void createFromJSON(final JSONObject json) {
        try {
            id = json.getInt("id");
            summary = json.getString("summary");
            creationDate = Util.formatDate("yyyy-MM-dd'T'HH:mm:ss'Z'", json.getString("creation_time"));
            priority = json.getString("priority");
            status = json.getString("status");
            reporter = json.getString("creator");
            assignee = json.getString("assigned_to");
            open = TextUtils.isEmpty(json.getString("resolution"));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
