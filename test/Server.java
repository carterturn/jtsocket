/*
  Copyright 2016 Carter Turnbaugh

  This file is part of Terca Java Sockets.

  Terca Java Sockets is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  Terca Java Sockets is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with Terca Java Sockets.  If not, see <http://www.gnu.org/licenses/>.
*/

import com.tercatech.jtsocket.JTServerSecure;
import java.lang.Exception;

public class Server{

	public static void main(String[] args){

		try{
			JTServerSecure server = new JTServerSecure(5552, "passwordpassword");
			server.create();

			server.getConnection();

			System.out.println(server.cRead());
			server.cWrite("This is some more dataaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

			System.out.println(server.sRead());
			server.sWrite("import com.tercatech.jtsocket.JTServer; public static void main(String[] args){}");

			server.cClose();
			server.serverClose();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
