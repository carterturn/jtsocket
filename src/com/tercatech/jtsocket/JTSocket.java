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

import java.lang.IllegalArgumentException;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.lang.StringBuilder;
import java.util.Arrays;
import java.lang.Math;

public abstract class JTSocket {
	protected final int JTSOCKET_DATASIZE = 255;

	protected Socket socket;
	private BufferedOutputStream writer;
	private BufferedInputStream reader;

	protected void create(Socket socket) throws IOException{
		if(socket == null){
			throw new IllegalArgumentException("Null Socket Not Acceptable for JTSocket");
		}
		this.socket = socket;

		writer = new BufferedOutputStream(socket.getOutputStream());
		reader = new BufferedInputStream(socket.getInputStream());
	}

	public String cRead() throws IOException{
		StringBuilder data  = new StringBuilder();
		boolean reading = true;

		while(reading){
			byte[] buffer = new byte[JTSOCKET_DATASIZE + 1];
			reader.read(buffer, 0, JTSOCKET_DATASIZE + 1);
			data.append(new String(Arrays.copyOfRange(buffer, 1, buffer.length)));
			if((char) buffer[0] < JTSOCKET_DATASIZE) reading = false;
		}
		return data.toString();
	}

	public void cWrite(String data) throws IOException{

		if(data.length() > JTSOCKET_DATASIZE){
			StringBuilder tmpData = new StringBuilder(data);
			while(tmpData.length() > 0){
				String temp = tmpData.substring(0, Math.min(JTSOCKET_DATASIZE, tmpData.length()));
				tmpData.delete(0, Math.min(JTSOCKET_DATASIZE, tmpData.length()));
				byte[] buffer = new byte[temp.length() + 1];
				buffer[0] = (byte) temp.length();
				System.arraycopy(temp.getBytes(), 0, buffer, 1, temp.getBytes().length);
				writer.write(buffer, 0, buffer.length);
				writer.flush();
			}
		}
		else{
			byte[] buffer = new byte[data.length() + 1];
			buffer[0] = (byte) data.length();
			System.arraycopy(data.getBytes(), 0, buffer, 1, data.getBytes().length);
			writer.write(buffer, 0, buffer.length);
			writer.flush();
		}
	}

	public void cClose() throws IOException{
		writer.close();
		reader.close();
		socket.close();
	}

}
