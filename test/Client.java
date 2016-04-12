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

import com.tercatech.jtsocket.JTClientSecure;
import java.lang.Exception;

public class Client{

	public static void main(String[] args){
		try{
			JTClientSecure client = new JTClientSecure("127.0.0.1", 4440, "asdfasdfasdfasdf");

			client.create();

			System.out.println(client.cRead());

			client.cWrite("This is some data	public static void main(String[] args){  Copyright 2016 Carter Turnbaugh  This file is part of Terca Java Sockets.  Terca Java Sockets is free software: you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation, either version 3 of the License, or  (at your option) any later version.  Terca Java Sockets is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  GNU General Public License for more details.  You should have received a copy of the GNU General Public License  along with Terca Java Sockets.  If not, see <http://www.gnu.org/licenses/>.");
			System.out.println(client.sRead());

			client.sWrite("	public static void main(String[] args){  Copyright 2016 Carter Turnbaugh  This file is part of Terca Java Sockets.  Terca Java Sockets is free software: you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation, either version 3 of the License, or  (at your option) any later version.  Terca Java Sockets is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  GNU General Public License for more details.  You should have received a copy of the GNU General Public License  along with Terca Java Sockets.  If not, see <http://www.gnu.org/licenses/>.");

			client.cClose();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
