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

package jp.bugscontrol.general;

import android.app.Activity;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import jp.bugscontrol.R;
import jp.bugscontrol.ui.AdapterProduct;

public abstract class Server {
    public static final String BUGZILLA = "Bugzilla";
    public static final String GITHUB = "GitHub";

    public static final int BUGZILLA_ICON = R.drawable.server_icon_bugzilla;
    public static final int GITHUB_ICON = R.drawable.server_icon_github;

    protected final List<Product> products = new ArrayList<Product>();
    protected final String type;
    protected String name;
    protected String url;
    protected String user;
    protected String password;

    private AdapterProduct adapter;
    private Activity activity;

    private jp.bugscontrol.db.Server databaseServer = null;

    public Server(final String name, final String url, final String type) {
        this.type = type;
        this.name = name;
        this.url = url;
        user = "";
        password = "";
    }

    public Server(final jp.bugscontrol.db.Server server) {
        databaseServer = server;
        type = server.type;
        name = server.name;
        url = server.url;
        user = server.user;
        password = server.password;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public void setUser(final String user, final String password) {
        this.user = user;
        this.password = password;
    }

    public void setAdapterProduct(final AdapterProduct adapter, final Activity activity) {
        this.adapter = adapter;
        this.activity = activity;

        activity.setProgressBarIndeterminateVisibility(true);
        loadProducts();
    }

    public Product getProductFromId(final int productId) {
        for (Product p : products) {
            if (p.getId() == productId) {
                return p;
            }
        }

        return null;
    }

    public Bug getBugFromId(final int bugId) {
        for (Product p : products) {
            for (Bug b : p.bugs) {
                if (b.getId() == bugId) {
                    return b;
                }
            }
        }

        return null;
    }

    public void save() {
        if (databaseServer == null) {
            databaseServer = new jp.bugscontrol.db.Server();
        }
        databaseServer.type = type;
        databaseServer.name = name;
        databaseServer.url = url;
        databaseServer.user = user;
        databaseServer.password = password;
        databaseServer.save();
    }

    public void delete() {
        if (databaseServer != null) {
            databaseServer.delete();
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public boolean hasUser() {
        return !TextUtils.isEmpty(user);
    }

    protected abstract void loadProducts();

    protected void productsListUpdated() {
        adapter.notifyDataSetChanged();
        activity.setProgressBarIndeterminateVisibility(false);
    }
}
