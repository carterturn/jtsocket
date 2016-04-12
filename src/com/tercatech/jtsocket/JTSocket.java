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
			byte[] trimmedBuffer = Arrays.copyOfRange(buffer, 1, ((int) (0xff & buffer[0])) + 1);
			for(byte b : trimmedBuffer) data.append((char) b);
			if((int) (0xff & buffer[0]) < JTSOCKET_DATASIZE) reading = false;
		}
		return data.toString();
	}

	public void cWrite(String data) throws IOException{

		if(data.length() >= JTSOCKET_DATASIZE){
			StringBuilder tmpData = new StringBuilder(data);
			while(tmpData.length() > 0){
				String temp = tmpData.substring(0, Math.min(JTSOCKET_DATASIZE, tmpData.length()));
				tmpData.delete(0, Math.min(JTSOCKET_DATASIZE, tmpData.length()));
				byte[] buffer = new byte[temp.length() + 1];
				buffer[0] = (byte) temp.length();
				char[] dataArray = temp.toCharArray();
				for(int i = 1; i < buffer.length; i++) buffer[i] = (byte) dataArray[i-1];
				writer.write(buffer, 0, buffer.length);
				writer.flush();
				if(tmpData.length() == 0 && temp.length() == JTSOCKET_DATASIZE) tmpData.append('\0');
			}
		}
		else{
			byte[] buffer = new byte[data.length() + 1];
			buffer[0] = (byte) data.length();
			char[] dataArray = data.toCharArray();
			for(int i = 1; i < buffer.length; i++) buffer[i] = (byte) dataArray[i-1];
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
