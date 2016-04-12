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

package com.tercatech.jtsocket;

import java.lang.RuntimeException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;

public class JTServer extends JTSocket {
	protected ServerSocket serverSocket;
	protected final int port;
		
	public JTServer(int port){
		this.port = port;
	}

	public JTServer(){
		this.port = -1;
	}

	public void create() throws IOException{
		if(port == -1) throw new RuntimeException("Port not defined in constructor");
		serverSocket = new ServerSocket(port);
	}

	public void create(int port) throws IOException{
		serverSocket = new ServerSocket(port);
	}

	public void getConnection() throws IOException{
		super.create(serverSocket.accept());
	}

	public void serverClose() throws IOException{
		serverSocket.close();
	}
}
