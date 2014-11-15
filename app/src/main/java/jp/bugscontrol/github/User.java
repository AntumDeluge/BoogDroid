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

package jp.bugscontrol.github;

import org.json.JSONObject;


public class User extends jp.bugscontrol.general.User {
    public User(final JSONObject json) {
        createFromJSON(json);
    }

    private void createFromJSON(final JSONObject json) {
        try {
            name = json.getString("login");
            email = "";
            avatarUrl = json.getString("avatar_url");
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
